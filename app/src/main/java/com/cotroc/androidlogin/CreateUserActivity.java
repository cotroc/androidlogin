package com.cotroc.androidlogin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CreateUserActivity extends AppCompatActivity implements SimpleUpdatableActivity{

    private AsyncRestClient asyncRestClient;
    private static final String TAG = "NewUserActivity";
    private final String urlServer = "http://192.168.0.103:8080/login.service/api/ws";
    private String activationCode = null;
    private JSONObject createdUser = null;
    EditText user, pass, confirmPass, mail, et_code;
    TextView status;
    Button verifUserName;
    Button btn_aceptar, btn_send_code;
    boolean exist = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        initComponents();
        btn_aceptar.setEnabled(false);
    }

    public void initComponents(){
        user = (EditText) findViewById(R.id.et_user);
        pass = (EditText) findViewById(R.id.et_pass);
        confirmPass = (EditText) findViewById(R.id.et_confirm_pass);
        mail = (EditText) findViewById(R.id.et_mail);
        et_code = (EditText) findViewById(R.id.et_code);
        status = (TextView) findViewById(R.id.status);
        verifUserName = (Button) findViewById(R.id.btn_exist);
        btn_aceptar = (Button) findViewById(R.id.btn_aceptar);
        btn_send_code = (Button) findViewById(R.id.btn_send_code);
    }

    public void createUser(View v) {
        try {
            if(pass.getText().toString().equals(confirmPass.getText().toString())) {
                UserDto userDto = new UserDto(user.getText().toString(), pass.getText().toString());
                userDto.setMail(mail.getText().toString());
                createdUser = new JSONObject(userDto.toString());
                asyncRestClient = new AsyncRestClient(this);
                asyncRestClient.execute("createUser", urlServer + "/create", userDto.toString());
            } else {
                toast("Password no coincide");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void existUserName(View v) {
        String jsonString = "{\"name\":\"" + user.getText().toString() + "\"}";
        asyncRestClient = new AsyncRestClient(this);
        asyncRestClient.execute("existUserName", urlServer + "/exist", jsonString);
    }

    public void checkActivationCode(View v) {
        String set_code = String.valueOf(et_code.getText());
        String sactivationCode = activationCode;
        if(et_code.getText().equals(activationCode)) {
            asyncRestClient = new AsyncRestClient(this);
            asyncRestClient.execute("userActivation", urlServer + "/activation", createdUser.toString());
        }
    }

    @Override
    public void update(ArrayList<String> results) {
        String flag = results.get(0);
        switch(flag) {
            case "existUserName":
                if(results.get(1).equals("true")) {
                    toast("Nombre invalido");
                    exist = true;
                }else{
                    toast("Nombre disponible");
                    exist = false;
                    btn_aceptar.setEnabled(true);
                }
                break;
            case "createUser":
                try {
                    createdUser = new JSONObject(results.get(1));
                    status.setText(createdUser.get("status").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            default: break;
        }
    }

    public void toast(String message) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, message, duration);
        toast.show();
    }
}