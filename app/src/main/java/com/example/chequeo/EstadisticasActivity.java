package com.example.chequeo;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chequeo.databinding.ActivityEstadisticasBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EstadisticasActivity extends AppCompatActivity {

    private ActivityEstadisticasBinding binding;
    private final int[] COLORES_CATEGORIA = {
            0xFF4F8EF7,
            0xFF7B68EE,
            0xFF3ECFA1,
            0xFFF76F6F,
            0xFFFFB347,
            0xFF5FD3C4,
            0xFFE07BE0,
            0xFF89D44D
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEstadisticasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configurarVideoFondo();
        cargarEstadisticasPorCategoria();
    }
    private void cargarEstadisticasPorCategoria() {
        binding.containerEstadisticas.removeAllViews();
        Map<String, List<Double>> porCategoria = new LinkedHashMap<>();

        for (String item : DatosGlobales.listaTransacciones) {
            String[] partes = item.split(": \\$");
            if (partes.length == 2) {
                String categoria = partes[0].trim();
                try {
                    double monto = Double.parseDouble(partes[1].trim());
                    if (!porCategoria.containsKey(categoria)) {
                        porCategoria.put(categoria, new ArrayList<>());
                    }
                    porCategoria.get(categoria).add(monto);
                } catch (NumberFormatException e) {
                }
            }
        }

        if (porCategoria.isEmpty()) {
            mostrarEstadoVacio();
            return;
        }

        Map<String, Double> totalesPorCategoria = new LinkedHashMap<>();
        double totalGeneral = 0.0;

        for (Map.Entry<String, List<Double>> entry : porCategoria.entrySet()) {
            double suma = 0;
            for (double v : entry.getValue()) suma += v;
            totalesPorCategoria.put(entry.getKey(), suma);
            totalGeneral += suma;
        }

        List<Map.Entry<String, Double>> listaOrdenada = new ArrayList<>(totalesPorCategoria.entrySet());
        Collections.sort(listaOrdenada, (a, b) -> Double.compare(b.getValue(), a.getValue()));

        actualizarResumenHeader(totalGeneral, listaOrdenada.size(),
                listaOrdenada.isEmpty() ? "" : listaOrdenada.get(0).getKey());
        LayoutInflater inflater = LayoutInflater.from(this);
        int colorIndex = 0;

        for (Map.Entry<String, Double> entry : listaOrdenada) {
            String categoria = entry.getKey();
            double montoCategoria = entry.getValue();
            int cantTransacciones = porCategoria.get(categoria).size();
            double porcentaje = (totalGeneral > 0) ? (montoCategoria / totalGeneral * 100) : 0;
            int colorCirculo = COLORES_CATEGORIA[colorIndex % COLORES_CATEGORIA.length];
            colorIndex++;

            View fila = inflater.inflate(
                    R.layout.item_transaccion,
                    binding.containerEstadisticas,
                    false
            );

            TextView tvLetra = fila.findViewById(R.id.tvLetraIcono);
            if (!categoria.isEmpty()) {
                tvLetra.setText(categoria.substring(0, 1).toUpperCase());
            }
            tvLetra.getBackground().setTint(colorCirculo);

            TextView tvCat = fila.findViewById(R.id.tvCat);
            tvCat.setText(categoria);

            TextView tvCantidad = fila.findViewById(R.id.tvCantTransacciones);
            tvCantidad.setText(cantTransacciones + (cantTransacciones == 1 ? " transacción" : " transacciones"));

            TextView tvMonto = fila.findViewById(R.id.tvMonto);
            tvMonto.setText(String.format("$%.2f", montoCategoria));

            TextView tvPorcentaje = fila.findViewById(R.id.tvPorcentaje);
            tvPorcentaje.setText(String.format("%.1f%%", porcentaje));

            View barra = fila.findViewById(R.id.barraProgreso);
            barra.post(() -> {
                ViewGroup parent = (ViewGroup) barra.getParent();
                int anchoTotal = parent.getWidth();
                int anchoBarra = (int) (anchoTotal * (porcentaje / 100.0));
                FrameLayout.LayoutParams params =
                        new FrameLayout.LayoutParams(Math.max(anchoBarra, 8), 4);
                barra.setLayoutParams(params);
                barra.getBackground().setTint(colorCirculo);
            });

            binding.containerEstadisticas.addView(fila);
        }
    }
    private void actualizarResumenHeader(double totalGeneral, int numCategorias, String mayorCategoria) {
        binding.tvTotalGastos.setText(String.format("$%.2f", totalGeneral));
        binding.tvNumCategorias.setText(String.valueOf(numCategorias));
        binding.tvMayorGasto.setText(mayorCategoria.isEmpty() ? "—" : mayorCategoria);
    }
    private void mostrarEstadoVacio() {
        binding.tvTotalGastos.setText("$0.00");
        binding.tvNumCategorias.setText("0");
        binding.tvMayorGasto.setText("—");

        TextView tvVacio = new TextView(this);
        tvVacio.setText("Sin transacciones registradas");
        tvVacio.setTextColor(0xAAFFFFFF);
        tvVacio.setTextSize(15f);
        tvVacio.setPadding(0, 48, 0, 0);
        tvVacio.setGravity(android.view.Gravity.CENTER);
        binding.containerEstadisticas.addView(tvVacio);
    }
    private void configurarVideoFondo() {
        String path = "android.resource://" + getPackageName() + "/" + R.raw.video;
        binding.vvBackground.setVideoURI(Uri.parse(path));
        binding.vvBackground.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            binding.vvBackground.start();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!binding.vvBackground.isPlaying()) {
            binding.vvBackground.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.vvBackground.pause();
    }
}