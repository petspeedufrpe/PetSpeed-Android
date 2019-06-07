package br.ufrpe.bsi.mpoo.petspeed.infra.negocio;

public class AppException extends Exception {
    public AppException(String msg) {
        super(msg);
    }

    public AppException(String msg, Throwable cause) {
        super(msg, cause);
    }
}


