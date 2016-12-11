package com.example.joaopaulo.bomfilmeapp.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Joao Paulo on 11/12/2016.
 */

public class FilmesDBHelper extends SQLiteOpenHelper {

    public FilmesDBHelper(Context context) {
        super(context, DataBaseContract.DATABASE_NAME, null, DataBaseContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLDB) {

        sqLDB.execSQL(DataBaseContract.retornaCriacaoTabelaFilmes());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLDB, int i, int i1) {

        sqLDB.execSQL(DataBaseContract.retornaDropTabelaFilmes());
        onCreate(sqLDB);
    }

    public static Long persistirData(Date date) {
        if (date != null) {
            return date.getTime();
        }
        return null;
    }
    public static Date carregarData(Cursor cursor, int indice) {

        if (cursor.isNull(indice)) {

            return null;
        }
        return new Date(cursor.getLong(indice));
    }

    public long adicionarAcessoAoFilme(Filme filme, Date acesso) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DataBaseContract.COLUMN_TITULO, filme.getTitulo());
        values.put(DataBaseContract.COLUMN_LANCAMENTO, persistirData(filme.getLancamento()));
        values.put(DataBaseContract.COLUMN_SINOPSE, filme.getSinopse());
        values.put(DataBaseContract.COLUMN_IMAGEM_POSTER, filme.getImagemPoster());
        values.put(DataBaseContract.COLUMN_IMAGEM_BACK, filme.getImagemBack());
        values.put(DataBaseContract.COLUMN_ACESSO, persistirData(acesso));

        long novoId = db.insert(DataBaseContract.TABLE_FILMES, null, values);
        db.close();

        return novoId;
    }


    public List<Filme> buscarFimes(int quantidade) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DataBaseContract.TABLE_FILMES, DataBaseContract.retornaCamposSelecao(),
                null, null, null, null, DataBaseContract.retornaOrdenacaoConsulta());

        List<Filme> lf = new ArrayList<Filme>();

        if (cursor.moveToFirst()) {

            do {

                Filme f = new Filme(cursor.getString(1), carregarData(cursor, 2), cursor.getString(3),
                        cursor.getString(4), cursor.getString(5));

                Date dataAcesso = carregarData(cursor, 6);
                if ( dataAcesso != null) {

                    f.setDataAcesso(dataAcesso);
                }

                lf.add(f);
            } while (cursor.moveToNext() && (quantidade == 0 || lf.size() <= quantidade));
        }
        cursor.close();

        return lf;
    }
}
