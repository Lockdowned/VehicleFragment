package com.example.vehiclefragment.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.vehiclefragment.databinding.ItemForEditFragmentBinding
import com.example.vehiclefragment.db.entities.TaskItem
import com.example.vehiclefragment.viewmodels.TaskViewModel

class TaskListAdaptor(private val list: List<TaskItem>, private val taskViewModel: TaskViewModel):
ListAdapter<TaskItem, TaskListAdaptor.TaskListHolder>(TaskComparator()){

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
        val taskItemBinding = ItemForEditFragmentBinding.inflate(LayoutInflater.from(parent.context),
        parent, false)
        return TaskListHolder(taskItemBinding)
    }

    override fun onBindViewHolder(holder: TaskListHolder, position: Int) {
        holder.bind(list[position])
//        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }




    class TaskComparator: DiffUtil.ItemCallback<TaskItem>(){
        override fun areItemsTheSame(oldItem: TaskItem, newItem: TaskItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TaskItem, newItem: TaskItem): Boolean {
            return oldItem.checkBox == newItem.checkBox &&
                    oldItem.taskText == newItem.taskText
        }
    }

}

