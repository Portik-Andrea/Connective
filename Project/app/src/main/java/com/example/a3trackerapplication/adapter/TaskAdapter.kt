package com.example.a3trackerapplication.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a3trackerapplication.R
import com.example.a3trackerapplication.models.Task
import com.example.a3trackerapplication.models.User
import java.text.SimpleDateFormat
import java.util.*



class TaskAdapter(
    private val list: List<Task>, private val users: List<User>
):  RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    var clickListener: ClickListener? = null

    // 1. user defined ViewHolder type
    inner class TaskViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val projectTextView: TextView = itemView.findViewById(R.id.projectTextView)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val createdByUserTextView : TextView = itemView.findViewById(R.id.createdByUserTextView)
        val createTimeTextView: TextView = itemView.findViewById(R.id.createdTimeTextView)
        val assigneeToUserTextView: TextView = itemView.findViewById(R.id.assigneeToUserTextView)
        val statusTextView: TextView = itemView.findViewById(R.id.statusTextView)
        val deadlineTextView: TextView = itemView.findViewById(R.id.deadlineTextView)
        val priorityTextView: TextView = itemView.findViewById(R.id.priorityTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
        // Constructor
        init{
            if(clickListener!= null){
                itemView.setOnClickListener( this )
            }

        }

        override fun onClick(p0: View?) {
            // Delegate event handling to ListFragment
            val position: Int = adapterPosition
            if( position != RecyclerView.NO_POSITION ) {
                clickListener?.onItemClick(position)
            }

        }
    }
    fun setOnItemClickListener(clickListener: ClickListener) {
        this.clickListener = clickListener
    }
    interface ClickListener{
        fun onItemClick(position: Int)
    }



    // 2. Called only a few times = number of items on screen + a few more ones
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        ++createCounter
        Log.i("XXX", "onCreateViewHolder $createCounter")
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return TaskViewHolder(itemView)
    }

    // 3. Called many times, when we scroll the list
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        ++bindCounter
        Log.i("XXX", "onBindViewHolder $bindCounter")
        val currentItem = list[position]
        setProjectType(currentItem.department_ID,holder.projectTextView)
        holder.titleTextView.text = currentItem.title
        holder.createdByUserTextView.text = searchUserName(currentItem.created_by_user_ID)
        holder.createTimeTextView.text = convertLongToTime(currentItem.created_time,"HH:mm a")
        holder.assigneeToUserTextView.text = searchUserName(currentItem.asigned_to_user_ID)
        holder.deadlineTextView.text = convertLongToTime(currentItem.deadline,"yyyy.MMMM.dd")
        holder.descriptionTextView.text = currentItem.description
        setStatus(currentItem.status, holder.statusTextView)
        setPriority(currentItem.priority,holder.priorityTextView)

    }

    // 4.
    override fun getItemCount(): Int {
        return list.size
    }

    companion object{
        var createCounter: Int = 0
        var bindCounter: Int = 0
    }

    private fun convertLongToTime(time: Long, simpleFormat: String): String {
        val date = Date(time)
        val format = SimpleDateFormat(simpleFormat)
        return format.format(date)
    }

    private fun searchUserName(id: Long):String{
        var name = ""
        users.forEach {
            if(it.id== id){
                name="${it.lastName} ${it.firstName}"
            }
        }

        return name
    }

    private fun setProjectType(departmentId: Int, projectTextView: TextView) {
        when(departmentId){
            1 -> projectTextView.text = "HR project"
            2 -> projectTextView.text = "Dev project"
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setStatus(status: Int, textView: TextView){
        when(status){
            0 -> {
                textView.text="New"
                textView.setTextColor(Color.parseColor("#FFFFFF"))
                textView.setBackgroundColor(Color.parseColor("#0000FF"))
                }
            1 -> {
                textView.text= "In Progress"
                textView.setTextColor(Color.parseColor("#FFFFFF"))
                textView.setBackgroundColor(Color.parseColor("#828282"))
                }
            2 -> {
                textView.text="Done"
                textView.setTextColor(Color.parseColor("#3BB143"))
            }
            else -> {
                textView.text="Blocked"
                textView.setBackgroundColor(Color.parseColor("#D30000"))
            }
        }
    }
    @SuppressLint("SetTextI18n")
    private fun setPriority(priority: Int, textView: TextView){
        when(priority){
            1 -> {
                textView.text="High prio"
                textView.setTextColor(Color.parseColor("#D30000"))
            }
            2 -> {
                textView.text= "Medium prio"
                textView.setTextColor(Color.parseColor("#ED7014"))
            }
            3 -> {
                textView.text="Low prio"
                textView.setTextColor(Color.parseColor("#3BB143"))
            }
            else -> {
                textView.text=""
            }
        }
    }

}

