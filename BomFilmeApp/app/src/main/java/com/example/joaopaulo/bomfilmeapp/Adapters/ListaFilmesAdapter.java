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

    private PrincipalActivity mActivity;
    private List<Filme> mListaFilmes;

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

        holder.atualizaInformacoesDoFilme(mListaFilmes.get(position), position + 1);
    }

    @Override
    public int getItemCount() {
        return mListaFilmes.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.img_cardview)
        ImageView imageViewPoster;
        @BindView(R.id.txt_cardview)
        TextView textViewTitulo;
        @BindView(R.id.txt_cardiview_position)
        TextView textViewPosicao;
        @BindView(R.id.txt_cardiview_data)
        TextView textViewData;

        Filme filme;
        private PrincipalActivity activity;

        ItemViewHolder(final PrincipalActivity activity, View view) {
            super(view);
            this.activity = activity;
            ButterKnife.bind(this, view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.detalhaFilme(filme);
                }
            });
        }
        private void atualizaInformacoesDoFilme(Filme filme, int posicao) {

            this.filme = filme;
            textViewTitulo.setText(filme.getTitulo());

            String textoPosicao = posicao + ".";
            String textoData = "";

            if (filme.getDataAcesso() != null) {

                textoData = "Acesso: " + android.text.format.DateFormat.format("dd/MM/yyyy - HH:mm", filme.getDataAcesso());

            } else if (filme.getLancamento() != null) {

                textoData = "Lanc.: " + android.text.format.DateFormat.format("dd/MM/yyyy", filme.getLancamento());
            }

            textViewPosicao.setText(textoPosicao);
            textViewData.setText(textoData);

            Picasso.with(activity)
                    .load(AcessoMovieDB.retornaUrlImagem(filme.getImagemPoster()))
                    .placeholder(R.mipmap.bomfilme_icon)
                    .into(imageViewPoster);
        }
    }
}
