package com.example.joaopaulo.bomfilmeapp.Data;

import android.provider.BaseColumns;

class DataBaseContract implements BaseColumns {

    static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "BomFilme.db";
    static final String TABLE_FILMES = "filmes";
    static final String COLUMN_TITULO = "titulo";
    static final String COLUMN_LANCAMENTO = "lancamento";
    static final String COLUMN_SINOPSE = "sinopse";
    static final String COLUMN_IMAGEM_POSTER = "imagemposter";
    static final String COLUMN_IMAGEM_BACK = "imagemback";
    static final String COLUMN_ACESSO= "acesso";

    private DataBaseContract() {}

    static String retornaCriacaoTabelaFilmes() {

        return "CREATE TABLE " + TABLE_FILMES + "( " +
                _ID  + " INTEGER PRIMARY KEY, " +
                COLUMN_TITULO + " INTEGER, " +
                COLUMN_LANCAMENTO + " LONG, " +
                COLUMN_SINOPSE + " TEXT, " +
                COLUMN_IMAGEM_POSTER + " TEXT, " +
                COLUMN_IMAGEM_BACK + " TEXT, " +
                COLUMN_ACESSO + " LONG )";
    }

    static String retornaDropTabelaFilmes() {

        return "DROP TABLE IF EXISTS " + TABLE_FILMES;
    }

    static String[] retornaCamposSelecao() {

        return new String[] {_ID, COLUMN_TITULO, COLUMN_LANCAMENTO, COLUMN_SINOPSE,
                COLUMN_IMAGEM_POSTER, COLUMN_IMAGEM_BACK, COLUMN_ACESSO};
    }

    static String retornaOrdenacaoConsulta() {

        return COLUMN_ACESSO + " DESC";
    }
}
