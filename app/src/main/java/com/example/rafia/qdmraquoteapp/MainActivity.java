package com.example.rafia.qdmraquoteapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.Random;

/**
 * This class manages the listView of the categories , creates a random
 * quote when a category is clicked and invoke the Quote Activity.
 * @author Rafia Anwar
 * @version 1.0.0
 */
public class MainActivity extends BaseClass {
    Random r;
    String c;
    int random, position;
    ArrayAdapter<String> aa = null;

    //= {"Celebrities","Computer Science","Philosophers","Politicians","Writers"};
    /**
     * Overridden onCreate method.
     * Sets an ArrayAdapter to the listView and
     * binds eventListener to the listview  to get
     * random quotes on click
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView listView = (ListView) findViewById(R.id.listView);
        aa = new ArrayAdapter<String>(this, R.layout.category_list, categories);
        listView.setAdapter(aa);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                position = i;
                TextView tv = (TextView) view.findViewById(R.id.textView1);
                tv.setText(categories[i]);
                findRandomQuote(categories[position]);
                System.out.println("the position is " + position + " view " + view);
            }
        });
    }

    /**
     * Generates a random number to get a random quote from the selected category,
     * invokes other methods to start QuoteActivity
     *
     * @param category represents the category selected by the user
     */
    private void findRandomQuote(String category) {
        r = new Random();
        random = r.nextInt(5) + 1;
        c = category;
        // System.out.println("the category is " + category + " with random  quote at " + random);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Quote quote = getAQuote(dataSnapshot, random, c);
                startQuoteActivity(quote);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
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
        quote.setAttributed(dataSnapshot.child(cat).child(category).child("" + rand).child("0").getValue().toString());
        quote.setBlurb(dataSnapshot.child(cat).child(category).child("" + rand).child("1").getValue().toString());
        quote.setQuote(dataSnapshot.child(cat).child(category).child("" + rand).child("2").getValue().toString());
        quote.setReference(dataSnapshot.child(cat).child(category).child("" + rand).child("3").getValue().toString());
        quote.setDate(dataSnapshot.child(cat).child(category).child("" + rand).child("4").getValue().toString());
        quote.setCategory(category);
        //      System.out.println(quote.toString());
        return quote;
    }

    /**
     * Starts the Quote Activity
     * @param quote the Quote object to be sent to the Quote Activity
     */
    private void startQuoteActivity(Quote quote) {
        Intent i = new Intent(this, QuoteActivity.class);
        i.putExtra("Quote", quote);
        startActivity(i);
    }
}