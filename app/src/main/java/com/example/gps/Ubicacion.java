package com.example.gps;

import android.location.Location;

public class Ubicacion {

    private String participante;
    private Location location;

    public Ubicacion() {
    }

    public Ubicacion(String participante, Location location) {
        this.participante = participante;
        this.location = location;
    }

    public String getParticipante() {
        return participante;
    }

    public void setParticipante(String participante) {
        this.participante = participante;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
