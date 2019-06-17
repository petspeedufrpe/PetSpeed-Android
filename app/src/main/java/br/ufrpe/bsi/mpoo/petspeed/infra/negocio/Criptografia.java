package br.ufrpe.bsi.mpoo.petspeed.infra.negocio;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Criptografia {

    private static MessageDigest messageDigest =null;

    static {
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static char[] hexCodes(byte[] text){
        char[] hexOutput = new char[text.length * 2];
        String hexString;

        for (int i=0;i<text.length;i++){
            hexString = "00" + Integer.toHexString(text[i]);
            hexString.toUpperCase().getChars(hexString.length()-2,
                    hexString.length(),hexOutput,i*2);
        }

        return hexOutput;
    }

    public static String criptografar(String pwd){
        if (messageDigest!= null){
            return new String(hexCodes(messageDigest.digest(pwd.getBytes())));
        }

        return null;
    }
}
