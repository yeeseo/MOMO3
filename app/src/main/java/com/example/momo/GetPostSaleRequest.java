package com.example.momo;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GetPostSaleRequest extends StringRequest {
    final static private String URL = "http://khsung0.dothome.co.kr/GetPostSale.php ";
    private Map<String, String> parameters;
    public GetPostSaleRequest(String id, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("ID", id);
    }
    @Override
    public Map<String,String> getParams(){
        return parameters;
    }
}


