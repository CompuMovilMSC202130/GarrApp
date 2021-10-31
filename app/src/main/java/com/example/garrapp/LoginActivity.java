package com.example.garrapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    FloatingActionButton floatingActionButton2, floatingActionButton1, floatingActionButton3;
    float v =0;


    EditText email;
    View login_tab_fragment;
    int btnUserid;
    String emailS;
    String passS;

    Button btn_ingreso;

    public static String TAG = "GarrApp";

    //FirebaseAuth
    //private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //inflate
        //mAuth=FirebaseAuth.getInstance();

        tabLayout=findViewById(R.id.tab_layout);
        viewPager = findViewById( R.id.view_pager);
        btn_ingreso = findViewById(R.id.btn_ingresar);



        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        login_tab_fragment = inflater.inflate(R.layout.login_tab_fragment, null);
        Dialog dialog = new Dialog(login_tab_fragment.getContext());
        email=dialog.findViewById(R.id.email);


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

    /*
    @Override
    protected void onStart(){
        super.onStart();
        //FirebaseUser user=mAuth.getCurrentUser();
        updateUI(user);
    }


    private void updateUI(FirebaseUser user) {
        if(user!=null){
            startActivity(new Intent(this,Principal.class));
        }else{
            //
        }
    }
    */


    private boolean validateForm(String emailS,String passwordS)
    {
        return true;
    }


    public void ingresarPressed (View v){
        startActivity(new Intent(this, PrincipalActivity.class));
    }

/*
    public void ingresarPresseda(View v){
        //emailS = login_tab_fragment.findViewById(R.id.email).toString();
        //emailS = ((EditText)login_tab_fragment.findViewById(R.id.email)).getText().toString();
        //passS = login_tab_fragment.findViewById(R.id.pass).toString();
        emailS=email.getText().toString();
        if(validateForm(emailS,passS))
        {
            mAuth.signInWithEmailAndPassword(emailS,passS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        updateUI(mAuth.getCurrentUser());
                    }else{
                        Log.e(TAG,"Autentificación fallida: "+task.getException().toString());
                        Toast.makeText(LoginActivity.this, task.getException().toString(),Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    public void signInPressed(View v){

    }
    */

}
