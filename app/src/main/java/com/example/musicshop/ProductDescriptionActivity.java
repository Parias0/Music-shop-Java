package com.example.musicshop;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class ProductDescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);

        // Inicjalizacja mapy opisów instrumentów
        Map<String, String> productDescriptions = new HashMap<>();
        productDescriptions.put("Gitara", "opis");
        productDescriptions.put("Keyboard", "opis2");
        productDescriptions.put("Perkusja", "opis3");
        productDescriptions.put("Saksofon", "opis4");
        productDescriptions.put("Mikrofon", "opis5");

        Map<String, Integer> productImages = new HashMap<>();
        productImages.put("Gitara", R.drawable.guitar);
        productImages.put("Keyboard", R.drawable.keyboard);
        productImages.put("Perkusja", R.drawable.drums);
        productImages.put("Saksofon", R.drawable.saxophone);
        productImages.put("Mikrofon", R.drawable.microphone);


        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v -> finish());


        // Pobierz nazwę instrumentu przekazaną z MainActivity
        String productName = getIntent().getStringExtra("productName");

        // Wyświetl opis instrumentu na podstawie nazwy
        TextView textViewDescription = findViewById(R.id.textViewDescription);
        ImageView imageViewInstrument = findViewById(R.id.imageViewInstrument);

        String description = productDescriptions.get(productName);
        if (description != null) {
            textViewDescription.setText(description);
        } else {
            textViewDescription.setText("Brak opisu dla tego instrumentu");
        }

// Wyświetl obrazek instrumentu na podstawie nazwy
        Integer imageResId = productImages.get(productName);
        if (imageResId != null) {
            Drawable instrumentImage = getResources().getDrawable(imageResId);
            imageViewInstrument.setImageDrawable(instrumentImage);
        } else {
            // Jeśli nie ma obrazka dla danego instrumentu, wyświetl domyślny obrazek
            imageViewInstrument.setImageResource(R.drawable.default_instrument);
        }

    }
}


