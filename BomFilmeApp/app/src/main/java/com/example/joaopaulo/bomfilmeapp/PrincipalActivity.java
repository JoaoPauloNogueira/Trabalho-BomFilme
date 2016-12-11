package com.example.joaopaulo.bomfilmeapp;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PrincipalActivity extends AppCompatActivity {

    @BindView(R.id.tb_navigation)
    Toolbar tbNavigation;
    @BindView(R.id.drawer_layout)
    DrawerLayout dLayout;
    @BindView(R.id.nvv_principal)
    NavigationView nvPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        ButterKnife.bind(this);

        inicializaSupportActionBar();
        inicializaNavigationView();
        inicializaDrawerLayout();
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
                Toast.makeText(getApplicationContext(), "onDrawerClosed", Toast.LENGTH_SHORT).show();
                dLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
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

                    case R.id.mit_filmes_populares: Toast.makeText(getApplicationContext(), R.string.it_filmes_populares, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.mit_filmes_acessados: Toast.makeText(getApplicationContext(), R.string.it_filmes_acessados, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(),"onDrawerOpened", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                Toast.makeText(getApplicationContext(),"onDrawerClosed", Toast.LENGTH_SHORT).show();
                getSupportActionBar().setHomeButtonEnabled(false);
            }

            @Override
            public void onDrawerStateChanged(int newState) {}
        });
    }
}
