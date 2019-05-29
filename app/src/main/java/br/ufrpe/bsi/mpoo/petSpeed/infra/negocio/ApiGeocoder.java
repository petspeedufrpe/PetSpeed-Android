package br.ufrpe.bsi.mpoo.petSpeed.infra.negocio;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufrpe.bsi.mpoo.petSpeed.infra.app.PetSpeedApp;

public class ApiGeocoder {
    public ApiGeocoder(Context context){

    }
    Geocoder geocoder = new Geocoder(PetSpeedApp.getContext());


    public LatLng getLatLgnLocation(String location) throws IOException {
        location = String.valueOf(50050000);
        List<Address> list = geocoder.getFromLocationName(location,1);
        Address address = list.get(0);
        address.getThoroughfare();
        Double lat = (Double) address.getLatitude();
        Double lgn = (Double) address.getLongitude();
        LatLng latLng = new LatLng(lat,lgn);
        return latLng;
    }
    public Map<String, Double> getPositions(String location) throws IOException {//metodo que retorna a lat e lgn do usuario, para persistir no banco
        List<Address> list = geocoder.getFromLocationName(location,1);
        Address address = list.get(0);
        Double lat = address.getLatitude();
        Double lgn = address.getLongitude();
        Map<String,Double> listPositions = new HashMap<String ,Double>();
        listPositions.put(GeocodeLatLng.LAT.getStr(),lat);
        listPositions.put(GeocodeLatLng.LNG.getStr(),lgn);
        return listPositions;
    }

    public enum GeocodeLatLng {
        LAT("lat"), LNG("lng"),ADDRESS("endereco");
        private final String descricao;
        GeocodeLatLng(String descricao) {
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
