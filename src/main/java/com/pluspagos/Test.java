package com.pluspagos;

import com.pluspagos.AESEncrypter;

public class Test {

    public  static void main(String[] arg){
        String secretKey = "BilleteraPlusPagos_bc320b82-d1fc-4459-89d1-a6695621ceec";
        String texto = "2300";

        String result = AESEncrypter.encryptString(texto,secretKey);

        System.out.println("text: " + result);

        String desencriptadoo = AESEncrypter.decryptString(result,secretKey);
        System.out.println("desencrriptado: " + desencriptadoo);
    }
}
