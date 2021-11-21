package com.example.garrapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.garrapp.Model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class NoticiasActivity extends AppCompatActivity {

    // barra de navegación inferior
    BottomNavigationView bottomNavigationView;

    // fragmentos de la actividad
    TabLayout tabLayout;
    ViewPager2 pager2;
    ChatAdapter adapter;
    
    // lista del chat
    CircleImageView profile_image;
    TextView username;
    
    FirebaseUser firebaseUser;
    DatabaseReference reference;


    //Firebase Auth
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias);

        ButtomBar();
        fragmentCall();

        //Toolbar toolbar = findViewById(R.id.toolbar);
        //getSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("Re");

        profile_image =findViewById(R.id.profile_image);
        username = findViewById(R.id.username);
        
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getUsername());
                if (user.getImageURL().equals("default")){
                    profile_image.setImageResource(R.mipmap.ic_launcher);
                } else {
                    Glide.with(NoticiasActivity.this).load(user.getImageURL()).into(profile_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



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


    public void fragmentCall(){

        tabLayout=findViewById(R.id.tab_layoutChat);
        pager2 = findViewById( R.id.view_pagerChat);

        FragmentManager fm = getSupportFragmentManager();
        adapter = new ChatAdapter(fm, getLifecycle());
        pager2.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Chats"));
        tabLayout.addTab(tabLayout.newTab().setText("Usuarios"));

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



}