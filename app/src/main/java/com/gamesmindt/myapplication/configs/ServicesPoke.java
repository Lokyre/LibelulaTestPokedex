package com.gamesmindt.myapplication.configs;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gamesmindt.myapplication.Model.PokemonModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ServicesPoke {
    private RequestQueue requestQueue;
    public final String DESC_URL = "https://pokeapi.co/api/v2/pokemon-species/";
    public final String GENERAL_URL = "https://pokeapi.co/api/v2/pokemon/";

    public ServicesPoke(Context context) {
        this.requestQueue = Volley.newRequestQueue(context);
    }


}
