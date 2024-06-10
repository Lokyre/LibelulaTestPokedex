package com.gamesmindt.myapplication.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.gamesmindt.myapplication.R;

public class ProfileFragment extends Fragment {

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_profile, viewGroup, false);
        RelativeLayout apiweb = inflate.findViewById(R.id.apiweb);

        apiweb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // URL de la página web que deseas abrir
                String url = "https://pokeapi.co/api/v2/pokemon/26/";

                // Crear un Intent con la acción ACTION_VIEW
                Intent intent = new Intent(Intent.ACTION_VIEW);

                // Establecer la URL en el Intent
                intent.setData(Uri.parse(url));

                // Verificar si hay aplicaciones que puedan manejar el Intent
                if (getContext() != null) {
                    // Abrir la página web
                    getContext().startActivity(intent);
                } else {
                    // Si no hay aplicaciones que puedan manejar el Intent, mostrar un mensaje de error
                    Toast.makeText(getContext(), "No se pudo abrir la página web", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return inflate;
    }

    public void shareApp() {
        // Note: Corregir
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.SUBJECT", R.string.app_name);
        intent.putExtra("android.intent.extra.TEXT", R.string.base_link_apk);
        startActivity(Intent.createChooser(intent, "Share via"));
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
