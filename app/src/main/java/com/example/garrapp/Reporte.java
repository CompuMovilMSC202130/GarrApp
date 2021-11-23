package com.example.garrapp;

public class Reporte {
    String Genero;
    String Raza;
    String Tamaño;
    String Descripción;
    String FotoURL;
    String Ubicacion;

    public Reporte() {

    }

    public Reporte(String genero, String raza, String tamaño, String descripcion, String fotoURL) {
    }


    public String getGenero() {
        return Genero;
    }

    public void setGenero(String genero) {
        Genero = genero;
    }

    public String getRaza() {
        return Raza;
    }

    public void setRaza(String raza) {
        Raza = raza;
    }

    public String getTamaño() {
        return Tamaño;
    }

    public void setTamaño(String tamaño) {
        Tamaño = tamaño;
    }

    public String getDescripción() {
        return Descripción;
    }

    public void setDescripción(String descripcion) { Descripción = descripcion; }

    public String getFotoURL() {
        return FotoURL;
    }

    public void setFotoURL(String fotoURL) {
        FotoURL = fotoURL;
    }

    public String getUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        Ubicacion = ubicacion;
    }
}
