package br.ufrpe.bsi.mpoo.petSpeed.infra.gui;

import android.app.Dialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import br.ufrpe.bsi.mpoo.petSpeed.R;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.negocio.ClienteServices;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.persistencia.ClienteDAO;
import br.ufrpe.bsi.mpoo.petSpeed.infra.app.PetSpeedApp;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.ApiGeocoder;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.ApiRequestService;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Endereco;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.negocio.PessoaServices;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.dominio.Usuario;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.persistencia.UsuarioDAO;

public class MapsFragment extends SupportMapFragment implements OnMapReadyCallback {
    private ApiGeocoder geocoder = new ApiGeocoder(PetSpeedApp.getContext());
    private GoogleMap mMap;
    private String location;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //Endereco endereco = getAddressByName();
        //location = endereco.getLogradouro();
        addMarkerOnMap(getAddressByLatLng(),"TESTE2");
        /**
        setLocation(getAddressByName());
        Toast.makeText(PetSpeedApp.getContext(),location,Toast.LENGTH_LONG).show();
        try {
            addMarkerOnMap(geocoder.getLatLgnLocation(location),"TESTE");
        } catch (IOException e) {
            e.printStackTrace();
        }**/
    }
    /**
    public void geolocation() throws IOException {
        Usuario usuario = Sessao.instance.getUsuario();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        ClienteServices clienteServices = new ClienteServices();
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = clienteDAO.getIdClienteByUsuario(usuario.getId());
        cliente = clienteServices.getClienteCompleto(cliente.getId());
        String locationName = cliente.getDadosPessoais().getEndereco().getLogradouro().concat(",").concat(cliente.getDadosPessoais().getEndereco().getComplemento());
        Toast.makeText(getContext(),locationName,Toast.LENGTH_LONG).show();
        Geocoder geocoder = new Geocoder(getContext());
        List<Address> list = geocoder.getFromLocationName(locationName,1);
        Address address = list.get(0);
        LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
        addMarkerOnMap(latLng,"Rua da Aurora");
    }
     **/
    public void addMarkerOnMap(LatLng latLng,String title){
        MarkerOptions marker = new MarkerOptions();
        marker.position(latLng);
        marker.title(title);
        mMap.addMarker(marker);
    }

    public Endereco getAddressByName(){//recuperar a lat e lgn pelo nome do endereco
        Usuario usuario = Sessao.instance.getUsuario();
        ClienteServices clienteServices = new ClienteServices();
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = clienteDAO.getIdClienteByUsuario(usuario.getId());
        cliente = clienteServices.getClienteCompleto(cliente.getId());
        Endereco endereco = cliente.getDadosPessoais().getEndereco();

        return endereco;
    }

    public String  setLocation(Endereco endereco){
        location = endereco.getLogradouro();
        return location;
    }

    public LatLng getAddressByLatLng(){//Passando a lat e lgn do banco de dados sem fazer request do geocoder
        Usuario usuario = Sessao.instance.getUsuario();
        ClienteServices clienteServices = new ClienteServices();
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = clienteDAO.getIdClienteByUsuario(usuario.getId());
        cliente = clienteServices.getClienteCompleto(cliente.getId());
        Endereco endereco = cliente.getDadosPessoais().getEndereco();
        Double lat = Double.parseDouble(endereco.getLatidude());
        Double lgn = Double.parseDouble(endereco.getLongitude());
        LatLng latLng = new LatLng(lat,lgn);

        return latLng;

    }

}
