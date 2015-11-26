package br.senac.pi.biblioteca;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import br.senac.pi.biblioteca.domain.DataBase;

public class AlterarLivro extends AppCompatActivity {
    private DataBase livrodb;
    private SQLiteDatabase db;
    private EditText edtAlterarTitulo,edtAlterarAutor,edtAlterarLocal;
    private TextView txtIdLivro;
    private String id;
    private Cursor cursor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_livro);
        id = getIntent().getStringExtra("id");
        livrodb = new DataBase(this);
        txtIdLivro = (TextView) findViewById(R.id.txtIdLivro);
        edtAlterarTitulo = (EditText) findViewById(R.id.edtaltTitulo);
        edtAlterarAutor = (EditText) findViewById(R.id.edtaltAutor);
        edtAlterarLocal = (EditText) findViewById(R.id.edtaltLocal);
        cursor = mostralivro(Integer.parseInt(id));
        txtIdLivro.setText(cursor.getString(cursor.getColumnIndexOrThrow("_id")));
        edtAlterarTitulo.setText(cursor.getString(cursor.getColumnIndexOrThrow("titulo")));
        edtAlterarAutor.setText(cursor.getString(cursor.getColumnIndexOrThrow("autor")));
        edtAlterarLocal.setText(cursor.getString(cursor.getColumnIndexOrThrow("local")));
        findViewById(R.id.btnAlterarLivro).setOnClickListener(alterarlivro());
    }
    private Cursor mostralivro(int id){
        db = livrodb.getWritableDatabase();
        String[] campos = {"_id","titulo","autor","local"};
        String whereArgs = String.valueOf(id);
        cursor = db.query("livro",campos,whereArgs,null,null,null,null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }
    private View.OnClickListener alterarlivro(){
        return new View.OnClickListener(){
            public void onClick(View v) {
                db = livrodb.getWritableDatabase();
                ContentValues values = new ContentValues();
                String whereArgs = id;
                values.put("titulo",edtAlterarTitulo.getText().toString());
                values.put("autor",edtAlterarAutor.getText().toString());
                values.put("local",edtAlterarLocal.getText().toString());
                db.update("livro",values,"_id = "+ whereArgs,null);
                db.close();
            }
        };
    }
}
