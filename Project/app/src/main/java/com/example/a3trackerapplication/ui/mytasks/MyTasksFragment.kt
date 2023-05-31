package com.example.a3trackerapplication.ui.mytasks

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a3trackerapplication.R
import com.example.a3trackerapplication.adapter.OnTaskClickListener
import com.example.a3trackerapplication.adapter.TaskAdapter
import com.example.a3trackerapplication.models.Task
import com.example.a3trackerapplication.models.User
import com.example.a3trackerapplication.repositories.TaskRepository
import com.example.a3trackerapplication.repositories.UserRepository
import com.example.a3trackerapplication.util.UserListViewModel
import com.example.a3trackerapplication.util.UserListViewModelFactory

class MyTasksFragment : Fragment() , OnTaskClickListener {
    private lateinit var editTaskViewModel: EditTaskViewModel
    private lateinit var myTasksViewModel: MyTasksViewModel
    private lateinit var list: List<Task>
    private var users : List<User>? = null
    private lateinit var adapter: TaskAdapter

    private lateinit var addNewTaskButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = MyTasksViewModelFactory(TaskRepository())
        myTasksViewModel = ViewModelProvider(this, factory).get(MyTasksViewModel::class.java)

        val editTaskFactory = EditTaskViewModelFactory(TaskRepository())
        editTaskViewModel = ViewModelProvider(this, editTaskFactory).get(EditTaskViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        val taskType = resources.getStringArray(R.array.tasks_type)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_item,taskType)
        requireView().findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView).setAdapter(arrayAdapter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.apply {
            myTasksViewModel.getTasks()
            myTasksViewModel.myTasks.observe(viewLifecycleOwner) {
                list = myTasksViewModel.myTasks.value!!
                Log.i("xxx", "GetMy my tasks list in MyTasks fragment " + list.toString())
                val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
                recyclerView.adapter = TaskAdapter(list,this@MyTasksFragment)
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.setHasFixedSize(true)


                /*val manager = LinearLayoutManager(requireContext())
                recyclerView.setLayoutManager(manager)
                recyclerView.setHasFixedSize(true)
                // 1. No event handling
                // recycler_view.adapter = DataAdapter(list)
                // 2. Event handling - pass fragment (this) to data adapter
                if(list!=null && users!=null){
                    adapter = TaskAdapter(list, users!!)
                    adapter.setOnItemClickListener(object  : TaskAdapter.ClickListener{
                        override fun onItemClick(position: Int){
                            MyApplication.taskPosition = position
                            val prefs1 = requireActivity().getPreferences(Context.MODE_PRIVATE)
                            val edit = prefs1.edit()
                            edit.putInt("taskPosition", MyApplication.taskPosition)
                            TaskSelected.created_by_user_ID = list[position].createdByUserId
                            TaskSelected.assigned_to_user_ID = list[position].assignedToUserId
                            Log.d("xxx", "GetMy task1 position in description ${MyApplication.taskPosition}")
                            findNavController().navigate(R.id.action_myTasksFragment_to_taskDescriptionFragment)
                        }

                    })
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(this.context)
                    recyclerView.setHasFixedSize(true)*/
                }
                registerListeners()
            }
        }

    override fun onTaskClick(position: Int) {
        var clickedItem : Task = list[position]
        val bundle = bundleOf(
            "taskId" to clickedItem.taskId
        )
        findNavController().navigate(R.id.action_myTasksFragment_to_taskDescriptionFragment,bundle)
    }


    private fun registerListeners() {
        addNewTaskButton = requireView().findViewById(R.id.addNewTaskButton)

        addNewTaskButton.setOnClickListener{
            findNavController().navigate(R.id.action_myTasksFragment_to_newTaskFragment)
        }
    }

}

