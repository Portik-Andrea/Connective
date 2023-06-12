package com.example.a3trackerapplication.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a3trackerapplication.R
import com.example.a3trackerapplication.adapter.ActivityAdapter
import com.example.a3trackerapplication.models.ActivityModel
import com.example.a3trackerapplication.repositories.GroupRepository
import com.example.a3trackerapplication.repositories.TaskRepository
import com.example.a3trackerapplication.repositories.UserRepository
import com.example.a3trackerapplication.ui.mygroups.GroupViewModel
import com.example.a3trackerapplication.ui.mygroups.GroupViewModelFactory
import com.example.a3trackerapplication.ui.mytasks.MyTasksViewModel
import com.example.a3trackerapplication.ui.mytasks.MyTasksViewModelFactory
import com.example.a3trackerapplication.util.UserListViewModel
import com.example.a3trackerapplication.util.UserListViewModelFactory

class ActivitiesFragment : Fragment() {
    private lateinit var myTasksViewModel: MyTasksViewModel
    private lateinit var groupViewModel: GroupViewModel
    private lateinit var activitiesViewModel: ActivitiesViewModel

    private val dataAdapter: ActivityAdapter by lazy {
        ActivityAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ActivitiesViewModelFactory(UserRepository())
        activitiesViewModel = ViewModelProvider(this, factory)[ActivitiesViewModel::class.java]
        val taskFactory = MyTasksViewModelFactory(TaskRepository())
        myTasksViewModel = ViewModelProvider(this, taskFactory)[MyTasksViewModel::class.java]
        val groupFactory = GroupViewModelFactory(GroupRepository())
        groupViewModel = ViewModelProvider(this, groupFactory)[GroupViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_activities, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*activitiesViewModel.getUserActivities()
        activitiesViewModel.users.observe(viewLifecycleOwner) {
            val users = activitiesViewModel.users.value
            /*myTasksViewModel.getTaskActivities()
            myTasksViewModel.tasks.observe(viewLifecycleOwner){
                val tasks = myTasksViewModel.tasks.value

                groupViewModel.getGroupActivities()
                groupViewModel.groups.observe(viewLifecycleOwner){
                    val groups = groupViewModel.groups.value*/
                    val list: List<ActivityModel> = ArrayList<ActivityModel>()
                    //list.plus(tasks)
                    //list.plus(groups)
                    list.plus(users)
                    Log.d("xxx", "GetActivities list: $list")
                    Log.d("xxx", "GetActivities users: $users")
                    //Log.d("xxx", "GetActivities tasks: $tasks")
                    //Log.d("xxx", "GetActivities groups: $groups")
                    dataAdapter.setData(list)

                    view.findViewById<RecyclerView>(R.id.activitiesRecyclerView)
                        .apply {
                            layoutManager = LinearLayoutManager(context)
                            hasFixedSize()
                            this.adapter = dataAdapter
                        }
               /* }

            }*/

        }*/
        myTasksViewModel.getTaskActivities()
        myTasksViewModel.tasks.observe(viewLifecycleOwner){
            val tasks = myTasksViewModel.tasks.value

            groupViewModel.getGroupActivities()
            groupViewModel.groups.observe(viewLifecycleOwner){
                val groups = groupViewModel.groups.value
                val list: List<ActivityModel> = concatenate(tasks!!,groups!!)

                //list.plus(tasks)
                //list.plus(groups)
                //list.plus(users)
                Log.d("xxx", "GetActivities list: $list")
                //Log.d("xxx", "GetActivities users: $users")
                //Log.d("xxx", "GetActivities tasks: $tasks")
                //Log.d("xxx", "GetActivities groups: $groups")
                dataAdapter.setData(list)

                view.findViewById<RecyclerView>(R.id.activitiesRecyclerView)
                    .apply {
                        layoutManager = LinearLayoutManager(context)
                        hasFixedSize()
                        this.adapter = dataAdapter
                    }
            }

        }
    }
    private fun  concatenate(vararg lists: List<ActivityModel>): List<ActivityModel> {
        return listOf(*lists).flatten()
    }

}