package com.example.qikai_countbook;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Object realises counter
 * @author qikai
 */
public class Counter implements Parcelable{

    // Required fields of a counter
    private String counter_name;
    private int init_count;
    private int count;
    private Date last_update;
    private String comment;

    // Constructor
    public Counter(String counter_name, int count, String comment) {
        this.counter_name = counter_name;
        this.init_count = count;
        this.count = count;
        this.last_update = new Date();
        this.comment = comment;
    }

    // The string representation of the object
    @Override
    public String toString() {
        return "Counter Name: " + this.counter_name + "\n" +
                "Last Update: " + (new SimpleDateFormat("yyyy-MM-dd").format(this.last_update)).toString() + "\n" +
                "Count: " + this.count;
    }

    public String getCounter_name() {
        return counter_name;
    }

    public void setCounter_name(String counter_name) {
        this.counter_name = counter_name;
    }

    public int getInit_count() {
        return init_count;
    }

    public void setInit_count(int init_count) {
        this.init_count = init_count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getLast_update() {
        return last_update;
    }

    public void setLast_update(Date last_update) {
        this.last_update = last_update;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    // Parcelable methods implmentations

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.counter_name);
        out.writeInt(this.init_count);
        out.writeInt(this.count);
        out.writeLong(this.last_update.getTime());
        out.writeString(this.comment);
    }

    private Counter(Parcel in) {
        this.counter_name = in.readString();
        this.init_count = in.readInt();
        this.count = in.readInt();
        this.last_update = new Date(in.readLong());
        this.comment = in.readString();
    }

    public static final Parcelable.Creator<Counter> CREATOR = new Parcelable.Creator<Counter>() {
        public Counter createFromParcel(Parcel in) {
            return new Counter(in);
        }
        public Counter[] newArray(int size) {
            return new Counter[size];
        }
    };

}
