package com.gamesmindt.myapplication.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gamesmindt.myapplication.Model.PokemonModel;
import com.gamesmindt.myapplication.R;

import java.util.List;

public class PokemonDetailAdapter extends RecyclerView.Adapter<PokemonDetailAdapter.PokemonViewHolder> {

    private PokemonModel pokemonModel;
    private List<String> desc;
    private Context context;

    public PokemonDetailAdapter(Context context, PokemonModel pokemonModel) {
        this.context = context;
        this.pokemonModel = pokemonModel;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_option, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        holder.bind(pokemonModel);
    }

    @Override
    public int getItemCount() {
        return 1; // Solo hay un objeto PokemonModel
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder {

        private TextView weightText, heightText, descriptiontxt;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            weightText = itemView.findViewById(R.id.weightTextnumber);
            heightText = itemView.findViewById(R.id.heightTextnumber);
            descriptiontxt = itemView.findViewById(R.id.description_txt);
        }

        @SuppressLint("SetTextI18n")
        public void bind(PokemonModel pokeMod) {
            weightText.setText(pokeMod.getWeight());
            heightText.setText(pokeMod.getHeight());
            descriptiontxt.setText(pokeMod.getDescription());
        }
    }
}
