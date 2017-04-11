package com.example.android.justjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import static com.example.android.justjava.R.id.totallabel;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    String [] drinkChoice = new String[3];
    int [] drinkQty = new int[3];
    double[] drinkPrice = {3.50, 4.50, 5.50};
    String [] resourceArr = new String[3];

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putIntArray("drinkQtySaved", drinkQty);
        outState.putStringArray("resourceSaved", resourceArr);
        outState.putStringArray("choiceSaved", drinkChoice);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {

            drinkQty = savedInstanceState.getIntArray("drinkQtySaved");
            resourceArr = savedInstanceState.getStringArray("resourceSaved");
            drinkChoice = savedInstanceState.getStringArray("choiceSaved");

            for (int i = 0; i < 3; i++) {
                display(drinkQty[i], resourceArr[i]);
            }
            if ((drinkQty[0] == 0) && (drinkQty[1] == 0) && (drinkQty[2] == 0)) {
                displayAlert();
            }
            displayInvoice();
        }
    }

    /** This method increments the quantity on screen. **/
    public void increment(View view) {
        String resource = "";
        int quantity = 0;

        switch (view.getId()) {
            case R.id.coffee_increase:
                drinkChoice[0] = getString(R.string.drinkCoffee);
                drinkQty[0] = drinkQty[0] + 1;
                quantity = drinkQty[0];
                resourceArr[0] = resource = "quantity_coffee";
                break;
            case R.id.latte_increase:
                drinkChoice[1] = getString(R.string.drinkLatte);
                drinkQty[1] = drinkQty[1] + 1;
                quantity = drinkQty[1];
                resourceArr[1] = resource = "quantity_latte";
                break;
            case R.id.cappuccino_increase:
                drinkChoice[2] = getString(R.string.drinkCappuccino);
                drinkQty[2] = drinkQty[2] + 1;
                quantity = drinkQty[2];
                resourceArr[2] = resource = "quantity_cappuccino";
                break;
        }
        display(quantity, resource);
    }

    /** This method decrements the quantity on screen. **/
    public void decrement(View view) {
        String resource = "";
        int quantity = 0;

        switch (view.getId()) {
            case R.id.coffee_decrease:
                drinkChoice[0] = getString(R.string.drinkCoffee);
                drinkQty[0] = (drinkQty[0] == 0) ? 0 :  (drinkQty[0] - 1);
                quantity = drinkQty[0];
                resource = "quantity_coffee";
                break;
            case R.id.latte_decrease:
                drinkChoice[1] = getString(R.string.drinkLatte);
                drinkQty[1] = (drinkQty[1] == 0) ? 0 :  (drinkQty[1] - 1);
                quantity = drinkQty[1];
                resource = "quantity_latte";
                break;
            case R.id.cappuccino_decrease:
                drinkChoice[2] = getString(R.string.drinkCappuccino);
                drinkQty[2] = (drinkQty[2] == 0) ? 0 :  (drinkQty[2] - 1);
                quantity = drinkQty[2];
                resource = "quantity_cappuccino";
                break;
        }
        display(quantity, resource);
    }

    /** This method displays the given quantity value on the screen. **/
    private void display(int number, String res) {
        int resID = getResources().getIdentifier(res, "id", getPackageName());
        TextView quantityTextView = (TextView) findViewById(resID);
        quantityTextView.setText("" + number);
    }

    /** This method is called when the order button is clicked. **/
    public void submitOrder(View view) {
        if ((drinkQty[0] == 0) && (drinkQty[1] == 0) && (drinkQty[2] == 0)) {
            displayAlert();
        }
        else {
            displayInvoice();
        }
    }

    /** This method displays alert if order button clicked with no drink is selected **/
    public void displayAlert() {
        TextView alertTextView = (TextView) findViewById(R.id.boxAlert);
        alertTextView.setVisibility(View.VISIBLE);

        TextView invoiceTextView = (TextView) findViewById(R.id.labelInvoice);
        invoiceTextView.setVisibility(View.INVISIBLE);
        TableLayout invoiceTbl = (TableLayout) findViewById(R.id.invoiceTable);
        invoiceTbl.setVisibility(View.INVISIBLE);
        TextView thankTextView = (TextView) findViewById(R.id.labelThanks);
        thankTextView.setVisibility(View.INVISIBLE);
    }

    public void displayInvoice() {
        String drinks = "";
        String priceMessage = "";
        String totalMessage = "";
        String priceFormatted = "";
        Double total = 0.0;
        Double price = 0.0;

        TextView alertTextView = (TextView) findViewById(R.id.boxAlert);
        alertTextView.setVisibility(View.GONE);

        TextView invoiceTextView = (TextView) findViewById(R.id.labelInvoice);
        invoiceTextView.setVisibility(View.VISIBLE);
        TableLayout invoiceTbl = (TableLayout) findViewById(R.id.invoiceTable);
        invoiceTbl.setVisibility(View.VISIBLE);

        int i;
        for (i = 0; i < 3; i++) {
            if (drinkQty[i] > 0) {
                drinks = drinks + drinkChoice[i] + " " + " x " + drinkQty[i] + "\n\n";
                price = drinkQty[i] * drinkPrice[i];
                priceFormatted = String.format("%.2f", price);
                priceMessage = priceMessage + "£" + priceFormatted + "\n\n";
                total = total + price;
            }
        }

        TextView drinksTextView = (TextView) findViewById(R.id.selected_drinks);
        drinksTextView.setText(drinks);
        TextView priceTextView = (TextView) findViewById(R.id.selected_drinks_price);
        priceTextView.setText(priceMessage);

        TextView totalTextView = (TextView) findViewById(totallabel);
        totalTextView.setText(getString(R.string.label_total));

        totalMessage = "£" + String.format("%.2f", total);
        TextView totalpriceTextView = (TextView) findViewById(R.id.total_price);
        totalpriceTextView.setText(totalMessage);

        TextView thankTextView = (TextView) findViewById(R.id.labelThanks);
        thankTextView.setVisibility(View.VISIBLE);

        View targetView = findViewById(R.id.labelThanks);
        targetView.getParent().requestChildFocus(targetView,targetView);
    }
}