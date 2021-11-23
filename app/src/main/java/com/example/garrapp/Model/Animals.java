package com.example.garrapp.Model;

public class Animals {
    private String genero;
    private String raza;
    private String tamaño;
    private String descripcion;
    private String ultUbicacion;
    private String imgAnimals;

    public Animals(){}

    public Animals(String especie, String genero ,String raza  ,String tamaño,
                   String descripcion, String ultUbicacion, String imgAnimals){

        this.genero = genero;
        this.raza = raza;
        this.tamaño = tamaño;
        this.descripcion = descripcion;
        this.ultUbicacion = ultUbicacion;
        this.imgAnimals = imgAnimals;

    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getTamaño() {
        return tamaño;
    }

    public void setTamaño(String tamaño) {
        this.tamaño = tamaño;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUltUbicacion() {
        return ultUbicacion;
    }

    public void setUltUbicacion(String ultUbicacion) {
        this.ultUbicacion = ultUbicacion;
    }

    public String getImgAnimals() {
        return imgAnimals;
    }

    public void setImgAnimals(String imgAnimals) {
        this.imgAnimals = imgAnimals;
    }
}
