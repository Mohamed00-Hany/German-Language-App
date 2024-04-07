package com.projects.germanlanguageapp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.projects.germanlanguageapp.databinding.FragmentWordsBinding;
import com.projects.germanlanguageapp.ui.wordsdetails.WordsDetailsActivity;

public class Words_page_Fragment extends Fragment {

    private FragmentWordsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Words_page_ViewModel wordspageViewModel =
                new ViewModelProvider(this).get(Words_page_ViewModel.class);

        binding = FragmentWordsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.VerbsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWordsActivity();
            }
        });
        binding.adjectivesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWordsActivity();
            }
        });
        binding.NounsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWordsActivity();
            }
        });
        binding.OtherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWordsActivity();
            }
        });
        return root;
    }

    private void openWordsActivity() {
        startActivity(new Intent(requireActivity(),WordsDetailsActivity.class));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}