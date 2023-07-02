package com.example.a3trackerapplication.ui.mytasks

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.a3trackerapplication.MyApplication
import com.example.a3trackerapplication.R
import com.example.a3trackerapplication.TaskSelected
import com.example.a3trackerapplication.models.EditTaskRequest
import com.example.a3trackerapplication.models.Task
import com.example.a3trackerapplication.models.TaskPriorities
import com.example.a3trackerapplication.models.TaskStatus
import com.example.a3trackerapplication.models.UserType
import com.example.a3trackerapplication.repositories.TaskRepository
import java.text.SimpleDateFormat
import java.util.*

class TaskDescriptionFragment : Fragment() {
    private lateinit var currentItem:Task
    private lateinit var projectTextView: TextView
    private lateinit var titleTextView: TextView
    private lateinit var createdByUserTextView : TextView
    private lateinit var createTimeTextView: TextView
    private lateinit var assigneeToUserTextView: TextView
    private lateinit var statusSpinner: Spinner
    private lateinit var deadlineTextView: TextView
    private lateinit var priorityTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var callBackTaskDescButton: ImageButton
    private lateinit var editImageButton: ImageButton

    private lateinit var myTasksViewModel: MyTasksViewModel
    private lateinit var editTaskViewModel: EditTaskViewModel

    private var taskId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = MyTasksViewModelFactory(TaskRepository())
        myTasksViewModel = ViewModelProvider(this, factory)[MyTasksViewModel::class.java]

        val editTaskFactory = EditTaskViewModelFactory(TaskRepository())
        editTaskViewModel = ViewModelProvider(this, editTaskFactory)[EditTaskViewModel::class.java]

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(!shouldInterceptBackPress()){
                    isEnabled = false
                    activity?.onBackPressed()
                }
            }
        })
    }
    fun shouldInterceptBackPress()=true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            if (it["taskId"] != null) {
                taskId = it.getLong("taskId")
            }
        }
        return inflater.inflate(R.layout.fragment_task_description, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.apply{
            initViewItems(view)
            if(taskId!=null){
                editTaskViewModel.getTask(taskId!!)
                editTaskViewModel.selectTask.observe(viewLifecycleOwner){
                    currentItem = editTaskViewModel.selectTask.value!!
                    registerListeners()
                }
                if(MyApplication.userType == UserType.MENTOR.name){
                    editImageButton.visibility = View.VISIBLE
                }
            }
            callBackTaskDescButton.setOnClickListener{
                findNavController().navigate(R.id.action_taskDescriptionFragment_to_myTasksFragment)
            }
            editImageButton.setOnClickListener {
                val bundle = bundleOf(
                    "taskId" to taskId
                )
                findNavController().navigate(R.id.action_taskDescriptionFragment_to_editTaskFragment,bundle)
            }

        }
    }

    @SuppressLint("SetTextI18n")
    private fun registerListeners() {
        titleTextView.text = currentItem.title
        projectTextView.text = currentItem.groupName+" project"
        val createTime = convertLongToTime(currentItem.createdTime,"HH:mm MMMM dd yyyy")
        createdByUserTextView.text = "${currentItem.creatorUserName} $createTime"
        createTimeTextView.text = createTime
        assigneeToUserTextView.text = currentItem.assignedToUserName
        deadlineTextView.text = convertLongToTime(currentItem.deadline,"yyyy.MMMM.dd")
        descriptionTextView.text = currentItem.description
        setPriority(currentItem.priority,priorityTextView)
        setSpinners(requireView(),currentItem.status)
    }

    private fun initViewItems(itemView: View) {
        projectTextView = itemView.findViewById(R.id.projectType)
        titleTextView = itemView.findViewById(R.id.titleDesc)
        createdByUserTextView = itemView.findViewById(R.id.createdByUserTime)
        createTimeTextView = itemView.findViewById(R.id.createdByDate)
        assigneeToUserTextView = itemView.findViewById(R.id.assigneeUserDesc)
        statusSpinner = itemView.findViewById(R.id.statusSpinner)
        deadlineTextView = itemView.findViewById(R.id.deadlineDateDesc)
        priorityTextView = itemView.findViewById(R.id.priorityValueDesc)
        descriptionTextView = itemView.findViewById(R.id.descriptionDesc)
        progressBar = itemView.findViewById(R.id.progressBarDesc)
        callBackTaskDescButton = itemView.findViewById(R.id.callBackTaskDescButton)
        editImageButton = itemView.findViewById(R.id.editImageButton)
    }

    private fun convertLongToTime(time: Long, simpleFormat: String): String {
        val date = Date(time)
        val format = SimpleDateFormat(simpleFormat)
        return format.format(date)
    }
    @SuppressLint("SetTextI18n")
    private fun setPriority(priority: TaskPriorities, textView: TextView){
        when(priority){
            TaskPriorities.HIGH -> {
                textView.text="High prio"
                textView.setTextColor(Color.parseColor("#D30000"))
            }
            TaskPriorities.MEDIUM -> {
                textView.text= "Medium prio"
                textView.setTextColor(Color.parseColor("#ED7014"))
            }
            TaskPriorities.LOW -> {
                textView.text="Low prio"
                textView.setTextColor(Color.parseColor("#3BB143"))
            }
        }
    }

    private fun setSpinners(view: View, itemStatus: TaskStatus) {
        val status = resources.getStringArray(R.array.Status)

        // access the spinner
        val spinner = view.findViewById<Spinner>(R.id.statusSpinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, status)
            spinner.adapter = adapter
            spinner.setSelection(itemStatus.ordinal)

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    if(position!=itemStatus.ordinal && position>0){
                        var editTaskRequest = EditTaskRequest(currentItem.taskId,
                            currentItem.title,
                            currentItem.description,
                            currentItem.assignedToUserId,
                            currentItem.priority,
                            currentItem.deadline,
                            currentItem.groupId,
                            TaskStatus.values()[position]
                        )
                        editTaskViewModel.updateTask(editTaskRequest)
                    }
                    editTaskViewModel.editTaskResult.observe(viewLifecycleOwner) {
                        if(it == "Update is successful!"){
                            Toast.makeText(
                                requireContext(),
                                "Update task status",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else{
                            Toast.makeText(
                                requireContext(),
                                it.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        }
    }
}