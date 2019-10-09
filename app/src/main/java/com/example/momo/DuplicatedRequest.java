package com.example.momo;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DuplicatedRequest extends StringRequest {
    final static private String URL = "http://khsung0.dothome.co.kr/DupleID.php ";
    private Map<String, String> parameters;
    public DuplicatedRequest(String userID, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);

    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}