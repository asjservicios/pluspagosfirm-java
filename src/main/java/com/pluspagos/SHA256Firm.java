package com.pluspagos;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Firm {

    public static final Charset UTF_8 = Charset.forName("UTF-8");

    public String getFirm(String ipClient, String secretKey, String guidComercio, String sucursalId, String monto) throws NoSuchAlgorithmException {

        String input = (ipClient+"*"+ guidComercio+"*"+sucursalId+"*"+monto + "*" + secretKey);
        MessageDigest provider = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = provider.digest(input.getBytes(UTF_8));

        StringBuilder output = new StringBuilder(2 * hashedBytes.length);
        for (byte b : hashedBytes) {
            output.append(String.format("%02X", b).toLowerCase());
        }
        return output.toString();
    }
}

