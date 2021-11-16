package models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Cursos {
    private int Id;
    private String Nome;
    private int Duracao;
    private int Valor;

    public Cursos(int id, String nome, int duracao, int valor) {
        Id = id;
        Nome = nome;
        Duracao = duracao;
        Valor = valor;
    }

    public Cursos() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public int getDuracao() {
        return Duracao;
    }

    public void setDuracao(int duracao) {
        Duracao = duracao;
    }

    public int getValor() {
        return Valor;
    }

    public void setValor(int valor) {
        Valor = valor;
    }

    public static String parseJson(Cursos cursos){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Nome", cursos.getNome());
            jsonObject.put("Email", cursos.getDuracao());
            jsonObject.put("Senha", cursos.getValor());
            return  jsonObject.toString();
        }
        catch (Exception ex){
            return "";
        }
    }

    public static Cursos parseOneObject(String json){
        try {
            Cursos cursos = new Cursos();
            JSONObject obj = new JSONObject(json);
            cursos.setNome(obj.getString("Nome"));
            cursos.setDuracao(obj.getInt("Email"));
            cursos.setValor(obj.getInt("Senha"));
            cursos.setId(obj.getInt("id"));

            return cursos;
        }
        catch (Exception ex){
            return null;
        }
    }


    public static ArrayList<Cursos> parseObject(String json){
        ArrayList<Cursos> cursos = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                Cursos curso = new Cursos();
                JSONObject obj = array.getJSONObject(i);
                curso.setNome(obj.getString("Nome"));
                curso.setDuracao(obj.getInt("Email"));
                curso.setValor(obj.getInt("Senha"));
                curso.setId(obj.getInt("id"));
                cursos.add(curso);
            }
            return cursos;
        }
        catch (Exception ex){
            return cursos;
        }
    }
}
