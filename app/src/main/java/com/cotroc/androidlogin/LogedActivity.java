package com.cotroc.androidlogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class LogedActivity extends AppCompatActivity {

    TextView lbl_Welcome;
    TextView lbl_logedUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loged);
        Intent intent = getIntent();
        String logedUserJsonString = intent.getStringExtra("logedUser");
        initComponents();
        UserDto userDto = new UserDto();
        try {
            JSONObject jsonObject = new JSONObject(logedUserJsonString);
            userDto.setId(jsonObject.getInt("id"));
            userDto.setName(jsonObject.getString("name"));
            userDto.setPwd(jsonObject.getString("pwd"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        lbl_logedUser.setText(userDto.getName());
    }

    public void initComponents() {
        lbl_logedUser = (TextView) findViewById(R.id.lbl_logedUser);
        lbl_Welcome = (TextView) findViewById(R.id.lbl_welcome);
    }
}
