package com.example.android.quakereport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class EarthquakeAdapter extends ArrayAdapter {


    /**
     * Create a new {@link EarthquakeAdapter} object.
     *  @param context is the current context (i.e. Activity) that the adapter is being created in.
     * @param simple_list_item_1
     * @param earthquakes is the list of {@link Earthquake}s to be displayed.
     */
    public EarthquakeAdapter(Context context, int simple_list_item_1, ArrayList<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.earthquake_list_item, parent, false);
        }

        // Get the {@link Word} object located at this position in the list
        Earthquake currentWord = (Earthquake) getItem(position);

        // Find the TextView in the list_item.xml layout with the ID miwok_text_view.
        TextView magtext = (TextView) listItemView.findViewById(R.id.mag_textView);
        // Get the Miwok translation from the currentWord object and set this text on
        // the Miwok TextView.
        magtext.setText(currentWord.getmMag());

        // Find the TextView in the list_item.xml layout with the ID default_text_view.
        TextView loctext = (TextView) listItemView.findViewById(R.id.loc_textView);
        // Get the default translation from the currentWord object and set this text on
        // the default TextView.
        loctext.setText(currentWord.getmLoc());

        // Find the TextView in the list_item.xml layout with the ID default_text_view.
        TextView timetext = (TextView) listItemView.findViewById(R.id.time_textView);
        // Get the default translation from the currentWord object and set this text on
        // the default TextView.
        timetext.setText(currentWord.getmTime());

        // Return the whole list item layout (containing 2 TextViews) so that it can be shown in
        // the ListView.
        return listItemView;
    }
}

