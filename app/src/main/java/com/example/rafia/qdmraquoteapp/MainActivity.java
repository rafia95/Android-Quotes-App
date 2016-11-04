package com.example.rafia.qdmraquoteapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class MainActivity extends BaseClass {
//    private DatabaseReference mDatabase;
//    private FirebaseAuth mFirebaseAuth;
//    private FirebaseUser mFirebaseUser;
    private String mUserId;
    Random r;
    int random;
    String c;
    String[] categories = {"Computer Science","Politicians","Writers","Philosophers","Celebrities"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mFirebaseAuth = FirebaseAuth.getInstance();
//        mFirebaseUser = mFirebaseAuth.getCurrentUser();
//        mFirebaseAuth.signInWithEmailAndPassword("rafiasend@gmail.com", "compscisend");
//        mDatabase = FirebaseDatabase.getInstance().getReference();
        // Set up ListView
  //      final ListView listView = (ListView) findViewById(R.id.listView);
     //   final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
    //    listView.setAdapter(new CategoryAdapter(this,mDatabase,categories))
    }

    private void setListeners(){
    TextView tv1 = (TextView) findViewById(R.id.textViewC1);

        tv1.setText(""+categories[0]);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findRandomQuote(v,categories[0]);
            }
        });
    }
    private void findRandomQuote(View v,String category ){
        r = new Random();
       random =  r.nextInt(5) + 1;
        c = category;
        System.out.println("the category is " + category + " with random  quote at " + random);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               Quote quote= getAQuote(dataSnapshot,random,c);
                startQuoteActivity(quote);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());

            }
        });
        }
    private Quote getAQuote(DataSnapshot dataSnapshot,int rand,String category){
        Quote quote = new Quote();
        quote.setAttributed(dataSnapshot.child("Category").child(category).child("" + rand).child("0").getValue().toString());
        quote.setBlurb(dataSnapshot.child("Category").child(category).child("" + rand).child("1").getValue().toString());
        quote.setQuote(dataSnapshot.child("Category").child(category).child("" + rand).child("2").getValue().toString());
        quote.setReference(dataSnapshot.child("Category").child(category).child("" + rand).child("3").getValue().toString());
        quote.setDate(dataSnapshot.child("Category").child(category).child("" + rand).child("4").getValue().toString());
        quote.setCategory(category);
        System.out.println(quote.toString());
        return quote;
    }

    private void startQuoteActivity(Quote quote)
    {
        Intent i = new Intent(this, QuoteActivity.class);

        i.putExtra("Quote", quote);

        startActivity(i);

    }
}
