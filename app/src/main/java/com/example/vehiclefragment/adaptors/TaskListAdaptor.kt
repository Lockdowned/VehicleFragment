package com.example.vehiclefragment.adaptors

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vehiclefragment.databinding.ItemForEditFragmentBinding
import com.example.vehiclefragment.db.entities.TaskItem
import com.example.vehiclefragment.viewmodels.TaskViewModel

class TaskListAdaptor(
        private val list: MutableList<TaskItem>,
        private val taskViewModel: TaskViewModel):
        RecyclerView.Adapter<TaskListAdaptor.TaskListHolder>() {

    private var context: Context? = null

    inner class TaskListHolder(private val taskItemBinding: ItemForEditFragmentBinding):
            RecyclerView.ViewHolder(taskItemBinding.root){

        fun bind(taskItem: TaskItem){
            taskItemBinding.cbFlagTask.isChecked = taskItem.checkBox
            taskItemBinding.tvTaskText.text = taskItem.taskText

            taskItemBinding.cbFlagTask.setOnCheckedChangeListener { _, isChecked ->
                taskItem.checkBox = isChecked
                taskViewModel.update(taskItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListHolder {
        context = parent.context
        val taskItemBinding = ItemForEditFragmentBinding.inflate(LayoutInflater.from(parent.context),
        parent, false)
        return TaskListHolder(taskItemBinding)
    }

    override fun onBindViewHolder(holder: TaskListHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnLongClickListener {
            deleteDialog(list[position])
            true
        }
    }

    private fun deleteDialog(taskItem: TaskItem){
        val dialog = AlertDialog.Builder(context)
        dialog.run{
            setMessage("Delete this task: ${taskItem.taskText} ?")
            setPositiveButton("Yes"){ _,_ ->
                taskViewModel.delete(taskItem.id!!)
            }
            setNegativeButton("No"){ _,_ -> }
        }
        val alertDialog = dialog.create()
        alertDialog.show()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

