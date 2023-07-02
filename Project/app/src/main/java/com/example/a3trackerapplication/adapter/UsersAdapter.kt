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
    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userCircleImageView: ImageView = itemView.findViewById(R.id.userCircleImageView)
        val userNameTextView: TextView = itemView.findViewById(R.id.userNameTextView)
        val userDepartmentTextView: TextView = itemView.findViewById(R.id.userDepartmentTextView)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_layout, parent, false)
        return DataViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val currentUser = list[position]
        if(currentUser.imageUrl!=null){
            var decodeBitmap = decodeBase64ToImage(currentUser.imageUrl!!)
            holder.userCircleImageView.setImageBitmap(decodeBitmap)
        }
        holder.userNameTextView.text = "${currentUser.lastName} ${currentUser.firstName}"
        holder.userDepartmentTextView.text = currentUser.departmentName
    }
    override fun getItemCount() = list.size

    private fun decodeBase64ToImage(image: String): Bitmap {
        val decodeBytes = Base64.decode(image, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodeBytes,0,decodeBytes.size)
    }
}