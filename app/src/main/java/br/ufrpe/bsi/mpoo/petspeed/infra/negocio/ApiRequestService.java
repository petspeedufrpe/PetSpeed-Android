package br.ufrpe.bsi.mpoo.petspeed.infra.negocio;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.ufrpe.bsi.mpoo.petspeed.infra.app.PetSpeedApp;

public class ApiRequestService {


    public void geocodeRequest(final String address, final GeocodeRequestCallbackListener listener) {

        String url = getGeocodeUrl(address);

        Cache cache = new DiskBasedCache(PetSpeedApp.getContext().getCacheDir(), 1024 * 1024); // 1MB cap

        Network network = new BasicNetwork(new HurlStack());

        RequestQueue mQueue = new RequestQueue(cache, network);

        mQueue.start();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Map<Enum, Object> latLng = new HashMap<Enum, Object>();

                    JSONArray jsonResults = response.getJSONArray("results");
                    JSONObject jsonData = jsonResults.getJSONObject(0);
                    JSONObject jsonGeometry = jsonData.getJSONObject("geometry");
                    JSONObject jsonLocation = jsonGeometry.getJSONObject("location");

                    Object jsonFormatterAddress = jsonData.get("formatted_address");
                    String address = jsonFormatterAddress.toString();

                    double latitude = jsonLocation.getDouble(ApiRequestService.GeoCodeCoord.LAT.getString());
                    double longitude = jsonLocation.getDouble(ApiRequestService.GeoCodeCoord.LNG.getString());

                    latLng.put(GeoCodeCoord.RESULT, GeoCodeCoord.SUCCESS);
                    latLng.put(GeoCodeCoord.LAT, latitude);
                    latLng.put(GeoCodeCoord.LNG, longitude);
                    latLng.put(GeoCodeCoord.ADDRESS, address);

                    listener.onGeocodeCallback(latLng);

                } catch (JSONException e) {
                    Map<Enum, Object> latLng = new HashMap<Enum, Object>();
                    latLng.put(GeoCodeCoord.RESULT, GeoCodeCoord.FAIL);
                    listener.onGeocodeCallback(latLng);
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

    private String getGeocodeUrl(String address) {
        return "https://maps.googleapis.com/maps/api/geocode/json?address=" + address +
                    "&key=" + getApiKey();
    }

    private String getApiKey() {
        String key = "AIzaSyBhntuU8NDLx8ZoIIfxnNRXaziGPvtEB6s";
        return key;
    }

    public enum GeoCodeCoord {
        LAT("lat"), LNG("lng"), RESULT("result"), ADDRESS("endereco"),
        ZERO_RESULTS("ZERO_RESULTS"), SUCCESS("success"),
        FAIL("fail");
        private final String descricao;

        GeoCodeCoord(String descricao) {
            this.descricao = descricao;
        }

        public String getString() {
            return descricao;
        }

        @Override
        public String toString() {
            return this.descricao;
        }
    }
}
