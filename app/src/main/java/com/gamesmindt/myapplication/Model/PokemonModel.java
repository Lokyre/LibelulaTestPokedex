package com.gamesmindt.myapplication.Model;


import android.content.Context;
import android.content.Intent;

import com.gamesmindt.myapplication.activities.PokemonDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class PokemonModel {
    private String name;
    private int id;
    private String weight;
    private String height;
    private String imageUrl;
    private List<String> types;
    private List<String> abilities;
    private String description;


    // constrains
    public PokemonModel(String name, int id, String imageUrl, List<String> types, String weight, String height) {
        this.name = name;
        this.id = id;
        this.imageUrl = imageUrl;
        this.types = types;
        this.description = description;
        this.weight = weight;
        this.height = height;
    }

    public PokemonModel(String weight, String height, String imageUrl, List<String> types, List<String> abilities) {
        this.weight = weight;
        this.height = height;
        this.imageUrl = imageUrl;
        this.types = types;
        this.abilities = abilities;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getWeight() {
        return weight;
    }

    public String getHeight() {
        return height;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<String> getTypes() {
        return types;
    }

    public List<String> getAbilities() {
        return abilities;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Description is not going to be showed, because apparently the url i use does not have it,
    // but this "https://pokeapi.co/api/v2/pokemon-species/<pokemon_number>/ url contains some cool info about each pokemon and their description in different languages
    // Unfortunately, i did not use it, as i am running out of time, but i am planning on doing that anyway, even after submitting the project in its current state."

}