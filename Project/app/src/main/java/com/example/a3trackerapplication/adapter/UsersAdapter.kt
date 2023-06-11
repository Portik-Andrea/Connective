package com.example.a3trackerapplication.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a3trackerapplication.R
import com.example.a3trackerapplication.models.User


class UserAdapter(private val list: List<User>) : RecyclerView.Adapter<UserAdapter.DataViewHolder>() {
    // 1. user defined ViewHolder type
    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userCircleImageView: ImageView = itemView.findViewById(R.id.userCircleImageView)
        val userNameTextView: TextView = itemView.findViewById(R.id.userNameTextView)
        val userDepartmentTextView: TextView = itemView.findViewById(R.id.userDepartmentTextView)
    }
    // 2. Called only a few times = number of items on screen + a few more ones
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_layout, parent, false)
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