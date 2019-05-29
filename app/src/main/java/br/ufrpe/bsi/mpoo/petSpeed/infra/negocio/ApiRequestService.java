package br.ufrpe.bsi.mpoo.petSpeed.infra.negocio;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;

import br.ufrpe.bsi.mpoo.petSpeed.infra.app.PetSpeedApp;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Endereco;

public class ApiRequestService {

    public interface RequestCallback{
        void onCallback();
    }

    public void geocodeReq(String urlRequest, final RequestCallback callback) {

        final ArrayList<Double>latlong = new ArrayList<>();

        Cache cache = new DiskBasedCache(PetSpeedApp.getContext().getCacheDir(), 1024 * 1024); // 1MB cap

        Network network = new BasicNetwork(new HurlStack());

        RequestQueue mQueue = new RequestQueue(cache, network);

        mQueue.start();

        String url = urlRequest;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonResults = response.getJSONArray("results");
                    JSONObject jsonData = jsonResults.getJSONObject(0);
                    JSONObject jsonGeometry = jsonData.getJSONObject("geometry");
                    JSONObject jsonLocation = jsonGeometry.getJSONObject("location");
                    Object jsonFormatterAddress = jsonData.get("formatted_address");
                    String address = jsonFormatterAddress.toString();
                    double latitude = jsonLocation.getDouble(geoCodeCoord.LAT.getStr());
                    double longitude = jsonLocation.getDouble(geoCodeCoord.LNG.getStr());

                    Sessao.instance.setValue(geoCodeCoord.ADDRESS.getStr(),address);
                    Sessao.instance.setValue(geoCodeCoord.LAT.getStr(),latitude);
                    Sessao.instance.setValue(geoCodeCoord.LNG.getStr(),longitude);


                    callback.onCallback();//Fazer uma sessão para inserir num map as infos de lat long

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

    public enum geoCodeCoord {
        LAT("lat"), LNG("lng"),ADDRESS("endereco");
        private final String descricao;
        geoCodeCoord(String descricao) {
            this.descricao = descricao;
        }
        public String getStr() {
            return descricao;
        }
        @Override
        public String toString() {
            return this.descricao;
        }
    }

}
