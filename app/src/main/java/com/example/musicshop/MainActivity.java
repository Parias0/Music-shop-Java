package com.example.musicshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName;
    private Spinner spinnerProduct;
    private TextView textViewQuantity;
    private TextView textViewPrice;

    private Map<String, Double> productPrices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicjalizacja widoków
        editTextName = findViewById(R.id.editTextName);
        spinnerProduct = findViewById(R.id.spinnerProduct);
        textViewQuantity = findViewById(R.id.textViewQuantity);
        Button buttonMinus = findViewById(R.id.buttonMinus);
        Button buttonPlus = findViewById(R.id.buttonPlus);
        Button buttonNext = findViewById(R.id.buttonNext);
        textViewPrice = findViewById(R.id.textViewPrice);

        // Ustawienie początkowej wartości
        textViewQuantity.setText("0");



        // Inicjalizacja listy produktów
        List<String> productList = new ArrayList<>();
        productList.add("Gitara");
        productList.add("Keyboard");
        productList.add("Perkusja");
        productList.add("Saksofon");
        productList.add("Mikrofon");

        // Inicjalizacja mapy cen produktów
        productPrices = new HashMap<>();
        productPrices.put("Gitara", 999.99);
        productPrices.put("Keyboard", 599.99);
        productPrices.put("Perkusja", 1499.99);
        productPrices.put("Saksofon", 899.99);
        productPrices.put("Mikrofon", 199.99);

        // Konfiguracja ArrayAdapter dla Spinnera
        ArrayAdapter<String> productAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, productList);
        productAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProduct.setAdapter(productAdapter);

        // Obsługa przycisku minus
        buttonMinus.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(textViewQuantity.getText().toString());
            if (currentQuantity > 0) {
                currentQuantity--;
                textViewQuantity.setText(String.valueOf(currentQuantity));
                updateTotalPrice();
            }
        });

        // Obsługa przycisku plus
        buttonPlus.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(textViewQuantity.getText().toString());
            currentQuantity++;
            textViewQuantity.setText(String.valueOf(currentQuantity));
            updateTotalPrice();
        });


// Dodaj obsługę przycisku "buttonOpis"
        Button buttonOpis = findViewById(R.id.buttonOpis);
        buttonOpis.setOnClickListener(v -> {
            String product = spinnerProduct.getSelectedItem().toString();

            // Przekazanie danych do ProductDescriptionActivity
            Intent intent = new Intent(MainActivity.this, ProductDescriptionActivity.class);
            intent.putExtra("productName", product);
            startActivity(intent);
        });
    }

    private void updateTotalPrice() {
        String selectedProduct = spinnerProduct.getSelectedItem().toString();
        int quantity = Integer.parseInt(textViewQuantity.getText().toString());

        if (productPrices.containsKey(selectedProduct)) {
            double price = productPrices.get(selectedProduct);
            double totalPrice = price * quantity;
            textViewPrice.setText("Cena: PLN" + totalPrice);
        }
    }

}

