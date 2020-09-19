package com.r.indian_rivers.views.alertdialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.r.indian_rivers.R;
import com.r.indian_rivers.util.PrefManager;

public class ShowAlertDialog {

    private String lang = "";
    private PrefManager prefManager;

    private Context context;
    private View view = null;
    private AlertResponse response;

    public ShowAlertDialog(Context context, AlertResponse response) {
        this.context = context;
        this.response = response;
    }

    public ShowAlertDialog(Context context, View view, AlertResponse response) {
        this.context = context;
        this.view = view;
        this.response = response;
    }

    public void showExitAlert() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context, R.style.AlertDialogTheme);
        if (view != null) {
            builder.setView(view);
        }
        else {
            builder.setMessage("");
        }
        builder.setTitle("Do you want to Exit?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                response.onPositiveClick("");
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void showLanguageAlert() {
        String[] items = context.getResources().getStringArray(R.array.languages);

        prefManager = new PrefManager(context);

        int checkedItem;
        if (!prefManager.getLang().isEmpty()) {
            if (prefManager.getLang().equals("Hindi")) {
                checkedItem = 0;
            } else {
                checkedItem = 1;
            }
        } else {
            checkedItem = 1;
        }

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context, R.style.AlertDialogTheme);
        builder.setTitle(context.getString(R.string.alert_set_lang_title));
        builder.setCancelable(false);
        if (view != null) {
            builder.setView(view);
        }
        builder.setPositiveButton(context.getString(R.string.alert_set_lang_positive), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
                if (!lang.isEmpty()) {
                    prefManager.setLang(lang);
                    response.onPositiveClick(lang);
                }
            }
        });
        builder.setNeutralButton(context.getString(R.string.alert_set_lang_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        builder.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        lang = "Hindi";
                        break;
                    case 1:
                        lang = "English";
                        break;
                }
            }
        });
        builder.show();
    }
}
