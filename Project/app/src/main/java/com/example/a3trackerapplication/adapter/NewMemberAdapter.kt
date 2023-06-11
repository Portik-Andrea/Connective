package com.example.a3trackerapplication.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a3trackerapplication.R
import com.example.a3trackerapplication.models.User

interface OnUserClickListener{
    fun onSelectClick(position: Int)
}

class NewMemberAdapter(
    private val list: List<User>,
    private val listener: OnUserClickListener
) : RecyclerView.Adapter<NewMemberAdapter.DataViewHolder>() {
    // 1. user defined ViewHolder type
    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
        val userCircleImageView: ImageView = itemView.findViewById(R.id.userCircleImageView)
        val userNameTextView: TextView = itemView.findViewById(R.id.userNameTextView)
        val userDepartmentTextView: TextView = itemView.findViewById(R.id.userDepartmentTextView)
        private val addNewMemberButton: ImageButton = itemView.findViewById(R.id.addNewMemberButton)

        init{
            addNewMemberButton.setOnClickListener {
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
    // 2. Called only a few times = number of items on screen + a few more ones
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.search_user_layout, parent, false)
        return DataViewHolder(itemView)
    }
    // 3. Called many times, when we scroll the list
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val currentUser = list[position]
        if(currentUser.imageUrl!=null){
            var decodeBitmap = decodeBase64ToImage(currentUser.imageUrl!!)
            holder.userCircleImageView.setImageBitmap(decodeBitmap)
        }
        holder.userNameTextView.text = "${currentUser.lastName} ${currentUser.firstName}"
        holder.userDepartmentTextView.text = currentUser.departmentName

    }
    // 4.
    override fun getItemCount() = list.size

    private fun decodeBase64ToImage(image: String): Bitmap {
        val decodeBytes = Base64.decode(image, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodeBytes,0,decodeBytes.size)
    }
}