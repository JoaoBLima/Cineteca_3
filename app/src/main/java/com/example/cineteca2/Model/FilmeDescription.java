package com.example.cineteca2.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FilmeDescription implements Serializable {
    @SerializedName("#TITLE")
    private String titulo;
    @SerializedName("#YEAR")
    private int ano;
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo=titulo;
    }
    public int getAno() {
        return ano;
    }
    public void setAno(int ano) {
        this.ano=ano;
    }
}
