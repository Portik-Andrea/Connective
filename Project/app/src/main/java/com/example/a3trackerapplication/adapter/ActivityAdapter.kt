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
import com.example.a3trackerapplication.models.ActivityModel
import java.text.SimpleDateFormat
import java.util.Date

class ActivityAdapter : RecyclerView.Adapter<ActivityAdapter.DataAdapterViewHolder>() {

    private val adapterData = mutableListOf<ActivityModel>()

    //--------onCreateViewHolder: inflate layout with view holder-------
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdapterViewHolder {

        val layout = when (viewType) {
            TYPE_TASK -> R.layout.activities_task_layout
            TYPE_GROUP -> R.layout.activities_group_layout
            TYPE_USER -> R.layout.activities_user_layout
            else -> throw IllegalArgumentException("Invalid view type")
        }

        val view = LayoutInflater
            .from(parent.context)
            .inflate(layout, parent, false)

        return DataAdapterViewHolder(view)
    }


    //-----------onBindViewHolder: bind view with data model---------
    override fun onBindViewHolder(holder: DataAdapterViewHolder, position: Int) {
        holder.bind(adapterData[position])
    }

    override fun getItemCount(): Int = adapterData.size

    override fun getItemViewType(position: Int): Int {
        return when (adapterData[position]) {
            is ActivityModel.TaskData -> TYPE_TASK
            is ActivityModel.GroupData -> TYPE_GROUP
            is ActivityModel.UserData -> TYPE_USER
            else -> throw IllegalArgumentException("Unknown item type at position: $position")
        }
    }

    fun setData(data: List<ActivityModel>) {
        adapterData.apply {
            clear()
            addAll(data)
        }
    }

    companion object {
        private const val TYPE_TASK = 0
        private const val TYPE_GROUP = 1
        private const val TYPE_USER = 2
    }

    class DataAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private fun bindTask(item: ActivityModel.TaskData) {
            //Do your view assignment here from the data model
            if(item.creatorUserImage!= null) {
                val decodeBitmap = decodeBase64ToImage(item.creatorUserImage)
                itemView.findViewById<ImageView>(R.id.creatorCircleImageView)
                    .setImageBitmap(decodeBitmap)
            }
            itemView.findViewById<TextView>(R.id.creatorNameTextView)?.text = item.creatorUserName
            itemView.findViewById<TextView>(R.id.createTimeTextView)?.text = convertLongToTime(item.createdTime,"MMMM.dd")
            itemView.findViewById<TextView>(R.id.activityTaskMessageTextView)?.text = item.creatorUserName+" assigned a task to you:"
            itemView.findViewById<TextView>(R.id.taskGroupTextView)?.text = item.groupName
            itemView.findViewById<TextView>(R.id.taskTitleTextView)?.text = item.title
            itemView.findViewById<TextView>(R.id.creationDataTextView)?.text = item.creatorUserName+" "+convertLongToTime(item.createdTime,"HH:mm a")
            itemView.findViewById<TextView>(R.id.taskAssigneeUserTextView)?.text = item.assignedToUserName
        }

        private fun bindGroup(item: ActivityModel.GroupData) {
            //Do your view assignment here from the data model
            if(item.invitingUserImage!= null) {
                val decodeBitmap = decodeBase64ToImage(item.invitingUserImage)
                itemView.findViewById<ImageView>(R.id.mentorCircleImageView)
                    .setImageBitmap(decodeBitmap)
            }
            itemView.findViewById<TextView>(R.id.mentorUserNameTextView)?.text = item.invitingUserName
            itemView.findViewById<TextView>(R.id.joinDateTextView)?.text = convertLongToTime(item.joiningDate,"MMMM.dd")
            itemView.findViewById<TextView>(R.id.activityUserMessageTextView)?.text = item.invitingUserName+" added you to "+item.groupName+" group"
        }

        private fun bindColleague(item: ActivityModel.UserData) {
            //Do your view assignment here from the data model
            itemView.findViewById<TextView>(R.id.mentorUserNameTextView)?.text = item.mentorName
            itemView.findViewById<TextView>(R.id.activityUserMessageTextView)?.text = "New joiner: "+item.name
        }


        fun bind(dataModel: ActivityModel) {
            when (dataModel) {
                is ActivityModel.TaskData -> bindTask(dataModel)
                is ActivityModel.GroupData -> bindGroup(dataModel)
                is ActivityModel.UserData -> bindColleague(dataModel)
                else -> {}
            }
        }

        private fun convertLongToTime(time: Long, simpleFormat: String): String {
            val date = Date(time)
            val format = SimpleDateFormat(simpleFormat)
            return format.format(date)
        }

        private fun decodeBase64ToImage(image: String): Bitmap {
            val decodeBytes = Base64.decode(image, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(decodeBytes,0,decodeBytes.size)
        }
    }
}