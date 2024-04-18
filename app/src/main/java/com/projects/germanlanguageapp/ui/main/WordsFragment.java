package com.projects.germanlanguageapp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.projects.germanlanguageapp.databinding.FragmentWordsBinding;
import com.projects.germanlanguageapp.ui.wordsdetails.WordsDetailsActivity;

public class WordsFragment extends Fragment {

    private FragmentWordsBinding binding;
    private Integer levelId = 0 ;
    private Integer lessonId = 0 ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Words_page_ViewModel wordspageViewModel =
                new ViewModelProvider(this).get(Words_page_ViewModel.class);
        levelId = requireActivity().getIntent().getIntExtra("levelId",0) ;
        lessonId = requireActivity().getIntent().getIntExtra("lessonId",0) ;
        binding = FragmentWordsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.VerbsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWordsActivity("verbs");
            }
        });
        binding.adjectivesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWordsActivity("adjectives");
            }
        });
        binding.NounsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWordsActivity("nouns");
            }
        });
        binding.OtherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWordsActivity("others");
            }
        });
        return root;
    }

    private void openWordsActivity(String wordsType) {
        Intent intent = new Intent(requireActivity(),WordsDetailsActivity.class);
        intent.putExtra("wordsType",wordsType);
        intent.putExtra("levelId",levelId);
        intent.putExtra("lessonId",lessonId);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}