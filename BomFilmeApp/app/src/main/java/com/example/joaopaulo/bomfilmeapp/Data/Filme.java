package com.example.joaopaulo.bomfilmeapp.Data;

import java.io.Serializable;
import java.util.Date;

public class Filme implements Serializable {

    private String titulo;
    private Date lancamento;
    private String sinopse;
    private String imagemPoster;
    private String imagemBack;
    private Date dataAcesso;

    public Filme(String titulo, Date lancamento, String sinopse) {

        this.titulo = titulo;
        this.lancamento = lancamento;
        this.sinopse = sinopse;
    }

    public Filme(String titulo, Date lancamento, String sinopse, String imagemPoster, String imagemBack) {

        this.titulo = titulo;
        this.lancamento = lancamento;
        this.sinopse = sinopse;
        this.imagemPoster = imagemPoster;
        this.imagemBack = imagemBack;
    }

    public String getTitulo() {
        return titulo;
    }
    public Date getLancamento() {
        return lancamento;
    }
    public String getSinopse() {
        return sinopse;
    }
    public String getImagemPoster() {
        return imagemPoster;
    }
    public String getImagemBack() {
        return imagemBack;
    }
    public Date getDataAcesso() {
        return dataAcesso;
    }
    public void setDataAcesso(Date dataAcesso) {
        this.dataAcesso = dataAcesso;
    }

    public String toString() {

        return "Título: " + this.titulo + "\r\n" +
               "Lançamento: " + this.lancamento.toString() + "\r\n" +
               "Sinopse: " + this.sinopse;
    }
}
