package com.example.rafia.qdmraquoteapp;

import android.os.Parcel;
import android.os.Parcelable;
/**
 * Quote class is parceable class which holds all the necessary
 * information about a quote.
 * @author Rafia Anwar
 * @version 1.0.0
 */
public class Quote implements Parcelable {
    private String attributed;
    private String blurb;
    private String quote;
    private String reference;
    private String date;
    private String category;
    public Quote(){}

    /**
     * Gets the value of attributed
     * @return the value of attributed
     */
    public String getAttributed() {
        return attributed;
    }

    /**
     * Sets the value of attributed
     * @param attributed the name of the person the quote is attributed to
     */
    public void setAttributed(String attributed) {
        this.attributed = attributed;
    }

    /**
     * Gets the value of blurb
     * @return the value of blurb
     */
     public String getBlurb() {
        return blurb;
    }

    /**
     * Sets the value of blurb
     * @param blurb DOB and one sentence of information about the person who said the quote
     */
    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    /**
     * Gets the value of quote
     * @return the value of quote
     */
    public String getQuote() {
        return quote;
    }

    /**
     * Sets the value of the quote
     * @param quote the quote itself
     */
    public void setQuote(String quote) {
        this.quote = quote;
    }

    /**
     * Gets the value of reference
     * @return the value of reference of the quote
     */
    public String getReference() {
        return reference;
    }

    /**
     * Sets the value of reference of the quote
     * @param reference url of the site where you found the quote
     */
    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * Gets the date value when the quote was added to the database
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date value
     * @param date  (date added)  YYYY-MM-DD
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets the category of the quote
     * @return the quote category
     */
    public String getCategory() { return category;}

    /**
     * Sets the category value of the quote
     * @param category to which the quote belongs
     */
    public void setCategory(String category){this.category = category;}

    /*
     * Creates a parcelabale quote object
     */
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

    /*
     * Writes the values to a parcel
     */
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

    /**
     * String representation of the object
     * @return string representation of the object
     */
    @Override
    public String toString(){
        return this.getAttributed() + "-" + this.getBlurb() + "-" + this.getQuote() + "-" + this.getReference() + "-" + this.getDate() + "-"+this.getCategory();
    }
}