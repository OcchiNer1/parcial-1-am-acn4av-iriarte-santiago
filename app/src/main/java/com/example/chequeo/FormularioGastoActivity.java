package com.example.chequeo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.chequeo.databinding.ActivityFormularioGastoBinding;

public class FormularioGastoActivity extends AppCompatActivity {

    private ActivityFormularioGastoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormularioGastoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configurarVideoFondo();

        binding.btnConfirmarRegistro.setOnClickListener(v -> {
            String montoStr = binding.etMonto.getText().toString();
            String categoria = binding.etCategoria.getText().toString();
            if (montoStr.isEmpty() || categoria.isEmpty()) {
                Toast.makeText(this, "Por favor completá todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                double monto = Double.parseDouble(montoStr);
                DatosGlobales.gastoTotalSemanal += monto;
                DatosGlobales.listaTransacciones.add(categoria + ": $" + montoStr);
                Intent intent = new Intent(FormularioGastoActivity.this, ResumenActivity.class);
                intent.putExtra("MONTO_EXTRA", montoStr);
                intent.putExtra("CATEGORIA_EXTRA", categoria);
                startActivity(intent);
                DatosGlobales.guardarDatos(this);
                finish();
            }
        });
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