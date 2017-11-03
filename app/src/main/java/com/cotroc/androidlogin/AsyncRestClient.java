package com.cotroc.androidlogin;

import android.os.AsyncTask;
import java.util.ArrayList;

public class AsyncRestClient extends AsyncTask<String, String, ArrayList<String>> {
    private SimpleUpdatableActivity simpleUpdatableActivity;
    private static final String TAG = "AsyncRestClient";

    public AsyncRestClient(SimpleUpdatableActivity simpleUpdatableActivity) {
        this.simpleUpdatableActivity = simpleUpdatableActivity;
    }

    @Override
    protected ArrayList<String> doInBackground(String... data) {
        String flag = data[0];
        ArrayList<String> result = new ArrayList<>();
        publishProgress("Consultando Servidor");
        switch(flag) {
            case "listUser":
                String listUser = getRequest(data[1]);
                result.add(0, flag);
                result.add(1, listUser);
                break;
            case "verifyUser":
                String userVerified = postRequest(data[1], data[2]);
                result.add(0, flag);
                result.add(1, userVerified);
                break;
            case "existUserName":
                String userExist = postRequest(data[1], data[2]);
                result.add(0, flag);
                result.add(1, userExist);
                break;
            case "createUser":
                String createUser = postRequest(data[1], data[2]);
                result.add(0, flag);
                result.add(1, createUser);
                break;
            case "serverStatus":
                String serverStatus = getRequest(data[1]);
                result.add(0, flag);
                result.add(1, serverStatus);
                break;
            case "userActivation":
                String userActivation = getRequest(data[1]);
                result.add(0, flag);
                result.add(1, userActivation);
                break;
            default: break;
        }
        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<String> result) {
        if (null != result) {
            this.simpleUpdatableActivity.update(result);
        }
    }

    @Override
    protected void onProgressUpdate(String... value){
        simpleUpdatableActivity.progress(value[0]);
    }

    protected String getRequest(String url) {
        String list;
            HttpUrlConnectionClient httpUrlConnectionClient = new HttpUrlConnectionClient();
        list = httpUrlConnectionClient.sendGet(url);
        return list;
    }

    protected String postRequest(String... params) {
        String result;
            HttpUrlConnectionClient httpUrlConnectionClient = new HttpUrlConnectionClient();
        result = httpUrlConnectionClient.sendPost(params[0], params[1]);
        return result;
    }
 }