import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.projects.germanlanguageapp.R
class QuestionsAdapter(
    private var dataSet: List<String>,
    private val clickListener: QuestionClickListener
) : RecyclerView.Adapter<QuestionsAdapter.ViewHolder>() {

    interface QuestionClickListener {
        fun onQuestionClick(position: Int)
        fun onDeleteClick(position: Int)
        fun onEditClick(position: Int)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val buttonQuestion: Button = view.findViewById(R.id.Questions_button)
        val buttonDelete: ImageButton = view.findViewById(R.id.Delete_button)
        val buttonEdit: ImageButton = view.findViewById(R.id.Edit_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.questions_recycler_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataSet[position]
        holder.buttonQuestion.text = data

        holder.buttonQuestion.setOnClickListener {
            clickListener.onQuestionClick(position)
        }

        holder.buttonDelete.setOnClickListener {
            clickListener.onDeleteClick(position)
        }

        holder.buttonEdit.setOnClickListener {
            clickListener.onEditClick(position)
        }
    }
    override fun getItemCount(): Int {
        return dataSet.size
    }
}
