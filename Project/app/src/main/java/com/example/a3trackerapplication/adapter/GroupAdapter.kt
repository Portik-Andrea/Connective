package com.example.a3trackerapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a3trackerapplication.R
import com.example.a3trackerapplication.models.Group

interface OnGroupClickListener{
    fun onSelectClick(position: Int)
}

class GroupAdapter(
    private val list: List<Group>,
    private val listener: OnGroupClickListener
) : RecyclerView.Adapter<GroupAdapter.DataViewHolder>()  {

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val groupNameTextView: TextView = itemView.findViewById(R.id.groupNameTextView)
        private val membersImageButton: ImageButton = itemView.findViewById(R.id.membersImageButton)

        init{
            membersImageButton.setOnClickListener {
                listener.onSelectClick(adapterPosition)
            }
        }

        override  fun onClick(p0: View?){
            val position: Int = adapterPosition
            if( position != RecyclerView.NO_POSITION) {
                listener.onSelectClick(position)
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GroupAdapter.DataViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.groups_layout, parent, false)
        return DataViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val currentGroup = list[position]
        holder.groupNameTextView.text = "${currentGroup.groupName} group"
    }

    override fun getItemCount(): Int =list.size



}