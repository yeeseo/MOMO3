package com.example.momo;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ReplyRequest extends StringRequest {
    final static private String URL = "http://khsung0.dothome.co.kr/UploadReply.php ";
    private Map<String, String> parameters;
    public ReplyRequest(String BID, String userID, String content, String create_at, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("BID", BID);
        parameters.put("userID", userID);
        parameters.put("content", content);
        parameters.put("create_at", create_at);
    }
    @Override
    public Map<String,String> getParams(){
        return parameters;
    }
}