package com.example.garrapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ReportarPerdidoActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    //Firebase Auth
    private FirebaseAuth mAuth;

    //Realtime DB
    FirebaseDatabase database;
    DatabaseReference myRef;

    // permisos de la cámara y galeria
    private static final int PERMISSION_REQUEST_CAMERA = 0;
    private static final int PERMISSION_READ_EXTERNAL = 1;
    private View mLayout;
    private ImageView viewCamera;

    EditText  descripcion,ubicacion, foto ;
    Spinner genero,tamaño,raza;

    Button btnFinalizar;

    public static final String PATH_REPORTES="Reportes/";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportar_perdido);

        mLayout = findViewById(R.id.idReportarPerdido_layout);
        viewCamera = findViewById(R.id.imageViewFotoPerdido);

        mAuth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        // Register a listener for the 'Show Camera Preview' button.

        findViewById(R.id.btn_selec_Imgp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accessMediaPreview();
            }
        });


        genero=findViewById(R.id.spinnerGenero);
        raza= findViewById(R.id.spinnerRaza);
        descripcion= findViewById(R.id.descripcionp);
        tamaño=findViewById(R.id.spinner);

        //ubicacion= findViewById(R.id.);
        //foto= findViewById(R.id.imageViewFotoEncontrado);


        btnFinalizar =findViewById(R.id.FinalizarReportePerdido);


        ButtonBar();
    }

    public void ButtonBar(){
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.Reportes);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.Reportes:
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
                        startActivity(new Intent(getApplicationContext(),NoticiasActivity.class));
                        overridePendingTransition(1,0);
                        return true;
                }

                return false;
            }
        });

    }


    /* ****************************************************************
                    PERMISOS DE LA CAMARA Y GALLERIA
    **************************************************************** */


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // BEGIN_INCLUDE(onRequestPermissionsResult)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            // Request for camera permission.
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted. Start camera preview Activity.
                Snackbar.make(mLayout, R.string.camera_permission_granted,
                        Snackbar.LENGTH_SHORT)
                        .show();
                startCamera();
            } else {
                // Permission request was denied.
                Snackbar.make(mLayout, R.string.camera_permission_denied,
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
        }

        if (requestCode == PERMISSION_READ_EXTERNAL) {
            // Request for camera permission.
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted. Start camera preview Activity.
                Snackbar.make(mLayout, R.string.read_permission_granted,
                        Snackbar.LENGTH_SHORT)
                        .show();
                startAccessMedia();
            } else {
                // Permission request was denied.
                Snackbar.make(mLayout, R.string.read_permission_denied,
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
        }






        // END_INCLUDE(onRequestPermissionsResult)
    }

    private void accessMediaPreview() {
        // BEGIN_INCLUDE(startCamera)
        // Check if the Camera permission has been granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission is already available, start camera preview
            Snackbar.make(mLayout,
                    R.string.camera_permission_available,
                    Snackbar.LENGTH_SHORT).show();
            startAccessMedia();
        } else {
            // Permission is missing and must be requested.
            requestAccessMediaPermission();
        }
        // END_INCLUDE(startCamera)
    }


    /**
     * Requests the {@link android.Manifest.permission#CAMERA} permission.
     * If an additional rationale should be displayed, the user has to launch the request from
     * a SnackBar that includes additional information.
     */

    private void requestAccessMediaPermission() {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // Display a SnackBar with cda button to request the missing permission.
            Snackbar.make(mLayout, R.string.camera_access_required,
                    Snackbar.LENGTH_INDEFINITE).setAction(R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Request the permission
                    ActivityCompat.requestPermissions(ReportarPerdidoActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            PERMISSION_READ_EXTERNAL);
                }
            }).show();

        } else {
            Snackbar.make(mLayout, R.string.camera_unavailable, Snackbar.LENGTH_SHORT).show();
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_READ_EXTERNAL);
        }
    }


    private void startCamera() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent,PERMISSION_REQUEST_CAMERA);
        }catch (ActivityNotFoundException e){
            Log.e("PERMISSION_APP" , e.getMessage());
        }


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PERMISSION_REQUEST_CAMERA && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            viewCamera.setImageBitmap(imageBitmap);
        }

        if (requestCode == PERMISSION_READ_EXTERNAL && resultCode == RESULT_OK) {

            try {
                final Uri imageUri =data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                viewCamera.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }



    }


    private void startAccessMedia() {
        Intent cam_ImagesIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        cam_ImagesIntent.setType("image/*");
        startActivityForResult(cam_ImagesIntent, PERMISSION_READ_EXTERNAL);



        // Intent takePictureIntent = new Intent(Intent.ACTION_PICK);
        //if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent,PERMISSION_REQUEST_CAMERA);
        //      }
    }

    public void Salir(View v){
        //cierre sesion
        mAuth.signOut();
        //UpdateUi
        Intent intent=new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void EnviarReporte(View v){
        myRef=database.getReference("Reportes").child("Perdido");

         String generoS=genero.getSelectedItem().toString();
        String razaS=raza.getSelectedItem().toString();
        String tamañoS= tamaño.getSelectedItem().toString();
        String descripcionS=descripcion.getText().toString();
        if (validateForm(generoS,razaS,tamañoS,descripcionS)){
           // myRef=database.getReference(PATH_REPORTES+mAuth.getUid());
            String key=myRef.push().getKey();
           // myRef=database.getReference(PATH_REPORTES+key);

            Reporte reporte=new Reporte();
            reporte.setGenero(generoS);
            reporte.setRaza(razaS);
            reporte.setTamaño(tamañoS);
            reporte.setDescripción(descripcionS);

            myRef.child(mAuth.getUid()).child(key).setValue(reporte);

            //myRef.push().setValue(reporte);
            Log.d("Key" , key);

            Toast.makeText(ReportarPerdidoActivity.this, "Reporte creado exitosamente",Toast.LENGTH_LONG).show();


            genero.setSelection(0);
            raza.setSelection(0);
            descripcion.setText("");
            tamaño.setSelection(0);

        }

    }

    private boolean validateForm(String generoS, String razaS, String tamañoS, String descripcionS) {
        return true;
    }


}