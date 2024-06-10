package com.gamesmindt.myapplication.Model;

public class Ability {
    private String name;
    private String effect;

    public Ability(String name, String effect) {
        this.name = name;
        this.effect = effect;
    }

    public String getName() {
        return name;
    }
    public String getEffect() {
        return effect;
    }


}
