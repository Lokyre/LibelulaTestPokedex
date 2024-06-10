package com.gamesmindt.myapplication.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gamesmindt.myapplication.Model.PokemonModel;
import com.gamesmindt.myapplication.R;
import com.gamesmindt.myapplication.activities.PokemonDetailActivity;
import com.gamesmindt.myapplication.adapters.PokemonAdapter;
import com.gamesmindt.myapplication.configs.LoadingDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements PokemonAdapter.OnClickListener {
    private List<PokemonModel> pokemonList = new ArrayList<>(); // List with pokemons
    private RecyclerView recyclerView;
    private PokemonAdapter pokemonAdapter;
    LoadingDialog loadingDialog;
    private RequestQueue requestQueue;
    private static final int PAGE_SIZE = 40; // Tamaño del bloque
    private int offset = 0; // Desplazamiento inicial
    private boolean isLoading = false; // Para evitar múltiples llamadas simultáneas
    View view;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        loadingDialog = new LoadingDialog(requireActivity());
    }

    public interface OnMoreButtonClickListener {
        void onMoreButtonClicked();
    }

    private OnMoreButtonClickListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (OnMoreButtonClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnMoreButtonClickListener");
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        view = layoutInflater.inflate(R.layout.fragment_home, viewGroup, false);
        CargarPokemons();
        return view;
    }

    private void CargarPokemons() {
        if (isLoading) return; // Si ya estamos cargando, no hacer nada
        isLoading = true;
        loadingDialog.startLoagingDialog();

        requestQueue = Volley.newRequestQueue(getContext());
        String url = "https://pokeapi.co/api/v2/pokemon?limit=" + PAGE_SIZE + "&offset=" + offset; // URL con paginación
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray results = response.getJSONArray("results"); // Nombre y URL del Pokémon están en el atributo results
                        for (int i = 0; i < results.length(); i++) {
                            final int index = i; // Declarar una variable final dentro del bucle
                            JSONObject pokemonObject = results.getJSONObject(i);
                            String name = pokemonObject.getString("name");
                            String urlDetail = pokemonObject.getString("url"); // URL para detalles específicos del Pokémon

                            JsonObjectRequest requestDetail = new JsonObjectRequest(Request.Method.GET, urlDetail, null,
                                    detailResponse -> {
                                        try {
                                            int id = detailResponse.getInt("id");
                                            String weight = detailResponse.getString("weight") + " KG";
                                            String height = detailResponse.getString("height") + " M";
                                            String imageUrl = detailResponse.getJSONObject("sprites").getJSONObject("other").getJSONObject("home").getString("front_default");

                                            List<String> types = new ArrayList<>();
                                            JSONArray typesArray = detailResponse.getJSONArray("types");
                                            for (int j = 0; j < typesArray.length(); j++) {
                                                types.add(typesArray.getJSONObject(j).getJSONObject("type").getString("name"));
                                                System.out.println(typesArray.getJSONObject(j).getJSONObject("type").getString("name") + "TYPOEE");
                                            }

                                            PokemonModel pokemon = new PokemonModel(name, id, imageUrl, types, weight, height);
                                            pokemonList.add(pokemon); // Añadir el Pokémon a la lista

                                            if (index == results.length() - 1) {
                                                setupRecyclerView(); // Configurar el RecyclerView al final de la carga
                                                loadingDialog.cerrarLoadingDialog();
                                                isLoading = false; // Indicar que la carga ha terminado
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    },
                                    error -> {
                                        error.printStackTrace();
                                        isLoading = false;
                                        loadingDialog.cerrarLoadingDialog();
                                    }
                            );
                            requestQueue.add(requestDetail);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        isLoading = false;
                        loadingDialog.cerrarLoadingDialog();
                    }
                },
                error -> {
                    error.printStackTrace();
                    isLoading = false;
                    loadingDialog.cerrarLoadingDialog();
                }
        );
        requestQueue.add(request);
    }


    @Override
    public void onVerMasClick(Integer id_pokemon, String name_pokemon) {
        Intent intent = new Intent(getActivity(), PokemonDetailActivity.class);
        intent.putExtra("id", id_pokemon);
        intent.putExtra("name", name_pokemon);
        startActivity(intent);
    }


    private void setupRecyclerView() {
        recyclerView = view.findViewById(R.id.recent_items);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        pokemonAdapter = new PokemonAdapter(getContext(), pokemonList);
        pokemonAdapter.setOnClickListener(HomeFragment.this);
        recyclerView.setAdapter(pokemonAdapter);

        // Añadir el scroll listener para cargar más datos cuando se llegue al final
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == pokemonList.size() - 1) {
                    offset += PAGE_SIZE; // Incrementar el offset para la próxima carga
                    CargarPokemons(); // Cargar el siguiente bloque de datos
                }
            }
        });
    }


    public void onDestroy() {
        super.onDestroy();
    }
}
