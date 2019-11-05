package com.example.momo;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;

public class UserListAdapter extends BaseAdapter {
    private Context context;
    private List<User> userList;
    private Activity parentActivity;
    public UserListAdapter(Context context, List<User> userList, Activity parentActivity){
        this.context=context;
        this.userList = userList;
        this.parentActivity = parentActivity;
    }
    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v=View.inflate(context, R.layout.user,null);
        final TextView userID = v.findViewById(R.id.userID);
        final TextView userPassword = v.findViewById(R.id.userPassword);
        TextView userName = v.findViewById(R.id.userName);
        TextView userEmail = v.findViewById(R.id.userEmail);
        userID.setText(userList.get(i).getUserID());
        userPassword.setText(userList.get(i).getUserPassword());
        userName.setText(userList.get(i).getUserName());
        userEmail.setText(userList.get(i).getUserEmail());

        v.setTag(userList.get(i).getUserID());

        Button deleteBtn = v.findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try{
                            Log.d("response@@@@@@", response);
                            JSONObject jsonResponse = new JSONObject(response);

                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                userList.remove(i);
                                notifyDataSetChanged();
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                DeleteRequest deleteRequest= new DeleteRequest(userID.getText().toString(),responseListener);
                RequestQueue queue = Volley.newRequestQueue(parentActivity);
                queue.add(deleteRequest);
            }

        });

        return v;
    }

}