package com.example.assignment3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class activity3 extends AppCompatActivity {
    private TextView universityNameTextView;
    private TextView universityDomainTextView;
    private Button OKButton;
    private Button VisitButton;
    private University selectedUniversity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity3);


        // Set the title of the ActionBar


        University university = (University) getIntent().getSerializableExtra("university");
        if (university != null) {
            selectedUniversity = university; // Assign the value to selectedUniversity
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(selectedUniversity.getName() + "'s Info");
            }
            universityNameTextView = findViewById(R.id.universityNameTextView);
            universityDomainTextView = findViewById(R.id.universityDomainTextView);

            String universityDomain = university.getDomain();

            universityDomain = universityDomain.replace("[", "").replace("]", "").replaceAll("\"", "");

            universityNameTextView.setText(university.getName());
            universityDomainTextView.setText("Domain: " + universityDomain);
        }

        OKButton = findViewById(R.id.okButton);
        VisitButton = findViewById(R.id.visitButton);

        OKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        VisitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String universityDomain = selectedUniversity.getDomain();
                openUniversityDomain(universityDomain); // Call the method to open the university webpag
            }
        });
    }

    private void openUniversityDomain(String universityDomain) {
        universityDomain = universityDomain.replace("[", "").replace("]", "").replaceAll("\"", "");

        if (!universityDomain.startsWith("http://") && !universityDomain.startsWith("https://")) {
            universityDomain = "http://" + universityDomain;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(universityDomain));
        startActivity(intent);
    }

}

