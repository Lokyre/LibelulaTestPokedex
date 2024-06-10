package com.gamesmindt.myapplication.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gamesmindt.myapplication.Model.Ability;
import com.gamesmindt.myapplication.Model.PokemonModel;
import com.gamesmindt.myapplication.R;

import java.util.List;

public class PokemonAbilitiesAdapter extends RecyclerView.Adapter<PokemonAbilitiesAdapter.PokemonViewHolder> {

    private List<Ability> abilities;
    private Context context;

    public PokemonAbilitiesAdapter(Context context, List<Ability> abilities) {
        this.context = context;
        this.abilities = abilities;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_abilities, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        Ability ability = abilities.get(position);
        holder.bind(ability);
    }

    @Override
    public int getItemCount() {
        return abilities.size();
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewName, textViewEffect;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewEffect = itemView.findViewById(R.id.textViewEffect);
        }

        @SuppressLint("SetTextI18n")
        public void bind(Ability ability) {
            textViewName.setText(ability.getName());
            textViewEffect.setText(ability.getEffect());
        }
    }
}
