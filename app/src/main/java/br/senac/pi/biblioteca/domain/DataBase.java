package br.senac.pi.biblioteca.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Aluno on 20/11/2015.
 */
public class DataBase extends SQLiteOpenHelper {
    //private static final String TAG = "sql";
    private static final String name = "bibliotecaDB.sqlite";
    private static final int version = 1;

    public DataBase(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Log.d(TAG,);
        db.execSQL("CREATE TABLE IF NOT EXISTS livro(_id integer primary key autoincrement," + "titulo text, autor text ,local text);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public long save(Livro livro){
        long id = livro.getId();
        SQLiteDatabase db = getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put("titulo",livro.getTitulo());
            values.put("autor",livro.getAutor());
            values.put("local",livro.getLocal());
            if(id != 0){
                String _id = String.valueOf(livro.getId());
                String[] whereArgs = new String[]{_id};
                //
                int count = db.update("livro",values,"_id=?",whereArgs);
                return count;
            }else{
                id = db.insert("livro","",values);
                return id;
            }
        }finally {
            db.close();

        }

    }
    public int deletelivro(Livro livro){
        SQLiteDatabase db = getWritableDatabase();
        try {
            int count = db.delete("livro","_id=?",new String[]{String.valueOf(livro.getId())});
            return count;
        }finally {
            db.close();

        }
    }
    public List<Livro> findAll(){
        SQLiteDatabase db = getWritableDatabase();
        try{
            Cursor cursor = db.query("livro", null, null, null, null, null, null,null);
            return toList(cursor);
        }finally {
            db.close();
        }

    }

    private List<Livro> toList(Cursor cursor) {
        List<Livro> livros = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                Livro livro =  new Livro();
                livros.add(livro);
                livro.setId(cursor.getLong(cursor.getColumnIndex("_id")));
                livro.setAutor(cursor.getString(cursor.getColumnIndex("autor")));
                livro.setTitulo(cursor.getString(cursor.getColumnIndex("titulo")));
                livro.setLocal(cursor.getString(cursor.getColumnIndex("local")));
            }while (cursor.moveToNext());
        }
        return livros;
    }

}
