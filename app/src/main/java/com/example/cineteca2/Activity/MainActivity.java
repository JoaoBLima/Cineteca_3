package com.example.cineteca2.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cineteca2.DAO.UsuarioDAO;
import com.example.cineteca2.Model.Usuario;
import com.example.cineteca2.R;

public class MainActivity extends AppCompatActivity {
    private EditText edtllogin,edtsenha;
    private Button btnentrar;
    private TextView txtresultado;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtllogin= findViewById(R.id.edtlLogin);
        edtsenha= findViewById(R.id.edtSenha);
        btnentrar= findViewById(R.id.btnEntrar);
        txtresultado= findViewById(R.id.txtResultado);

        btnentrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UsuarioDAO dbuser = new UsuarioDAO(getApplicationContext());
                Usuario user = new Usuario();
                user.setLogin(edtllogin.getText().toString());
                user.setSenha(edtsenha.getText().toString());
                int usuarioId = dbuser.getIdUsuarioPorLogin(user.getLogin());
                if (usuarioId != -1) {
                    user.setId(usuarioId);
                } else {
                    Log.i("IDUSUARIO", "USUARIO NULO ");
                }
                boolean testeusuario= dbuser.getByLogin(user.getLogin());
                if (testeusuario){

                }else {
                    dbuser.salvar(user);
                    Toast.makeText(MainActivity.this,"dados salvos com sucesso",Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(), FilmeActivity.class);
                intent.putExtra("userid",user.getId());
                startActivity(intent);
            }
        });
    }
}