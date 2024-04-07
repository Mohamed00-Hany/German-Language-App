package com.projects.germanlanguageapp.ui.wordsdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projects.germanlanguageapp.databinding.WordItemBinding
import com.projects.germanlanguageapp.domain.models.WordsResponse
import com.projects.germanlanguageapp.ui.lessons.LessonsAdapter

class WordsAdapter(private var wordsList:List<WordsResponse>?):RecyclerView.Adapter<WordsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = WordItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val word=wordsList?.get(position)
        holder.bind(word,position,onMicrophoneClickListener)
    }

    override fun getItemCount(): Int = wordsList?.size ?: 0

    lateinit var onMicrophoneClickListener: WordsAdapter.OnMicrophoneClick

    interface OnMicrophoneClick{
        fun onClick(position:Int,word: String?)
    }

    class ViewHolder(private val itemBinding: WordItemBinding) :RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(word: WordsResponse?, position: Int, onMicrophoneClickListener: WordsAdapter.OnMicrophoneClick)
        {
            itemBinding.word=word?.word
            itemBinding.translation=word?.wordTranslation
            itemBinding.invalidateAll()
            itemBinding.microphone.setOnClickListener{
                onMicrophoneClickListener.onClick(position,word?.word)
            }
        }
    }

    fun changeData(wordsList:List<WordsResponse>?) {
        this.wordsList = wordsList
        notifyDataSetChanged()
    }

}