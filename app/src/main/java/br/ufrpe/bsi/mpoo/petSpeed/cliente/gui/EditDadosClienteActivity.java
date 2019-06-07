package br.ufrpe.bsi.mpoo.petSpeed.cliente.gui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

import br.ufrpe.bsi.mpoo.petSpeed.R;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.negocio.ClienteServices;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.persistencia.UsuarioDAO;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class EditDadosClienteActivity extends AppCompatActivity {


    private EditText mNome, mEmail, mTelefone;
    private String nome, email, telefone;
    private Button btnMudarSenha;
    private android.support.v7.widget.Toolbar toolbar;
    private Cliente cliente = Sessao.instance.getCliente();
    private ClienteServices clienteServices = new ClienteServices();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private String mCurrenPath;
    private static final int PERMISSION_REQUEST = 0;
    private static final int REQUEST_GALLERY = 2;
    private static final int REQUEST_CAPTURE = 1;
    ImageView mImagemCliente;
    ImageView cameraAlteraFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dados_cliente);
        toolbar = (Toolbar) findViewById(R.id.toolbar_altera_dados);
        setSupportActionBar(toolbar);
        mImagemCliente = (ImageView) findViewById(R.id.campo_imagem);
        cameraAlteraFoto = (ImageView) findViewById(R.id.camera_alterar_foto_cliente);
        cameraAlteraFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPermissionsGaleria();
                takePhoto();
            }
        });
        btnMudarSenha = (Button) findViewById(R.id.btn_alterar_senha);
        btnMudarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditDadosClienteActivity.this, MudarSenhaAcitivity.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_checked, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        alteraDados();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, PerfilClienteActivity.class);
        startActivity(intent);
        finish();
    }

    public void findEditTexts() {

        mNome = (EditText) findViewById(R.id.campo_altera_nome_cliente);
        mTelefone = (EditText) findViewById(R.id.campo_altera_telefone_cliente);
        mEmail = (EditText) findViewById(R.id.campo_altera_email_cliente);
    }

    public void capturaTextos() {
        findEditTexts();
        nome = mNome.getText().toString().trim();
        telefone = mTelefone.getText().toString().trim();
        email = mEmail.getText().toString().trim();
    }

    public boolean validaCampos() {
        capturaTextos();
        boolean result = true;
        View focusView = null;
        if (nome.isEmpty()) {
            mNome.setError("Campo vazio");
            focusView = mNome;
            result = false;
        } else if (telefone.isEmpty()) {
            mTelefone.setError("Campo vazio");
            focusView = mTelefone;
            result = false;
        } else if (email.isEmpty()) {
            mEmail.setError("Campo Vazio");
            focusView = mEmail;
            result = false;
        }

        if (!result) {
            focusView.requestFocus();
        }
        return result;
    }

    public boolean setNovosDados() {
        boolean result;
        if (validaCampos()) {
            cliente.getDadosPessoais().setNome(nome);
            cliente.setTelefone(telefone);
            if (!cliente.getUsuario().getEmail().equals(email) && usuarioDAO.getUsuario(email) == null) {
                cliente.getUsuario().setEmail(email);
            } else {
                Toast.makeText(this, "Email já cadastrado", Toast.LENGTH_SHORT).show();

                result = false;
            }
        }
        result = false;

        return result;
    }

    public void setNovoNome(String nome){
        cliente.getDadosPessoais().setNome(nome);
    }

    public void setmTelefone(String telefone){
        cliente.setTelefone(telefone);
    }

    public void alteraDados() {
        if (setNovosDados()) {
            clienteServices.alterarDadosCliente(cliente);
            Toast.makeText(this, "Dados Alterados com sucesso", Toast.LENGTH_SHORT).show();
            sair();
        } else {
            Toast.makeText(this, "Verifique seus dados", Toast.LENGTH_SHORT).show();
        }
    }

    private void sair() {
        startActivity(new Intent(EditDadosClienteActivity.this, PerfilClienteActivity.class));
        finish();
    }

    private void takePhoto(){

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager())!= null){
            File photoFile = null;
            photoFile = createPhotoFile();
            if (photoFile!= null){
                String pathTofile = photoFile.getAbsolutePath();
                Uri photoUri = FileProvider.getUriForFile(EditDadosClienteActivity.this,"br.ufrpe.bsi.mpoo.petSpeed",photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                startActivityForResult(intent,REQUEST_CAPTURE);
            }
        }

    }

    private File createPhotoFile() {

        String name = new SimpleDateFormat("ddMMyyy_HHmmss").format(new Date());
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(name,".jpg",storageDir);
        } catch (IOException e) {
            Log.d("My Tag","Error "+ e.toString());
        }

        return image;

    }

    private void getPermissionsGaleria() {
        int permissionCheckRead = ContextCompat.checkSelfPermission(EditDadosClienteActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheckRead != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(EditDadosClienteActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
            } else {
                ActivityCompat.requestPermissions(EditDadosClienteActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
            }
        } else {
            abrirGaleriaIntent();
        }
    }

    private void getPermissionsCamera() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        else
            takePhoto();
    }
    private void abrirGaleriaIntent() {
        Intent intent =new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Selecione a foto"),REQUEST_GALLERY);
    }

    private void salvarFoto() {
        byte[] foto = conveterImageViewToByte();
        cliente.setFoto(foto);
        clienteServices.alteraFotoCliente(cliente);
    }

    private byte[] conveterImageViewToByte() {
        Bitmap bitmap = ((BitmapDrawable) mImagemCliente.getDrawable()).getBitmap();
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
        switch (requestCode){
            case REQUEST_GALLERY:
                if (resultCode == RESULT_OK){
                    try {
                        Uri img = data.getData();
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(img));
                        bitmap = getThumbnailFromBitmap(bitmap);
                        mImagemCliente.setImageBitmap(bitmap);
                        salvarFoto();
                        Toast.makeText(this,"Foto Alterada Com sucesso",Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {

                        Toast.makeText(this,"Foto não encontrada",Toast.LENGTH_SHORT).show();
                    }
                }

            case REQUEST_CAPTURE:
                if (resultCode == RESULT_OK){
                    if (requestCode == 1){
                        Uri img = data.getData();
                        Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(img));
                        mImagemCliente.setImageBitmap(bitmap);
                    }
                }
        }

    }
}