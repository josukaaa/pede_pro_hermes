package br.com.senac.pede_pro_hermes;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.senac.pede_pro_hermes.webservice.RequestHandler;
import br.com.senac.pede_pro_hermes.modelo.PedeproHermes;
import br.com.senac.pede_pro_hermes.webservice.Api;


public class MainActivity extends AppCompatActivity {


    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    EditText editTextIdPedido;
    EditText editTextCliente;
    EditText editTextModelo;
    EditText editTextMarca;
    EditText editTextTamanho;
    EditText editTextValor;
    Button buttonSalvar;
    ProgressBar progressBar;
    ListView listView;
    List<PedeproHermes>pedeprohermesList;

    Boolean isUpdating=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar =findViewById(R.id.barraProgresso);
        listView =findViewById(R.id.listViewTarefas);
        editTextValor =findViewById(R.id.editTextValor);
        editTextTamanho =findViewById(R.id.editTextTamanho);
        editTextMarca= findViewById(R.id.editTextMarca);
        editTextModelo=findViewById(R.id.editTextModelo);
        editTextCliente=findViewById(R.id.editTextCliente);
        editTextIdPedido=findViewById(R.id.editTextIdPedido);
        buttonSalvar =findViewById(R.id.buttonSalvar);
        pedeprohermesList = new ArrayList<>();


        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUpdating){
                    updatePedeproHermes();

                } else{
                    createPedeproHermes();
                }
            }
        });
        readPedeproHermes();
    }
    private void createPedeproHermes(){
        String cliente = editTextCliente.getText().toString().trim();
        String marca = editTextMarca.getText().toString().trim();
        String modelo  = editTextModelo .getText().toString().trim();
        String tamanho = editTextTamanho.getText().toString().trim();
        String valor = editTextValor.getText().toString().trim();

        if(TextUtils.isEmpty(cliente)){
            editTextCliente.setError("Digite o nome do Cliente");
            editTextCliente.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(modelo)){
            editTextModelo.setError("Digite o Modelo");
            editTextModelo.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(marca)){
            editTextMarca.setError("Digite a Marca");
            editTextMarca.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(tamanho)){
            editTextTamanho.setError("Digite o Tamanho");
            editTextTamanho.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(valor)){
            editTextValor.setError("Digite o Valor ");
            editTextValor.requestFocus();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("cliente",cliente);
        params.put("marca",marca);
        params.put("modelo", modelo);
        params.put("tamanho",tamanho);
        params.put("valor", valor);

        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_CREATE_PEDEPROHERMES, params, CODE_POST_REQUEST);
        request.execute();


    }

    private void updatePedeproHermes(){
        String id = editTextIdPedido.getText().toString();
        String cliente = editTextCliente.getText().toString().trim();
        String modelo  = editTextModelo .getText().toString().trim();
        String marca = editTextMarca.getText().toString().trim();
        String tamanho = editTextTamanho.getText().toString().trim();
        String valor = editTextValor.getText().toString().trim();



        if(TextUtils.isEmpty(cliente)){
            editTextCliente.setError("Digite o nome do Cliente");
            editTextCliente.requestFocus();
            return;

        }
        if(TextUtils.isEmpty(marca)){
            editTextMarca.setError("Digite a Marca");
            editTextMarca.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(modelo)){
            editTextModelo.setError("Digite o Modelo");
            editTextModelo.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(tamanho)){
            editTextTamanho.setError("Digite o Tamanho");
            editTextTamanho.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(valor)){
            editTextValor.setError("Digite o Valor ");
            editTextValor.requestFocus();
            return;
        }
        HashMap<String,String> params =new HashMap<>();
        params.put("id",id);
        params.put("cliente",cliente);
        params.put("marca", marca);
        params.put("modelo",modelo);
        params.put("tamanho",tamanho);
        params.put("valor", valor);

        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_UPDATE_PEDEPROHERMES, params, CODE_GET_REQUEST);
        request.execute();

        buttonSalvar.setText("Salvar");
        editTextCliente.setText("");
        editTextModelo.setText("");
        editTextMarca.setText("");
        editTextTamanho.setText("");
        editTextValor.setText("");

        isUpdating = false;

    }
    private void readPedeproHermes() {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_PEDEPROHERMES,null,CODE_GET_REQUEST);
        request.execute();
    }

    private void deletePedeproHermes(int id){
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_DELETE_PEDEPROHERMES + id,null,CODE_GET_REQUEST);
        request.execute();
    }

    private void refreshPedeproHermesList(JSONArray pedeprohermes) throws JSONException {

        pedeprohermesList.clear();

        for(int i = 0; i <pedeprohermes.length();i++){
            JSONObject obj =pedeprohermes.getJSONObject(i);
            pedeprohermesList.add(new PedeproHermes(
                    obj.getInt("id"),
                    obj.getString("cliente"),
                    obj.getString("marca"),
                    obj.getString("modelo"),
                    obj.getString("tamanho"),
                    obj.getString("valor")
            ));
        }
        PedeproHermesAdapter adapter =new PedeproHermesAdapter(pedeprohermesList);
        listView.setAdapter(adapter);
    }

    private class   PerformNetworkRequest extends AsyncTask<Void, Void, String>{

        String url;
        HashMap<String, String> params;
        int requestCode;

        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(),object.getString("message"), Toast.LENGTH_SHORT).show();
                    refreshPedeproHermesList(object.getJSONArray("pedeprohermes"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }

    }
    class PedeproHermesAdapter extends ArrayAdapter<PedeproHermes> {
        List<PedeproHermes> pedeprohermesList;

        public PedeproHermesAdapter(List<PedeproHermes> pedeprohermesList) {
            super(MainActivity.this, R.layout.layout_pedeprohermes_list,pedeprohermesList);

            this.pedeprohermesList = pedeprohermesList;
        }


        @Override
        public View getView(int position, View convertView,  ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_pedeprohermes_list, null, true);

            TextView textViewPedidos = listViewItem.findViewById(R.id.textViewPedidos);

            TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);

            TextView textViewAlterar = listViewItem.findViewById(R.id.textViewAlterar);

            final PedeproHermes pedeproHermes = pedeprohermesList.get(position);


            textViewPedidos.setText(pedeproHermes.getCliente());
            textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    builder.setTitle("Delete " + pedeproHermes.getCliente()).setMessage("Você deseja realmente deletar?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deletePedeproHermes(pedeproHermes.getId());

                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            });
            textViewAlterar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isUpdating=true;

                    editTextCliente.setText(String.valueOf(pedeproHermes.getCliente()));
                    editTextModelo.setText(String.valueOf(pedeproHermes.getModelo()));
                    editTextMarca.setText(String.valueOf(pedeproHermes.getMarca()));
                    editTextTamanho.setText(String.valueOf(pedeproHermes.getTamanho()));
                    editTextValor.setText(String.valueOf(pedeproHermes.getValor()));

                    buttonSalvar.setText("Alterar");
                }
            });

            return listViewItem;
        }
    }






}
