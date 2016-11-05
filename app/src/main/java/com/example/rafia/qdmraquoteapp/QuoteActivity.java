package com.example.rafia.qdmraquoteapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

/**
 * @author Denys Melyukhov
 * @version 1.0.0
 */
public class QuoteActivity extends BaseClass
{
    private TextView category, attribute, quote, date, reference;
    private Quote quoteBean;

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
        quote = (TextView)findViewById(R.id.quote);
        date = (TextView)findViewById(R.id.date);
        reference = (TextView)findViewById(R.id.ref);

        //Intent i = getIntent();
        //quoteBean = (Quote) i.getSerializableExtra("data");
        Bundle bun = getIntent().getExtras();
        quoteBean = (Quote) bun.get("Quote");
        int numWinsP1 = bun.getInt("p1Wins");

        //category.setText(quoteBean.getCategory());
        attribute.setText(quoteBean.getAttributed());
        quote.setText(quoteBean.getQuote());
        date.setText(quoteBean.getDate());
        reference.setText(quoteBean.getReference());
    }

    /**
     * Event handler for an onClick event on the person the quote is attributed to.
     * Shows additional information (Blurb) about the person.
     * @param view TextView
     */
    public void onAttributed(View view)
    {
        new AlertDialog.Builder(this).setMessage(quoteBean.getBlurb()).setTitle("Blurb")
                .setCancelable(true).show();
    }

    /**
     * Event handler for an onClick event on the reference's URL. Opens the web browser to show
     * the reference.
     * @param view TextView
     */
    public void onRef(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(quoteBean.getReference()));
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
