package com.example.cineteca2.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.cineteca2.Helper.SQLiteDatabaseHelper;
import com.example.cineteca2.Model.Filme;

import java.util.ArrayList;
import java.util.List;

public class FilmeDAO implements IFilmeDAO {
    private SQLiteDatabase objwrite;
    private SQLiteDatabase objRead;

    public FilmeDAO(Context context){
        SQLiteDatabaseHelper db = new SQLiteDatabaseHelper(context) ;
        objwrite= db.getWritableDatabase();
        objRead= db.getReadableDatabase();


    }

    @Override
    public long adicionarFilme(Filme filme) {
        ContentValues values = new ContentValues();
        values.put("titulo", filme.getTitulo());
        values.put("ano", filme.getAno());
        Log.i("MY SQLite", "filme adicionado ");

        return objwrite.insert("filme", null, values);
    }

    @Override
    public List<Filme> getTodosFilmes() {
        List<Filme>listar= new ArrayList<>();
        try {
            String sql = "SELECT * FROM filme";
            Cursor cursor = objRead.rawQuery(sql,null);
            int ititulo = cursor.getColumnIndexOrThrow("titulo");
            int iano = cursor.getColumnIndexOrThrow("ano");
            int iid = cursor.getColumnIndexOrThrow("id");



            cursor.moveToFirst();

            do {
                if (cursor.getCount() == 0) {
                    break;
                }
                Filme filme = new Filme();
                filme.setTitulo(cursor.getString(ititulo));
                filme.setAno(Integer.valueOf(cursor.getString(iano)));
                filme.setId(Integer.valueOf(cursor.getString(iid)));

                listar.add(filme);
            } while (cursor.moveToNext());

        }catch (Exception e){
            return null;
        }
        return listar;


    }

    @Override
    public Filme getFilmePorId(long filmeId) {
        String stl = "SELECT * FROM filme WHERE id = "+filmeId;
        Cursor cursor = objRead.rawQuery(stl, null);
        Filme filme = new Filme();
        if (cursor.moveToFirst()) {
            // Usuário encontrado
            @SuppressLint("Range") int idfilme = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String titulofilme= cursor.getString(cursor.getColumnIndex("titulo"));
            @SuppressLint("Range") int anofilme = cursor.getInt(cursor.getColumnIndex("ano"));


            filme.setId(idfilme);
            filme.setTitulo(titulofilme);
            filme.setAno(anofilme);
            cursor.close();
            return filme;
        }
        return null;
    }
}
