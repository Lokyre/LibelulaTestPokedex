package com.gamesmindt.myapplication.activities;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.gamesmindt.myapplication.Model.Ability;
import com.gamesmindt.myapplication.Model.Movement;
import com.gamesmindt.myapplication.Model.PokemonModel;
import com.gamesmindt.myapplication.R;
import com.gamesmindt.myapplication.adapters.PokemonAbilitiesAdapter;
import com.gamesmindt.myapplication.adapters.PokemonDetailAdapter;
import com.gamesmindt.myapplication.adapters.PokemonMovementsAdapter;
import com.gamesmindt.myapplication.configs.LoadingDialog;
import com.gamesmindt.myapplication.configs.ServicesPoke;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class PokemonDetailActivity extends AppCompatActivity {

    Window window = null;
    private RequestQueue requestQueue;
    private ServicesPoke servicesPoke;
    private PokemonModel pokemonList;
    private PokemonDetailAdapter pokemonDetailAdapter;
    private PokemonAbilitiesAdapter pokemonAbilitiesAdapter;
    private PokemonMovementsAdapter pokemonMovementsAdapter;
    private RecyclerView recyclerView;
    private int idPokemon;
    private ChipGroup mTypeChipGroup;
    LoadingDialog loadingDialog = new LoadingDialog(PokemonDetailActivity.this);
    private TextView id_txt, name_txt, weight_txt, height_txt, telefono_txt, estado_txt;
    private ImageView imageView;
    private Button button;

    private boolean isLoading = false; // Para evitar múltiples llamadas simultáneas


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_pokemon);
        servicesPoke = new ServicesPoke(this); // Inicializar el objeto servicesPoke aquí

        findViewById(R.id.button1).setOnClickListener(this::onButtonClick);
        findViewById(R.id.button2).setOnClickListener(this::onButtonClick);
        findViewById(R.id.button3).setOnClickListener(this::onButtonClick);

        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.black));

        name_txt = findViewById(R.id.name_txt);
        id_txt = findViewById(R.id.id_txt);
        imageView = findViewById(R.id.imageView);
        mTypeChipGroup = findViewById(R.id.typeChipGroup);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        if (intent != null) {
            int id = intent.getIntExtra("id", 0);
            String name = intent.getStringExtra("name");

            name_txt.setText(name.substring(0, 1).toUpperCase() + name.substring(1));
            id_txt.setText("# " + id);
               /* mWeightTextnumber.setText(weight);
                mHeightTextnumber.setText(height);*/
            CargarPokemon(id);
        }
    }

    private void CargarPokemon(int ids) {
        idPokemon = ids;
        if (isLoading) return; // Si ya estamos cargando, no hacer nada
        isLoading = true;
        loadingDialog.startLoagingDialog();
        requestQueue = Volley.newRequestQueue(this);
        String url = "https://pokeapi.co/api/v2/pokemon/" + ids + "/"; // URL con paginación
        JsonObjectRequest requestDetail = new JsonObjectRequest(Request.Method.GET, url, null,
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
                            System.out.println(typesArray.getJSONObject(j).getJSONObject("type").getString("name") + "Topop");
                        }
                        List<String> abilities = new ArrayList<>();
                        JSONArray abilitiesArray = detailResponse.getJSONArray("abilities");
                        for (int l = 0; l < abilitiesArray.length(); l++) {
                            abilities.add(abilitiesArray.getJSONObject(l).getJSONObject("ability").getString("name"));
                            System.out.println(abilitiesArray.getJSONObject(l).getJSONObject("ability").getString("name") + "Topop12");

                        }
                        PokemonModel pokemon = new PokemonModel(weight, height, imageUrl, types, abilities);
                        pokemonList = pokemon;
                        putData();
                        loadingDialog.cerrarLoadingDialog();
                        isLoading = false;
                        Button button1 = findViewById(R.id.button1); // Ajusta el ID del botón si es necesario
                        onButtonClick(button1);
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


    public void putData() {
        Glide.with(this)
                .load(pokemonList.getImageUrl())
                //.placeholder(R.drawable.placeHolder)
                //.error(R.drawable.error)
                .into(imageView);
        for (String type : pokemonList.getTypes()) {
            Chip chip = new Chip(this);
            chip.setText(type);
            chip.setBackgroundResource(R.drawable.bg_chip_semitransparent);
            mTypeChipGroup.addView(chip);
        }
    }

    public void onButtonClick(View view) {
        System.out.println("CLICAKDO");
        View indicator = findViewById(R.id.selectionIndicator);
        float targetX = view.getX() + view.getWidth() / 2 - indicator.getWidth() / 2;
        ObjectAnimator anim = ObjectAnimator.ofFloat(indicator, "translationX", indicator.getTranslationX(), targetX);
        anim.setDuration(300);
        anim.start();

        if (view.getId() == R.id.button1) {
            String url = servicesPoke.DESC_URL + idPokemon + "/";
            JsonObjectRequest requestDetail = new JsonObjectRequest(Request.Method.GET, url, null,
                    response -> {
                        try {
                            Set<String> englishFlavorTexts = new HashSet<>(); // Conjunto para almacenar los textos de sabor en inglés sin duplicados

                            JSONArray flavorTextEntries = response.getJSONArray("flavor_text_entries");
                            int count = 0;

                            for (int i = 0; i < flavorTextEntries.length(); i++) {
                                if (count >= 3) break;
                                final JSONObject entry = flavorTextEntries.getJSONObject(i); // Declarar como final
                                final JSONObject languageObject = entry.getJSONObject("language"); // Declarar como final
                                String languageName = languageObject.getString("name");
                                if (languageName.equals("en")) {
                                    String flavorText = entry.getString("flavor_text");
                                    String cleanedFlavorText = flavorText.replace("\n", " ").trim(); // Limpiar y recortar el texto de sabor
                                    englishFlavorTexts.add(cleanedFlavorText);
                                    count++;
                                }
                            }
                            StringBuilder descriptionBuilder = new StringBuilder();
                            for (String flavor : englishFlavorTexts) {
                                descriptionBuilder.append(flavor).append(" ");
                            }
                            String descripcion_txt = descriptionBuilder.toString().trim();

                            pokemonList.setDescription(descripcion_txt);
                            pokemonDetailAdapter = new PokemonDetailAdapter(this, pokemonList);
                            recyclerView.setAdapter(pokemonDetailAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> error.printStackTrace()
            );
            requestQueue.add(requestDetail);
        } else if (view.getId() == R.id.button2) {
            List<Ability> abilities = new ArrayList<>();
            String url = servicesPoke.GENERAL_URL + idPokemon + "/";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    response -> {
                        try {
                            JSONArray abilitiesArray = response.getJSONArray("abilities");
                            for (int i = 0; i < abilitiesArray.length(); i++) {
                                JSONObject abilityObject = abilitiesArray.getJSONObject(i);
                                JSONObject ability = abilityObject.getJSONObject("ability");
                                String abilityName = ability.getString("name");
                                String abilityUrl = ability.getString("url");

                                JsonObjectRequest requestDetail = new JsonObjectRequest(Request.Method.GET, abilityUrl, null,
                                        detailResponse -> {
                                            try {
                                                JSONArray effectEntriesArray = detailResponse.getJSONArray("effect_entries");
                                                String englishEffect = "";

                                                for (int x = 0; x < effectEntriesArray.length(); x++) {
                                                    JSONObject effectEntry = effectEntriesArray.getJSONObject(x);
                                                    JSONObject languageObject = effectEntry.getJSONObject("language");
                                                    String languageName = languageObject.getString("name");

                                                    // Verificar si el idioma es inglés
                                                    if (languageName.equals("en")) {
                                                        englishEffect = effectEntry.getString("effect").replace("-", " ");
                                                        break;
                                                    }
                                                }

                                                Ability abilityModel = new Ability(abilityName, englishEffect);
                                                abilities.add(abilityModel);
                                                pokemonAbilitiesAdapter = new PokemonAbilitiesAdapter(this, abilities);
                                                recyclerView.setAdapter(pokemonAbilitiesAdapter);

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        },
                                        error -> {
                                            error.printStackTrace();
                                        }
                                );
                                requestQueue.add(requestDetail);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        error.printStackTrace();
                    }
            );

            requestQueue.add(request);

        } else if (view.getId() == R.id.button3) {
            System.out.println("Button 3 clicked");

            List<Movement> movements = new ArrayList<>();
            String url = servicesPoke.GENERAL_URL + idPokemon + "/";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    response -> {
                        try {
                            JSONArray abilitiesArray = response.getJSONArray("moves");
                            for (int i = 0; i < abilitiesArray.length(); i++) {
                                JSONObject abilityObject = abilitiesArray.getJSONObject(i);
                                JSONObject ability = abilityObject.getJSONObject("move");
                                String movementName = ability.getString("name");
                                String movementUrl = ability.getString("url");

                                JsonObjectRequest requestDetail = new JsonObjectRequest(Request.Method.GET, movementUrl, null,
                                        detailResponse -> {
                                            try {

                                                String accuracy = detailResponse.isNull("accuracy") ? "N/A" : detailResponse.getString("accuracy");
                                                String power = detailResponse.isNull("power") ? "N/A" : detailResponse.getString("power");

                                                JSONArray effectEntriesArray = detailResponse.getJSONArray("effect_entries");
                                                String englishEffect = "";

                                                for (int x = 0; x < effectEntriesArray.length(); x++) {
                                                    JSONObject effectEntry = effectEntriesArray.getJSONObject(x);
                                                    JSONObject languageObject = effectEntry.getJSONObject("language");
                                                    String languageName = languageObject.getString("name");

                                                    // Verificar si el idioma es inglés
                                                    if (languageName.equals("en")) {
                                                        englishEffect = effectEntry.getString("effect").replace("-", " ");
                                                        break;
                                                    }
                                                }

                                                Movement movementModel = new Movement(movementName, englishEffect,accuracy,power);
                                                movements.add(movementModel);
                                                pokemonMovementsAdapter = new PokemonMovementsAdapter(this, movements);
                                                recyclerView.setAdapter(pokemonMovementsAdapter);

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        },
                                        error -> {
                                            error.printStackTrace();
                                        }
                                );
                                requestQueue.add(requestDetail);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        error.printStackTrace();
                    }
            );

            requestQueue.add(request);

        } else {
        }
    }

    private void loadPokemonData() {

    }
}
