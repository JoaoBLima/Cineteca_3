package com.example.cineteca2.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "my_database.db";
    private static final int DATABASE_VERSION = 9;
    public SQLiteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE usuario (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "login VARCHAR(20)," +
                "senha CHAR(4))";
        Log.i("MYSQLITE", "TABELA CRIADA COM SUCESSO ");

        db.execSQL(createTable);
        String createTableFilme = "CREATE TABLE IF NOT EXISTS  filme (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "titulo VARCHAR(255)," +
                "ano INTEGER)";
        Log.i("MYSQLITE", "TABELA FILME CRIADA COM SUCESSO ");
        db.execSQL(createTableFilme);

        String createTablePlaylist = "CREATE TABLE playlist (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "usuario_id INTEGER," +
                "filme_id INTEGER," +
                "FOREIGN KEY(filme_id) REFERENCES filme(id)," +
                "FOREIGN KEY(usuario_id) REFERENCES usuario(id))";
        Log.i("MYSQLITE", "TABELA PLAYLIST CRIADA COM SUCESSO ");
        db.execSQL(createTablePlaylist);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if  (i < 4) {
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS filme (id INTEGER PRIMARY KEY AUTOINCREMENT, titulo TEXT, ano INTEGER)");
        }
        if  (i< 6) {
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS playlist (id INTEGER PRIMARY KEY AUTOINCREMENT, usuario_id INTEGER, filme_id INTEGER, FOREIGN KEY(filme_id) REFERENCES filme(id), FOREIGN KEY(usuario_id) REFERENCES usuario(id))");
        }
        if (i < 8) {
          sqLiteDatabase.execSQL("ALTER TABLE playlist ADD COLUMN usuario_id INTEGER");
        }
        if (i<9){
            sqLiteDatabase.execSQL("ALTER TABLE playlist ADD COLUMN filme_id");
        }
    }
}
