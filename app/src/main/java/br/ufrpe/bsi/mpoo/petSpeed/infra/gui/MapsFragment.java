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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufrpe.bsi.mpoo.petSpeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.gui.HomeClienteActivity;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.gui.ViewMedicosFragment;
import br.ufrpe.bsi.mpoo.petSpeed.infra.app.PetSpeedApp;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.ContasDeUsuario;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.FragmentDataTransferInterface;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petSpeed.medico.dominio.Medico;
import br.ufrpe.bsi.mpoo.petSpeed.medico.negocio.MedicoServices;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Endereco;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.persistencia.EnderecoDAO;

public class MapsFragment extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener{


    private double raio = 5.0;
    private GoogleMap mMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 101;
    private MedicoServices medicoServices = new MedicoServices();
    private Context mContext;
    private LocationManager mLocationManager;
    Cliente cliente = Sessao.instance.getCliente();
    double lat = cliente.getDadosPessoais().getEndereco().getLatitude();
    double lng = cliente.getDadosPessoais().getEndereco().getLongitude();
    private Location mLocation = new Location(LocationManager.GPS_PROVIDER);
    private LocationRequest locationRequest;
    private GoogleApiClient mGoogleApiClient;
    private List<Medico> listMedicos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);
        mContext = getActivity().getBaseContext();
        mLocation.setLatitude(lat);
        mLocation.setLongitude(lng);
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

        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            stopLocationRequest();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        createNoGpsDialog();
        InitalizeMap();
        setTypeOfSearch();
        ArrayList<Marker> markers = addMutilpeMarkersOnMap(listMedicos);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Medico medico;
        Cliente cliente;
        Map markerMap = (Map<ContasDeUsuario, Object>) marker.getTag();
        if (markerMap.containsKey(ContasDeUsuario.MEDICO)) {
            medico = (Medico) markerMap.get(ContasDeUsuario.MEDICO);
            Sessao.instance.setValue(ContasDeUsuario.MEDICO.getDescricao(),medico);

        }
        HomeClienteActivity hCliente = (HomeClienteActivity) getActivity();

        Sessao.instance.setValue("HostActivity",hCliente);
        ViewMedicosFragment viewPinMedico = new ViewMedicosFragment();
        viewPinMedico.show(getFragmentManager(), "ViewMedicosFragment");
        LatLng location = marker.getPosition();
        return false;
    }


    public ArrayList<Marker> addMutilpeMarkersOnMap(List<Medico> listMedicos) {
        int i = 0;
        ArrayList<Marker> list = new ArrayList<>();
        listMedicos.size();
        while (i < listMedicos.size()) {
            double lat = listMedicos.get(i).getDadosPessoais().getEndereco().getLatitude();
            double lng = listMedicos.get(i).getDadosPessoais().getEndereco().getLongitude();
            LatLng latLng = new LatLng(lat, lng);
            Medico medico = listMedicos.get(i);
            String nome = medico.getDadosPessoais().getNome();
            String aval = String.valueOf(medico.getAvaliacao());
            Map<ContasDeUsuario, Object> mapSessao = new HashMap<>();
            mapSessao.put(ContasDeUsuario.MEDICO, (Object) medico);
            list.add(addMarkerOnMap(latLng, nome, aval, mapSessao));
            i++;
        }
        Map<ContasDeUsuario, Object> mapSessao = new HashMap<>();
        mapSessao.put(ContasDeUsuario.CLIENTE, (Object) Sessao.instance.getCliente());
        String nome = "Meu Endereço";
        Endereco endereco = Sessao.instance.getCliente().getDadosPessoais().getEndereco();
        String aval = endereco.getLogradouro() +" N° "+String.valueOf(endereco.getNumero());
        double lat = Sessao.instance.getCliente().getDadosPessoais().getEndereco().getLatitude();
        double lng = Sessao.instance.getCliente().getDadosPessoais().getEndereco().getLongitude();
        LatLng latLng = new LatLng(lat, lng);
        list.add(addMarkerOnMap(latLng, nome, aval, mapSessao));

        return list;
    }


    public Marker addMarkerOnMap(LatLng latLng, String title, String snippetInfo, Map<ContasDeUsuario, Object> mapConta) {
        Marker marker;
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(title);
        if(mapConta.containsKey(ContasDeUsuario.MEDICO)){
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            markerOptions.snippet("média: " + snippetInfo);

        } else {
            markerOptions.snippet("Local: " + snippetInfo);

            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
        }
        marker = mMap.addMarker(markerOptions);
        marker.setTag(mapConta);
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
        double lat = cliente.getDadosPessoais().getEndereco().getLatitude();
        double lng = cliente.getDadosPessoais().getEndereco().getLongitude();
        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            listMedicos = medicoServices.getMedicosInRaio(raio, mLocation.getLatitude(), mLocation.getLongitude());
        } else {
            listMedicos = medicoServices.getMedicosInRaio(raio, lat, lng);
        }
    }

    public String getBairroCliente() {
        Cliente cliente = Sessao.instance.getCliente();
        return cliente.getDadosPessoais().getEndereco().getBairro();
    }

    public LatLng defaultLocationCLient() {
        Cliente cliente = Sessao.instance.getCliente();
        return new LatLng(cliente.getDadosPessoais().getEndereco().getLatitude(), cliente.getDadosPessoais().getEndereco().getLongitude());
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
        moveCameraToMyLocation();

    }

    public void moveCameraToMyLocation() {
        LatLng latLng = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
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
        return false;

    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

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
    public void setNovoRaio(Double novoRaio) {
        this.raio = novoRaio;
        setTypeOfSearch();
        mMap.clear();
        addMutilpeMarkersOnMap(listMedicos);
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
            mLocation = location;

        }
        startLocationUpdate();
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            moveCameraToMyLocation();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            mLocation.set(location);
            setTypeOfSearch();
            mMap.clear();
            addMutilpeMarkersOnMap(listMedicos);
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

