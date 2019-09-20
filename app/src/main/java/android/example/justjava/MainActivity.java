package android.example.justjava;

/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/




import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity = 0;

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        EditText name = (EditText) findViewById(R.id.name_field);
        String nameField = name.getText().toString();
        boolean hasChocolate = chocolateCheckbox.isChecked();
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();
        String message = createOrderSummary(nameField, 5, hasWhippedCream, hasChocolate);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just java app for " + nameField);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    public String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {
        if (addWhippedCream)
            price += 1;
        if (addChocolate)
            price += 2;
        String summary = "Name:" + name;
        summary += "\nAdd Whipped Cream?" + addWhippedCream;
        summary += "\nAdd Chocolate?" + addChocolate;
        summary = summary + "\n Quantity:" + quantity + "\nTotal:$" + (quantity * price) + "\nThank You";
        return summary;
    }


    public void increment(View view) {
        if (quantity > 100) {
            Toast.makeText(MainActivity.this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);

    }

    public void decrement(View view) {
        if (quantity < 1) {
            Toast.makeText(MainActivity.this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);

    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}

