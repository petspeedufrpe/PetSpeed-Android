package br.ufrpe.bsi.mpoo.petSpeed.infra.negocio;

public interface GeocodeRequestCallbackListener<T> {
    void onGeocodeCallback(T response);
}