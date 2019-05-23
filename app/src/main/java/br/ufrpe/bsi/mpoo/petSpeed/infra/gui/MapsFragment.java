package br.ufrpe.bsi.mpoo.petSpeed.infra.gui;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.negocio.ClienteServices;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.persistencia.ClienteDAO;
import br.ufrpe.bsi.mpoo.petSpeed.infra.app.PetSpeedApp;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.ApiGeocoder;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Endereco;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.dominio.Usuario;

public class MapsFragment extends SupportMapFragment implements OnMapReadyCallback {
    private ApiGeocoder geocoder = new ApiGeocoder(PetSpeedApp.getContext());
    private GoogleMap mMap;
    private String location;
    private ClienteDAO clienteDAO = new ClienteDAO();
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
    }
    public void addMarkerOnMap(LatLng latLng,String title){
        MarkerOptions marker = new MarkerOptions();
        marker.position(latLng);
        marker.title(title);
        mMap.addMarker(marker);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
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
        Double lat = endereco.getLatidude();
        Double lgn = endereco.getLongitude();
        LatLng latLng = new LatLng(lat,lgn);

        return latLng;

    }

}
