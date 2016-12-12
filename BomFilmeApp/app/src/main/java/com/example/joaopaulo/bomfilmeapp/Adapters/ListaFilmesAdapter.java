package com.example.joaopaulo.bomfilmeapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joaopaulo.bomfilmeapp.Data.AcessoMovieDB;
import com.example.joaopaulo.bomfilmeapp.Data.Filme;
import com.example.joaopaulo.bomfilmeapp.PrincipalActivity;
import com.example.joaopaulo.bomfilmeapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListaFilmesAdapter extends RecyclerView.Adapter<ListaFilmesAdapter.ItemViewHolder> {

    PrincipalActivity mActivity;
    List<Filme> mListaFilmes;

    public ListaFilmesAdapter(PrincipalActivity inicialActivity, List<Filme> listaFilmes) {
        mActivity = inicialActivity;
        mListaFilmes = listaFilmes;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filme, parent, false);
        return new ItemViewHolder(mActivity, view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        holder.atualizaInformacoesDoFilme(mListaFilmes.get(position));
    }

    @Override
    public int getItemCount() {
        return mListaFilmes.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.img_cardview)
        ImageView imageView;
        @BindView(R.id.txt_cardview)
        TextView textView;
        Filme filme;
        View view;
        private PrincipalActivity activity;

        public ItemViewHolder(PrincipalActivity activity, View view) {
            super(view);
            this.activity = activity;
            ButterKnife.bind(this, view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mActivity.detalhaFilme(filme);
                }
            });
        }
        private void atualizaInformacoesDoFilme(Filme filme) {

            this.filme = filme;
            textView.setText(filme.getTitulo());

            activity.atualizaImagem(imageView, filme.getImagemPoster());
            Picasso.with(activity)
                    .load(AcessoMovieDB.retornaUrlImagem(filme.getImagemPoster()))
                    .placeholder(R.mipmap.bomfilme_icon)
                    .into(imageView);
        }
    }
}
