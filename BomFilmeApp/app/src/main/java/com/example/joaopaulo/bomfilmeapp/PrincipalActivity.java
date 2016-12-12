package com.example.joaopaulo.bomfilmeapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.joaopaulo.bomfilmeapp.Data.AcessoMovieDB;
import com.example.joaopaulo.bomfilmeapp.Data.ConexaoMovieDBAsyncTask;
import com.example.joaopaulo.bomfilmeapp.Data.Filme;
import com.example.joaopaulo.bomfilmeapp.Data.FilmesDBHelper;
import com.example.joaopaulo.bomfilmeapp.Fragments.ListaFilmesFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PrincipalActivity extends AppCompatActivity implements
ListaFilmesFragment.OnFragmentInteractionListener {

    static final int ORIGEM_INICIO = 1;
    static final int ORIGEM_BUSCA = 2;
    static final int ORIGEM_HISTORICO = 3;

    @BindView(R.id.tb_navigation)
    Toolbar tbNavigation;
    @BindView(R.id.drawer_layout)
    DrawerLayout dLayout;
    @BindView(R.id.nvv_principal)
    NavigationView nvPrincipal;
    @BindView(R.id.pb_filmes)
    ProgressBar pbFilmes;

    FilmesDBHelper filmesDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        ButterKnife.bind(this);

        inicializaSupportActionBar();
        inicializaNavigationView();
        inicializaDrawerLayout();

        handleIntent(getIntent());
        buscaFilmesParaVisualizar(ORIGEM_INICIO, "");

        filmesDB = new FilmesDBHelper(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_principal, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                dLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    /**
     *  Métodos auxiliares da activity.
     */
    private void inicializaSupportActionBar() {

        setSupportActionBar(tbNavigation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
    private void inicializaNavigationView() {

        nvPrincipal.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.mit_filmes_populares: buscaFilmesParaVisualizar(ORIGEM_INICIO, "");
                        break;
                    case R.id.mit_filmes_acessados: buscaFilmesParaVisualizar(ORIGEM_HISTORICO, "");
                        break;
                }

                dLayout.closeDrawers();

                return true;
            }
        });
    }
    private void inicializaDrawerLayout() {

        dLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {}

            @Override
            public void onDrawerOpened(View drawerView) {

                getSupportActionBar().setHomeButtonEnabled(true);
            }

            @Override
            public void onDrawerClosed(View drawerView) {

                getSupportActionBar().setHomeButtonEnabled(false);
            }

            @Override
            public void onDrawerStateChanged(int newState) {}
        });
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {

            String nomeFilme = intent.getStringExtra(SearchManager.QUERY);

            buscaFilmesParaVisualizar(ORIGEM_BUSCA, nomeFilme);
        }
    }
    public void detalhaFilme(Filme filme) {

        filmesDB.adicionarAcessoAoFilme(filme, System.currentTimeMillis());

        Intent intFilme = new Intent(PrincipalActivity.this, FilmeActivity.class);
        intFilme.putExtra("filme", filme);
        startActivity(intFilme);
    }

    public void buscaFilmesParaVisualizar(int origem, String nomeFilme) {

        switch (origem) {
            case ORIGEM_INICIO: new ConexaoMovieDBAsyncTask(this).execute(AcessoMovieDB.retornaUrlAcessoInicial());

                break;
            case ORIGEM_BUSCA: new ConexaoMovieDBAsyncTask(this).execute(AcessoMovieDB.retornaUrlPesquisa(nomeFilme));
                break;
            case ORIGEM_HISTORICO: verificaListaFilmes(filmesDB.buscarFimes(15));
                break;
        }
    }
    public void trataRetornoMovieDB(List<Filme> listaFilmes) {

        atualizaProgressBar(false, 0);
        verificaListaFilmes(listaFilmes);
    }

    public void verificaListaFilmes( List<Filme> listaFilmes) {

        if (listaFilmes != null) {

            if (listaFilmes.size() > 0) {

                ListaFilmesFragment fAtual = new ListaFilmesFragment();

                fAtual.setListaFilmes(listaFilmes);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frl_fragment, fAtual);
                ft.commit();

            } else {

                Toast.makeText(this, R.string.msg_erro_busca_filme, Toast.LENGTH_LONG).show();
            }
        } else {

            Toast.makeText(this, R.string.msg_erro_conexao, Toast.LENGTH_LONG).show();
        }
    }

    public void atualizaProgressBar(Boolean visibilidade, int progresso) {

        if (visibilidade) {

            pbFilmes.setVisibility(View.VISIBLE);
            pbFilmes.setProgress(progresso);

        } else {

            pbFilmes.setVisibility(View.INVISIBLE);
        }
    }

    public void atualizaImagem(ImageView imageView, String imagemFilme ) {

        Picasso.with(this)
                .load(AcessoMovieDB.retornaUrlImagem(imagemFilme))
                .placeholder(R.mipmap.bomfilme_icon)
                .into(imageView);
    }

    @Override
    public void onFragmentInteraction() { }
}
