package com.projects.germanlanguageapp.ui.wordsdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projects.germanlanguageapp.databinding.WordItemBinding
import com.projects.germanlanguageapp.domain.models.WordsResponse

class WordsAdapter(private var wordsList:List<WordsResponse>?):RecyclerView.Adapter<WordsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = WordItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val word=wordsList?.get(position)
        holder.bind(word,position,onFeatureClickListener)
    }

    override fun getItemCount(): Int = wordsList?.size ?: 0

    lateinit var onFeatureClickListener: OnFeatureClick

    interface OnFeatureClick{
        fun onClick(position:Int,word: String?,featureNumber: Int)
    }

    class ViewHolder(private val itemBinding: WordItemBinding) :RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(word: WordsResponse?, position: Int, onFeatureClickListener: OnFeatureClick)
        {
            itemBinding.word=word?.wordInGerman
            itemBinding.translation=word?.wordTranslationInArabic
            itemBinding.invalidateAll()
            itemBinding.microphone.setOnClickListener{
                onFeatureClickListener.onClick(position,word?.wordInGerman,1)
            }
            itemBinding.mic.setOnClickListener{
                onFeatureClickListener.onClick(position,word?.wordInGerman,2)
            }
        }
    }

    fun changeData(wordsList:List<WordsResponse>?) {
        this.wordsList = wordsList
        notifyDataSetChanged()
    }

}