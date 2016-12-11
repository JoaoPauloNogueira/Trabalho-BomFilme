package com.example.joaopaulo.bomfilmeapp.Data;

import android.provider.BaseColumns;

/**
 * Created by Joao Paulo on 11/12/2016.
 */

public class DataBaseContract implements BaseColumns {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BomFilme.db";
    public static final String TABLE_FILMES = "filmes";
    public static final String COLUMN_TITULO = "titulo";
    public static final String COLUMN_LANCAMENTO = "lancamento";
    public static final String COLUMN_SINOPSE = "sinopse";
    public static final String COLUMN_IMAGEM_POSTER = "imagemposter";
    public static final String COLUMN_IMAGEM_BACK = "imagemback";
    public static final String COLUMN_ACESSO= "acesso";

    private DataBaseContract() {}

    public static String retornaCriacaoTabelaFilmes() {

        return "CREATE TABLE " + TABLE_FILMES + "( " +
                _ID  + " INTEGER PRIMARY KEY, " +
                COLUMN_TITULO + " INTEGER, " +
                COLUMN_LANCAMENTO + " LONG, " +
                COLUMN_SINOPSE + " TEXT, " +
                COLUMN_IMAGEM_POSTER + " TEXT, " +
                COLUMN_IMAGEM_BACK + " TEXT, " +
                COLUMN_ACESSO + " LONG )";
    }

    public static String retornaDropTabelaFilmes() {

        return "DROP TABLE IF EXISTS " + TABLE_FILMES;
    }

    public static String[] retornaCamposSelecao() {

        return new String[] {_ID, COLUMN_TITULO, COLUMN_LANCAMENTO, COLUMN_SINOPSE,
                COLUMN_IMAGEM_POSTER, COLUMN_IMAGEM_BACK, COLUMN_ACESSO};
    }

    public static String retornaOrdenacaoConsulta() {

        return COLUMN_ACESSO + "DESC";
    }

}
