package com.example.joaopaulo.bomfilmeapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.example.joaopaulo.bomfilmeapp.Adapters.ListaFilmesAdapter;
import com.example.joaopaulo.bomfilmeapp.Data.Filme;
import com.example.joaopaulo.bomfilmeapp.PrincipalActivity;
import com.example.joaopaulo.bomfilmeapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ListaFilmesFragment extends Fragment {

    private Unbinder unbinder;
    private OnFragmentInteractionListener mListener;
    @BindView(R.id.list_items)
    RecyclerView rvListaFilmes;
    List<Filme> listaFilmes;

    public ListaFilmesFragment() { }

    public void setListaFilmes(List<Filme> listaFilmes) {

        this.listaFilmes = listaFilmes;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lista_filmes, container, false);

        unbinder = ButterKnife.bind(this, view);

        final ListaFilmesAdapter listaFilmesAdapter =  new ListaFilmesAdapter((PrincipalActivity) mListener, listaFilmes);

        rvListaFilmes.setAdapter(listaFilmesAdapter);
        rvListaFilmes.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction();
    }
}
