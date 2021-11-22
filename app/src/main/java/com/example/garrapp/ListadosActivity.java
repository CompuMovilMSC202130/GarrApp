package com.example.garrapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.garrapp.Model.Animals;
import com.example.garrapp.utilidades.Constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListadosActivity extends AppCompatActivity {

    // barra de navegación inferior
    BottomNavigationView bottomNavigationView;

    // fragmentos de la actividad
    TabLayout tabLayout;
    ViewPager2 pager2;
    ListasAdapter adapter;

    //Firebase Auth


    private FirebaseAuth mAuth;
    DatabaseReference reference;
    private ArrayList<Animals> nAnimalsList =  new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listados);

        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(Constants.KEY_REPORTES);
        /**************************** Lectura Listado Perdidos *******************/

        myRef.child("Perdido").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                   for (DataSnapshot ds:snapshot.getChildren()){
                       String raza =  ds.child("raza").getValue().toString();
                       String genero =  ds.child("genero").getValue().toString();
                       nAnimalsList.add(new Animals("casa" ,genero , raza , "medio","uno","dos","tres"));
                       Log.d("RAZA" , raza);
                   }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        // barra de navegación inferior, casos para el cambio
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.Listados);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.Reportes:
                        startActivity(new Intent(getApplicationContext(),TusReportesActivity.class));
                        overridePendingTransition(1,0);
                        return true;

                    case R.id.Listados:
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
                        startActivity(new Intent(getApplicationContext(),NoticiasActivity.class));
                        overridePendingTransition(1,0);
                        return true;
                }

                return false;
            }
        });



        // fragmentos de la actividad encontrados / perdidos
        tabLayout = findViewById(R.id.tab_layoutlistados);
        pager2 = findViewById( R.id.view_pagerlistados);

        FragmentManager fm = getSupportFragmentManager();
        adapter = new ListasAdapter(fm, getLifecycle());
        pager2.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Animales perdidos"));
        tabLayout.addTab(tabLayout.newTab().setText("Animales encontrados"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
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



    private void getAnimalesEncontrados (){

    }


}




