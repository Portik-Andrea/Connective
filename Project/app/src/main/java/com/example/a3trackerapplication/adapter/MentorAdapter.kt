package com.example.a3trackerapplication.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a3trackerapplication.R
import com.example.a3trackerapplication.models.User

interface OnItemClickListener{
    fun onSelectClick(position: Int)
}

class MentorAdapter(
    private val list: List<User>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<MentorAdapter.DataViewHolder>() {
    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
        val mentorImageView: ImageView = itemView.findViewById(R.id.mentorImageView)
        val mentorNameTextView: TextView = itemView.findViewById(R.id.mentorNameTextView)
        val mentorDepartmentTextView: TextView = itemView.findViewById(R.id.mentorDepartmentTextView)
        val mentorEmailTextView: TextView = itemView.findViewById(R.id.mentorEmailTextView)
        val mentorPhoneTextView: TextView = itemView.findViewById(R.id.mentorPhoneTextView)
        val selectAMentorButton: Button = itemView.findViewById(R.id.selectAMentorButton)

        init{
            selectAMentorButton.setOnClickListener {
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.mentors_layout, parent, false)
        return DataViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val currentMentor = list[position]
        if(currentMentor.imageUrl!=null){
            var decodeBitmap = decodeBase64ToImage(currentMentor.imageUrl!!)
            holder.mentorImageView.setImageBitmap(decodeBitmap)
        }
        holder.mentorNameTextView.text = "${currentMentor.lastName} ${currentMentor.firstName}"
        holder.mentorDepartmentTextView.text = currentMentor.departmentName
        holder.mentorEmailTextView.text = currentMentor.email
        if(currentMentor.phoneNumber != null){
            holder.mentorPhoneTextView.text = currentMentor.phoneNumber
        } else{
            holder.mentorPhoneTextView.text =""
        }
    }
    override fun getItemCount() = list.size

    private fun decodeBase64ToImage(image: String): Bitmap {
        val decodeBytes = Base64.decode(image, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodeBytes,0,decodeBytes.size)
    }
}