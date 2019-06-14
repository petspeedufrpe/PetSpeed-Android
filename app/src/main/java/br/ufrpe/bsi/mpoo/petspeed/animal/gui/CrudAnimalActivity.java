package br.ufrpe.bsi.mpoo.petspeed.animal.gui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.animal.dominio.Animal;
import br.ufrpe.bsi.mpoo.petspeed.animal.negocio.AnimalServices;
import br.ufrpe.bsi.mpoo.petspeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petspeed.cliente.gui.AnimalClienteActivity;
import br.ufrpe.bsi.mpoo.petspeed.cliente.gui.EditDadosClienteActivity;
import br.ufrpe.bsi.mpoo.petspeed.cliente.persistencia.ClienteDAO;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petspeed.usuario.dominio.Usuario;

import static android.os.Environment.getExternalStoragePublicDirectory;

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
    private static final int PERMISSION_REQUEST = 0;
    private static final int REQUEST_GALLERY = 2;
    private static final int REQUEST_CAPTURE = 1;
    private ImageView mImagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_crud_animal);
        Button btnCadastrar = findViewById(R.id.btn_cadastrar_animal);
        mImagem = findViewById(R.id.campo_imagem);
        ImageView btnAddFoto = findViewById(R.id.img_view_animal_cadastro);
        btnAddFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPermissionsGaleria();
                //takePhoto();
            }
        });
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
        //salvarFoto();

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

    private void takePhoto() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            photoFile = createPhotoFile();
            if (photoFile != null) {
                Uri photoUri = FileProvider.getUriForFile(CrudAnimalActivity.this, "br.ufrpe.bsi.mpoo.petSpeed", photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, REQUEST_CAPTURE);
            }
        }

    }
    private File createPhotoFile() {

        String name = new SimpleDateFormat("ddMMyyy_HHmmss").format(new Date());
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(name, ".jpg", storageDir);
        } catch (IOException e) {
            Log.d("My Tag", "Error " + e.toString());
        }

        return image;

    }
    private void getPermissionsGaleria() {
        int permissionCheckRead = ContextCompat.checkSelfPermission(CrudAnimalActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheckRead != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(CrudAnimalActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
            } else {
                ActivityCompat.requestPermissions(CrudAnimalActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
            }
        } else {
            abrirGaleriaIntent();
        }
    }

    private void abrirGaleriaIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecione a foto"), REQUEST_GALLERY);
    }

    private void salvarFoto() {
        byte[] foto = conveterImageViewToByte();
        createAnimal().setFoto(foto);
        //clienteServices.alteraFotoCliente(cliente);
    }

    private byte[] conveterImageViewToByte() {
        Bitmap bitmap = ((BitmapDrawable) mImagem.getDrawable()).getBitmap();
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, saida);
        return saida.toByteArray();
    }

    private Bitmap getThumbnailFromBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int max = Math.max(width, height);
        if (max > 512) {
            int thumbWidth = Math.round((512f / max) * width);
            int thumbHeight = Math.round((512f / max) * height);
            Bitmap thumbnail = ThumbnailUtils.extractThumbnail(bitmap, thumbWidth, thumbHeight);
            bitmap.recycle();
            return thumbnail;
        } else {
            return bitmap;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQUEST_GALLERY:
                if (resultCode == RESULT_OK) {
                    try {
                        Uri img = data.getData();
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(img));
                        bitmap = getThumbnailFromBitmap(bitmap);
                        mImagem.setImageBitmap(bitmap);
                        salvarFoto();
                        Toast.makeText(this, "Foto Alterada Com sucesso", Toast.LENGTH_SHORT).show();
                        break;
                    } catch (FileNotFoundException e) {

                        Toast.makeText(this, "Foto não encontrada", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case REQUEST_CAPTURE:
                if (resultCode == RESULT_OK && requestCode == 1) {
                    Uri img = data.getData();
                    Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(img));
                    mImagem.setImageBitmap(bitmap);
                }
                break;
            default:
                break;
        }

    }

}
