package com.example.joaopaulo.bomfilmeapp.Data;


public class AcessoMovieDB {

    static final String API_KEY = "api_key=6a7947e1163bd0fa190e5b2d583b018c";
    static final String LINGUAGEM = "&language=pt-BR";
    static final String QUERY = "&query=";
    static final String URL_IMAGEM = "http://image.tmdb.org/t/p/w500/";
    static final String URL_INICIAL = "https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc";
    static final String URL_PESQUISA = "https://api.themoviedb.org/3/search/movie";

    public AcessoMovieDB() {}

    public static String retornaUrlAcessoInicial() {

        return URL_INICIAL + "&" + API_KEY + LINGUAGEM;
    }

    public static String retornaUrlImagem(String codigoImagem) {

        return URL_IMAGEM + codigoImagem;
    }

    public static String retornaUrlPesquisa(String nomeFilme) {

        return URL_PESQUISA + "?" + API_KEY + QUERY + nomeFilme.replace(" ", "%20") + LINGUAGEM;
    }
}
