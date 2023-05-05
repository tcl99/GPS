package com.example.gps;

import android.location.Location;

import java.time.LocalDateTime;

public class Caida {

    private Location location;
    private String usuario;
    private LocalDateTime time;

    public Caida() {
    }

    public Caida(Location location, String usuario) {
        this.location = location;
        this.usuario = usuario;
        this.time = LocalDateTime.now();
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
