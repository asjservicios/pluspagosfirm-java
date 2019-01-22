package com.pluspagos;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

public class AESEncrypter {

    public static String encryptString(String plainText, String phrase) {
        try {
            while (phrase.length() < 32) {
                phrase = phrase + phrase;
            }
            phrase = phrase.substring(0, 32);

            byte[] ba = phrase.getBytes("utf-8");

            Security.setProperty("crypto.policy", "unlimited");

            SecretKey key = new SecretKeySpec(ba, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] iv = cipher.getIV();

            byte[] encrypted = cipher.doFinal(plainText.getBytes("utf-8"));

            byte[] combinedIvCt = new byte[iv.length + encrypted.length];
            System.arraycopy(iv, 0, combinedIvCt, 0, iv.length);
            System.arraycopy(encrypted, 0, combinedIvCt, iv.length, encrypted.length);

            return com.pluspago.Base64.encodeToString(combinedIvCt, com.pluspago.Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String decryptString(String plainText, String phrase) {
        try {
            while (phrase.length() < 32) {
                phrase = phrase + phrase;
            }
            phrase = phrase.substring(0, 32);

            byte[] ba = phrase.getBytes("utf-8");
            Security.setProperty("crypto.policy", "unlimited");

            SecretKey key = new SecretKeySpec(ba, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            byte[] cipherTextCombined = com.pluspago.Base64.decode(plainText, 0);
            byte[] iv = new byte[cipher.getBlockSize()];
            byte[] cipherText = new byte[cipherTextCombined.length - iv.length];
            System.arraycopy(cipherTextCombined, 0, iv, 0, iv.length);
            System.arraycopy(cipherTextCombined, iv.length, cipherText, 0, cipherText.length);

            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));

            byte[] result = cipher.doFinal(cipherText);
            return new String(result, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
