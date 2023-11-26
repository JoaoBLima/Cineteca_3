package com.example.cineteca2.Model;
public class Filme {
    private int id;
    private String titulo;
    private int ano;
    public Filme(){
    }
    public Filme(int id, String titulo, int ano) {
        this.setId(id);
        this.setTitulo(titulo);
        this.setAno(ano);
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public int getAno() {
        return ano;
    }
    public void setAno(int ano) {
        this.ano = ano;
    }
    @Override
    public String toString() {
        return "ID: " + getId() + ", Título: " + getTitulo() + ", Ano: " + getAno();
    }
}
