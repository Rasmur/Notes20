package com.example.igory.notes20.ListView;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.Random;

/**
 * Created by igory on 18.03.2018.
 */

public class ListItem implements Parcelable {

    private String head;
    private String description;
    private String date;
    private int color;

    Random rand = new Random();

    public ListItem(String head, String description, String date)
    {
        this.head = head;
        this.description = description;
        this.date = date;
        this.color = Color.argb(255, rand.nextInt(255), rand.nextInt(255),
                rand.nextInt(255));
    }

    public ListItem(String head, String description, int color, String date )
    {
        this.head = head;
        this.description = description;
        this.date = date;
        this.color = color;
    }

    public ListItem(Parcel source)
    {
        String[] data = new String[3];
        source.readStringArray(data);

        this.head = data[0];
        this.description = data[1];
        this.date = data[2];

        this.color = source.readInt();
    }

    public String getHead()
    {
        return head;
    }

    public String getDescription()
    {
        return description;
    }

    public String getDate()
    {
        return date;
    }

    public int getColor()
    {
        return color;
    }

    public void setColor(int color)
    {
        this.color = color;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public void setHead(String head)
    {
        this.head = head;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{getHead(), getDescription(), getDate()});
        dest.writeInt(getColor());
    }

    public static final Parcelable.Creator<ListItem> CREATOR = new Parcelable.Creator<ListItem>() {

        @Override
        public ListItem createFromParcel(Parcel source) {
            return new ListItem(source);
        }

        @Override
        public ListItem[] newArray(int size) {
            return new ListItem[size];
        }
    };
}
