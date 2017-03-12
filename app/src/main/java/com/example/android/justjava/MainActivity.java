package com.example.android.justjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    String [] drinkChoice = new String[3];
    int [] drinkQty = new int[3];
    double[] drinkPrice = {3.50, 4.50, 5.50};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                resource = "quantity_coffee";
                break;
            case R.id.latte_increase:
                drinkChoice[1] = getString(R.string.drinkLatte);
                drinkQty[1] = drinkQty[1] + 1;
                quantity = drinkQty[1];
                resource = "quantity_latte";
                break;
            case R.id.cappuccino_increase:
                drinkChoice[2] = getString(R.string.drinkCappuccino);
                drinkQty[2] = drinkQty[2] + 1;
                quantity = drinkQty[2];
                resource = "quantity_cappuccino";
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
        String drinks = "";
        String priceMessage = "";
        String totalMessage = "";
        String priceFormatted = "";
        Double total = 0.0;
        Double price = 0.0;

        if ((drinkQty[0] == 0) && (drinkQty[1] == 0) && (drinkQty[2] == 0)) {
            TextView alertTextView = (TextView) findViewById(R.id.boxAlert);
            alertTextView.setVisibility(View.VISIBLE);

            TextView invoiceTextView = (TextView) findViewById(R.id.labelInvoice);
            invoiceTextView.setVisibility(View.INVISIBLE);
            TableLayout invoiceTbl = (TableLayout) findViewById(R.id.invoiceTable);
            invoiceTbl.setVisibility(View.INVISIBLE);
            TextView thankTextView = (TextView) findViewById(R.id.labelThanks);
            thankTextView.setVisibility(View.INVISIBLE);
        }
        else {
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

            TextView totalTextView = (TextView) findViewById(R.id.total);
             totalTextView.setText(getString(R.string.label_total));

            totalMessage = "£" + String.format("%.2f", total);
            TextView totalpriceTextView = (TextView) findViewById(R.id.total_price);
            totalpriceTextView.setText(totalMessage);

            TextView thankTextView = (TextView) findViewById(R.id.labelThanks);
            thankTextView.setVisibility(View.VISIBLE);
        }
    }
}