package com.gamesmindt.myapplication.configs;

import android.app.Activity;
import android.graphics.Color;
import android.widget.EditText;

import com.gamesmindt.myapplication.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoadingDialog {

    private Activity activity;
    private SweetAlertDialog pDialog;

    public LoadingDialog(Activity myActivity) {
        activity = myActivity;
    }

    public void startLoagingDialog() {
        pDialog = new SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Cargando..");
        pDialog.setCancelable(false);
        pDialog.show();
    }
    public void cerrarLoadingDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
        }
    }
}
