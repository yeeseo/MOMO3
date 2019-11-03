package com.example.momo;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UploadPostRequest extends StringRequest {
    final static private String URL = "http://khsung0.dothome.co.kr/UploadPost.php ";
    private Map<String, String> parameters;
    public UploadPostRequest(String userID, String title, String start_date, String end_date, String content, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("start_date", start_date);
        parameters.put("end_date", end_date);
        parameters.put("title", title);
        parameters.put("content", content);
    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}