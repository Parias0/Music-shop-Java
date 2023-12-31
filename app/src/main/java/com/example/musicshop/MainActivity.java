package com.example.musicshop;

import android.annotation.SuppressLint;
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
    private ImageView imageViewProduct; // Dodany ImageView

    private Map<String, Double> productPrices;
    private Map<String, Integer> productQuantities;

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
        imageViewProduct = findViewById(R.id.imageViewProduct); // Dodany ImageView

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

        // Inicjalizacja mapy ilości produktów
        productQuantities = new HashMap<>();

        // Konfiguracja ArrayAdapter dla Spinnera
        ArrayAdapter<String> productAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, productList);
        productAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProduct.setAdapter(productAdapter);

        // Obsługa przycisku minus
        buttonMinus.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(textViewQuantity.getText().toString());
            String selectedProduct = spinnerProduct.getSelectedItem().toString();

            if (currentQuantity > 0) {
                currentQuantity--;
                productQuantities.put(selectedProduct, currentQuantity);
            }

            textViewQuantity.setText(String.valueOf(currentQuantity));
            updateTotalPrice();
        });

        // Obsługa przycisku plus
        buttonPlus.setOnClickListener(v -> {
            int currentQuantity;
            String selectedProduct = spinnerProduct.getSelectedItem().toString();

            if (productQuantities.containsKey(selectedProduct)) {
                currentQuantity = productQuantities.get(selectedProduct) + 1;
            } else {
                currentQuantity = 1;
            }

            productQuantities.put(selectedProduct, currentQuantity);
            textViewQuantity.setText(String.valueOf(currentQuantity));
            updateTotalPrice();
        });

        // Obsługa przycisku "Dalej"
        buttonNext.setOnClickListener(v -> {
            String name = editTextName.getText().toString();

            if (name.isEmpty()) {
                Toast.makeText(MainActivity.this, "Wprowadź imię", Toast.LENGTH_SHORT).show();
                return; // Zatrzymanie działania metody, jeśli imię nie zostało wprowadzone
            }

            String product = spinnerProduct.getSelectedItem().toString();
            int quantity = Integer.parseInt(textViewQuantity.getText().toString());
            double price = productPrices.get(product);
            double totalPrice = price * quantity;

            // Przekazanie danych do SummaryActivity
            Intent intent = new Intent(MainActivity.this, SummaryActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("product", product);
            intent.putExtra("quantity", quantity);
            intent.putExtra("price", price);
            intent.putExtra("totalPrice", totalPrice);
            startActivity(intent);
        });

        // Ustawienie domyślnego produktu i obliczenie ceny
        spinnerProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String selectedProduct = spinnerProduct.getSelectedItem().toString();
                int currentQuantity = productQuantities.getOrDefault(selectedProduct, 0);
                textViewQuantity.setText(String.valueOf(currentQuantity));
                updateTotalPrice();
                updateProductImage(selectedProduct); // Dodane wywołanie aktualizacji zdjęcia
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void updateTotalPrice() {
        double totalPrice = 0.0;

        for (Map.Entry<String, Integer> entry : productQuantities.entrySet()) {
            String product = entry.getKey();
            int quantity = entry.getValue();

            if (productPrices.containsKey(product)) {
                double price = productPrices.get(product);
                totalPrice += price * quantity;
            }
        }

        textViewPrice.setText("Cena: PLN " + totalPrice);
    }

    private void updateProductImage(String selectedProduct) {
        int imageResource;

        switch (selectedProduct) {
            case "Gitara":
                imageResource = R.drawable.guitar;
                break;
            case "Keyboard":
                imageResource = R.drawable.keyboard;
                break;
            case "Perkusja":
                imageResource = R.drawable.drums;
                break;
            case "Saksofon":
                imageResource = R.drawable.saxophone;
                break;
            case "Mikrofon":
                imageResource = R.drawable.microphone;
                break;
            default:
                imageResource = R.drawable.default_image;
                break;
        }

        imageViewProduct.setImageResource(imageResource);
    }
}
