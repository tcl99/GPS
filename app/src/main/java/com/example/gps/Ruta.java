package com.example.gps;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class Ruta {

    private LatLng start;
    private LatLng end;
    private List<String> participantes;

    public Ruta() {
    }

    public Ruta(LatLng start, LatLng end, List<String> participantes) {
        this.start = start;
        this.end = end;
        this.participantes = participantes;
    }

    public LatLng getStart() {
        return start;
    }

    public void setStart(LatLng start) {
        this.start = start;
    }

    public LatLng getEnd() {
        return end;
    }

    public void setEnd(LatLng end) {
        this.end = end;
    }

    public List<String> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<String> participantes) {
        this.participantes = participantes;
    }
}
