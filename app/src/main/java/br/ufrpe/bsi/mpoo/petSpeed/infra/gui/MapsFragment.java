package br.ufrpe.bsi.mpoo.petSpeed.infra.gui;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.bsi.mpoo.petSpeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petSpeed.infra.app.PetSpeedApp;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petSpeed.medico.dominio.Medico;
import br.ufrpe.bsi.mpoo.petSpeed.medico.negocio.MedicoServices;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Endereco;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Pessoa;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.negocio.PessoaServices;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.persistencia.EnderecoDAO;

public class MapsFragment extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,/*LocationListener*/GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {



    private static final String REQUESTING_LOCATION_UPDATES_KEY = "requesting Update";
    private MedicoServices medicoServices = new MedicoServices();
    private Context mContext;
    private GoogleMap mMap;
    private LocationManager mLocationManager;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 101;
    private final LatLng mDefaultLocation = defaultLocationCLient();
    private Location mLocation = new Location(LocationManager.GPS_PROVIDER);
    private LocationRequest locationRequest;
    private GoogleApiClient mGoogleApiClient;
    private List<Medico> listMedicos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);
        mContext = getActivity().getBaseContext();
        callConnections();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            startLocationUpdate();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mGoogleApiClient != null && mGoogleApiClient.isConnected())  {
            stopLocationRequest();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        createNoGpsDialog();
        InitalizeMap();
        String bairro = getBairroCliente();
        setTypeOfSearch();
        ArrayList<Endereco> list = getAllAddressByBairro(bairro);
        ArrayList<Marker> markers = addMutilpeMarkersOnMap(listMedicos);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        mMap.setOnMyLocationButtonClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Integer clickCount = (Integer) marker.getTag();
        if (clickCount != null) {
            clickCount++;
            marker.setTag(clickCount);
        }
        LatLng location = marker.getPosition();
        Toast.makeText(PetSpeedApp.getContext(), location.toString(), Toast.LENGTH_SHORT).show();
        return false;
    }


    public ArrayList<Marker> addMutilpeMarkersOnMap(List<Medico> listMedicos) {
        int i = 0;
        ArrayList<Marker> list = new ArrayList<>();
        listMedicos.size();
        while (i < listMedicos.size()) {
            double lat = listMedicos.get(i).getDadosPessoais().getEndereco().getLatidude();
            double lng = listMedicos.get(i).getDadosPessoais().getEndereco().getLongitude();
            LatLng latLng = new LatLng(lat, lng);
            Medico medico = listMedicos.get(i);
            String nome = medico.getDadosPessoais().getNome();
            String aval = String.valueOf(medico.getAvaliacao());
            list.add(addMarkerOnMap(latLng, nome, aval));
            i++;
        }
        return list;
    }


    public Marker addMarkerOnMap(LatLng latLng, String title, String avaliacao) {
        Marker marker;
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(title);
        markerOptions.snippet(avaliacao);
        markerOptions.draggable(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        marker = mMap.addMarker(markerOptions);
        marker.showInfoWindow();
        return marker;
    }

    public ArrayList<Endereco> getAllAddressByBairro(String bairro) {
        ArrayList<Endereco> enderecoArrayList = new ArrayList<>();
        EnderecoDAO enderecoDAO = new EnderecoDAO();
        enderecoArrayList = enderecoDAO.getAllAddressByBairro(bairro);

        if (enderecoArrayList.isEmpty()) {
            return null;
        }
        return enderecoArrayList;
    }

    public void setTypeOfSearch() {

        Cliente cliente = Sessao.instance.getCliente();
        double lat = cliente.getDadosPessoais().getEndereco().getLatidude();
        double lng = cliente.getDadosPessoais().getEndereco().getLongitude();
        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            listMedicos = medicoServices.getMedicosInRaio(5,lat,lng);

        }
    }

    public String getBairroCliente() {
        Cliente cliente = Sessao.instance.getCliente();
        return cliente.getDadosPessoais().getEndereco().getBairro();
    }

    public LatLng defaultLocationCLient() {
        Cliente cliente = Sessao.instance.getCliente();
        return new LatLng(cliente.getDadosPessoais().getEndereco().getLatidude(), cliente.getDadosPessoais().getEndereco().getLongitude());
    }

    public void InitalizeMap() {

        UiSettings mapSettings;
        mapSettings = mMap.getUiSettings();
        mapSettings.setZoomControlsEnabled(true);
        mapSettings.setZoomGesturesEnabled(true);
        mapSettings.setMyLocationButtonEnabled(true);
        enableMyLocation();
        mMap.resetMinMaxZoomPreference();
        mMap.setOnMyLocationClickListener(this);
        mMap.setOnMyLocationButtonClickListener(this);

    }


    public void enableMyLocation() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(mContext, mLocation.toString(), Toast.LENGTH_SHORT).show();
        return false;

    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(mContext, location.toString(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                            ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
                    } else {
                        Toast.makeText(mContext, "This app requires Location permissions to be granted", Toast.LENGTH_LONG).show();
                    }
                }
        }
    }

    private void createNoGpsDialog() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent callGPSSettingIntent = new Intent(
                                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(callGPSSettingIntent);
                        break;
                }
            }
        };

        if ((!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            AlertDialog mNoGpsDialog = builder.setMessage("Por favor ative seu GPS para usar esse aplicativo.")
                    .setPositiveButton("Ativar", dialogClickListener)
                    .create();
            mNoGpsDialog.show();
        }

    }



    private synchronized void callConnections() {
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location != null) {
            Log.i("LOG", location.toString());
            mLocation = location;
        }
        startLocationUpdate();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if(location!=null){
            mLocation.set(location);
            Toast.makeText(mContext, location.toString(), Toast.LENGTH_LONG).show();
            setTypeOfSearch();
        }

    }

    public void initLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(30000);
        locationRequest.setFastestInterval(15000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public void startLocationUpdate() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        initLocationRequest();
        LocationServices.getFusedLocationProviderClient(mContext);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, MapsFragment.this);
    }

    private void stopLocationRequest() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, MapsFragment.this);
    }

}

