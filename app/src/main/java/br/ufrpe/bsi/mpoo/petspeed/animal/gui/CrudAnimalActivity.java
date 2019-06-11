package br.ufrpe.bsi.mpoo.petspeed.animal.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.animal.dominio.Animal;
import br.ufrpe.bsi.mpoo.petspeed.animal.negocio.AnimalServices;
import br.ufrpe.bsi.mpoo.petspeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petspeed.cliente.gui.AnimalClienteActivity;
import br.ufrpe.bsi.mpoo.petspeed.cliente.persistencia.ClienteDAO;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petspeed.usuario.dominio.Usuario;

public class CrudAnimalActivity extends AppCompatActivity {
    public static final String ERR_MSG_CAMPO_VAZIO = "Campo vazio";
    AnimalServices animalServices = new AnimalServices();
    private EditText mNome;
    private EditText mRaca;
    private EditText mIdade;
    private EditText mPeso;
    private String nome;
    private String raca;
    private String idade;
    private String peso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_crud_animal);
        Button btnCadastrar = findViewById(R.id.btn_cadastrar_animal);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturaTextos();
                if (isCamposValidos()) {
                    cadastrar();
                    startActivity(new Intent(CrudAnimalActivity.this, AnimalClienteActivity.class));
                }
            }
        });
    }

    public void findEditTexts() {
        mNome = findViewById(R.id.edt_nome_animal);
        mRaca = findViewById(R.id.edt_raca_animal);
        mPeso = findViewById(R.id.edt_peso_animal);
        mIdade = findViewById(R.id.edt_idade_animal);

    }

    public void capturaTextos() {
        findEditTexts();
        nome = mNome.getText().toString().trim();
        raca = mRaca.getText().toString().trim();
        idade = mIdade.getText().toString().trim();
        peso = mPeso.getText().toString().trim();
    }

    public void cadastrar() {


        capturaTextos();
        Usuario usuario = Sessao.instance.getUsuario();
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = clienteDAO.getIdClienteByUsuario(usuario.getId());
        Animal animal = createAnimal();
        animal.setFkCliente(cliente.getId());
        long res = animalServices.cadastraAnimal(animal);
        animal.setId(res);

    }


    public Animal createAnimal() {
        Animal animal = new Animal();
        animal.setNome(nome);
        animal.setRaca(raca);
        animal.setPeso(Double.parseDouble(peso));
        animal.setNascimento(Integer.parseInt(idade));

        return animal;
    }

    private boolean isCampoVazio(String valor) {
        return TextUtils.isEmpty(valor) || valor.trim().isEmpty();
    }

    private boolean isCamposValidos() {
        View focusView = null;
        boolean res = true;
        //reseta os erros
        mNome.setError(null);
        mRaca.setError(null);
        mIdade.setError(null);
        mPeso.setError(null);

        if (isCampoVazio(nome)) {
            mNome.setError(ERR_MSG_CAMPO_VAZIO);
            focusView = mNome;
            res = false;
        } else if (isCampoVazio(raca)) {
            mRaca.setError(ERR_MSG_CAMPO_VAZIO);
            focusView = mRaca;
            res = false;
        } else if (isCampoVazio(idade)) {
            mIdade.setError(ERR_MSG_CAMPO_VAZIO);
            focusView = mIdade;
            res = false;
        } else if (isCampoVazio(peso)) {
            mPeso.setError(ERR_MSG_CAMPO_VAZIO);
            focusView = mPeso;
            res = false;

        }
        if (!res) {
            focusView.requestFocus();
        }

        return res;
    }
}
