package com.cotroc.androidlogin;

/**
 * Created by Cotroc on 22/08/2017.
 */

import android.os.AsyncTask;
import java.util.ArrayList;

public class AsyncRestClient extends AsyncTask<String, Void, ArrayList<String>> {
    private SimpleUpdatableActivity simpleUpdatableActivity;
    private static final String TAG = "AsyncRestClient";

    public AsyncRestClient(SimpleUpdatableActivity simpleUpdatableActivity) {
        this.simpleUpdatableActivity = simpleUpdatableActivity;
    }

    @Override
    protected ArrayList<String> doInBackground(String... data) {
        String flag = data[0];
        ArrayList<String> result = new ArrayList<>();
        switch(flag) {

            case "listUser":
                String listUser = getAllUsers(data[1]);
                result.add(0, flag);
                result.add(1, listUser);
                break;
            case "verifyUser":
                String userVerified = verifyUser(data[1], data[2]);
                result.add(0, flag);
                result.add(1, userVerified);
                break;
            case "createUser":

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

    protected String getAllUsers(String url) {
        String list;
            HttpUrlConnectionClient httpUrlConnectionClient = new HttpUrlConnectionClient();
        list = httpUrlConnectionClient.sendGet(url);
        return list;
    }

    protected String verifyUser(String... params) {
        String result;
            HttpUrlConnectionClient httpUrlConnectionClient = new HttpUrlConnectionClient();
        result = httpUrlConnectionClient.sendPost(params[0], params[1]);
        return result;
    }

 }