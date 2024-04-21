package com.projects.germanlanguageapp.ui.translation;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;
import com.projects.germanlanguageapp.R;
import com.projects.germanlanguageapp.databinding.FragmentTranslationBinding;

public class TranslationFragment extends Fragment {

    private FragmentTranslationBinding binding;
    private Button translate_button;
    private EditText edittext_1;
    private EditText edittext_2;
    private ImageButton exchange_button;
    private TextView arabic_word;
    private TextView german_word;
    private Translator translator;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TranslationViewModel translationpageViewModel =
                new ViewModelProvider(this).get(TranslationViewModel.class);

        binding = FragmentTranslationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        edittext_1 = root.findViewById(R.id.edittext_1);
        translate_button = root.findViewById(R.id.translate_button);
        edittext_2 = root.findViewById(R.id.edittext_2);
        exchange_button = root.findViewById(R.id.exchange_button);
        arabic_word = root.findViewById(R.id.arabic_word);
        german_word = root.findViewById(R.id.german_word);

        translate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sourceText = edittext_1.getText().toString();
                if (TextUtils.isEmpty(sourceText)) {
                    Toast.makeText(getContext(), "No text found", Toast.LENGTH_SHORT).show();
                    return;
                }

                ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Downloading the translation model...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                TranslatorOptions options = new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.ARABIC)
                        .setTargetLanguage(TranslateLanguage.GERMAN)
                        .build();
                TranslatorOptions options1 = new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.GERMAN)
                        .setTargetLanguage(TranslateLanguage.ARABIC)
                        .build();

                if (german_word.getText().toString().equals("Arabisch")) {
                    translator = Translation.getClient(options1);
                } else {
                    translator = Translation.getClient(options);
                }

                translator.downloadModelIfNeeded().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        translateText(sourceText);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Failed to download the translation model.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        exchange_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text1 = arabic_word.getText().toString();
                String text2 = german_word.getText().toString();
                arabic_word.setText(text2);
                german_word.setText(text1);

                String text3 = edittext_1.getText().toString();
                String text4 = edittext_2.getText().toString();
                edittext_1.setText(text4);
                edittext_2.setText(text3);
            }
        });

        return root;
    }

    private void translateText(String sourceText) {
        translator.translate(sourceText).addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String translatedText) {
                edittext_2.setText(translatedText);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                edittext_2.setText(e.getMessage());
            }
        });
    }
    @Override
    public void onPause() {
        super.onPause();
        edittext_1.setText("");
        edittext_2.setText("");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}