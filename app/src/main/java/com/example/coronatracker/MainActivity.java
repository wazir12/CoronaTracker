package com.example.coronatracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Fake corona cases locations.
        ArrayList<String> coronaCases = new ArrayList<>();
        coronaCases.add("San Francisco");
        coronaCases.add("London");
        coronaCases.add("Tokyo");
        coronaCases.add("Mexico City");
        coronaCases.add("Moscow");
        coronaCases.add("Rio de Janeiro");
        coronaCases.add("Paris");

        // Find a reference to the {@link ListView} in the layout
        ListView CoronaListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of corona cases
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, coronaCases);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        CoronaListView.setAdapter(adapter);

    }
}
