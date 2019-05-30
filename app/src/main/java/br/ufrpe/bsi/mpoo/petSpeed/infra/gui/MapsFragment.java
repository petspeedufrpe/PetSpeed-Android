package br.ufrpe.bsi.mpoo.petSpeed.infra.gui;

import android.os.Bundle;
import android.widget.Toast;

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
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Endereco;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Pessoa;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.negocio.PessoaServices;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.persistencia.EnderecoDAO;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.dominio.Usuario;

public class MapsFragment extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        InitalizeMap();
        String bairro = getBairroCliente();
        ArrayList<Endereco> list = getAddressByBairro(bairro);
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
            Endereco endereco = enderecos.get(i);
            long idPessoa = endereco.getFkPessoa();
            Pessoa pessoa = pessoaServices.getPessoaCompleta(idPessoa);
            String nome = pessoa.getNome();
            String tel = "Telefone do Médico";
            list.add(addMarkerOnMap(latLng, nome, tel));
            i++;
        }
        return list;
    }

    public Marker addMarkerOnMap(LatLng latLng, String title, String tel) {
        Marker marker;
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(title);
        markerOptions.snippet(tel);
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

    public ArrayList<Endereco> getAddressByBairro(String bairro) {
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
        Usuario usuario = Sessao.instance.getUsuario();
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = clienteDAO.getIdClienteByUsuario(usuario.getId());
        ClienteServices clienteServices = new ClienteServices();
        cliente = clienteServices.getClienteCompleto(cliente.getId());

        return cliente.getDadosPessoais().getEndereco().getBairro();
    }

    public void goToCurrentLocation() {
    }

    public void InitalizeMap() {
        UiSettings mapSettings;
        mapSettings = mMap.getUiSettings();
        mapSettings.setZoomControlsEnabled(true);
        mapSettings.setZoomGesturesEnabled(true);
        mapSettings.setMyLocationButtonEnabled(true);
        mMap.resetMinMaxZoomPreference();

    }


}