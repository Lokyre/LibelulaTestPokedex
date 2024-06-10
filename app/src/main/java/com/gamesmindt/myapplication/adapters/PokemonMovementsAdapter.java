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
import com.gamesmindt.myapplication.Model.Movement;
import com.gamesmindt.myapplication.R;

import java.util.List;

public class PokemonMovementsAdapter extends RecyclerView.Adapter<PokemonMovementsAdapter.PokemonViewHolder> {

    private List<Movement> movements;
    private Context context;

    public PokemonMovementsAdapter(Context context, List<Movement> movements) {
        this.context = context;
        this.movements = movements;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_moves, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        Movement movement = movements.get(position);
        holder.bind(movement);
    }

    @Override
    public int getItemCount() {
        return movements.size();
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewName, textViewEffect, textViewAccu, textViewPower;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewEffect = itemView.findViewById(R.id.textViewEffect);
            textViewAccu = itemView.findViewById(R.id.textViewAccu);
            textViewPower = itemView.findViewById(R.id.textViewPower);
        }

        @SuppressLint("SetTextI18n")
        public void bind(Movement movement) {
            textViewName.setText(movement.getName());
            textViewEffect.setText(movement.getEffect());
            textViewAccu.setText(movement.getAccuracy());
            textViewPower.setText(movement.getPower());

        }
    }
}
