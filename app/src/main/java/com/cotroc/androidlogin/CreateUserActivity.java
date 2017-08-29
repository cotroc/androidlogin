package com.cotroc.androidlogin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class CreateUserActivity extends AppCompatActivity implements SimpleUpdatableActivity{

    private AsyncRestClient asyncRestClient;
    private static final String TAG = "NewUserActivity";
    private final String urlServer = "http://192.168.0.109:8080/login.service/api/ws";
    EditText user;
    EditText pass;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        initComponents();

    }

    public void initComponents(){
        user = (EditText) findViewById(R.id.et_user);
        pass = (EditText) findViewById(R.id.et_pass);
        button = (Button) findViewById(R.id.btn_verify);
    }

    public void createUser(View v) {
        UserDto userDto = new UserDto(user.getText().toString(), pass.getText().toString());
        asyncRestClient = new AsyncRestClient(this);
        asyncRestClient.execute("verifyUser", urlServer + "/create", userDto.toString()); //shared flag "verifyUser" MainActivity

    }

    @Override
    public void update(ArrayList<String> results) {
        int size = results.size();
    }
}
