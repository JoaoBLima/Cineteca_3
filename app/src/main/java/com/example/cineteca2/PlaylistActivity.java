package com.example.cineteca2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.cineteca2.DAO.PlaylistDAO;
import com.example.cineteca2.Model.Filme;
import com.example.cineteca2.Model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class PlaylistActivity extends AppCompatActivity {
    private Button btnbuscar;
    private ListView lstfilme;
    private PlaylistDAO playlistDAO;
    private FilmeAdapter filmeAdapter;
    Usuario user = new Usuario();
    List<Filme> filmes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        btnbuscar=findViewById(R.id.btnBuscar);
        lstfilme = findViewById(R.id.lstFilme);
        playlistDAO  = new PlaylistDAO(getApplicationContext());
       // List<Filme>listafilmes = playlistDAO.getFilmesNaPlaylist();
        Bundle dados = getIntent().getExtras();
        int iduser = dados.getInt("userid");
        user.setId(iduser);

        exibirFilmesNaPlaylist();

    }
    private void exibirFilmesNaPlaylist() {
        Cursor cursor = playlistDAO.getFilmesNaPlaylist(user.getId());
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int filmeId = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String titulo = cursor.getString(cursor.getColumnIndex("titulo"));
                @SuppressLint("Range") int ano = cursor.getInt(cursor.getColumnIndex("ano"));
                Filme filme = new Filme();
                filme.setId(filmeId);
                filme.setTitulo(titulo);
                filme.setAno(ano);
                filmes.add(filme);
            }
            ArrayAdapter<Filme>adapter = new ArrayAdapter<>(
                    getApplicationContext(),
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    filmes
                    );
            lstfilme.setAdapter(adapter);
            cursor.close();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}