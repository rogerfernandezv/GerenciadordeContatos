package rogerdev.gerenciadorcontatos.com.br.gerenciadordecontatos;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    EditText txtNome, txtNumero, txtEmail, txtEndereco;
    List<Contato> contatos = new ArrayList<>();
    ListView contatosListView;
    DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNome = (EditText) findViewById(R.id.txtNome);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtNumero = (EditText) findViewById(R.id.txtNumero);
        txtEndereco = (EditText) findViewById(R.id.txtEndereco);
        contatosListView = ((ListView) findViewById(R.id.listView));
        final Button btnAdd = (Button) findViewById(R.id.btnAdd);
        dbHandler = new DatabaseHandler(this);

        TabHost tabhost = (TabHost) findViewById(R.id.tabHost);
        tabhost.setup();

        TabHost.TabSpec tabSpec = tabhost.newTabSpec("listar");
        tabSpec.setContent(R.id.tabListaContatos);
        tabSpec.setIndicator("Lista de Contato");
        tabhost.addTab(tabSpec);

        tabSpec = tabhost.newTabSpec("criar");
        tabSpec.setContent(R.id.tabCriarContato);
        tabSpec.setIndicator("Adicionar Contato");
        tabhost.addTab(tabSpec);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contato contato = new Contato(dbHandler.getContatoCount(), String.valueOf(txtNome.getText().toString()), String.valueOf(txtEndereco.getText().toString()), String.valueOf(txtNumero.getText().toString()), String.valueOf(txtEmail.getText().toString()) );
                dbHandler.criarContato(contato);
                contatos.add(contato);
                popularLista();
                Toast.makeText(getApplicationContext(),txtNome.getText().toString() + " foi adicionado com sucesso!", Toast.LENGTH_LONG).show();
                clearListView();
            }
        });

        txtNome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnAdd.setEnabled(!txtNome.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        List<Contato> addableContatos = dbHandler.getAllContatos();
        int contatoCount = dbHandler.getContatoCount();

        for(int i = 0; i < contatoCount; i++){
            contatos.add(addableContatos.get(i));
        }

        if(!addableContatos.isEmpty())
            popularLista();

    }

   private void popularLista(){
       ArrayAdapter<Contato> adaptador = new ContatoListAdapter();
       contatosListView.setAdapter(adaptador);

   }

    private void clearListView(){
        txtNome.setText(null);
        txtEmail.setText(null);
        txtEndereco.setText(null);
        txtNumero.setText(null);
    }

    private class ContatoListAdapter extends ArrayAdapter<Contato>{
        public ContatoListAdapter(){
            super(MainActivity.this, R.layout.listview_item, contatos);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent){
            if(view == null)
                view = getLayoutInflater().inflate(R.layout.listview_item, parent, false);

            Contato currentContato = contatos.get(position);

            TextView nome = (TextView) view.findViewById(R.id.lblNome);
            nome.setText(currentContato.getNome());

            TextView numero = (TextView) view.findViewById(R.id.lblTelefone);
            numero.setText(currentContato.getNumero());

            TextView endereco = (TextView) view.findViewById(R.id.lblEndereco);
            endereco.setText(currentContato.getEndereco());

            TextView email = (TextView) view.findViewById(R.id.lblEmail);
            email.setText(currentContato.getEmail());

            return view;
        }
    }

    @Override
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
    }
}
