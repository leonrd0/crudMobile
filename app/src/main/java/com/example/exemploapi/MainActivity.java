package com.example.exemploapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import adapters.CursoAdapter;
import models.Cursos;
import services.ServiceApi;

public class MainActivity extends AppCompatActivity {
    ProgressDialog dialog;
    ArrayList<Cursos> listaCursos;
    RecyclerView recyclerUsuario;
    CursoAdapter cursoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerUsuario = (RecyclerView)findViewById(R.id.recyclerCurso);
        listaCursos = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        buscarCursos();
    }

    private void buscarCursos(){
        new CursoAPI("GET").execute("Cursos","");
    }
    public void excluirCursos(int id){ new CursoAPI("DELETE").execute("Cursos/" + id,""); }

    public void btnAddUsuarioClick(View view){
        Intent i = new Intent(MainActivity.this, CadCursoActivity.class);
        startActivity(i);
    }

    public void setupRecyclerCursos(){
        //Configurando o layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerUsuario.setLayoutManager(layoutManager);

        // add adapter
        cursoAdapter = new CursoAdapter(listaCursos);
        recyclerUsuario.setAdapter(cursoAdapter);

        //divisor entre linhas
        recyclerUsuario.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }

    public class CursoAPI extends AsyncTask<String,String,String>{
        private String metodo;
        public CursoAPI(String metodo){
            this.metodo = metodo;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(MainActivity.this,"Aguarde","Por favor aguarde...");
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
                listaCursos = Cursos.parseObject(s);
                setupRecyclerCursos();
                dialog.dismiss();
            }
            else if(s == "OK"){
                Toast.makeText(MainActivity.this,"Operação realizada com sucesso",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                buscarCursos();
            }
        }
    }
}