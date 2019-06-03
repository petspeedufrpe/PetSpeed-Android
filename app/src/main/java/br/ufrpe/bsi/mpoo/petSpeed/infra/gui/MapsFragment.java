package br.ufrpe.bsi.mpoo.petSpeed.infra.gui;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Toast;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import br.ufrpe.bsi.mpoo.petSpeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.negocio.ClienteServices;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.persistencia.ClienteDAO;
import br.ufrpe.bsi.mpoo.petSpeed.infra.app.PetSpeedApp;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.ConvertLatLngToKm;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petSpeed.medico.negocio.MedicoServices;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Endereco;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Pessoa;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.negocio.PessoaServices;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.persistencia.EnderecoDAO;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.dominio.Usuario;

public class MapsFragment extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener {

    private Context mContext;
    private GoogleMap mMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);

        mContext = getActivity().getBaseContext();
        if (Build.VERSION.SDK_INT >= 23) {
            control();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        InitalizeMap();
        String bairro = getBairroCliente();
        ArrayList<Endereco> list = getAllAddressByBairro(bairro);
        ArrayList<Marker> markers = addMutilpeMarkersOnMap(list);
        mMap.setOnMarkerClickListener(this);

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

    public ArrayList<Marker> addMutilpeMarkersOnMap(ArrayList<Endereco> enderecos) {
        int i = 0;
        ArrayList<Marker> list = new ArrayList<>();
        enderecos.size();
        while (i < enderecos.size()) {
            double lat = enderecos.get(i).getLatidude();
            double lng = enderecos.get(i).getLongitude();
            LatLng latLng = new LatLng(lat, lng);
            PessoaServices pessoaServices = new PessoaServices();
            MedicoServices medicoServices = new MedicoServices();
            Endereco endereco = enderecos.get(i);
            long idPessoa = endereco.getFkPessoa();
            Pessoa pessoa = pessoaServices.getPessoaCompleta(idPessoa);
            String nome = pessoa.getNome();
            String aval = String.valueOf(medicoServices.getAvaliacaoByIdPessoa(pessoa.getId()));
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

    public Endereco getAddressByName() {//recuperar a lat e lgn pelo nome do endereco
        Usuario usuario = Sessao.instance.getUsuario();
        ClienteServices clienteServices = new ClienteServices();
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = clienteDAO.getIdClienteByUsuario(usuario.getId());
        cliente = clienteServices.getClienteCompleto(cliente.getId());
        Endereco endereco = cliente.getDadosPessoais().getEndereco();

        return endereco;
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


    public LatLng getAddressClienteByLatLng() {//Passando a lat e lgn do banco de dados sem fazer request do geocoder
        Usuario usuario = Sessao.instance.getUsuario();
        ClienteServices clienteServices = new ClienteServices();
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = clienteDAO.getIdClienteByUsuario(usuario.getId());
        cliente = clienteServices.getClienteCompleto(cliente.getId());
        Endereco endereco = cliente.getDadosPessoais().getEndereco();
        Double lat = endereco.getLatidude();
        Double lgn = endereco.getLongitude();
        LatLng latLng = new LatLng(lat, lgn);
        return latLng;
    }

    public String getBairroCliente() {
        Cliente cliente = Sessao.instance.getCliente();
        return cliente.getDadosPessoais().getEndereco().getBairro();
    }

    public void InitalizeMap() {
        UiSettings mapSettings;
        mapSettings = mMap.getUiSettings();
        mapSettings.setZoomControlsEnabled(true);
        mapSettings.setZoomGesturesEnabled(true);
        mapSettings.setMyLocationButtonEnabled(true);
        mMap.resetMinMaxZoomPreference();
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            control();
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationClickListener(this);
        mMap.setOnMyLocationButtonClickListener(this);

    }


    //LOCATION IN REAL TIME BY DEVICE

    public void getLocation() {
        if(ActivityCompat.checkSelfPermission(mContext,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
        ActivityCompat.checkSelfPermission(mContext,Manifest.permission.ACCESS_COARSE_LOCATION )== PackageManager.PERMISSION_GRANTED);
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        Log.e("qwe", String.valueOf(lat));
        Log.e("qwe", String.valueOf(lng));
    }


    public void control() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(getActivity(), permissions,0);

        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(mContext,"My l Clicked", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(mContext,location.toString(),Toast.LENGTH_SHORT).show();

    }
}