package com.example.chequeo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chequeo.databinding.ActivityRegistroBinding;
import com.google.firebase.auth.FirebaseAuth;

public class RegistroActivity extends AppCompatActivity {

    private ActivityRegistroBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        configurarVideoFondo();

        binding.btnCrearCuenta.setOnClickListener(v -> intentarRegistro());

        binding.btnVolverLogin.setOnClickListener(v -> finish());
    }

    private void intentarRegistro() {
        String email = binding.etEmailRegistro.getText().toString().trim();
        String password = binding.etPasswordRegistro.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            mostrarError(getString(R.string.login_error_campos_vacios));
            return;
        }

        if (password.length() < 6) {
            mostrarError(getString(R.string.registro_error_password_corta));
            return;
        }
        binding.btnCrearCuenta.setEnabled(false);
        binding.tvRegistroError.setVisibility(View.GONE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Registro exitoso → ir al Main directamente
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        binding.btnCrearCuenta.setEnabled(true);
                        mostrarError(getString(R.string.registro_error_general));
                    }
                });
    }
    private void mostrarError(String mensaje) {
        binding.tvRegistroError.setText(mensaje);
        binding.tvRegistroError.setVisibility(View.VISIBLE);
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