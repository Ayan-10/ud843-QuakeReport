package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter {

     private static final String LOCATION_SEPARATOR = " of ";

    /**
     * Create a new {@link EarthquakeAdapter} object.
     *  @param context is the current context (i.e. Activity) that the adapter is being created in.
     * @param earthquakes is the list of {@link Earthquake}s to be displayed.
     */
    public EarthquakeAdapter(Context context, List<Earthquake> earthquakes) {
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
        Earthquake currentEarthquake = (Earthquake) getItem(position);


        // Find the TextView in the list_item.xml layout with the ID miwok_text_view.
        TextView magtext = (TextView) listItemView.findViewById(R.id.mag_textView);
        // Get the Miwok translation from the currentWord object and set this text on
        // the Miwok TextView.

        String formattedMag = formateMag(currentEarthquake.getMag());
        magtext.setText(formattedMag);

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magtext.getBackground();
        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMag());
        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        String originalLoc = currentEarthquake.getLoc();

        String primaryLoc;
        String offsetLoc;

        // Check whether the originalLocation string contains the " of " text
        if (originalLoc.contains(LOCATION_SEPARATOR)) {
            // Split the string into different parts (as an array of Strings)
            // based on the " of " text. We expect an array of 2 Strings, where
            // the first String will be "5km N" and the second String will be "Cairo, Egypt".
            String[] parts = originalLoc.split(LOCATION_SEPARATOR);
            // Location offset should be "5km N " + " of " --> "5km N of"
            offsetLoc = parts[0] + LOCATION_SEPARATOR;
            // Primary location should be "Cairo, Egypt"
            primaryLoc = parts[1];
        } else {
            // Otherwise, there is no " of " text in the originalLocation string.
            // Hence, set the default location offset to say "Near the".
            offsetLoc = getContext().getString(R.string.near_the);
            // The primary location will be the full location string "Pacific-Antarctic Ridge".
            primaryLoc = originalLoc;
        }

        // Find the TextView in the list_item.xml layout with the ID default_text_view.
        TextView offloctext = (TextView) listItemView.findViewById(R.id.loc_offset_textView);
        // Get the default translation from the currentWord object and set this text on
        // the default TextView.
        offloctext.setText(offsetLoc);


        // Find the TextView in the list_item.xml layout with the ID default_text_view.
        TextView priloctext = (TextView) listItemView.findViewById(R.id.loc_primary_textView);
        // Get the default translation from the currentWord object and set this text on
        // the default TextView.
        priloctext.setText(primaryLoc);


        Date dateObject = new Date(currentEarthquake.getTimeInMilliseconds());
        // Find the TextView in the list_item.xml layout with the ID default_text_view.
        TextView datetext = (TextView) listItemView.findViewById(R.id.date_textView);
        // Get the default translation from the currentWord object and set this text on
        // the default TextView.
        // Format the time string (i.e. "4:30PM")

        String formattedDate = formatDate(dateObject);

        datetext.setText(formattedDate);

        // Find the TextView in the list_item.xml layout with the ID default_text_view.
        TextView timetext = (TextView) listItemView.findViewById(R.id.time_textView);
        // Get the default translation from the currentWord object and set this text on
        // the default TextView.

        // Format the time string (i.e. "4:30PM")
        String formattedTime = formatTime(dateObject);
        timetext.setText(formattedTime);


        // Return the whole list item layout (containing 2 TextViews) so that it can be shown in
        // the ListView.
        return listItemView;
    }


    /**
     * Return the color for the magnitude circle based on the intensity of the earthquake.
     *
     * @param magnitude of the earthquake
     */
    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }

        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
    private String formateMag(double mag){
        DecimalFormat magFormat = new DecimalFormat("0.0");
        return magFormat.format(mag);
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
}

