package com.gzeinnumer.uomadapterlevel2.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.gzeinnumer.uomadapterlevel2.databinding.ActivityMainBinding;
import com.gzeinnumer.uomadapterlevel2.ui.single.SingleUOMActivity;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initOnClick();
    }

    private void initOnClick() {
        binding.btnSingle.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), SingleUOMActivity.class));
        });
    }
}