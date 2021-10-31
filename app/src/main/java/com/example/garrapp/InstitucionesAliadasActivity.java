package com.example.garrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class InstitucionesAliadasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instituciones_aliadas);
    }


    public void PrincipalActivity(View v)
    {
        startActivity(new Intent(this, PrincipalActivity.class));
    }

    public void InstitucionesActivity(View v)
    {
        startActivity(new Intent(this, InstitucionesAliadasActivity.class));
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
        startActivity(new Intent(this, TusReportesActivity.class));
    }
}