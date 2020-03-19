package com.example.coronatracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Get the list of Corona News from {@link QueryUtils}
        ArrayList<CoronaNews> coronaNews = QueryUtils.extractCoronaNews();

        // Find a reference to the {@link ListView} in the layout
        ListView CoronaNewsListView = (ListView) findViewById(R.id.list);

        // Create a new adapter that takes the list of Corona news as input
        final CoronaNewsAdapter adapter = new CoronaNewsAdapter(this, coronaNews);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        CoronaNewsListView.setAdapter(adapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected CoronaNews.
        CoronaNewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current Corona News that was clicked on
                CoronaNews currentCoronaNews = adapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri coronaUri = Uri.parse(currentCoronaNews.getUrl());

                // Create a new intent to view the Corona URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, coronaUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });




    }
}
