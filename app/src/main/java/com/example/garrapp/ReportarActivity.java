package com.example.garrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ReportarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportar);
    }


    public void AnimalEncontradoPressed(View v){
        startActivity(new Intent(this, ReportarEncontradoActivity.class));
    }

    public void AnimalPerdidoPressed(View v){
        startActivity(new Intent(this, ReportarPerdidoActivity.class));
    }



}