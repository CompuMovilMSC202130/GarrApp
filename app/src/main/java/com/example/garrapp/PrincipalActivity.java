package com.example.garrapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class PrincipalActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    //Firebase Auth
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        mAuth = FirebaseAuth.getInstance();

        ButtomBar();

    }

    public void ButtomBar(){
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.Home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.Reportes:
                        startActivity(new Intent(getApplicationContext(),TusReportesActivity.class));
                        overridePendingTransition(1,0);
                        return true;

                    case R.id.Listados:
                        startActivity(new Intent(getApplicationContext(),ListadosActivity.class));
                        overridePendingTransition(1,0);
                        return true;

                    case R.id.Home:
                        return true;

                    case R.id.Mapa:
                        startActivity(new Intent(getApplicationContext(),MapsActivity.class));
                        overridePendingTransition(1,0);
                        return true;

                    case R.id.Noticias:
                        startActivity(new Intent(getApplicationContext(),NoticiasActivity.class));
                        overridePendingTransition(1,0);
                        return true;
                }

                return false;
            }
        });

    }

    public void RealizarReportePressed(View v){
        startActivity(new Intent(this, ReportarActivity.class));
    }

    public void tusReportesPressed(View v){
        startActivity(new Intent(this, TusReportesActivity.class));
    }

    public void aliadosPressed(View v){
        startActivity(new Intent(this, InstitucionesAliadasActivity.class));
    }

    public void ListadosIsPressed(View v){
        startActivity(new Intent(this, ListadosActivity.class));
    }

    public void MapsIsPressed(View v){
        startActivity(new Intent(this, MapsActivity.class));
    }

    public void NewsIsPressed(View v){
        startActivity(new Intent(this, NoticiasActivity.class));
    }

    public void Salir(View v){
        //cierre sesion
        mAuth.signOut();
        //UpdateUi
        Intent intent=new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }



}