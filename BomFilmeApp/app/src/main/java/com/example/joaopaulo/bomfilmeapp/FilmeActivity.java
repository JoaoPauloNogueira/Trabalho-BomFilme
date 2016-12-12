package com.example.joaopaulo.bomfilmeapp;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joaopaulo.bomfilmeapp.Data.AcessoMovieDB;
import com.example.joaopaulo.bomfilmeapp.Data.Filme;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilmeActivity extends AppCompatActivity {

    @BindView(R.id.img_filme_back)
    ImageView filmeBack;
    @BindView(R.id.img_filme_poster)
    ImageView filmePoster;
    @BindView(R.id.txt_titulo_filme)
    TextView tituloFilme;
    @BindView(R.id.txt_back_filme)
    TextView tituloBackFilme;
    @BindView(R.id.txt_lancamento_filme)
    TextView lancamentoFilme;
    @BindView(R.id.txt_sinopse_filme)
    TextView sinopseFilme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filme);

        ButterKnife.bind(this);

        Bundle b = getIntent().getExtras();
        Filme f = (Filme) b.getSerializable("filme");

        Picasso.with(this)
                .load(AcessoMovieDB.retornaUrlImagem(f.getImagemBack()))
                .placeholder(R.mipmap.bomfilme)
                .into(filmeBack);

        Picasso.with(this)
                .load(AcessoMovieDB.retornaUrlImagem(f.getImagemPoster()))
                .placeholder(R.mipmap.bomfilme)
                .into(filmePoster);

        tituloBackFilme.setText(f.getTitulo());
        tituloFilme.setText(f.getTitulo());
        sinopseFilme.setText(f.getSinopse());
        sinopseFilme.setMovementMethod(new ScrollingMovementMethod());
        lancamentoFilme.setText(android.text.format.DateFormat.format("dd/MM/yyyy", f.getLancamento()));

    }

    private void finalizaVisualizacaoFilme() {

        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
