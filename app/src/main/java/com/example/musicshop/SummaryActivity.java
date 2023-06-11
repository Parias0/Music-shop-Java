package com.example.musicshop;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SummaryActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        // Inicjalizacja widoków
        TextView textViewName = findViewById(R.id.textViewName);
        TextView textViewProduct = findViewById(R.id.textViewProduct);
        TextView textViewQuantity = findViewById(R.id.textViewQuantity);
        TextView textViewPrice = findViewById(R.id.textViewPrice);
        TextView textViewTotalPrice = findViewById(R.id.textViewTotalPrice);

        // Odczytanie danych z Intentu
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String name = extras.getString("name");
            String product = extras.getString("product");
            int quantity = extras.getInt("quantity");
            double price = extras.getDouble("price");
            double totalPrice = extras.getDouble("totalPrice");

            // Ustawienie danych w widoku
            textViewName.setText("Imię: " + name);
            textViewProduct.setText("Produkt: " + product);
            textViewQuantity.setText("Ilość: " + quantity);
            textViewPrice.setText("Cena: " + price);
            textViewTotalPrice.setText("Suma do zapłaty: " + totalPrice);

            // Obsługa przycisku wysyłania e-maila
            Button buttonSendEmail = findViewById(R.id.buttonSendEmail);
            buttonSendEmail.setOnClickListener(v -> sendEmail(name, product, quantity, totalPrice));
        }
    }

    private void sendEmail(String name, String product, int quantity, double totalPrice) {
        String subject = "Zamówienie z MusicShop";
        String body = "Imię: " + name +
                "\nProdukt: " + product +
                "\nIlość: " + quantity +
                "\nSuma do zapłaty: " + totalPrice;

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // Adres e-mail można również podać jako URI
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"mail"}); // Podaj właściwy adres e-mail
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);

        try {
            startActivity(Intent.createChooser(intent, "Wybierz program do wysyłania e-maila"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Brak zainstalowanego klienta poczty e-mail", Toast.LENGTH_SHORT).show();
        }
    }
}
