package com.example.assignment3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class UniversityAdapter extends ArrayAdapter<University> {
    private LayoutInflater inflater;

    public UniversityAdapter(Context context , List<University> universities){
        super(context,0,universities );
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;
        if(view == null){
            view = inflater.inflate(R.layout.university_list_item, parent, false);
        }

        University university = getItem(position);
        if(university!=null){
            TextView nameTextView = view.findViewById(R.id.nameTextView);
            nameTextView.setText(university.getName());
        }
        return view;
    }
}
