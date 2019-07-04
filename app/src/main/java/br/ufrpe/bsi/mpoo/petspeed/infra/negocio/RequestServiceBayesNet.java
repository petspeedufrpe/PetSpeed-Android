package br.ufrpe.bsi.mpoo.petspeed.infra.negocio;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class RequestServiceBayesNet {


    private ArrayList<Sintomas> sintomas;
    private Context context;

    RequestServiceBayesNet(Context context, ArrayList<Sintomas> sintomasArrayList) {
        this.context = context;
        this.sintomas = sintomasArrayList;
    }


    public void getDisesaseProb() {
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://localhost:8080/rest-petspeed/rest/bayesnetwork/getprobdata").build();

                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    return response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                Toast.makeText(context, o.toString(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    public String setSymptoms(String json) {
        String jsonResp = "";
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        builder.url("http://localhost:8080/rest-petspeed/rest/bayesnetwork/getprobdata");
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType, json);
        builder.post(requestBody);

        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            jsonResp = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonResp;
    }

    private void toJSON() {
        final String JSON_ARRAY_NAME = "symptomsList";
        //logar();
        JSONObject postData = new JSONObject();
        try {
            JSONObject object = new JSONObject();
            JSONArray array = new JSONArray();
            for (int i = 0; i < sintomas.size(); i++) {
                array.put(sintomas.get(i));
            }
            object.put(JSON_ARRAY_NAME, array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
