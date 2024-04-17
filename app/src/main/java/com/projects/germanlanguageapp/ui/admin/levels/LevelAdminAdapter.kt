package com.projects.germanlanguageapp.ui.admin.levels
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projects.germanlanguageapp.databinding.LevelsRecyclerItemBinding
import com.projects.germanlanguageapp.domain.models.LevelsResponseItem
class LevelAdminAdapter(var levelsList: MutableList<LevelsResponseItem> = mutableListOf()): RecyclerView.Adapter<LevelAdminAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = LevelsRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val level = levelsList[position]
        holder.bind(level, position, onLevelClickListener)
    }

    override fun getItemCount(): Int = levelsList.size

    lateinit var onLevelClickListener: OnLevelClick

    interface OnLevelClick {
        fun onClick(position: Int, levelId: Int?)
    }

    fun changeData(levelsList: List<LevelsResponseItem>?) {
        this.levelsList.clear()
        levelsList?.let { this.levelsList.addAll(it) }
        notifyDataSetChanged()
    }
    fun addLevel(level: LevelsResponseItem?) {
        level?.let {
            levelsList.add(level)
            notifyItemInserted(levelsList.size - 1)
        }
    }

    class ViewHolder(private val itemBinding: LevelsRecyclerItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(level: LevelsResponseItem?, position: Int, onLevelClickListener: OnLevelClick) {
            itemBinding.title = level?.levelName
            itemBinding.invalidateAll()
            itemBinding.levelButton.setOnClickListener {
                onLevelClickListener.onClick(position, level?.levelId)
            }
        }
    }
}