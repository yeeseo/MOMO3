package com.example.momo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ManageActivity extends AppCompatActivity {
    private ListView listView;
    private UserListAdapter adapter;
    private List<User> userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        listView=findViewById(R.id.ListView);
        userList = new ArrayList<User>();

        Intent intent = getIntent();
        adapter = new UserListAdapter(getApplicationContext(),userList,this);
        listView.setAdapter(adapter);
        try {
            Log.d("intent@@@@@@@@@@@@@@@", intent.getStringExtra("userList"));
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("userList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String userID,userPassword,userName,userEmail;
            while(count<jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);
                userID = object.getString("userID");
                userPassword = object.getString("userPassword");
                userName = object.getString("userName");
                userEmail = object.getString("userEmail");
                User user = new User(userID,userPassword,userName,userEmail);
                if(!userID.equals("admin"))
                    userList.add(user);
                count++;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
