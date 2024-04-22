package com.projects.germanlanguageapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.projects.germanlanguageapp.R
import com.projects.germanlanguageapp.databinding.FragmentQuistionsBinding
import com.projects.germanlanguageapp.ui.chooseQuestions.ChooseQuestionsActivity
import com.projects.germanlanguageapp.ui.completeQuestions.CompleteQuestionsActivity
import com.projects.germanlanguageapp.ui.matchQuestions.MatchActivity
import com.projects.germanlanguageapp.ui.rearrangeQuestions.RearrangeQuestionsActivity

class QuestionsFragment : Fragment() {
    private var binding: FragmentQuistionsBinding? = null
    private var choose_button: Button? = null
    private var rearrange_button: Button? = null
    private var match_button: Button? = null
    private var complete_button: Button? = null
    private var levelId: Int? = null
    private var lessonId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val homeViewModel = ViewModelProvider(this).get(
            QuestionsViewModel::class.java
        )
        levelId = (requireActivity() as MainActivity).intent.getIntExtra("levelId", 0)
        lessonId = (requireActivity() as MainActivity).intent.getIntExtra("lessonId", 0)
        binding = FragmentQuistionsBinding.inflate(inflater, container, false)
        val root = binding!!.root
        choose_button = root.findViewById(R.id.choose_button)
        rearrange_button = root.findViewById(R.id.rearrange_button)
        match_button = root.findViewById(R.id.match_button)
        complete_button = root.findViewById(R.id.complete_button)
        binding!!.chooseButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, ChooseQuestionsActivity::class.java)
            intent.putExtra("levelId",levelId)
            intent.putExtra("lessonId",lessonId)
            startActivity(intent)
        })
        binding!!.rearrangeButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, RearrangeQuestionsActivity::class.java)
            intent.putExtra("levelId",levelId)
            intent.putExtra("lessonId",lessonId)
            startActivity(intent)
        })
        binding!!.matchButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, MatchActivity::class.java)
            intent.putExtra("levelId",levelId)
            intent.putExtra("lessonId",lessonId)
            startActivity(intent)
        })
        binding!!.completeButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, CompleteQuestionsActivity::class.java)
            intent.putExtra("levelId",levelId)
            intent.putExtra("lessonId",lessonId)
            startActivity(intent)
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}