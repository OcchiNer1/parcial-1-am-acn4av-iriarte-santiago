package com.example.chequeo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.example.chequeo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

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
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configurarVideoFondo();

        binding.btnNuevoGasto.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FormularioGastoActivity.class);
            startActivity(intent);
        });

        binding.btnEstadistica.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EstadisticasActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!binding.vvBackground.isPlaying()) {
            binding.vvBackground.start();
        }
        actualizarInterfaz();
    }

    private void actualizarInterfaz() {
        binding.tvMonto.setText(String.format("$%.2f", DatosGlobales.gastoTotalSemanal));
        binding.containerTransacciones.removeAllViews();

        int colorIndex = 0;
        for (String item : DatosGlobales.listaTransacciones) {
            String[] partes = item.split(": \\$");
            String categoria = partes.length > 0 ? partes[0] : item;
            String monto = partes.length > 1 ? "$" + partes[1] : "";
            int colorAccent = COLORES_CATEGORIA[colorIndex % COLORES_CATEGORIA.length];
            colorIndex++;
            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.HORIZONTAL);
            card.setGravity(Gravity.CENTER_VERTICAL);
            card.setBackgroundResource(R.drawable.bg_card_transaccion_mini);
            int padH = dpToPx(12);
            int padV = dpToPx(10);
            card.setPadding(padH, padV, padH, padV);
            LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            cardParams.setMargins(0, 0, 0, dpToPx(8));
            card.setLayoutParams(cardParams);

            TextView tvPunto = new TextView(this);
            tvPunto.setText("●");
            tvPunto.setTextColor(colorAccent);
            tvPunto.setTextSize(10f);
            LinearLayout.LayoutParams puntoParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            puntoParams.setMarginEnd(dpToPx(8));
            tvPunto.setLayoutParams(puntoParams);

            TextView tvCategoria = new TextView(this);
            tvCategoria.setText(categoria);
            tvCategoria.setTextColor(ContextCompat.getColor(this, android.R.color.white));
            tvCategoria.setTextSize(14f);
            LinearLayout.LayoutParams catParams = new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f
            );
            tvCategoria.setLayoutParams(catParams);

            TextView tvMonto = new TextView(this);
            tvMonto.setText(monto);
            tvMonto.setTextColor(ContextCompat.getColor(this, android.R.color.white));
            tvMonto.setTextSize(14f);
            tvMonto.setTypeface(null, android.graphics.Typeface.BOLD);
            tvMonto.setGravity(Gravity.END);

            card.addView(tvPunto);
            card.addView(tvCategoria);
            card.addView(tvMonto);

            binding.containerTransacciones.addView(card);
        }
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
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
    protected void onPause() {
        super.onPause();
        binding.vvBackground.pause();
    }
}