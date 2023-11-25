package com.example.cineteca2.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cineteca2.Helper.SQLiteDatabaseHelper;

public class PlaylistDAO {
    private SQLiteDatabase objwrite;
    private SQLiteDatabase objRead;

    public PlaylistDAO(Context context){
        SQLiteDatabaseHelper db = new SQLiteDatabaseHelper(context);
        objwrite= db.getWritableDatabase();
        objRead= db.getReadableDatabase();

    }

    public long adicionarFilmeAPlaylist(long usuarioId, long filmeId) {
        ContentValues values = new ContentValues();
        values.put("usuario_id", usuarioId);
        values.put("filme_id", filmeId);

        return objwrite.insert("playlist",null,values);
    }

    public Cursor getFilmesNaPlaylist(long usuarioId) {
        String query = "SELECT f.* FROM filme f " +
                "INNER JOIN playlist p ON f.id = p.filme_id " +
                "WHERE p.usuario_id = " + usuarioId;
        return objwrite.rawQuery(query, null);}


}
