package br.senac.pi.biblioteca;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import br.senac.pi.biblioteca.domain.DataBase;
import br.senac.pi.biblioteca.domain.Livro;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        findViewById(R.id.btnCadastrar).setOnClickListener(cadastrar());
        findViewById(R.id.btnListar).setOnClickListener(listar());
        //findViewById(R.id.edtAutor);
        //findViewById(R.id.edtLocal);
        //findViewById(R.id.edtTitulo);
    }

    private View.OnClickListener cadastrar() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Livro livro  = new Livro();
                DataBase db = new DataBase(MainActivity.this);
                EditText edtAutor = (EditText) findViewById(R.id.edtAutor);
                EditText edtTitulo = (EditText) findViewById(R.id.edtTitulo);
                EditText edtLocal = (EditText) findViewById(R.id.edtLocal);
                String Local = edtLocal.getText().toString();
                String Titulo = edtTitulo.getText().toString();
                String Autor = edtAutor.getText().toString();
                livro.setAutor(Autor);
                livro.setTitulo(Titulo);
                livro.setLocal(Local);
                if(db.save(livro) != -1){
                    Toast.makeText(MainActivity.this,getString(R.string.salvo),Toast.LENGTH_LONG).show();
                    edtAutor.setText(" ");
                    edtLocal.setText(" ");
                    edtTitulo.setText(" ");
                    edtTitulo.requestFocus();

                }else{
                    Toast.makeText(getBaseContext(),getString(R.string.errosalve),Toast.LENGTH_LONG).show();
                }

            }
        };
    }
    public View.OnClickListener listar(){
        return new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ListarLivros.class);
                startActivity(intent);
            }
        };

    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
