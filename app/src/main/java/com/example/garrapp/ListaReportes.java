package com.example.garrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ListaReportes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_reportes);
    }

    public void PrincipalActivity(View v)
    {

        startActivity(new Intent(this,Principal.class));
    }

    public void InstitucionesActivity(View v)
    {
        startActivity(new Intent(this,Instituciones.class));
    }

    public void MapsActivity(View v)
    {
        startActivity(new Intent(this,MapsActivity2.class));
    }

    public void AnimalesEncontradosActivity(View v)
    {
        startActivity(new Intent(this,AnimalesEncontrados.class));
    }

    public void ListaReportesActivity(View v)
    {
        startActivity(new Intent(this,ListaReportes.class));
    }
}