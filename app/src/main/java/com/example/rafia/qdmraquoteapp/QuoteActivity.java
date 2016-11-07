package com.example.rafia.qdmraquoteapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * @author Denys Melyukhov
 * @version 1.0.0
 */
public class QuoteActivity extends BaseClass
{
    private TextView category, attribute, quoteText, date, reference;
    private Quote quote;

    /**
     * Overridden onCreate method.
     * Binds the TextViews and sets their content.
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);

        category = (TextView)findViewById(R.id.cat);
        attribute = (TextView)findViewById(R.id.att);
        quoteText = (TextView)findViewById(R.id.quote);
        date = (TextView)findViewById(R.id.date);
        reference = (TextView)findViewById(R.id.ref);

        //Intent i = getIntent();
        //quote = i.getParcelableExtra("data");
        Bundle bun = getIntent().getExtras();
        quote = (Quote) bun.get("Quote");

        category.setText(quote.getCategory());
        attribute.setText(quote.getAttributed());
        quoteText.setText(quote.getQuote());
        date.setText(quote.getDate());
        reference.setText(quote.getReference());
    }

    /**
     * Event handler for an onClick event on the person the quoteText is attributed to.
     * Shows additional information (Blurb) about the person.
     * @param view TextView
     */
    public void onAttributed(View view)
    {
        new AlertDialog.Builder(this).setMessage(quote.getBlurb()).setTitle("Blurb")
                .setCancelable(true).show();
    }

    /**
     * Event handler for an onClick event on the reference's URL. Opens the web browser to show
     * the reference.
     * @param view TextView
     */
    public void onRef(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(quote.getReference()));
        startActivity(browserIntent);
    }

    /**
     * Event handler for the Dismiss button. Closes the activity.
     * @param view Button
     */
    public void onDismiss(View view)
    {
        finish();
    }
}