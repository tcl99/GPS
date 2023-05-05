package com.example.gps;

public class Usuario {
    private String uid;
    private boolean guia;

    public Usuario (){}

    public Usuario(boolean guia, String uid) {
        this.uid = uid;
        this.guia = guia;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean isGuia() {
        return guia;
    }

    public void setGuia(boolean guia) {
        this.guia = guia;
    }
}
