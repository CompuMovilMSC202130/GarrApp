package com.example.garrapp;

import static android.webkit.URLUtil.decode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.garrapp.utilidades.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListadosActivity extends AppCompatActivity {

    // barra de navegación inferior
    BottomNavigationView bottomNavigationView;

    // fragmentos de la actividad
    TabLayout tabLayout;
    ViewPager2 pager2;
    ListasAdapter adapter;

    public int bandera=0;

    //Firebase Auth
    private FirebaseAuth mAuth;
    DatabaseReference reference;

    //Storage
    StorageReference mStorageRef;

    //inflate
    ImageView imgperdidos1, imgperdidos2, imgperdidos3, imgperdidos4, imgperdidos5;
    TextView genperdidos1, genperdidos2, genperdidos3, genperdidos4, genperdidos5;
    TextView razaperdidos1, razaperdidos2, razaperdidos3, razaperdidos4, razaperdidos5;
    TextView tamanoperdidos1, tamanoperdidos2, tamanoperdidos3, tamanoperdidos4, tamanoperdidos5;
    TextView desperdidos1, desperdidos2, desperdidos3, desperdidos4, desperdidos5;
    TextView ubiperdidos1, ubiperdidos2, ubiperdidos3, ubiperdidos4, ubiperdidos5;

    ImageView imgencontrados1, imgencontrados2, imgencontrados3, imgencontrados4, imgencontrados5;
    TextView genencontrados1, genencontrados2, genencontrados3, genencontrados4, genencontrados5;
    TextView razaencontrados1, razaencontrados2, razaencontrados3, razaencontrados4, razaencontrados5;
    TextView tamanoencontrados1, tamanoencontrados2, tamanoencontrados3, tamanoencontrados4, tamanoencontrados5;
    TextView desencontrados1, desencontrados2, desencontrados3, desencontrados4, desencontrados5;
    TextView ubiencontrados1, ubiencontrados2, ubiencontrados3, ubiencontrados4, ubiencontrados5;

    private ArrayList<String> IDReportesPerdidosList =  new ArrayList<String>();
    private ArrayList<String> IDReportesEncontradosList =  new ArrayList<String>();

    private ArrayList<String> PerdidosGeneroList =  new ArrayList<String>();
    private ArrayList<String> PerdidosRazaList =  new ArrayList<String>();
    private ArrayList<String> PerdidosTamañoList =  new ArrayList<String>();
    private ArrayList<String> PerdidosDescripcionList =  new ArrayList<String>();
    private ArrayList<String> PerdidosFotosList =  new ArrayList<String>();
    private ArrayList<String> PerdidosUbicacionList =  new ArrayList<String>();

    private ArrayList<String> EncontradosGeneroList =  new ArrayList<String>();
    private ArrayList<String> EncontradosRazaList =  new ArrayList<String>();
    private ArrayList<String> EncontradosTamañoList =  new ArrayList<String>();
    private ArrayList<String> EncontradosDescripcionList =  new ArrayList<String>();
    private ArrayList<String> EncontradosFotosList =  new ArrayList<String>();
    private ArrayList<String> EncontradosUbicacionList =  new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listados);

        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(Constants.KEY_REPORTES);

        // Create a Cloud Storage reference from the app
        mStorageRef= FirebaseStorage.getInstance().getReference("Fotos/");


        //cargarIDReportesEncontrados();
        cargarIDReportesPerdidos();
        //cargarPerdidos();

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
                if (position!=0)
                    cargarIDReportesEncontrados();

                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
        int tab_position=tabLayout.getSelectedTabPosition();
    }

    public interface OnGetDataListener {
        //make new interface for call back
        void onSuccess(DataSnapshot dataSnapshot);
        void onStart();
        void onFailure();
    }

    public void readData(DatabaseReference ref, final OnGetDataListener listener) {
        listener.onStart();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailure();
            }
        });

    }

    public <var> void cargarIDReportesPerdidos() {


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(Constants.KEY_REPORTES+"/"+"Perdidos/");

        // Create a Cloud Storage reference from the app
        mStorageRef= FirebaseStorage.getInstance().getReference("Fotos/");
        //StorageReference mStorageRef2 = FirebaseStorage.getInstance().getReference();


        readData(myRef, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                //whatever you need to do with the data
                if (dataSnapshot.exists()){
                    for (DataSnapshot ds:dataSnapshot.getChildren()){

                        String llave=ds.getKey();
                        IDReportesPerdidosList.add(llave);
                        String gsLink=mStorageRef+"/"+llave+".jpg";

                        //PerdidosFotosList.add(gsLink);
                    }

                }
                cargarPerdidos();
            }
            @Override
            public void onStart() {
                //whatever you need to do onStart
                Log.d("ONSTART", "Started");
            }

            @Override
            public void onFailure() {

            }
        });
    }

    public <var> void cargarIDReportesEncontrados() {


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(Constants.KEY_REPORTES+"/"+"Encontrados/");

        // Create a Cloud Storage reference from the app
        mStorageRef= FirebaseStorage.getInstance().getReference("Fotos/");
        //StorageReference mStorageRef2 = FirebaseStorage.getInstance().getReference();


        readData(myRef, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                //whatever you need to do with the data
                if (dataSnapshot.exists()){
                    for (DataSnapshot ds:dataSnapshot.getChildren()){

                        String llave=ds.getKey();
                        IDReportesEncontradosList.add(llave);
                        String gsLink=mStorageRef+"/"+llave+".jpg";

                        //PerdidosFotosList.add(gsLink);
                    }

                }
                cargarEncontrados();
            }
            @Override
            public void onStart() {
                //whatever you need to do onStart
                Log.d("ONSTART", "Started");
            }

            @Override
            public void onFailure() {

            }
        });
    }


    public void cargarPerdidos() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();


        for (int n=0;n<IDReportesPerdidosList.size();n++)
        {
            DatabaseReference myRef = database.getReference(Constants.KEY_REPORTES + "/" + "Perdidos/"+IDReportesPerdidosList.get(n).toString());
            readData(myRef, new OnGetDataListener() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    //whatever you need to do with the data
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            //Reporte reporte=ds.getValue(Reporte.class);
                            String llave = ds.getKey();
                            switch (llave) {
                                case "genero":
                                    PerdidosGeneroList.add(String.valueOf(ds.getValue()));
                                    break;
                                case "raza":
                                    PerdidosRazaList.add(String.valueOf(ds.getValue()));
                                    break;
                                case "tamaño":
                                    PerdidosTamañoList.add(String.valueOf(ds.getValue()));
                                    break;
                                case "descripción":
                                    PerdidosDescripcionList.add(String.valueOf(ds.getValue()));
                                    break;
                                case "fotoURL":
                                    PerdidosFotosList.add(String.valueOf(ds.getValue()));
                                    break;
                                case "ubicacion":
                                    PerdidosUbicacionList.add(String.valueOf(ds.getValue()));
                                    break;

                            }


                        }

                    }
                    cargarDatosPerdidos();
                }

                @Override
                public void onStart() {
                    //whatever you need to do onStart
                    Log.d("ONSTART", "Started");
                }

                @Override
                public void onFailure() {

                }
            });

        }
    }

    public void cargarEncontrados() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();


        for (int n=0;n<IDReportesEncontradosList.size();n++)
        {
            DatabaseReference myRef = database.getReference(Constants.KEY_REPORTES + "/" + "Encontrados/"+IDReportesEncontradosList.get(n).toString());
            readData(myRef, new OnGetDataListener() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    //whatever you need to do with the data
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            //Reporte reporte=ds.getValue(Reporte.class);
                            String llave = ds.getKey();
                            switch (llave) {
                                case "genero":
                                    EncontradosGeneroList.add(String.valueOf(ds.getValue()));
                                    break;
                                case "raza":
                                    EncontradosRazaList.add(String.valueOf(ds.getValue()));
                                    break;
                                case "tamaño":
                                    EncontradosTamañoList.add(String.valueOf(ds.getValue()));
                                    break;
                                case "descripción":
                                    EncontradosDescripcionList.add(String.valueOf(ds.getValue()));
                                    break;
                                case "fotoURL":
                                    EncontradosFotosList.add(String.valueOf(ds.getValue()));
                                    break;
                                case "ubicacion":
                                    EncontradosUbicacionList.add(String.valueOf(ds.getValue()));
                                    break;

                            }


                        }

                    }
                    cargarDatosEncontrados();
                }

                @Override
                public void onStart() {
                    //whatever you need to do onStart
                    Log.d("ONSTART", "Started");
                }

                @Override
                public void onFailure() {

                }
            });

        }
    }

    private void cargarDatosPerdidos() {
        imgperdidos1 = findViewById(R.id.imgperdidos1);
        imgperdidos2 = findViewById(R.id.imgperdidos2);
        imgperdidos3 = findViewById(R.id.imgperdidos3);
        imgperdidos4 = findViewById(R.id.imgperdidos4);
        imgperdidos5 = findViewById(R.id.imgperdidos5);

        genperdidos1 = findViewById(R.id.genperdidos1);
        genperdidos2 = findViewById(R.id.genperdidos2);
        genperdidos3 = findViewById(R.id.genperdidos3);
        genperdidos4 = findViewById(R.id.genperdidos4);
        genperdidos5 = findViewById(R.id.genperdidos5);

        razaperdidos1 = findViewById(R.id.razaperdidos1);
        razaperdidos2 = findViewById(R.id.razaperdidos2);
        razaperdidos3 = findViewById(R.id.razaperdidos3);
        razaperdidos4 = findViewById(R.id.razaperdidos4);
        razaperdidos5 = findViewById(R.id.razaperdidos5);

        tamanoperdidos1 = findViewById(R.id.tamanoperdidos1);
        tamanoperdidos2 = findViewById(R.id.tamanoperdidos2);
        tamanoperdidos3 = findViewById(R.id.tamanoperdidos3);
        tamanoperdidos4 = findViewById(R.id.tamanoperdidos4);
        tamanoperdidos5 = findViewById(R.id.tamanoperdidos5);

        desperdidos1 = findViewById(R.id.desperdidos1);
        desperdidos2 = findViewById(R.id.desperdidos2);
        desperdidos3 = findViewById(R.id.desperdidos3);
        desperdidos4 = findViewById(R.id.desperdidos4);
        desperdidos5 = findViewById(R.id.desperdidos5);

        ubiperdidos1 = findViewById(R.id.ubiperdidos1);
        ubiperdidos2 = findViewById(R.id.ubiperdidos2);
        ubiperdidos3 = findViewById(R.id.ubiperdidos3);
        ubiperdidos4 = findViewById(R.id.ubiperdidos4);
        ubiperdidos5 = findViewById(R.id.ubiperdidos5);


        int numReportesP=PerdidosGeneroList.size();
        switch(numReportesP){
            case 1:
                genperdidos1.append(" : " + PerdidosGeneroList.get(0).toString());
                razaperdidos1.append(" : " + PerdidosRazaList.get(0).toString());
                tamanoperdidos1.append(" : " + PerdidosTamañoList.get(0).toString());
                desperdidos1.append(" : " + PerdidosDescripcionList.get(0).toString());
                Glide.with(this).load(PerdidosFotosList.get(0).toString()).into(imgperdidos1);
                ubiperdidos1.append(" : " + PerdidosUbicacionList.get(0).toString());
                break;
            case 2:
                genperdidos2.append(" : " + PerdidosGeneroList.get(1).toString());
                razaperdidos2.append(" : " + PerdidosRazaList.get(1).toString());
                tamanoperdidos2.append(" : " + PerdidosTamañoList.get(1).toString());
                desperdidos2.append(" : " + PerdidosDescripcionList.get(1).toString());
                Glide.with(this).load(PerdidosFotosList.get(1).toString()).into(imgperdidos2);
                ubiperdidos2.append(" : " + PerdidosUbicacionList.get(1).toString());
                break;
            case 3:
                genperdidos3.append(" : " + PerdidosGeneroList.get(2).toString());
                razaperdidos3.append(" : " + PerdidosRazaList.get(2).toString());
                tamanoperdidos3.append(" : " + PerdidosTamañoList.get(2).toString());
                desperdidos3.append(" : " + PerdidosDescripcionList.get(2).toString());
                Glide.with(this).load(PerdidosFotosList.get(2).toString()).into(imgperdidos3);
                ubiperdidos3.append(" : " + PerdidosUbicacionList.get(2).toString());
                break;
            case 4:
                genperdidos4.append(" : " + PerdidosGeneroList.get(3).toString());
                razaperdidos4.append(" : " + PerdidosRazaList.get(3).toString());
                tamanoperdidos4.append(" : " + PerdidosTamañoList.get(3).toString());
                desperdidos4.append(" : " + PerdidosDescripcionList.get(3).toString());
                Glide.with(this).load(PerdidosFotosList.get(3).toString()).into(imgperdidos4);
                ubiperdidos4.append(" : " + PerdidosUbicacionList.get(3).toString());
                break;
            case 5:
                genperdidos5.append(" : " + PerdidosGeneroList.get(4).toString());
                razaperdidos5.append(" : " + PerdidosRazaList.get(4).toString());
                tamanoperdidos5.append(" : " + PerdidosTamañoList.get(4).toString());
                desperdidos5.append(" : " + PerdidosDescripcionList.get(4).toString());
                Glide.with(this).load(PerdidosFotosList.get(4).toString()).into(imgperdidos5);
                ubiperdidos5.append(" : " + PerdidosUbicacionList.get(4).toString());
                break;
        };

    }

    private void cargarDatosEncontrados() {

        imgencontrados1 = findViewById(R.id.imgencontrados1);
        imgencontrados2 = findViewById(R.id.imgencontrados2);
        imgencontrados3 = findViewById(R.id.imgencontrados3);
        imgencontrados4 = findViewById(R.id.imgencontrados4);
        imgencontrados5 = findViewById(R.id.imgencontrados5);

        genencontrados1 = findViewById(R.id.genencontrados1);
        genencontrados2 = findViewById(R.id.genencontrados2);
        genencontrados3 = findViewById(R.id.genencontrados3);
        genencontrados4 = findViewById(R.id.genencontrados4);
        genencontrados5 = findViewById(R.id.genencontrados5);

        razaencontrados1 = findViewById(R.id.razaencontrados1);
        razaencontrados2 = findViewById(R.id.razaencontrados2);
        razaencontrados3 = findViewById(R.id.razaencontrados3);
        razaencontrados4 = findViewById(R.id.razaencontrados4);
        razaencontrados5 = findViewById(R.id.razaencontrados5);

        tamanoencontrados1 = findViewById(R.id.tamanoencontrados1);
        tamanoencontrados2 = findViewById(R.id.tamanoencontrados2);
        tamanoencontrados3 = findViewById(R.id.tamanoencontrados3);
        tamanoencontrados4 = findViewById(R.id.tamanoencontrados4);
        tamanoencontrados5 = findViewById(R.id.tamanoencontrados5);

        ubiencontrados1 = findViewById(R.id.ubiencontrados1);
        ubiencontrados2 = findViewById(R.id.ubiencontrados2);
        ubiencontrados3 = findViewById(R.id.ubiencontrados3);
        ubiencontrados4 = findViewById(R.id.ubiencontrados4);
        ubiencontrados5 = findViewById(R.id.ubiencontrados5);

        int numReportesE=EncontradosGeneroList.size();
        switch(numReportesE){
            case 1:
                genencontrados1.append(" : " + EncontradosGeneroList.get(0).toString());
                razaencontrados1.append(" : " + EncontradosRazaList.get(0).toString());
                tamanoencontrados1.append(" : " + EncontradosTamañoList.get(0).toString());
                desencontrados1.append(" : " + EncontradosDescripcionList.get(0).toString());
                Glide.with(this).load(EncontradosFotosList.get(0).toString()).into(imgencontrados1);
                break;
            case 2:
                genencontrados2.append(" : " + EncontradosGeneroList.get(1).toString());
                razaencontrados2.append(" : " + EncontradosRazaList.get(1).toString());
                tamanoencontrados2.append(" : " + EncontradosTamañoList.get(1).toString());
                desencontrados2.append(" : " + EncontradosDescripcionList.get(1).toString());
                Glide.with(this).load(EncontradosFotosList.get(1).toString()).into(imgencontrados2);
                break;
            case 3:
                genencontrados3.append(" : " + EncontradosGeneroList.get(2).toString());
                razaencontrados3.append(" : " + EncontradosRazaList.get(2).toString());
                tamanoencontrados3.append(" : " + EncontradosTamañoList.get(2).toString());
                desencontrados3.append(" : " + EncontradosDescripcionList.get(2).toString());
                Glide.with(this).load(EncontradosFotosList.get(2).toString()).into(imgencontrados3);
                break;
            case 4:
                genencontrados4.append(" : " + EncontradosGeneroList.get(3).toString());
                razaencontrados4.append(" : " + EncontradosRazaList.get(3).toString());
                tamanoencontrados4.append(" : " + EncontradosTamañoList.get(3).toString());
                desencontrados4.append(" : " + EncontradosDescripcionList.get(3).toString());
                Glide.with(this).load(EncontradosFotosList.get(3).toString()).into(imgencontrados4);
                break;
            case 5:
                genencontrados5.append(" : " + EncontradosGeneroList.get(4).toString());
                razaencontrados5.append(" : " + EncontradosRazaList.get(4).toString());
                tamanoencontrados5.append(" : " + EncontradosTamañoList.get(4).toString());
                desencontrados5.append(" : " + EncontradosDescripcionList.get(4).toString());
                Glide.with(this).load(EncontradosFotosList.get(4).toString()).into(imgencontrados5);
                break;
        };
    }

    private String revisarNull(String texto) {
        if(texto.equals(null))
            return "";
        else return texto;
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




