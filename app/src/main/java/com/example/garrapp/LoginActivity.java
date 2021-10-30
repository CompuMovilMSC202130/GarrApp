package com.example.garrapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    FloatingActionButton floatingActionButton2, floatingActionButton1, floatingActionButton3;
    float v =0;

    Button btn_ingreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tabLayout=findViewById(R.id.tab_layout);
        viewPager = findViewById( R.id.view_pager);
        btn_ingreso = findViewById(R.id.btn_ingresar);

        floatingActionButton1 = findViewById( R.id.floatingActionButton1);
        floatingActionButton2 = findViewById( R.id.floatingActionButton2);
        floatingActionButton3 = findViewById( R.id.floatingActionButton3);

        tabLayout.addTab(tabLayout.newTab().setText("Ingreso"));
        tabLayout.addTab(tabLayout.newTab().setText("Registro"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final LoginAdapter adapter = new LoginAdapter(getSupportFragmentManager(),this,tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        floatingActionButton1.setTranslationY(300);
        floatingActionButton2.setTranslationY(300);
        floatingActionButton3.setTranslationY(300);
        tabLayout.setTranslationY(300);

        floatingActionButton1.setAlpha(v);
        floatingActionButton2.setAlpha(v);
        floatingActionButton3.setAlpha(v);
        tabLayout.setAlpha(v);

        floatingActionButton1.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        floatingActionButton2.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        floatingActionButton3.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        tabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();




    }
    public void ingresarPressed (View v){
        startActivity(new Intent(this, PrincipalActivity.class));
    }
}