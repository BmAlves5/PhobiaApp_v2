package com.feup.bmta.phobiaapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class DialogUtils {

    public static void showExitConfirmationDialog(final Context context, final Class<?> targetActivity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Exit Confirmation");
        builder.setMessage("Are you sure you want to exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Sim, redirecione para a atividade de destino
                Intent intent = new Intent(context, targetActivity);
                context.startActivity(intent);
                if (context instanceof Activity) {
                    ((Activity) context).finish(); // Finaliza a atividade atual
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Não, não faça nada
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}
