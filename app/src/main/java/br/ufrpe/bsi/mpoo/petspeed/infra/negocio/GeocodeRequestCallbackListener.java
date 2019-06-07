package br.ufrpe.bsi.mpoo.petspeed.infra.negocio;

public interface GeocodeRequestCallbackListener<T> {
    void onGeocodeCallback(T response);
}