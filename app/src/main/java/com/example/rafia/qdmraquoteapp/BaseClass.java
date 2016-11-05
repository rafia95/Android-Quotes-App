package com.example.rafia.qdmraquoteapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BaseClass extends AppCompatActivity {
    protected FirebaseAuth mFirebaseAuth;
    protected FirebaseUser mFirebaseUser;
    protected DatabaseReference mDatabase;

    protected List<Quote> quotes;
    protected int numOfQuotes;

    protected SharedPreferences prefs;
    MainActivity mainAct;
    private String c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        // Initialize Firebase Auth and Database Reference
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mFirebaseAuth.signInWithEmailAndPassword("rafiasend@gmail.com", "compscisend");
        mDatabase = FirebaseDatabase.getInstance().getReference();

        quotes = new ArrayList<>();
        numOfQuotes = 0;

        prefs = this.getPreferences(Context.MODE_PRIVATE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            aboutAction();

            return true;
        } else if (id == R.id.action_random) {
            randomAction();

            return true;
        } else if (id == R.id.action_last) {
            lastAction();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void aboutAction() {
        /*if (!this.getClass().equals(AboutActivity.class)) {
            Intent i = new Intent(this, AboutActivity.class);
            startActivity(i);
        }*/
    }

    protected void randomAction() {
        mainAct = new MainActivity();
        System.out.println("IN RANDOM");
        Random r = new Random();
        int random = r.nextInt(4) + 0;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        String c = mainAct.categories[random];
        getRandomQuote(mDatabase, c);
    }

    protected void getRandomQuote(DatabaseReference db, String category) {
        Random r = new Random();
        final int position = r.nextInt(5) + 1;
        c = category;
        System.out.println("the category is " + category + " with random  quote at " + position);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Quote quote = mainAct.getAQuote(dataSnapshot, position, c);
                startQuoteActivity(quote);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    protected void startQuoteActivity(Quote quote) {
        Intent i = new Intent(this, QuoteActivity.class);
        i.putExtra("Quote", quote);
        startActivity(i);
    }

    private void lastAction() {
        String id = prefs.getString("id", "");
        String category = prefs.getString("cat", "");
        String attributed = prefs.getString("attributed", "");
        String blurb = prefs.getString("blurb", "");
        String date = prefs.getString("date", "");
        String quoteText = prefs.getString("quote", "");
        String reference = prefs.getString("ref", "");

        Quote quote = new Quote();
        //quote.setCategory(category);
        quote.setAttributed(attributed);
        quote.setBlurb(blurb);
        quote.setDate(date);
        quote.setQuote(quoteText);
        quote.setReference(reference);

        Intent i = new Intent(this, QuoteActivity.class);
        i.putExtra("data", quote);
        startActivity(i);
    }
}