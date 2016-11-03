package com.example.rafia.qdmraquoteapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

/**
 * Created by 1410926 on 11/2/2016.
 */
public class CategoryAdapter extends BaseAdapter {
    String[] result;
    Context context;
    DatabaseReference db;
    Random random;
    Quote q;
    private static LayoutInflater inflater = null;

    public CategoryAdapter(MainActivity mainActivity, DatabaseReference mDatabase, String[] categories) {
        result = categories;
        context = mainActivity;
        db = mDatabase;
        random = new Random();
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return result.length;
    }

    @Override
    public Object getItem(int i) {
        return result[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View rowView;
        rowView = inflater.inflate(R.layout.category_list, null);
        TextView tv = (TextView) rowView.findViewById(R.id.textView1);
        tv.setText(result[i]);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int rand = random.nextInt(5) + 1;
                Toast.makeText(context, "You Clicked " + result[i] + " quote " + rand, Toast.LENGTH_LONG).show();
                db.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        q = new Quote();
                        q.setAttributed(dataSnapshot.child("Category").child(result[i]).child("" + rand).child("0").getValue().toString());
                        q.setBlurb(dataSnapshot.child("Category").child(result[i]).child("" + rand).child("1").getValue().toString());
                        q.setQuote(dataSnapshot.child("Category").child(result[i]).child("" + rand).child("2").getValue().toString());
                        q.setReference(dataSnapshot.child("Category").child(result[i]).child("" + rand).child("3").getValue().toString());
                        q.setDate(dataSnapshot.child("Category").child(result[i]).child("" + rand).child("4").getValue().toString());
                        System.out.println(q.toString());

                        // invoke quote activity here


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });

            }
        });
        return rowView;
    }
}
