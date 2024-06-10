package com.gamesmindt.myapplication.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.gamesmindt.myapplication.Model.PokemonModel;
import com.gamesmindt.myapplication.R;
import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.OfertaViewHolder> {

    private Context context;
    private List<PokemonModel> listPoke;

    public PokemonAdapter(Context context, List<PokemonModel> listPoke) {
        this.context = context;
        this.listPoke = listPoke;
    }

    public interface OnClickListener {
        void onVerMasClick(Integer id_pokemon, String name_pokemon);

    }

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener listener) {
        this.onClickListener = listener;
    }

    @NonNull
    @Override
    public OfertaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pokemon, parent, false);
        return new OfertaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfertaViewHolder holder, int position) {
        PokemonModel pokmod = listPoke.get(position);
        holder.bind(pokmod);

        holder.cardViewAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onVerMasClick(listPoke.get(holder.getAdapterPosition()).getId(), listPoke.get(holder.getAdapterPosition()).getName());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPoke.size();
    }

    public void filterList(List<PokemonModel> filteredList) {
        listPoke = filteredList;
        notifyDataSetChanged();
    }

    public class OfertaViewHolder extends RecyclerView.ViewHolder {

        private TextView name_txt, idpoke_txt;
        private RelativeLayout relativeLayout;
        private ImageView pokeImg;
        private CardView cardViewAction;

        public OfertaViewHolder(@NonNull View itemView) {
            super(itemView);
            idpoke_txt = itemView.findViewById(R.id.idpoke_txt);
            name_txt = itemView.findViewById(R.id.name_txt);
            pokeImg = itemView.findViewById(R.id.pokeImg);
            cardViewAction = itemView.findViewById(R.id.cardViewAction);
            relativeLayout = itemView.findViewById(R.id.relativeLayoutTop);
        }

        @SuppressLint("SetTextI18n")
        public void bind(PokemonModel pokeMod) {
            idpoke_txt.setText("# "+String.valueOf(pokeMod.getId()));

            name_txt.setText(pokeMod.getName());
            Glide.with(itemView.getContext())
                    .load(pokeMod.getImageUrl())
                    //.placeholder(R.drawable.placeHolder)
                    //.error(R.drawable.error)
                    .into(pokeImg);
            for (String type : pokeMod.getTypes()) {
                switch (type) {
                    case "grass":
                        relativeLayout.setBackgroundResource(R.drawable.bgpokemonsitems);
                        break;
                    case "fire":
                        relativeLayout.setBackgroundResource(R.drawable.bgitemspokemonred);
                        break;
                    case "ground":
                        relativeLayout.setBackgroundResource(R.drawable.bgitemspokemongrow);
                        break;
                    case "electric":
                        relativeLayout.setBackgroundResource(R.drawable.bgitemspokemonyellow);
                        break;
                    default:
                        // Si el tipo no coincide con ninguno de los anteriores, puedes establecer un fondo predeterminado
                        relativeLayout.setBackgroundResource(R.drawable.bgpokemonsitems);
                        break;
                }
            }
        }
    }
}