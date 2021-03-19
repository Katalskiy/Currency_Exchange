package com.example.currency_exchange;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonAdapter extends ArrayAdapter<JSONObject> {

    Context context;
    int listLayout;
    ArrayList<JSONObject> list;

    public JsonAdapter (Context context, int listLayout, int field, ArrayList<JSONObject> list) {
        super(context, listLayout, field, list);
        this.context = context;
        this.list = list;
        this.listLayout = listLayout;
    }

    public View getView (int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View itemView = inflater.inflate(listLayout, null, false);
        TextView charCode = itemView.findViewById(R.id.textCharCode);
        TextView name = itemView.findViewById(R.id.textName);
        TextView value = itemView.findViewById(R.id.textValue);
        Currency c = new Currency();
        charCode.setText(c.getCharCode());
        name.setText(c.getName());
        value.setText(c.getValue());
        return itemView;
    }
}
