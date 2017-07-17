package com.example.chrisgeorgakidis.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;

import static com.example.chrisgeorgakidis.justjava.R.string.price;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {
        if (quantity < 100) {
            quantity = quantity + 1;
        }
        displayQuantity(quantity);
    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view) {
        if (quantity > 0) {
            quantity = quantity - 1;
        }
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        boolean hasWhippedCream = whippedCream();
        boolean hasChocolate = chocolate();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String name = getName();
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);
        displayMessage(priceMessage);
        composeEmail(getString(R.string.JavaOrder) + name, priceMessage);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * Calculates the price of the order
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int price = 5;
        if (hasWhippedCream) price = price + 1;
        if (hasChocolate) price = price + 2;
        price = price * quantity;
        return price;
    }

    /**
     * Create summary of the order.
     * @param name the name of the customer
     * @param price of the order
     * @param addWhippedCream if the coffee has whipped cream topping or not
     * @param addChocolate if the coffee has chocolate topping or not
     * @return text summary
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate){
        String summary = getString(R.string.nameLabel) + name;
        summary = summary + "\n" + getString(R.string.addWhippedCream) + addWhippedCream;
        summary = summary + "\n" + getString(R.string.addChocolate) + addChocolate;
        summary = summary + "\n" + getString(R.string.quantityLabel) + quantity;
        summary = summary + "\n" + getString(R.string.totalLabel) + price + "\n" + getString(R.string.thankYou);
        return (summary);
    }

    /**
     * Returns if the coffee has a whipped cream topping
     */
    private boolean whippedCream() {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whippedCream);
        return whippedCreamCheckBox.isChecked();
    }

    /**
     * Returns if the coffee has a chocolate topping
     */
    private boolean chocolate() {
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate);
        return chocolateCheckBox.isChecked();
    }

    private String getName() {
        EditText nameField = (EditText) findViewById(R.id.name_field);
        return nameField.getText().toString();
    }

    public void composeEmail(String subject, String mailBody) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        //intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, mailBody);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
