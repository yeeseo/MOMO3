package com.example.momo;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class EditInfoRequest extends StringRequest {
    final static private String URL = "http://khsung0.dothome.co.kr/EditInfo.php ";
    private Map<String, String> parameters;
    public EditInfoRequest(String userID, String userEmail, String userName, String userPassword, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userEmail", userEmail);
        parameters.put("userName", userName);
        parameters.put("userPassword", userPassword);
    }
    @Override
    public Map<String,String> getParams(){
        return parameters;
    }
}
