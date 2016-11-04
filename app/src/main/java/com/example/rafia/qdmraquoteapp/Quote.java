package com.example.rafia.qdmraquoteapp;

import android.os.Parcel;
import android.os.Parcelable;
/**
 * Created by 1410926 on 11/2/2016.
 */
public class Quote implements Parcelable {
    private String attributed;
    private String blurb;
    private String quote;
    private String reference;
    private String date;
    private String category;
    public Quote(){}

    public String getAttributed() {
        return attributed;
    }

    public void setAttributed(String attributed) {
        this.attributed = attributed;
    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() { return category;}

    public void setCategory(String category){this.category = category;}

    protected Quote(Parcel in) {
        attributed = in.readString();
        blurb = in.readString();
        quote = in.readString();
        reference = in.readString();
        date = in.readString();
        category = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(attributed);
        dest.writeString(blurb);
        dest.writeString(quote);
        dest.writeString(reference);
        dest.writeString(date);
        dest.writeString(category);

    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Quote> CREATOR = new Parcelable.Creator<Quote>() {
        @Override
        public Quote createFromParcel(Parcel in) {
            return new Quote(in);
        }

        @Override
        public Quote[] newArray(int size) {
            return new Quote[size];
        }
    };

    @Override
    public String toString(){
        return this.getAttributed() + "-" + this.getBlurb() + "-" + this.getQuote() + "-" + this.getReference() + "-" + this.getDate() + "-"+this.getCategory();
    }
}