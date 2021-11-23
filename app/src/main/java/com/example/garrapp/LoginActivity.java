package com.example.garrapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.garrapp.utilidades.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.concurrent.Executor;

public class LoginActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 pager2;
    LoginAdapter adapter;
    FloatingActionButton floatingActionButton2, floatingActionButton1, floatingActionButton3;
    float v =0;





    EditText email;
    EditText pass;
    EditText username;

    View login_tab_fragment;
    int btnUserid;

    String emailS;
    String passS;
    String usernameS;

    Button btn_ingreso;
    Button btn_registro;

    public static String TAG = "GarrApp";

    //FirebaseAuth
    private FirebaseAuth mAuth;
    DatabaseReference reference;
    PreferManag preferenceManager;
    FirebaseUser firebaseUser;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreference;

    TextView forgotpassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // firebase intent

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        preferenceManager= new PreferManag(getApplicationContext());
        progressDialog = new ProgressDialog(this);


        floatingActionButton1 = findViewById( R.id.floatingActionButton1);
        floatingActionButton2 = findViewById( R.id.floatingActionButton2);
        floatingActionButton3 = findViewById( R.id.floatingActionButton3);


        // reset password
        forgotpassword = findViewById(R.id.forgot_password);

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        /****************************  FingerPrint************************************************/




        BiometricManager biometricManager = BiometricManager.from(this);

        sharedPreference = getSharedPreferences("data", MODE_PRIVATE);
        boolean isLogin = sharedPreference.getBoolean("isLogin",false);
        if (isLogin){
            floatingActionButton1.setVisibility(View.VISIBLE);
        }else{
            floatingActionButton1.setVisibility(View.GONE);

        }


        Executor executor = ContextCompat.getMainExecutor(this);


        BiometricPrompt biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                Toast.makeText(getApplicationContext(), "Login Sucess", Toast.LENGTH_SHORT).show();
                progressDialog.setMessage("Login");
                progressDialog.show();
                sharedPreference = getSharedPreferences("data", MODE_PRIVATE);
                String emailS = sharedPreference.getString("email", "");
                String passS = sharedPreference.getString("password" , "");


          //      String emailS = "a@mail.com";
          //      String passS = "123456";

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


                super.onAuthenticationSucceeded(result);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });


        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("Login").setDescription("Usa tu huella para ingresar a GarrApp")
                .setNegativeButtonText("Cancelar").build();




        //inflate
        mAuth=FirebaseAuth.getInstance();

        tabLayout=findViewById(R.id.tab_layout);
        pager2=findViewById(R.id.view_pager2);


        FragmentManager fm = getSupportFragmentManager();
        adapter = new LoginAdapter(fm,getLifecycle());
        pager2.setAdapter(adapter);


        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        Log.d(TAG, token);
                      //  Toast.makeText(LoginActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
        tabLayout.addTab(tabLayout.newTab().setText("Ingreso"));
        tabLayout.addTab(tabLayout.newTab().setText("Registro"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

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






        floatingActionButton3.setVisibility(View.GONE);
        floatingActionButton2.setVisibility(View.GONE);



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


        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                biometricPrompt.authenticate(promptInfo);
            }
        });



    }


    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser user=mAuth.getCurrentUser();
        updateUI(user);
    }


    private void updateUI(FirebaseUser user) {
        if(user!=null){
            startActivity(new Intent(this,PrincipalActivity.class));
            progressDialog.dismiss();
        }else{

        }
    }



    private boolean validateForm(String emailS, String passwordS, String usernameS)
    {
        boolean esValido=true;

        emailS.trim();
        if(TextUtils.isEmpty(emailS)){
            esValido=false;
            email.setError("Campo Requerido");
        }

        usernameS.trim();
        if(TextUtils.isEmpty(usernameS)){
            esValido=false;
         //   username.setError("Campo Requerido");
        }

        passwordS.trim();
        if(TextUtils.isEmpty(passwordS)){
            esValido=false;
            pass.setError("Campo Requerido");
        }

        if(passwordS.length()<6)
        {
            esValido=false;
            pass.setError("La contraseña debe tener al menos 6 caracteres");
        }

        if(esValido=true && TextUtils.isEmpty(passwordS)!=true){
            esValido=validarEmail(emailS);
            if (esValido==false) {
                email.setError("Formato de email incorrecto");
            }
        }

        return esValido;
    }




    public void ingresarPressed(View v){

        email=(EditText) findViewById(R.id.email);
        pass=(EditText) findViewById(R.id.pass);

        emailS=email.getText().toString();
        passS=pass.getText().toString();

        if(validateForm(emailS,passS,emailS))
        {
            progressDialog.setMessage("Login");
            progressDialog.show();


            mAuth.signInWithEmailAndPassword(emailS,passS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                        editor.putString("email" , emailS);
                        editor.putString("password", passS);
                        editor.putBoolean("isLogin",true);
                        editor.apply();
                        updateUI(mAuth.getCurrentUser());
                    }else{
                        progressDialog.dismiss();
                        Log.e(TAG,"Autentificación fallida: "+task.getException().toString());
                        Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrectos",Toast.LENGTH_LONG).show();
                        //Toast.makeText(LoginActivity.this, task.getException().toString(),Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    public void registrarPressed(View v){
        email=(EditText) findViewById(R.id.emailR);
        pass=(EditText) findViewById(R.id.passR);
        username=(EditText) findViewById(R.id.usernameR);

        emailS=email.getText().toString();
        passS=pass.getText().toString();
        usernameS=username.getText().toString();

        if(validateForm(emailS,passS,usernameS))
        {
            mAuth.createUserWithEmailAndPassword(emailS,passS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        // updateUI(mAuth.getCurrentUser());

                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        assert firebaseUser != null;
                        String userid = firebaseUser.getUid();

                        reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("id", userid);
                        hashMap.put("username", usernameS);
                        hashMap.put(Constants.KEY_EMAIL , emailS);
                        hashMap.put(Constants.KEY_PASSWORD , passS);
                        hashMap.put("imageURL", "default");
                        hashMap.put("status", "offline");
                        hashMap.put("search", usernameS.toLowerCase());


                        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(LoginActivity.this, "Registro existoso, por favor inicie sesión", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });

                    }else{
                        Log.e(TAG,"Autentificación fallida: "+task.getException().toString());
                        Toast.makeText(LoginActivity.this, task.getException().toString(),Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private boolean validarEmail(String email) {
        if (!email.contains("@") ||
                !email.contains(".") ||
                email.length() < 6)
            return false;
        return true;
    }

}
