package com.example.chequeo;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.chequeo.databinding.ActivityEstadisticasBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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

    private static final Map<String, String> IMAGENES_CATEGORIA = new HashMap<>();
    static {
        IMAGENES_CATEGORIA.put("supermercado",  "https://images.unsplash.com/photo-1542838132-92c53300491e?w=80&h=80&fit=crop");
        IMAGENES_CATEGORIA.put("comida",        "https://images.unsplash.com/photo-1504674900247-0877df9cc836?w=80&h=80&fit=crop");
        IMAGENES_CATEGORIA.put("restaurante",   "https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=80&h=80&fit=crop");
        IMAGENES_CATEGORIA.put("transporte",    "https://images.unsplash.com/photo-1570125909232-eb263c188f7e?w=80&h=80&fit=crop");
        IMAGENES_CATEGORIA.put("nafta",         "https://images.unsplash.com/photo-1611293388250-580b08c4a145?w=80&h=80&fit=crop");
        IMAGENES_CATEGORIA.put("combustible",   "https://images.unsplash.com/photo-1611293388250-580b08c4a145?w=80&h=80&fit=crop");
        IMAGENES_CATEGORIA.put("salud",         "https://images.unsplash.com/photo-1576091160550-2173dba999ef?w=80&h=80&fit=crop");
        IMAGENES_CATEGORIA.put("farmacia",      "https://images.unsplash.com/photo-1584308666744-24d5c474f2ae?w=80&h=80&fit=crop");
        IMAGENES_CATEGORIA.put("ropa",          "https://images.unsplash.com/photo-1467043237213-65f2da53396f?w=80&h=80&fit=crop");
        IMAGENES_CATEGORIA.put("indumentaria",  "https://images.unsplash.com/photo-1467043237213-65f2da53396f?w=80&h=80&fit=crop");
        IMAGENES_CATEGORIA.put("entretenimiento","https://images.unsplash.com/photo-1489599849927-2ee91cede3ba?w=80&h=80&fit=crop");
        IMAGENES_CATEGORIA.put("cine",          "https://images.unsplash.com/photo-1489599849927-2ee91cede3ba?w=80&h=80&fit=crop");
        IMAGENES_CATEGORIA.put("educacion",     "https://images.unsplash.com/photo-1481627834876-b7833e8f5570?w=80&h=80&fit=crop");
        IMAGENES_CATEGORIA.put("libros",        "https://images.unsplash.com/photo-1481627834876-b7833e8f5570?w=80&h=80&fit=crop");
        IMAGENES_CATEGORIA.put("servicios",     "https://images.unsplash.com/photo-1556742049-0cfed4f6a45d?w=80&h=80&fit=crop");
        IMAGENES_CATEGORIA.put("tecnologia",    "https://images.unsplash.com/photo-1518770660439-4636190af475?w=80&h=80&fit=crop");
        IMAGENES_CATEGORIA.put("electronica",   "https://images.unsplash.com/photo-1518770660439-4636190af475?w=80&h=80&fit=crop");
        IMAGENES_CATEGORIA.put("hogar",         "https://images.unsplash.com/photo-1556909114-f6e7ad7d3136?w=80&h=80&fit=crop");
        IMAGENES_CATEGORIA.put("limpieza",      "https://images.unsplash.com/photo-1556909114-f6e7ad7d3136?w=80&h=80&fit=crop");
        IMAGENES_CATEGORIA.put("gimnasio",      "https://images.unsplash.com/photo-1534438327276-14e5300c3a48?w=80&h=80&fit=crop");
        IMAGENES_CATEGORIA.put("deporte",       "https://images.unsplash.com/photo-1534438327276-14e5300c3a48?w=80&h=80&fit=crop");
        IMAGENES_CATEGORIA.put("viaje",         "https://images.unsplash.com/photo-1436491865332-7a61a109cc05?w=80&h=80&fit=crop");
        IMAGENES_CATEGORIA.put("mascota",       "https://images.unsplash.com/photo-1587300003388-59208cc962cb?w=80&h=80&fit=crop");
        IMAGENES_CATEGORIA.put("cafe",          "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=80&h=80&fit=crop");
        IMAGENES_CATEGORIA.put("bar",           "https://images.unsplash.com/photo-1543007630-9710e4a00a20?w=80&h=80&fit=crop");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEstadisticasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configurarVideoFondo();
        cargarEstadisticasPorCategoria();
    }
    private String obtenerUrlImagen(String categoria) {
        String claveBusqueda = categoria.toLowerCase().trim();
        if (IMAGENES_CATEGORIA.containsKey(claveBusqueda)) {
            return IMAGENES_CATEGORIA.get(claveBusqueda);
        }
        for (Map.Entry<String, String> entry : IMAGENES_CATEGORIA.entrySet()) {
            if (claveBusqueda.contains(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
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

            ImageView ivIcono = fila.findViewById(R.id.ivCategoriaIcono);
            TextView tvLetra = fila.findViewById(R.id.tvLetraIcono);

            String urlImagen = obtenerUrlImagen(categoria);
            if (urlImagen != null) {
                ivIcono.setVisibility(View.VISIBLE);
                tvLetra.setVisibility(View.GONE);
                Glide.with(this)
                        .load(urlImagen)
                        .circleCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(ivIcono);
            } else {
                ivIcono.setVisibility(View.GONE);
                tvLetra.setVisibility(View.VISIBLE);
                if (!categoria.isEmpty()) {
                    tvLetra.setText(categoria.substring(0, 1).toUpperCase());
                }
                tvLetra.getBackground().setTint(colorCirculo);
            }

            TextView tvCat = fila.findViewById(R.id.tvCat);
            tvCat.setText(categoria);

            TextView tvCantidad = fila.findViewById(R.id.tvCantTransacciones);
            String textoTransacciones = cantTransacciones == 1 ?
                    getString(R.string.transaccion_singular) :
                    getString(R.string.transaccion_plural);
            tvCantidad.setText(cantTransacciones + " " + textoTransacciones);

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
        binding.tvMayorGasto.setText(mayorCategoria.isEmpty() ? getString(R.string.sin_dato) : mayorCategoria);
    }

    private void mostrarEstadoVacio() {
        binding.tvTotalGastos.setText(getString(R.string.monto_cero));
        binding.tvNumCategorias.setText("0");
        binding.tvMayorGasto.setText(getString(R.string.sin_dato));

        TextView tvVacio = new TextView(this);
        tvVacio.setText(getString(R.string.lista_vacia));
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