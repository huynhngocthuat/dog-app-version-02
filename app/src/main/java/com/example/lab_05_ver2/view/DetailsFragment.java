package com.example.lab_05_ver2.view;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab_05_ver2.R;
import com.example.lab_05_ver2.databinding.FragmentDetailsBinding;
import com.example.lab_05_ver2.model.DogBreed;


public class DetailsFragment extends Fragment {

    private DogBreed dogBreed;
    private FragmentDetailsBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dogBreed = (DogBreed) getArguments().getSerializable("dogBreed");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_details, null, false);
        View view = binding.getRoot();
        binding.setDog(dogBreed);
        return view;
    }
}