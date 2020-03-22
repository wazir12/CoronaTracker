package com.lwazir.coronatracker;

import android.content.Context;


//import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CoronaNewsAdapter extends ArrayAdapter<CoronaNews>
{
   // private static final String LOCATION_SEPARATOR = " of ";

    /**
     * Constructs a new {@link CoronaNewsAdapter}.
     *
     * @param context of the app
     * @param coronaNews is the list of Corona News, which is the data source of the adapter
     */
    public CoronaNewsAdapter(Context context, List<CoronaNews> coronaNews) {
        super(context, 0, coronaNews);
    }

    /**
     * Returns a list item view that displays information about the corona news at the given position
     * in the list of corona News.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.corona_news_list_item, parent, false);
        }

        // Find the corona News at the given position in the list of coronaNews
        CoronaNews currentCoronaNews = getItem(position);
        // Get the original source of the news from the Corona News object,
        String originalSource = currentCoronaNews.getsName();

        // Find the TextView with view ID location
        TextView source = (TextView) listItemView.findViewById(R.id.source);
        // Display the source of the current News in that TextView
        source.setText(originalSource);

        String title = currentCoronaNews.getTitle();
        // Find the TextView with view ID location offset
        TextView titleView = (TextView) listItemView.findViewById(R.id.title);

        titleView.setText(title);


        String date = formatDate(new Date(currentCoronaNews.getPublishedAt()));
        String time = formatTime(new Date(currentCoronaNews.getPublishedAt()));



        // Find the TextView with view ID date
        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        // Format the date string (i.e. "Mar 3, 1984")

        // Display the date of the current corona  in that TextView
        dateView.setText(date);

        // Find the TextView with view ID time
        TextView timeView = (TextView) listItemView.findViewById(R.id.time);

        // Display the time of the current coronavirus case in that TextView
        timeView.setText(time);

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }


    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
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
