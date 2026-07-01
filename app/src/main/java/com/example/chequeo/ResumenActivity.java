package com.example.chequeo;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.chequeo.databinding.ActivityResumenBinding;
import android.net.Uri;

public class ResumenActivity extends AppCompatActivity {

    private ActivityResumenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityResumenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configurarVideoFondo();

        Bundle datosRecibidos = getIntent().getExtras();

        if (datosRecibidos != null) {
            String monto = datosRecibidos.getString("MONTO_EXTRA");
            String categoria = datosRecibidos.getString("CATEGORIA_EXTRA");
            binding.tvResumenMonto.setText(getString(R.string.formato_resumen_monto, monto));
            String categoriaFormateada = categoria != null && !categoria.isEmpty()
                    ? categoria.substring(0, 1).toUpperCase() + categoria.substring(1).toLowerCase()
                    : "";
            binding.tvResumenCategoria.setText(getString(R.string.formato_resumen_categoria, categoriaFormateada));
        }

        binding.btnVolver.setOnClickListener(v -> finish());
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
}