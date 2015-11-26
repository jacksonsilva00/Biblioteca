package br.senac.pi.biblioteca;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.DialogPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import br.senac.pi.biblioteca.domain.DataBase;
import br.senac.pi.biblioteca.domain.Livro;

public class ListarLivros extends AppCompatActivity {
    private CursorAdapter dataSource;
    private SQLiteDatabase database;
    private static final String campos[] = {"autor","titulo","local","_id"};
    ListView listView;
    DataBase livrodb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_livros);
        listView = (ListView) findViewById(R.id.listView);
        livrodb = new DataBase(this);
        database = livrodb.getWritableDatabase();
        findViewById(R.id.btnlistardb).setOnClickListener(listarlivro());
        listView.setOnItemClickListener(deletarlivro());

    }
    private View.OnClickListener listarlivro(){
        return new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Cursor livros = database.query("livro",campos,null,null,null,null,null,null);
                if (livros.getCount() > 0){
                    dataSource = new SimpleCursorAdapter(ListarLivros.this , R.layout.row ,livros, campos , new int[]{R.id.txtTitulo,R.id.txtAutor,R.id.txtLocal});
                    listView.setAdapter(dataSource);
                }else{
                    Toast.makeText(ListarLivros.this,getString(R.string.action_settings),Toast.LENGTH_LONG).show();
                }
            }
        };
    }
    private AdapterView.OnItemClickListener deletarlivro(){
        return new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int posicao = position;
                final long itemSelecionado = id;
                AlertDialog.Builder builder = new AlertDialog.Builder(ListarLivros.this);
                builder.setTitle(R.string.pergunta);
                builder.setMessage(R.string.mensagem);
                builder.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String codigo;
                        Cursor livro = database.query("livro", campos, null, null, null, null, null, null);
                        livro.moveToPosition(posicao);
                        codigo = livro.getString(livro.getColumnIndexOrThrow("_id"));
                        Intent intent = new Intent(ListarLivros.this, AlterarLivro.class);
                        intent.putExtra("id", codigo);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("Deletar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Livro livro = new Livro();
                        livro.setId(itemSelecionado);
                        livrodb.deletelivro(livro);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        };
    }
}
