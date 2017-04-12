package com.example.android.justjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String [] mDrinkChoice = new String[3];
    private int [] mDrinkQty = new int[3];
    private double[] mDrinkPrice = {3.50, 4.50, 5.50};
    private String [] mResourceArr = new String[3];

    // SavedInstance variables
    private static final String STATE_DRINKQTY = "mDrinkQtySaved";
    private static final String STATE_RESOURCE = "resourceSaved";
    private static final String STATE_CHOICE = "choiceSaved";

    // All UI components
    private TextView mTextViewCoffee;
    private TextView mTextViewLatte;
    private TextView mTextViewCappuccino;
    private TextView mTextViewCoffeeQty;
    private TextView mTextViewLatteQty;
    private TextView mTextViewCappuccinoQty;
    private TextView mTextViewAlert;
    private TextView mTextViewTitleInvoice;
    private TextView mTextViewSelectedDrinks;
    private TextView mTextViewSelectedDrinksPrice;
    private TextView mTextViewTotal;
    private TextView mTextViewTotalPrice;
    private TextView mTextViewMsg;

    private Button mButtonDecreaseCoffee;
    private Button mButtonIncreaseCoffee;
    private Button mButtonDecreaseLatte;
    private Button mButtonIncreaseLatte;
    private Button mButtonDecreaseCappuccino;
    private Button mButtonIncreaseCappuccino;
    private Button mSubmit;

    private TableLayout mTableInvoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize all UI components
        mTextViewCoffee = (TextView) findViewById(R.id.text_coffee);
        mTextViewLatte = (TextView) findViewById(R.id.text_latte);
        mTextViewCappuccino = (TextView) findViewById(R.id.text_cappuccino);
        mTextViewCoffeeQty = (TextView) findViewById(R.id.text_quantity_coffee);
        mTextViewLatteQty = (TextView) findViewById(R.id.text_quantity_latte);
        mTextViewCappuccinoQty = (TextView) findViewById(R.id.text_quantity_cappuccino);
        mTextViewTitleInvoice = (TextView) findViewById(R.id.text_title_invoice);
        mTextViewAlert = (TextView) findViewById(R.id.text_alert);
        mTextViewSelectedDrinks = (TextView) findViewById(R.id.text_selected_drinks);
        mTextViewSelectedDrinksPrice = (TextView) findViewById(R.id.text_selected_drinks_price);
        mTextViewTotal = (TextView) findViewById(R.id.text_title_total);
        mTextViewTotalPrice = (TextView) findViewById(R.id.text_total_price);
        mTextViewMsg = (TextView) findViewById(R.id.text_title_thanks);

        mButtonDecreaseCoffee = (Button) findViewById(R.id.button_coffee_decrease);
        mButtonIncreaseCoffee = (Button) findViewById(R.id.button_coffee_increase);
        mButtonDecreaseLatte = (Button) findViewById(R.id.button_latte_decrease);
        mButtonIncreaseLatte = (Button) findViewById(R.id.button_latte_increase);
        mButtonDecreaseCappuccino = (Button) findViewById(R.id.button_cappuccino_decrease);
        mButtonIncreaseCappuccino = (Button) findViewById(R.id.button_cappuccino_increase);
        mSubmit = (Button) findViewById(R.id.button_submit_order);

        mTableInvoice = (TableLayout) findViewById(R.id.table_invoice);

        mButtonDecreaseCoffee.setOnClickListener(this);
        mButtonIncreaseCoffee.setOnClickListener(this);
        mButtonDecreaseLatte.setOnClickListener(this);
        mButtonIncreaseLatte.setOnClickListener(this);
        mButtonDecreaseCappuccino.setOnClickListener(this);
        mButtonIncreaseCappuccino.setOnClickListener(this);
        mSubmit.setOnClickListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putIntArray("STATE_DRINKQTY", mDrinkQty);
        outState.putStringArray("STATE_RESOURCE", mResourceArr);
        outState.putStringArray("STATE_CHOICE", mDrinkChoice);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            mDrinkQty = savedInstanceState.getIntArray("STATE_DRINKQTY");
            mResourceArr = savedInstanceState.getStringArray("STATE_RESOURCE");
            mDrinkChoice = savedInstanceState.getStringArray("STATE_CHOICE");

            for (int i = 0; i < 3; i++) {
                display(mDrinkQty[i], mResourceArr[i]);
            }
            // Display alert if order is placed with no drink selected
            if ((mDrinkQty[0] == 0) && (mDrinkQty[1] == 0) && (mDrinkQty[2] == 0)) {
                displayAlert();
            }
            displayInvoice();
        }
    }

    /**
     * Invokes methods for individual call to action buttons
     * @param view
     */
    @Override
    public void onClick(View view) {
        String resource = "";
        int quantity = 0;

        switch (view.getId()) {

            case R.id.button_coffee_increase:
                mDrinkChoice[0] = getString(R.string.label_coffee);
                mDrinkQty[0] = mDrinkQty[0] + 1;
                quantity = mDrinkQty[0];
                mResourceArr[0] = resource = getString(R.string.label_coffee_qty);
                display(quantity, resource);
                break;
            case R.id.button_latte_increase:
                mDrinkChoice[1] = getString(R.string.label_latte);
                mDrinkQty[1] = mDrinkQty[1] + 1;
                quantity = mDrinkQty[1];
                mResourceArr[1] = resource = getString(R.string.label_latte_qty);
                display(quantity, resource);
                break;
            case R.id.button_cappuccino_increase:
                mDrinkChoice[2] = getString(R.string.label_cappuccino);
                mDrinkQty[2] = mDrinkQty[2] + 1;
                quantity = mDrinkQty[2];
                mResourceArr[2] = resource = getString(R.string.label_cappuccino_qty);
                display(quantity, resource);
                break;
            case R.id.button_coffee_decrease:
                mDrinkChoice[0] = getString(R.string.label_coffee);
                mDrinkQty[0] = (mDrinkQty[0] == 0) ? 0 :  (mDrinkQty[0] - 1);
                quantity = mDrinkQty[0];
                resource = getString(R.string.label_coffee_qty);
                display(quantity, resource);
                break;
            case R.id.button_latte_decrease:
                mDrinkChoice[1] = getString(R.string.label_latte);
                mDrinkQty[1] = (mDrinkQty[1] == 0) ? 0 :  (mDrinkQty[1] - 1);
                quantity = mDrinkQty[1];
                resource = getString(R.string.label_latte_qty);
                display(quantity, resource);
                break;
            case R.id.button_cappuccino_decrease:
                mDrinkChoice[2] = getString(R.string.label_cappuccino);
                mDrinkQty[2] = (mDrinkQty[2] == 0) ? 0 :  (mDrinkQty[2] - 1);
                quantity = mDrinkQty[2];
                resource = getString(R.string.label_cappuccino_qty);
                display(quantity, resource);
                break;
            case R.id.button_submit_order:
                submitOrder();
                break;
        }

    }

    /**
     * This method displays the given quantity value on the screen.
     * @param number, resource
     */
    private void display(int number, String res) {
        int resID = getResources().getIdentifier(res, "id", getPackageName());
        TextView quantityTextView = (TextView) findViewById(resID);
        quantityTextView.setText("" + number);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder() {
        // Display alert if order is placed with no drink selected
        if ((mDrinkQty[0] == 0) && (mDrinkQty[1] == 0) && (mDrinkQty[2] == 0)) {
            displayAlert();
        } else {
            displayInvoice();
        }
    }

    /**
     * This method displays alert if order button clicked with no drink is selected
     */
    public void displayAlert() {
        mTextViewAlert.setVisibility(View.VISIBLE);
        mTextViewTitleInvoice.setVisibility(View.INVISIBLE);
        mTableInvoice.setVisibility(View.INVISIBLE);
        mTextViewMsg.setVisibility(View.INVISIBLE);
    }

    /**
     * This method displays invoice
     */
    public void displayInvoice() {
        String drinks = "";
        String priceMessage = "";
        String totalMessage = "";
        String priceFormatted = "";
        Double total = 0.0;
        Double price = 0.0;

        mTextViewAlert.setVisibility(View.GONE);
        mTextViewTitleInvoice.setVisibility(View.VISIBLE);
        mTableInvoice.setVisibility(View.VISIBLE);

        for (int i = 0; i < 3; i++) {
            if (mDrinkQty[i] > 0) {
                drinks = drinks + mDrinkChoice[i] + " " + " x " + mDrinkQty[i] + "\n\n";
                price = mDrinkQty[i] * mDrinkPrice[i];
                priceFormatted = String.format("%.2f", price);
                priceMessage = priceMessage + "£" + priceFormatted + "\n\n";
                total = total + price;
            }
        }

        mTextViewSelectedDrinks.setText(drinks);
        mTextViewSelectedDrinksPrice.setText(priceMessage);
        mTextViewTotal.setText(getString(R.string.label_total));

        totalMessage = "£" + String.format("%.2f", total);
        mTextViewTotalPrice.setText(totalMessage);

        mTextViewMsg.setVisibility(View.VISIBLE);

    /*    View targetView = findViewById(R.id.labelThanks);
        targetView.getParent().requestChildFocus(targetView,targetView);*/
    }
}