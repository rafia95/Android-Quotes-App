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

    protected SharedPreferences prefs;
    MainActivity mainAct;
    private String c;
    String[] categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        // Initialize Firebase Auth and Database Reference
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mFirebaseAuth.signInWithEmailAndPassword(getString(R.string.email), getString(R.string.password));
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // using the strings.xml array
        categories = getResources().getStringArray(R.array.category_array);

        mainAct = new MainActivity();
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

        if (!(this.getClass().equals(AboutActivity.class))) {
            Intent i = new Intent(this, AboutActivity.class);
            startActivity(i);
        }
    }

    protected void randomAction() {
        //   mainAct = new MainActivity();
        Random r = new Random();
        int random = r.nextInt(4) + 0;
        //  mDatabase = FirebaseDatabase.getInstance().getReference();
        String c = categories[random];
        getRandomQuote(c);
    }

    private void getRandomQuote(String category) {
        Random r = new Random();
        final int position = r.nextInt(5) + 1;
        c = category;
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Quote quote = getAQuote(dataSnapshot, position, c);
                startQuoteActivity(quote);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void startQuoteActivity(Quote quote) {
        savePref(quote);
        Intent i = new Intent(this, QuoteActivity.class);
        i.putExtra("Quote", quote);
        startActivity(i);
    }
    /**
     *
     * Retrieves the data from the firebase and creates a quote object
     * @param dataSnapshot data snapshot
     * @param rand random number generated for a quote
     * @param category name of the category selected
     * @return returns the Quote Object created using the firebase data
     */
    private Quote getAQuote(DataSnapshot dataSnapshot, int rand, String category) {
        Quote quote = new Quote();
        String cat = getResources().getString(R.string.categoryName);
        String randomQuote = ""+rand;
        quote.setAttributed(dataSnapshot.child(cat).child(category).child(randomQuote).child("0").getValue().toString());
        quote.setBlurb(dataSnapshot.child(cat).child(category).child(randomQuote).child("1").getValue().toString());
        quote.setQuote(dataSnapshot.child(cat).child(category).child(randomQuote).child("2").getValue().toString());
        quote.setReference(dataSnapshot.child(cat).child(category).child(randomQuote).child("3").getValue().toString());
        quote.setDate(dataSnapshot.child(cat).child(category).child(randomQuote).child("4").getValue().toString());
        quote.setCategory(category);
        return quote;
    }
    private void lastAction() {
        String category = prefs.getString("category", "");
        String attributed = prefs.getString("attributed", "");
        String blurb = prefs.getString("blurb", "");
        String date = prefs.getString("date", "");
        String quoteText = prefs.getString("quoteText", "");
        String reference = prefs.getString("reference", "");

        Quote quote = new Quote();
        quote.setCategory(category);
        quote.setAttributed(attributed);
        quote.setBlurb(blurb);
        quote.setDate(date);
        quote.setQuote(quoteText);
        quote.setReference(reference);

        Intent i = new Intent(this, QuoteActivity.class);
        i.putExtra("Quote", quote);
        startActivity(i);
    }
    private void savePref(Quote quote)
    {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("category", quote.getCategory());
        editor.putString("attributed", quote.getAttributed());
        editor.putString("blurb", quote.getBlurb());
        editor.putString("date", quote.getDate());
        editor.putString("quoteText", quote.getQuote());
        editor.putString("reference", quote.getReference());
        editor.commit();
    }
}