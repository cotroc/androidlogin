package com.cotroc.androidlogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SimpleUpdatableActivity {

    private AsyncRestClient asyncRestClient;
    private static final String TAG = "PrincipalActivity";
    private final String urlServer = "http://192.168.0.111:8080/login.service/ws";
    private ProgressDialog pDialog;

    private TextView status;
    private TextView serverStatus;
    private EditText et_name;
    private EditText et_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    @Override
    public void progress(String message) {
        pDialog = new ProgressDialog(this);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setTitle("Procesando");
        pDialog.setMessage(message);
        pDialog.setCancelable(true);
        pDialog.show();

        ProgressBar progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleSmall);
    }

    public void getAllUser(View v) {
        Log.i(TAG, "Enviando peticion");
        asyncRestClient = new AsyncRestClient(this);
        asyncRestClient.execute("listUser", urlServer);
    }

    public void createUser(View v) {
        Log.i(TAG, "Creating User");
        Intent intent = new Intent(this, CreateUserActivity.class);
        startActivity(intent);

    }

    public void verifyUser(View v) {
        asyncRestClient = new AsyncRestClient(this);
        UserDto userDto = new UserDto();
        userDto.setName(et_name.getText().toString());
        userDto.setPwd(et_pwd.getText().toString());
        String resu = userDto.toString();
        asyncRestClient.execute("verifyUser", urlServer + "/verify", userDto.toString());
    }

    private void initComponents() {
        status = (TextView) findViewById(R.id.status);
        serverStatus = (TextView) findViewById(R.id.serverStatus);
        et_name = (EditText) findViewById(R.id.et_user);
        et_pwd = (EditText) findViewById(R.id.et_pass);
        status.setText("Esperando");
        asyncRestClient = new AsyncRestClient(this);
        asyncRestClient.execute("serverStatus", urlServer + "/status");
    }

    @Override
    public void update(ArrayList<String> results) {
       String flag = results.get(0);
       pDialog.dismiss();
        switch(flag) {
            case "listUser":
                updateListUser(results.get(1));
                break;
            case "verifyUser":
                updateVerifyUser(results.get(1));
                break;
            case "serverStatus":
                serverStatus.setText(results.get(1));
            default: break;
        }
    }

    private void updateListUser(String listUser) {
        try {
            JSONArray jsonObject = new JSONArray(listUser);
            status.setText(jsonObject.length() + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateVerifyUser(String verifiedUser) {
        try {
            String resu = verifiedUser;
            JSONObject jsonObject = new JSONObject(verifiedUser);//verifiedUser
            UserDto userDto = this.jsonToUserObject(verifiedUser);
            if(userDto.getName().equals("null")) {
                status.setText("Usuario incorrecto o no existe");
            }else{
                if(userDto.isActive()){
                    status.setText("Bienvenido " + userDto.getName());
                    Intent intent = new Intent(this, LogedActivity.class);
                    intent.putExtra("logedUser", verifiedUser);
                    startActivity(intent);
                    //userPage : change password
                }else{
                    this.showToast("Activar usuario primero");
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private UserDto jsonToUserObject(String userJson) throws JSONException {
        JSONObject jsonObject = new JSONObject(userJson);//verifiedUser
        UserDto userDto = new UserDto();
        userDto.setId(jsonObject.getInt("id"));
        userDto.setName(jsonObject.getString("name"));
        userDto.setPwd(jsonObject.getString("pwd"));
        userDto.setMail(jsonObject.getString("mail"));
        userDto.setActive(jsonObject.getBoolean("active"));
        return userDto;
    }

    private void showToast(String message) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, message, duration);
        toast.show();
    }
}