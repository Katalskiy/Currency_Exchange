package com.example.currency_exchange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Currency currencyItem = new Currency();
    public List<Currency> currencyList = new ArrayList<>();

    private static final String JSON_URL = "https://www.cbr-xml-daily.ru/daily_json.js";
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        loanJSONFromUrl(JSON_URL);
    }

    private void loanJSONFromUrl (String url) {
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(ListView.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
        new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(ListView.INVISIBLE);
                try {
                    JSONObject object = new JSONObject(EncodingToUTF8(response));
                    JSONObject jsonValute = object.getJSONObject("Valute");
                    Iterator<String> arrayKey = jsonValute.keys();
                    ArrayList<JSONObject> listItems = new ArrayList<JSONObject>() ;
                    while (arrayKey.hasNext()){
                        String key = arrayKey.next();
                        JSONObject jsonItems = jsonValute.getJSONObject(key);
                        currencyItem.setCharCode(jsonItems.getString("CharCode"));
                        currencyItem.setName(jsonItems.getString("Name"));
                        currencyItem.setValue(jsonItems.getString("Value"));
                        listItems.add(currencyItem);
                    }
                    
                    JsonAdapter adapter = new JsonAdapter(getApplication(), R.layout.row, R.id.textCharCode, listItems);
                    listView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
        new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private ArrayList<JSONObject> getArrayListFromJsonArray (JSONArray jsonArray) {
        ArrayList<JSONObject> listJson = new ArrayList<JSONObject>();
        try {
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    listJson.add(jsonArray.getJSONObject(i));
                }
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return listJson;
    }

    public  static  String EncodingToUTF8(String response){
        try {
            byte[] code = response.toString().getBytes("ISO-8859-1");
            response = new String(code, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        return response;
    }
}