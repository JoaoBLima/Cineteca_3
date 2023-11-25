package com.example.cineteca2.DAO;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.example.cineteca2.Helper.SQLiteDatabaseHelper;
import com.example.cineteca2.Model.Filme;

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
    public boolean deletar(Filme filme) {
        try {
            objwrite.delete("filme", "id=" + filme.getId(), null);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}
