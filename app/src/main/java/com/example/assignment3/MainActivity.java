package com.example.assignment3;

import android.content.Context;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment3.R;
import com.example.assignment3.activity2;

public class MainActivity extends AppCompatActivity {
    private ListView countryListView;
    private String[] countries;
    private int[] flagResources = {R.drawable.spflag, R.drawable.saflag, R.drawable.egflag, R.drawable.inflag, R.drawable.usflag, R.drawable.geflag, R.drawable.ukflag, R.drawable.caflag};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Countries");

        countries = getResources().getStringArray(R.array.country_names);

        countryListView = findViewById(R.id.countryListView);
        CountryAdapter adapter = new CountryAdapter(this, countries, flagResources);
        countryListView.setAdapter(adapter);

        countryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCountry = (String) parent.getItemAtPosition(position);
                openActivity2(selectedCountry);
            }
        });
    }

    private void openActivity2(String country) {
        Intent intent = new Intent(this, activity2.class);
        intent.putExtra("country", country);
        startActivity(intent);
    }

    private static class CountryAdapter extends ArrayAdapter<String> {
        private final Context context;
        private String[] countries;
        private int[] flagResources;

        public CountryAdapter(Context context, String[] countries, int[] flagResources) {
            super(context, 0, countries);
            this.context = context;
            this.countries = countries;
            this.flagResources = flagResources;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.country_list_item, parent, false);
            }
            String country = countries[position];
            int flagResource = flagResources[position];

            TextView countryTextView = view.findViewById(R.id.countryTextView);
            ImageView flagsImageView = view.findViewById(R.id.flagImageView);
            countryTextView.setText(country);
            Bitmap flagBitmap = BitmapFactory.decodeResource(context.getResources(), flagResource);
            flagsImageView.setImageBitmap(flagBitmap);

            return view;
        }
    }
}

