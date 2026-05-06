package com.example.chequeo;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;

public class DatosGlobales {
    public static double gastoTotalSemanal = 0.0;
    public static ArrayList<String> listaTransacciones = new ArrayList<>();

    public static void guardarDatos(Context context) {
        SharedPreferences pref = context.getSharedPreferences("MisGastos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putFloat("total", (float) gastoTotalSemanal);

        StringBuilder sb = new StringBuilder();
        for (String s : listaTransacciones) {
            sb.append(s).append(";");
        }
        editor.putString("lista", sb.toString());
        editor.apply();
    }
    public static void cargarDatos(Context context) {
        SharedPreferences pref = context.getSharedPreferences("MisGastos", Context.MODE_PRIVATE);
        gastoTotalSemanal = pref.getFloat("total", 0.0f);

        String listaS = pref.getString("lista", "");
        if (!listaS.isEmpty()) {
            String[] items = listaS.split(";");
            listaTransacciones = new ArrayList<>(Arrays.asList(items));
        }
    }
}