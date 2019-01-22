package com.pluspago.model;

import java.util.List;

import com.pluspago.AESEncrypter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class FormularioModel {

    private String hash;
    private String callbackSuccess;
    private String callbackCancel;
    private String comercio;
    private String sucursalComercio = "";
    private String transaccionComercioId;
    private double monto;
    private List<String> productos = new ArrayList<String>();
    private String secretKey;

    public static class Builder {
        private FormularioModel pb = new FormularioModel();

        public Builder(String secretKey) {
            pb.secretKey = secretKey;
        }

        public Builder hash(String hash) {
            pb.hash = hash;
            return this;
        }
        public Builder callbackSuccess(String callbackSuccess) {
            pb.callbackSuccess = callbackSuccess;
            return this;
        }
        public Builder callbackCancel(String callbackCancel) {
            pb.callbackCancel = callbackCancel;
            return this;
        }
        public Builder comercio(String comercio) {
            pb.comercio = comercio;
            return this;
        }
        public Builder sucursal(String sucursal) {
            pb.sucursalComercio = sucursal;
            return this;
        }
        public Builder transaccionId(String transaccionId) {
            pb.transaccionComercioId = transaccionId;
            return this;
        }
        public Builder monto(double monto) {
            pb.monto = monto;
            return this;
        }
        public Builder addProducto(String producto) {
            pb.productos.add(producto);
            return this;
        }
        public FormularioModel build() {
            return pb;
        }
    }

    public String toUrlEncodedForm() {
        char fieldSep = '&';
        char valueSep = '=';

        try {
            String result = "";
            result += "Hash" + valueSep + URLEncoder.encode(hash, "utf-8") + fieldSep;
            result += "TransaccionComercioId" + valueSep + URLEncoder.encode(transaccionComercioId, "utf-8") + fieldSep;
            result += "COMERCIO" + valueSep + URLEncoder.encode(comercio, "utf-8") + fieldSep;

            for (int p = 0; p < productos.size(); p++) {
                result += "Producto[" + p + "]" + valueSep + URLEncoder.encode(productos.get(p), "utf-8") + fieldSep;
            }

            result += "Monto" + valueSep + URLEncoder.encode(AESEncrypter.encryptString(Integer.toString((int) Math.round(monto * 100)), secretKey), "utf-8") + fieldSep;
            result += "SucursalComercio" + valueSep + URLEncoder.encode(AESEncrypter.encryptString(sucursalComercio, secretKey), "utf-8") + fieldSep;
            result += "CallbackCancel" + valueSep + URLEncoder.encode(AESEncrypter.encryptString(callbackCancel, secretKey), "utf-8") + fieldSep;
            result += "CallbackSuccess" + valueSep + URLEncoder.encode(AESEncrypter.encryptString(callbackSuccess, secretKey), "utf-8");

            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
