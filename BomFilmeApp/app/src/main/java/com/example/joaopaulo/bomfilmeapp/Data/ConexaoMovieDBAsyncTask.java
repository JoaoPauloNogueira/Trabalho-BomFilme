package com.example.joaopaulo.bomfilmeapp.Data;

import android.os.AsyncTask;
import android.util.Log;

import com.example.joaopaulo.bomfilmeapp.PrincipalActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ConexaoMovieDBAsyncTask extends AsyncTask<String, Integer, List<Filme>> {

    private PrincipalActivity mActivity;

    public ConexaoMovieDBAsyncTask(PrincipalActivity activity) {
        mActivity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mActivity.atualizaProgressBar(true, 0);
    }

    @Override
    protected List<Filme> doInBackground(String... params) {

        String urlString = params[0];
        HttpURLConnection urlConnection = null;
        StringBuilder stringBuilder = new StringBuilder();

        try {

            URL url = new URL(urlString);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            publishProgress(25);

            InputStream inputStream = urlConnection.getInputStream();

            if (inputStream == null) {

                return null;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();
            publishProgress(50);

            return  retornaListaFilmes(stringBuilder.toString());

        } catch (IOException e) {

            Log.e("PlaceholderFragment", "Error ", e);
            return null;

        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        mActivity.atualizaProgressBar(true, values[0]);
    }

    @Override
    protected void onPostExecute(List<Filme> listaFilmes) {

        mActivity.trataRetornoMovieDB(listaFilmes);

        super.onPostExecute(listaFilmes);
    }

    private List<Filme> retornaListaFilmes(String jsonString) {

        List<Filme> listaFilmes = new ArrayList<>();

        try {

            JSONObject resultado = new JSONObject(jsonString);
            JSONArray filmesArray = resultado.getJSONArray("results");

            if ( filmesArray.length() > 0) {
                JSONObject filmeJson;

                int fator = 50 / filmesArray.length();

                for (int i = 0; i < filmesArray.length(); i++) {

                    publishProgress(50 + (fator * i));

                    filmeJson = new JSONObject(filmesArray.getString(i));
                    Date dataLancamento;

                    try {
                        dataLancamento = Date.valueOf(filmeJson.getString("release_date"));

                        Filme filme = new Filme(filmeJson.getString("title"), dataLancamento,
                                filmeJson.getString("overview"), filmeJson.getString("poster_path").replace("/", ""),
                                filmeJson.getString("backdrop_path").replace("/", ""));

                        listaFilmes.add(filme);

                    } catch (Exception e) {

                        Log.e("Lista de Filmes", "Error ", e);
                    }
                }
            }
        } catch (JSONException e) {
            Log.e("DEVMEDIA", "Erro no parsing do JSON", e);
        }

        publishProgress(100);
        return listaFilmes;
    }

}
