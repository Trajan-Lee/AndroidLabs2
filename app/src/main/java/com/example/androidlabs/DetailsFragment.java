package com.example.androidlabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        TextView name_value = rootView.findViewById(R.id.name_value);
        TextView height_value = rootView.findViewById(R.id.height_value);
        TextView mass_value = rootView.findViewById(R.id.mass_value);

        // Retrieve the bundle passed when the fragment was created
        Bundle bundle = getArguments();
        if (bundle != null) {
            name_value.setText(bundle.getString("character_name"));
            height_value.setText(bundle.getString("character_height"));
            mass_value.setText(bundle.getString("character_mass"));
        }

        return rootView;
    }

}