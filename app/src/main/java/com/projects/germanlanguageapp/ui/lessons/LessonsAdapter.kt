package com.projects.germanlanguageapp.ui.lessons

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projects.germanlanguageapp.databinding.LessonRecyclerItemBinding

class LessonsAdapter(private val lessonsList:List<String>?):RecyclerView.Adapter<LessonsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = LessonRecyclerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lesson=lessonsList?.get(position)
        holder.bind(lesson,position,onLessonClickListener)
    }

    override fun getItemCount(): Int = lessonsList?.size ?: 0

    lateinit var onLessonClickListener:OnLessonClick


    interface OnLessonClick{
        fun onClick(position:Int,lessonName: String?)
    }

    class ViewHolder(private val itemBinding: LessonRecyclerItemBinding) :RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(lesson: String?,position: Int,onLessonClickListener:OnLessonClick)
        {
            itemBinding.title=lesson
            itemBinding.invalidateAll()
            itemBinding.levelButton.setOnClickListener{
                onLessonClickListener.onClick(position,lesson)
            }
        }
    }
}