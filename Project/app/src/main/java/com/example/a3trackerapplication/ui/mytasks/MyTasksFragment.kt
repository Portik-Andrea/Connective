package com.example.a3trackerapplication.ui.mytasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a3trackerapplication.MyApplication
import com.example.a3trackerapplication.R
import com.example.a3trackerapplication.adapter.OnTaskClickListener
import com.example.a3trackerapplication.adapter.TaskAdapter
import com.example.a3trackerapplication.models.Task
import com.example.a3trackerapplication.models.TaskStatus
import com.example.a3trackerapplication.repositories.TaskRepository
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class MyTasksFragment : Fragment() , OnTaskClickListener {
    private lateinit var editTaskViewModel: EditTaskViewModel
    private lateinit var myTasksViewModel: MyTasksViewModel
    private lateinit var list: List<Task>

    private lateinit var addNewTaskButton: ImageButton

    private val selectedChips: MutableSet<Int> = HashSet()
    private lateinit var chipGroup: ChipGroup
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = MyTasksViewModelFactory(TaskRepository())
        myTasksViewModel = ViewModelProvider(this, factory)[MyTasksViewModel::class.java]

        val editTaskFactory = EditTaskViewModelFactory(TaskRepository())
        editTaskViewModel = ViewModelProvider(this, editTaskFactory)[EditTaskViewModel::class.java]
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
            chipGroup = findViewById(R.id.chipGroup)
            recyclerView = findViewById(R.id.recycler_view)
            addNewTaskButton = requireView().findViewById(R.id.addNewTaskButton)
            if(MyApplication.userType =="MENTOR"){
                addNewTaskButton.visibility = View.VISIBLE
            }
            myTasksViewModel.getTasks()
            myTasksViewModel.myTasks.observe(viewLifecycleOwner) {
                list = myTasksViewModel.myTasks.value!!.sorted()
                recyclerView.adapter = TaskAdapter(list,this@MyTasksFragment)
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.setHasFixedSize(true)

                chipGroup.setOnCheckedStateChangeListener  { group, _ ->
                    selectedChips.clear()
                    for (i in 0 until group.childCount) {
                        val chip = group.getChildAt(i) as Chip
                        if (chip.isChecked) {
                            selectedChips.add(chip.id)
                        }
                    }
                    val filteredList: List<Task> = if (selectedChips.isEmpty()) {
                        list
                    } else {
                        list.filter { task ->
                            when {
                                selectedChips.contains(R.id.chip_1) && task.status == TaskStatus.NEW -> true
                                selectedChips.contains(R.id.chip_2) && task.status == TaskStatus.IN_PROGRESS -> true
                                selectedChips.contains(R.id.chip_3) && task.status==TaskStatus.DONE -> true
                                else -> false
                            }
                        }
                    }
                    recyclerView.adapter = TaskAdapter(filteredList,this@MyTasksFragment)
                    recyclerView.layoutManager = LinearLayoutManager(context)
                    recyclerView.setHasFixedSize(true)
                }
            }
            registerListeners()
        }
    }

    override fun onTaskClick(position: Int) {
        val clickedItem : Task = list[position]
        val bundle = bundleOf(
            "taskId" to clickedItem.taskId
        )
        findNavController().navigate(R.id.action_myTasksFragment_to_taskDescriptionFragment,bundle)
    }

    private fun registerListeners() {
        addNewTaskButton.setOnClickListener{
            findNavController().navigate(R.id.action_myTasksFragment_to_newTaskFragment)
        }
    }

}

