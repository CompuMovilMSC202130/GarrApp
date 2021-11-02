package com.example.garrapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.garrapp.databinding.ActivityMapsBinding;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.garrapp.utilidades.FetchURL;
import com.example.garrapp.utilidades.TaskLoadedCallback;
import android.graphics.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, TaskLoadedCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private Marker searchMarker;
    private Marker touchMarker;
    private Marker locationMarker;
    LatLng aLatLng = new LatLng(4.65, -74.05);
    LatLng destination;
    private Button ruta;

    // botones de barra inferior
    BottomNavigationView bottomNavigationView;

    String locationPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final int PERMISSION_LOCATION = 2;

    // Light Sensor
    SensorManager sensorManager;
    Sensor lightSensor;
    SensorEventListener lightSensorListener;

    //Geocoder
    Geocoder mGeocoder;

    Location newLocation = new Location("");

    //Simple location Atributtes
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    static final int REQUEST_CHECK_SETTINGS = 6;
    private final int RADIUS_OF_EARTH_KM = 6371;
    Polyline currentPolyline;
    private List<Marker> markers = new ArrayList<Marker>();
    boolean isGPSEnabled = false;


    /****************************      ON CREATE  ***********************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = createLocationRequest();
        locationCallback = createLocationCallback();
        myRequestPermission(this, locationPermission, "Access to GPS", PERMISSION_LOCATION);


        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // botones de barra inferior

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.Mapa);

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
                        return true;

                    case R.id.Noticias:
                        startActivity(new Intent(getApplicationContext(),NoticiasActivity.class));
                        overridePendingTransition(1,0);
                        return true;
                }

                return false;
            }
        });


        mGeocoder = new Geocoder(this);

        // Light Sensor
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        lightSensorListener = createSensorEventListener();

        // Location


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        ruta = (Button) findViewById(R.id.botonRuta);

        ruta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateRoute("driving");
            }
        });
    }

    /***********************************************************************************************
     *                                          Location Request
     **********************************************************************************************/

    private LocationRequest createLocationRequest(){
        LocationRequest locationRequest = LocationRequest.create()
                .setInterval(10000)
                .setFastestInterval(5000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }
    /***********************************************************************************************
     *                                          LocationCallback
     **********************************************************************************************/

    private LocationCallback createLocationCallback(){

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location = locationResult.getLastLocation();

                if(location != null){
                    Log.i ( "TAG" , "location" + location.toString());
                    LatLng bogota = new LatLng(location.getLatitude(), location.getLongitude()); // Guardamos la posición
                    /************************  JSON***********************************************/
                    if (locationMarker == null){
                        locationMarker = mMap.addMarker(new MarkerOptions().position(bogota).title("Usted esta aqui"));
                    }

                    /********************* Comparar con el anterior*******************************/
                    Location locationOne = new Location("");
                    locationOne.setLatitude(aLatLng.latitude);
                    locationOne.setLongitude(aLatLng.longitude);
                    /********************* Guardar la localización ******************************/
                    newLocation = location;
                    /***********************Inicializar con un puntero *************************/

                    float distanceInMetersOne = location.distanceTo(locationOne);
                    if( distanceInMetersOne > 5){
                        locationMarker.remove();
                        locationMarker = mMap.addMarker(new MarkerOptions().position(bogota).title("Usted esta aqui"));
                        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(bogota));
                        //Habilitar los "gestures" como pitch to zoom"
                        mMap.getUiSettings().setZoomGesturesEnabled(true);
                        //Habilitar botones de zoom
                        mMap.getUiSettings().setZoomControlsEnabled(true);
                        aLatLng = bogota;
                    }
                }

            }
        };

        return locationCallback;
    }



    private SensorEventListener createSensorEventListener(){
        SensorEventListener lightSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (mMap != null) {
                    if (sensorEvent.values[0] <5000) {
                        Log.i("MAPS", "DARK MAP " + sensorEvent.values[0]);
                        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(MapsActivity.this, R.raw.dark_style_map));
                    } else {
                        Log.i("MAPS", "LIGHT MAP " + sensorEvent.values[0]);
                        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(MapsActivity.this, R.raw.light_style_map));
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {}
        };
        return lightSensorListener;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override

            public void onMapLongClick(@NonNull LatLng latLng) {

               String name = searchByLocation(latLng.latitude , latLng.longitude);

                if(!"".equals(name)){
                    if(touchMarker!=null) touchMarker.remove();
                    touchMarker = mMap.addMarker(new MarkerOptions()
                            .position(latLng).title(name)
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    destination = new LatLng(latLng.latitude,latLng.longitude);

                }
            }
        });
  }

    @Override
    protected void onResume() {
        super.onResume();
        checkSettingsLocation();
        sensorManager.registerListener(lightSensorListener, lightSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
        sensorManager.unregisterListener(lightSensorListener);

    }

    private void checkSettingsLocation() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                isGPSEnabled = true;
                startLocationUpdate();
            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                int statusCode = ((ApiException) e).getStatusCode();
                switch (statusCode) {
                    case CommonStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied, but this can be fixed by showing the user a dialog.
                        try {// Show the dialog by calling startResolutionForResult(), and check the result in onActivityResult().
                            ResolvableApiException resolvable = (ResolvableApiException) e;
                            resolvable.startResolutionForResult(MapsActivity.this,
                                    REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException sendEx) {

                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. No way to fix the settings so we won't show the dialog.


                        break;
                }
            }
        });
    }

    private void startLocationUpdate(){
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            if(isGPSEnabled){
                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
            }

        }
    }

    private void stopLocationUpdates(){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == RESULT_OK) {
                isGPSEnabled = true;
                startLocationUpdate();
            } else {
                startActivity(new Intent(MapsActivity.this, MainActivity.class));
                Toast.makeText(this, "No se puede usar esta funcionalidad", Toast.LENGTH_LONG).show();
            }
        }
    }

    // ***********************************************************************************************
    // **         Devuelve el nombre de una ubicación a partir de las coordenadas
    // **********************************************************************************************/

    private String searchByLocation(double latitude , double longitude){
        try {
            List<Address> addresses = mGeocoder.getFromLocation(latitude, longitude,2);
            if (addresses != null && !addresses.isEmpty()) {
                Address addressResult = addresses.get(0);
                String namePos = new String(addressResult.getAddressLine(0).toString());
                Location oldLocation = new Location("");
                oldLocation.setLatitude(latitude);
                oldLocation.setLongitude(longitude);

                /**************************** Calcula distancia **********************************/
                float distanceInMetersOne = oldLocation.distanceTo(newLocation);
                //Distancia
                Toast.makeText(this, "Distancia " + distanceInMetersOne + " m" , Toast.LENGTH_LONG).show();
                return  namePos;

            } else {Toast.makeText(MapsActivity.this, "Nombre no encontrada", Toast.LENGTH_SHORT).show();}
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "**";

    }

    /***********************************************************************************************
     **                                 PERMISOS DE LOCALIZACION
     **********************************************************************************************/


    private void myRequestPermission(Activity context, String permission, String justification, int id){
        // Si no tengo permisos
        if(ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(context, permission)){
                Toast.makeText(context, justification, Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(context, new String[]{permission}, id);
        }
    }

    /***********************************************************************************************
     **                                 GENERAR RUTA ENTRE DOS PUNTOS
     **********************************************************************************************/

    private void generateRoute (String transport){

        if (destination == null){
            Toast.makeText(this, "No se encuentra punto de destino", Toast.LENGTH_SHORT).show();
        }
        else {
            String address = searchByLocation(destination.latitude,destination.longitude);
            if (destination != null && mMap != null) {
                if (searchMarker != null) {
                    markers.remove(searchMarker);
                    searchMarker.remove();
                }
                searchMarker = mMap.addMarker(new MarkerOptions().position(destination).title(address).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                markers.add(searchMarker);
                zoomMarkers();
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(position));

                String url = getUrl(destination.latitude,destination.longitude,locationMarker.getPosition().latitude,locationMarker.getPosition().longitude,transport);
                new FetchURL(this).execute(url,transport);

                double distancia = distance(destination.latitude,destination.longitude,locationMarker.getPosition().latitude,locationMarker.getPosition().longitude);
                Toast.makeText(this, "Distancia hasta el punto buscado: "+distancia, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /***********************************************************************************************
     **                                MÉTODO PARA OBTENER RUTA DESDE API DE MAPS
     **********************************************************************************************/

    private String getUrl(double destinationLatitude, double destinationLongitude, double originLatitude, double originLongitude, String transport) {
        String str_origin = "origin=" + originLatitude + "," + originLongitude;
        String str_destination = "destination=" + destinationLatitude + "," + destinationLongitude;
        String mode = "mode=" + transport;
        String parameters = str_origin + "&" + str_destination + "&" + mode;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    /***********************************************************************************************
     **                                METODO QUE CALCULA LA DISTANCIA ENTRE DOS PUNTOS
     **********************************************************************************************/

    //metodo para calcular la distancia entre dos puntos
    public double distance(double lat1, double long1, double lat2, double long2) {
        double latDistance = Math.toRadians(lat1 - lat2);
        double lngDistance = Math.toRadians(long1 - long2);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)); double result = RADIUS_OF_EARTH_KM * c;
        return Math.round(result*100.0)/100.0;
    }


    /***********************************************************************************************
     **                                METODO QUE DIBUJA LA RUTA ENTRE DOS PUNTOS
     **********************************************************************************************/

    @Override
    public void onTaskDone(Object... values) {
        if( currentPolyline != null){
            currentPolyline.remove();
        }
        currentPolyline = mMap.addPolyline((PolylineOptions)values[0]);
        currentPolyline.setColor(Color.MAGENTA);

    }


    private void zoomMarkers() {
        if (markers.size() > 0 && markers != null) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (Marker marker : markers) {
                builder.include(marker.getPosition());
            }
            LatLngBounds bounds = builder.build();
            int padding = 200; // offset from edges of the map in pixels
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            mMap.animateCamera(cu);
        }
    }

}