/**
    //LOCATION IN REAL TIME BY DEVICE  && PERMISSIONS AND HANDLE THE GPS AND MY LOCATION IN GOOGLE MAPS

    public void initLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        SettingsClient settingsClient = LocationServices.getSettingsClient(mContext);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());
        task.addOnSuccessListener(getActivity(), new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
            }
        });
        task.addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {

                    ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                    try {
                        resolvableApiException.startResolutionForResult(getActivity(), 101);
                    } catch (IntentSender.SendIntentException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (requestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putBoolean(REQUESTING_LOCATION_UPDATES_KEY,requestingLocationUpdates);
        super.onSaveInstanceState(bundle);
    }

    public void upDateValuesFromBundle(Bundle bundle){
        if (bundle == null){
            return;
        }
        if (bundle.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)){
            requestingLocationUpdates = bundle.getBoolean(REQUESTING_LOCATION_UPDATES_KEY);
        }
    }
    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }


    @Override
    public void onLocationChanged(Location location) {
        if (location!=null){
            mLocation = location;
        }
    }
}


    /**
    //LOCATION IN REAL TIME BY DEVICE  && PERMISSIONS AND HANDLE THE GPS AND MY LOCATION IN GOOGLE MAPS


    public void deviceLocation() {
        try {
            if (mLocationPermissionGranted) {
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Task locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(getActivity(), new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            mLastKnownLocation = (Location) task.getResult();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude())
                                    , 15));
                        } else {
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, 15));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }

                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    public void initRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder build = new LocationSettingsRequest.Builder().addAllLocationRequests(Collections.singleton(locationRequest));
        SettingsClient client = LocationServices.getSettingsClient(mContext);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(build.build());
        task.addOnSuccessListener(getActivity(), new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {

            }
        });

        task.addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    try {
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(getActivity(), 101);

                    } catch (IntentSender.SendIntentException sendEx) {

                    }
                }
            }
        });
    }

    public void getDeviceLocation() { // TEST THIS METHOD NOT PROPERLY WORKING
        try {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Task<Location> locationTask = mFusedLocationProviderClient.getLastLocation();
                locationTask.addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            mLastKnownLocation = task.getResult();
                            Toast.makeText(mContext, "New Location" + mLastKnownLocation.toString(), Toast.LENGTH_LONG).show();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), 15));
                        } else {
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mDefaultLocation.latitude, mDefaultLocation.longitude), 15f));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exepction: %s", e.getMessage());
        }
    }

    public void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mFusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            mLastKnownLocation = location;
                        }

                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (requestingLocationsUpdate) {
            startLocationUpdates();
        }
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mFusedLocationProviderClient.requestLocationUpdates(locationRequest
                , locationCallback, null);
    }
}
**/