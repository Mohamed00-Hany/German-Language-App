package com.projects.germanlanguageapp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.projects.germanlanguageapp.R;
import com.projects.germanlanguageapp.databinding.FragmentQuistionsBinding;

public class Questions_page_Fragment extends Fragment {

    private FragmentQuistionsBinding binding;
    private Button choose_button;
    private Button rearrange_button;
    private Button match_button;
    private Button complete_button;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Questions_page_ViewModel homeViewModel =
                new ViewModelProvider(this).get(Questions_page_ViewModel.class);

        binding = FragmentQuistionsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        choose_button=root.findViewById(R.id.choose_button);
        rearrange_button=root.findViewById(R.id.rearrange_button);
        match_button=root.findViewById(R.id.match_button);
        complete_button=root.findViewById(R.id.complete_button);
        choose_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), choose_Q.class);
                startActivity(intent);
            }
        });

        rearrange_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), rearrangequestions.class);
                startActivity(intent);
            }
        });
        match_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MatchActivity.class);
                startActivity(intent);
            }
        });
        complete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), complete_Q_Activity.class);
                startActivity(intent);
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}