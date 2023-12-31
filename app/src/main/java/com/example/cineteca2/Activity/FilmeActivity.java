package com.example.cineteca2.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cineteca2.DAO.FilmeDAO;
import com.example.cineteca2.DAO.PlaylistDAO;
import com.example.cineteca2.Model.ApiResponse;
import com.example.cineteca2.Model.Filme;
import com.example.cineteca2.Model.FilmeDescription;
import com.example.cineteca2.Model.Usuario;
import com.example.cineteca2.R;
import com.example.cineteca2.Service.FilmeService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FilmeActivity extends AppCompatActivity {
    private Button btnvoltar, btnFilme, btnadicionar;
    private Retrofit retrofit;
    private EditText edtfilme;
    private TextView txtresultado;
    private Filme novoFilme;
    private FilmeDescription filmeDescription;
    Usuario user = new Usuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filme);

        btnvoltar = findViewById(R.id.btnVoltar);
        btnadicionar = findViewById(R.id.btnadicionar);
        btnFilme = findViewById(R.id.btnFilme);
        retrofit = new Retrofit.Builder().baseUrl("https://search.imdbot.workers.dev/").addConverterFactory(GsonConverterFactory.create()).build();

        edtfilme = findViewById(R.id.edtFilme);
        txtresultado = findViewById(R.id.txtResultado);
        novoFilme = new Filme();
        Bundle dados = getIntent().getExtras();
        int iduser = dados.getInt("userid");
        user.setId(iduser);

        btnvoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PlaylistActivity.class);
                intent.putExtra("userid", user.getId());
                startActivity(intent);
            }
        });

        btnadicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (novoFilme != null) {
                    FilmeDAO filmeDAO = new FilmeDAO(getApplicationContext());
                    PlaylistDAO playlistDAO = new PlaylistDAO(getApplicationContext());
                    novoFilme.setTitulo(filmeDescription.getTitulo());
                    novoFilme.setAno(filmeDescription.getAno());
                    long filmeId = filmeDAO.adicionarFilme(novoFilme);
                    long usuarioid = user.getId();
                    playlistDAO.adicionarFilmeAPlaylist(usuarioid, filmeId);
                    Toast.makeText(FilmeActivity.this, "Filme adicionado à playlist!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FilmeActivity.this, "Nenhum filme encontrado para adicionar à playlist.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnFilme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecuperaporTitulo();
            }
        });
    }

    private  void RecuperaporTitulo() {
        FilmeService filmeService = retrofit.create(FilmeService.class);
        Call<ApiResponse> call = filmeService.RecuperaFilmePorTitulo(edtfilme.getText().toString());
        call.enqueue(new Callback<ApiResponse>() {
        @Override
        public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
            if (response.isSuccessful()) {
                ApiResponse apiResponse = response.body();
                if (apiResponse != null && apiResponse.isOk()) {
                    List<FilmeDescription> filmesList = apiResponse.getDescriptionList();
                    if (filmesList != null && !filmesList.isEmpty()) {
                        filmeDescription = filmesList.get(0);
                        novoFilme.setTitulo(filmeDescription.getTitulo());
                        novoFilme.setAno(filmeDescription.getAno());
                        txtresultado.setText("Titulo: " + novoFilme.getTitulo() + "\nAno: " + novoFilme.getAno());
                    } else {
                        txtresultado.setText("Nenhum filme encontrado");
                    }
                } else {
                    txtresultado.setText("Resposta da API não está OK");
                }
            } else {
                txtresultado.setText("Erro na resposta da API: " + response.code());
            }
        }
        @Override
        public void onFailure(Call<ApiResponse> call, Throwable t) {
            txtresultado.setText("Erro na requisição: " + t.getMessage());
        }
    });
}}

