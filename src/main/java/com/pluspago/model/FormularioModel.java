package com.pluspago.model;

import java.util.List;

public class FormularioModel {
    public String callback;
    public String comercio;
    public String sucursalComercio;
    public String transaccionComercioId;
    public String monto;
    public List<String> producto;
    public String firma;

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getComercio() {
        return comercio;
    }

    public void setComercio(String comercio) {
        this.comercio = comercio;
    }

    public String getSucursalComercio() {
        return sucursalComercio;
    }

    public void setSucursalComercio(String sucursalComercio) {
        this.sucursalComercio = sucursalComercio;
    }

    public String getTransaccionComercioId() {
        return transaccionComercioId;
    }

    public void setTransaccionComercioId(String transaccionComercioId) {
        this.transaccionComercioId = transaccionComercioId;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public List<String> getProducto() {
        return producto;
    }

    public void setProducto(List<String> producto) {
        this.producto = producto;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }
}
