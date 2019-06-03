package br.ufrpe.bsi.mpoo.petSpeed.infra.negocio;

import com.google.android.gms.maps.model.LatLng;

public class ConvertLatLngToKm {

    private final double R = 6371000;
    public  double convert(LatLng latLngOrigin, LatLng latLngDestination) {
        double a1 = Math.toRadians(latLngOrigin.latitude);
        double a2 = Math.toRadians(latLngDestination.latitude);
        double delta = Math.toRadians(latLngDestination.latitude - latLngOrigin.latitude);
        double theta = Math.toRadians(latLngDestination.longitude - latLngOrigin.longitude);

        double a = Math.sin(delta/2) * Math.sin(delta/2) +
                Math.cos(a1) * Math.cos(a2)*
                        Math.sin(theta/2) * Math.sin(theta/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return c;


    }
}