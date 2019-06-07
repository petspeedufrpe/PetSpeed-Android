package br.ufrpe.bsi.mpoo.petspeed.animal.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.animal.dominio.Animal;
import br.ufrpe.bsi.mpoo.petspeed.animal.negocio.AnimalServices;
import br.ufrpe.bsi.mpoo.petspeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petspeed.cliente.gui.AnimalClienteActivity;
import br.ufrpe.bsi.mpoo.petspeed.cliente.persistencia.ClienteDAO;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petspeed.usuario.dominio.Usuario;

public class CrudAnimalActivity extends AppCompatActivity {
    AnimalServices animalServices = new AnimalServices();
    EditText mNome,mRaca,mIdade,mPeso;
    String nome,raca,idade,peso;
    Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_crud_animal);
        Toast.makeText(this,peso,Toast.LENGTH_SHORT).show();
        btnCadastrar = (Button) findViewById(R.id.btn_cadastrar_animal);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrar();
                startActivity(new Intent(CrudAnimalActivity.this, AnimalClienteActivity.class));
            }
        });
    }

    public void findEditTexts(){
        mNome = (EditText) findViewById(R.id.edt_nome_animal);
        mRaca = (EditText) findViewById(R.id.edt_raca_animal);
        mIdade = (EditText) findViewById(R.id.edt_peso_animal);
        mPeso = (EditText) findViewById(R.id.edt_idade_animal);

    }

    public void capturaTextos(){
        findEditTexts();
        nome = mNome.getText().toString().trim();
        raca = mRaca.getText().toString().trim();
        idade = mIdade.getText().toString().trim();
        peso = mPeso.getText().toString().trim();
    }

    public void cadastrar(){
        capturaTextos();
        Usuario usuario = Sessao.instance.getUsuario();
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = clienteDAO.getIdClienteByUsuario(usuario.getId());
        Animal animal = createAnimal();
        animal.setFkCliente(cliente.getId());
        long res = animalServices.cadastraAnimal(animal);
        animal.setId(res);

    }


    public Animal createAnimal(){
        Animal animal = new Animal();
        animal.setNome(nome);
        animal.setRaca(raca);
        animal.setPeso(Float.parseFloat(peso));
        animal.setIdade(Integer.parseInt(idade));

        return animal;
    }
}
