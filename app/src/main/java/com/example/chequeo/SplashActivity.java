package com.example.chequeo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chequeo.databinding.ActivitySplashBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DatosGlobales.cargarDatos(this);

        String path = "android.resource://" + getPackageName() + "/" + R.raw.intro;
        Uri uri = Uri.parse(path);
        binding.vvSplash.setVideoURI(uri);
        binding.vvSplash.start();

        new Handler().postDelayed(() -> {
            FirebaseUser usuarioActual = FirebaseAuth.getInstance().getCurrentUser();
            Intent intent;
            if (usuarioActual != null) {
                intent = new Intent(SplashActivity.this, MainActivity.class);
            } else {
                intent = new Intent(SplashActivity.this, LoginActivity.class);
            }
            startActivity(intent);
            finish();
        }, 4000);
    }
}