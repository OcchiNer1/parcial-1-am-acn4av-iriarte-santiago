package com.example.chequeo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.chequeo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

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
        actualizarInterfaz();
    }
    private void actualizarInterfaz() {
        binding.tvMonto.setText("$" + String.format("%.2f", DatosGlobales.gastoTotalSemanal));
        binding.containerTransacciones.removeAllViews();

        for (String item : DatosGlobales.listaTransacciones) {
            TextView txtTransaccion = new TextView(this);
            txtTransaccion.setText(item);
            txtTransaccion.setTextColor(getResources().getColor(android.R.color.white));
            txtTransaccion.setTextSize(16);
            txtTransaccion.setPadding(0, 10, 0, 10);

            binding.containerTransacciones.addView(txtTransaccion);
        }
    }

    private void configurarVideoFondo() {
        String path = "android.resource://" + getPackageName() + "/" + R.raw.video;
        binding.vvBackground.setVideoURI(Uri.parse(path));
        binding.vvBackground.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            binding.vvBackground.start();
        });
    }
}