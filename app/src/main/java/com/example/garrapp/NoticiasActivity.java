package com.example.garrapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class NoticiasActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;


    //Firebase Auth
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias);

        ButtomBar();




    }

    public void ButtomBar(){
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.Noticias);

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
                        startActivity(new Intent(getApplicationContext(),PrincipalActivity.class));
                        overridePendingTransition(1,0);
                        return true;

                    case R.id.Mapa:
                        startActivity(new Intent(getApplicationContext(),MapsActivity.class));
                        overridePendingTransition(1,0);
                        return true;

                    case R.id.Noticias:
                        return true;
                }

                return false;
            }
        });

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