package br.ufrpe.bsi.mpoo.petspeed.infra.negocio;

public class Haversine {
    private static final double RAIO_TERRA = 6372.8;

    public static double haversine(double latOrigem, double lngOrigem, double latDestino, double lngDestino) {
        double dLat = Math.toRadians(latDestino - latOrigem);
        double dLon = Math.toRadians(lngDestino - lngOrigem);

        latOrigem = Math.toRadians(latOrigem);
        latDestino = Math.toRadians(latDestino);

        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(latOrigem) * Math.cos(latDestino);
        double c = 2 * Math.asin(Math.sqrt(a));
        return RAIO_TERRA * c;
    }
}