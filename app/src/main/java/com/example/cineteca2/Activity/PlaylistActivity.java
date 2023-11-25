package com.example.cineteca2.Activity;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.example.cineteca2.DAO.FilmeDAO;
import com.example.cineteca2.DAO.PlaylistDAO;
import com.example.cineteca2.Adapter.FilmeAdapter;
import com.example.cineteca2.Model.Filme;
import com.example.cineteca2.Model.Usuario;
import com.example.cineteca2.R;
import java.util.ArrayList;
import java.util.List;

public class PlaylistActivity extends AppCompatActivity {
    private Button btnbuscar;
    private ListView lstfilme;
    private PlaylistDAO playlistDAO;
    Usuario user = new Usuario();
    List<Filme> filmes = new ArrayList<>();
    ArrayAdapter<Filme>adapter;
    int iduser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        btnbuscar=findViewById(R.id.btnBuscar);
        lstfilme = findViewById(R.id.lstFilme);
        playlistDAO  = new PlaylistDAO(getApplicationContext());
        Bundle dados = getIntent().getExtras();
        iduser = dados.getInt("userid");
        user.setId(iduser);
        exibirFilmesNaPlaylist();
        btnbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FilmeActivity.class);
                intent.putExtra("userid", user.getId());
                startActivity(intent);
            }
        });
        lstfilme.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter<Filme>adapter = (ArrayAdapter) lstfilme.getAdapter();
                Filme selected = adapter.getItem(i);
                FilmeDAO dbuser = new FilmeDAO(getApplicationContext());
                Filme filme = new Filme();
                filme.setId((int) selected.getId());
                dbuser.deletar(filme);
                exibirFilmesNaPlaylist();
                return true;
            }
        });
    }
    private void exibirFilmesNaPlaylist() {
        filmes.clear();
        Cursor cursor = playlistDAO.getFilmesNaPlaylist(user.getId());
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int filmeId = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String titulo = cursor.getString(cursor.getColumnIndex("titulo"));
                @SuppressLint("Range") int ano = cursor.getInt(cursor.getColumnIndex("ano"));
                Filme filme = new Filme(filmeId,titulo,ano);
                filmes.add(filme);
            }
            adapter = new ArrayAdapter<>(
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