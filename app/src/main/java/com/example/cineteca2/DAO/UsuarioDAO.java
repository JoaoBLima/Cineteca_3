package com.example.cineteca2.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.cineteca2.Helper.SQLiteDatabaseHelper;
import com.example.cineteca2.Model.Usuario;

public class UsuarioDAO implements IUsuarioDAO{
    private SQLiteDatabase objwrite;
    private SQLiteDatabase objRead;

    public UsuarioDAO(Context context){
        SQLiteDatabaseHelper db = new SQLiteDatabaseHelper(context);
        objwrite= db.getWritableDatabase();
        objRead= db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Usuario user) {

        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("login", user.getLogin());
            contentValues.put("senha", user.getSenha());

            objwrite.insert("usuario", null, contentValues);

        } catch (Exception e) {
            Log.e("INFO BD", "Erro ao inserir usuario: " + e.getMessage());
            return false;
        }
        return true;
    }
    @SuppressLint("Range")
    public int getIdUsuarioPorLogin(String login) {
        int usuarioId = -1;

        String query = "SELECT id FROM usuario WHERE login = ?";
        String[] selectionArgs = {login};
        Cursor cursor = objRead.rawQuery(query, selectionArgs);

        try {
            if (cursor.moveToFirst()) {
                usuarioId = cursor.getInt(cursor.getColumnIndex("id"));
            }
        } finally {
            cursor.close();
        }

        return usuarioId;
    }


    @Override
    public boolean getByLogin(String login) {
        String stg= "SELECT * FROM usuario WHERE login =? ";

        String[] selectionArgs = {login};

        // Execute a consulta
        Cursor cursor = objRead.rawQuery(stg, selectionArgs);

        // Verifique se há resultados
        if (cursor.moveToFirst()) {
            // Usuário encontrado
            @SuppressLint("Range") int userId = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String userLogin = cursor.getString(cursor.getColumnIndex("login"));
            @SuppressLint("Range") String userSenha = cursor.getString(cursor.getColumnIndex("senha"));

            // Faça algo com os dados do usuário, se necessário

            // Libere os recursos do cursor
            cursor.close();

            return true;
        } else {
            // Usuário não encontrado
            cursor.close();
            return false;
        }

    }


    }

