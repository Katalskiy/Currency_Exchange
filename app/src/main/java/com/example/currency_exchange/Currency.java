package com.example.currency_exchange;

import org.json.JSONObject;

public class Currency extends JSONObject {
    private String charCode = "charCode";
    private String value = "value";
    private String name = "name";

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
