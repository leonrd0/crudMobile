package com.example.exemploapi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import models.Cursos;
import services.ServiceApi;

public class CadCursoActivity extends AppCompatActivity {
    int id = 0;
    Cursos cursos;
    TextView textNome, textDuracao, textValor;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_usuario);
        textDuracao = findViewById(R.id.textDuracao);
        textNome = findViewById(R.id.textNome);
        textValor = findViewById(R.id.textValor);

        if(getIntent().hasExtra("id")){
            //editando um usuario
            id = getIntent().getIntExtra("id", 0);
            new CursoAPI("GET").execute("Curso/" + id, "");
        }
    }
    public void carregarCampos(){
        textValor.setText(cursos.getValor());
        textNome.setText(cursos.getNome());
        textDuracao.setText(cursos.getDuracao());
    }

    public void btnSalvarClick(View view){
        if(id > 0){
            //editar
            cursos.setNome(textNome.getText().toString());
            cursos.setDuracao(Integer.parseInt(textDuracao.getText().toString()));
            cursos.setValor(Integer.parseInt(textValor.getText().toString()));

            new CursoAPI("PUT").execute("Curso/" + id, Cursos.parseJson(cursos));
        }
        else{
            //inserir
            cursos.setNome(textNome.getText().toString());
            cursos.setDuracao(Integer.parseInt(textDuracao.getText().toString()));
            cursos.setValor(Integer.parseInt(textValor.getText().toString()));
            cursos.setId(0);

            new CursoAPI("POST").execute("Curso/", Cursos.parseJson(cursos));
        }
    }

    public class CursoAPI extends AsyncTask<String,String,String> {
        private String metodo;

        public CursoAPI(String metodo){
            this.metodo = metodo;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(CadCursoActivity.this,"Aguarde","Por favor aguarde...");
        }

        @Override
        protected String doInBackground(String... strings) {
            String data = ServiceApi.getService(strings[0],metodo,strings[1]);
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(metodo == "GET") {
                cursos = Cursos.parseOneObject(s);
                carregarCampos();
                dialog.dismiss();
            }
            else if(s == "OK"){
                Toast.makeText(CadCursoActivity.this,"Operação realizada com sucesso",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                finish();
            }
        }
    }
}