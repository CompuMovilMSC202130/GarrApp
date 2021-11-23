package com.example.garrapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ReportarEncontradoActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    //Firebase Auth
    private FirebaseAuth mAuth;

    //Realtime DB
    FirebaseDatabase database;
    DatabaseReference myRef;

    //Storage
    StorageReference mStorageRef;

    // permisos de la cámara y galeria
    private static final int PERMISSION_REQUEST_CAMERA = 0;
    private static final int PERMISSION_READ_EXTERNAL = 1;
    public String fotoURL,keyReportes;

    public String generoS,razaS,tamañoS,descripcionS,ubicacionS;

    private View mLayout;
    private ImageView viewCamera;

    EditText descripcion,ubicacion;
    Spinner genero,tamaño,raza;
    ImageView foto;

    Button btnFinalizar;

    public static final String PATH_REPORTES="Reportes/";

    int PLACE_PICKER_REQUEST = 1;
    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

    public int j=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportar_encontrado);
        FirebaseMessaging.getInstance().subscribeToTopic("all");

        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        // Create a Cloud Storage reference from the app
        mStorageRef= FirebaseStorage.getInstance().getReference("Fotos/");

        mLayout = findViewById(R.id.idReportarEncontrado_layout);
        viewCamera = findViewById(R.id.imageViewFotoEncontrado);


        // Register a listener for the 'Show Camera Preview' button.
        findViewById(R.id.button_open_camerae).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCameraPreview();
            }
        });

        findViewById(R.id.btn_selec_Imge).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accessMediaPreview();
            }
        });

        genero=findViewById(R.id.spinnerGenero);
        raza= findViewById(R.id.spinnerRaza);
        descripcion= findViewById(R.id.descripcione);
        tamaño=findViewById(R.id.spinner);
        ubicacion=findViewById(R.id.ubicacione);

        foto=findViewById(R.id.imageViewFotoEncontrado);


        btnFinalizar =findViewById(R.id.FinalizarReporteEncontrado);

        findViewById(R.id.FinalizarReporteEncontrado).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                /*FcmNotificationsSender notificationsSender = new FcmNotificationsSender("/topics/all"," \uD83D\uDC36 Se ha realizado un nuevo reporte",
                        " Milo se ha perdido :(  Ayudanos a encontrarlo ...", getApplicationContext(),ReportarEncontradoActivity.this);

                notificationsSender.SendNotifications();*/

                EnviarReporte(view);
            }
        });



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

    private void showCameraPreview() {
        // BEGIN_INCLUDE(startCamera)
        // Check if the Camera permission has been granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission is already available, start camera preview
            Snackbar.make(mLayout,
                    R.string.camera_permission_available,
                    Snackbar.LENGTH_SHORT).show();
            startCamera();
        } else {
            // Permission is missing and must be requested.
            requestCameraPermission();
        }
        // END_INCLUDE(startCamera)
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
    private void requestCameraPermission() {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // Display a SnackBar with cda button to request the missing permission.
            Snackbar.make(mLayout, R.string.camera_access_required,
                    Snackbar.LENGTH_INDEFINITE).setAction(R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Request the permission
                    ActivityCompat.requestPermissions(ReportarEncontradoActivity.this,
                            new String[]{Manifest.permission.CAMERA},
                            PERMISSION_REQUEST_CAMERA);
                }
            }).show();

        } else {
            Snackbar.make(mLayout, R.string.camera_unavailable, Snackbar.LENGTH_SHORT).show();
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
        }
    }


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
                    ActivityCompat.requestPermissions(ReportarEncontradoActivity.this,
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
        myRef=database.getReference("Reportes");
        j=0;
        generoS=genero.getSelectedItem().toString();
        razaS=raza.getSelectedItem().toString();
        tamañoS= tamaño.getSelectedItem().toString();
        descripcionS=descripcion.getText().toString();
        ubicacionS=ubicacion.getText().toString();
        if (validateForm(generoS,razaS,tamañoS,descripcionS,ubicacionS, foto)) {

            keyReportes = myRef.push().getKey();
            myRef = database.getReference(PATH_REPORTES + "Encontrados/" + keyReportes);
            //uploadFile(foto, mStorageRef, key);
            setListener();
            Reporte reporte = new Reporte();
            reporte.setGenero(generoS);
            reporte.setRaza(razaS);
            reporte.setTamaño(tamañoS);
            reporte.setDescripción(descripcionS);
            reporte.setUbicacion(ubicacionS);

            reporte.setFotoURL(fotoURL);
            myRef.setValue(reporte);
            Toast.makeText(ReportarEncontradoActivity.this, "Reporte creado exitosamente", Toast.LENGTH_LONG).show();

            genero.setSelection(0);
            raza.setSelection(0);
            descripcion.setText("");
            ubicacion.setText("");
            tamaño.setSelection(0);
            foto.setImageBitmap(null);
        }



    }

    public void setListener() {
        uploadFile(foto, mStorageRef, keyReportes,new MyResponseListener() {
            @Override
            public void onSuccess() {
                //handle success
            }

            @Override
            public void onFailure() {
                //hanlde failure
            }
        });
    }

    private boolean validateForm(String generoS, String razaS, String tamañoS, String descripcionS, String ubicacionS, ImageView foto) {
        boolean esValido=true;

        generoS.trim();
        if(TextUtils.isEmpty(generoS)){
            esValido=false;
            Toast.makeText(ReportarEncontradoActivity.this, "Se debe seleccionar un genero", Toast.LENGTH_LONG).show();
        }

        tamañoS.trim();
        if(TextUtils.isEmpty(tamañoS)){
            esValido=false;
            Toast.makeText(ReportarEncontradoActivity.this, "Se debe seleccionar un tamaño", Toast.LENGTH_LONG).show();
        }

        razaS.trim();
        if(TextUtils.isEmpty(razaS)){
            esValido=false;
            Toast.makeText(ReportarEncontradoActivity.this, "Se debe seleccionar una raza", Toast.LENGTH_LONG).show();
        }

        descripcionS.trim();
        if(TextUtils.isEmpty(descripcionS)){
            esValido=false;
            descripcion.setError("Campo Requerido");
        }

        ubicacionS.trim();
        if(TextUtils.isEmpty(ubicacionS)){
            esValido=false;
            ubicacion.setError("Campo Requerido");
        }


        return esValido;
    }



    private void uploadFile(ImageView imageView, StorageReference mStorageRef, String key,final MyResponseListener myResponseListener){
        // Get the data from an ImageView as bytes
        foto.setDrawingCacheEnabled(true);
        foto.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) foto.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = this.mStorageRef.child(key).putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                myResponseListener.onFailure();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                SystemClock.sleep(5000);
                mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                    @Override
                    public void onSuccess(Uri uri) {
                        // getting image uri and converting into string
                        fotoURL = uri.toString();
                        String uploadId = myRef.push().getKey();
                        myRef.child("fotoURL").setValue(fotoURL);
                    }


                });
            }
        });

    }

    private void guardarURL(String fotoURLS) {
        fotoURL=fotoURLS;
    }

    public interface MyResponseListener {

        void onSuccess();

        void onFailure();
    }


}