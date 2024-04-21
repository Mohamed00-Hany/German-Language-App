package com.projects.germanlanguageapp.ui.levels

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projects.germanlanguageapp.databinding.LevelsRecyclerItemBinding
import com.projects.germanlanguageapp.domain.models.LevelsResponseItem

class LevelsAdapter(private var levelsList:List<LevelsResponseItem?>?):RecyclerView.Adapter<LevelsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = LevelsRecyclerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val level=levelsList?.get(position)
        holder.bind(level,position,onLevelClickListener)

    }

    override fun getItemCount(): Int = levelsList?.size ?: 0

    lateinit var onLevelClickListener:OnLevelClick


    interface OnLevelClick{
        fun onClick(position:Int,levelId: Int?)
    }

    fun changeData(levelsList: List<LevelsResponseItem?>?) {
        this.levelsList = levelsList
        notifyDataSetChanged()
    }


    class ViewHolder(private val itemBinding: LevelsRecyclerItemBinding) :RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(level: LevelsResponseItem?,position: Int,onLevelClickListener:OnLevelClick)
        {
            itemBinding.title=level?.levelName
            itemBinding.invalidateAll()
            itemBinding.levelButton.setOnClickListener{
                onLevelClickListener.onClick(position,level?.levelId)
            }
        }
    }
}