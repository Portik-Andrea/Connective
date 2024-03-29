package com.example.a3trackerapplication.ui.mytasks

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.a3trackerapplication.R
import com.example.a3trackerapplication.models.EditTaskRequest
import com.example.a3trackerapplication.models.Group
import com.example.a3trackerapplication.models.Task
import com.example.a3trackerapplication.models.TaskPriorities
import com.example.a3trackerapplication.models.TaskStatus
import com.example.a3trackerapplication.models.User
import com.example.a3trackerapplication.repositories.GroupRepository
import com.example.a3trackerapplication.repositories.TaskRepository
import com.example.a3trackerapplication.repositories.UserRepository
import com.example.a3trackerapplication.ui.mygroups.GroupViewModel
import com.example.a3trackerapplication.ui.mygroups.GroupViewModelFactory
import com.example.a3trackerapplication.util.UserListViewModel
import com.example.a3trackerapplication.util.UserListViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class EditTaskFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var currentItem: Task

    private lateinit var updateTaskButton: Button
    private lateinit var selectDateUpdateTextView : TextView
    private lateinit var taskNameUpdateEditText : EditText
    private lateinit var descriptionUpdateEditText: EditText
    private lateinit var callBackUpdateTaskButton: ImageButton

    private lateinit var myTasksViewModel: MyTasksViewModel
    private lateinit var userListViewModel: UserListViewModel
    private lateinit var editTaskViewModel: EditTaskViewModel
    private lateinit var groupViewModel: GroupViewModel

    private var taskId: Long? = null

    private var groups: List<Group>? = null
    private var users : List<User>? = null
    private var createdUser: String = ""
    private var assignedUser: String = ""
    private var groupId : Long = 0L
    private var deadline : Long = 0
    private val calendar = Calendar.getInstance()

    private var editTaskRequest = EditTaskRequest(0L,"","",0L,TaskPriorities.LOW,0L,0L,TaskStatus.BLOCKED)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = MyTasksViewModelFactory(TaskRepository())
        myTasksViewModel = ViewModelProvider(this, factory)[MyTasksViewModel::class.java]

        val userListFactory = UserListViewModelFactory(UserRepository())
        userListViewModel = ViewModelProvider(this, userListFactory)[UserListViewModel::class.java]

        val editTaskFactory = EditTaskViewModelFactory(TaskRepository())
        editTaskViewModel = ViewModelProvider(this, editTaskFactory)[EditTaskViewModel::class.java]

        val groupFactory = GroupViewModelFactory(GroupRepository())
        groupViewModel = ViewModelProvider(this, groupFactory)[GroupViewModel::class.java]

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
        return inflater.inflate(R.layout.fragment_edit_task, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.apply {
            initViewItems(view)
            userListViewModel.readUsers()
            userListViewModel.userList.observe(viewLifecycleOwner) {
                users = userListViewModel.userList.value!!.sortedBy { it.id }
                groupViewModel.getGroups()
                groupViewModel.myGroups.observe(viewLifecycleOwner) {
                    groups = groupViewModel.myGroups.value!!.sortedBy { it.groupId }

                    if (taskId != null) {
                        editTaskViewModel.getTask(taskId!!)
                        editTaskViewModel.selectTask.observe(viewLifecycleOwner) {
                            currentItem = editTaskViewModel.selectTask.value!!
                            registerListeners()
                            setSpinners(requireView(),currentItem)
                        }
                    }
                }

            }
            updateTaskButton.setOnClickListener {
                if(editTaskRequest.taskId == 0L){
                    Toast.makeText(requireContext(),
                        "There is no such task!",
                        Toast.LENGTH_LONG
                    ).show()
                }else{
                    editTaskRequest.title = taskNameUpdateEditText.text.toString()
                    editTaskRequest.description = descriptionUpdateEditText.text.toString()
                    editTaskViewModel.updateTask(editTaskRequest)
                }
            }
            editTaskViewModel.editTaskResult.observe(viewLifecycleOwner){
                if(it == "Update is successful!"){
                    Toast.makeText(
                        requireContext(),
                        "Update task",
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
            callBackUpdateTaskButton.setOnClickListener {
                val bundle = bundleOf(
                    "taskId" to taskId
                )
                findNavController().navigate(R.id.action_editTaskFragment_to_taskDescriptionFragment,bundle)
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun registerListeners() {
        setEditTaskRequest()
        taskNameUpdateEditText.setText(currentItem.title)
        selectDateUpdateTextView.text = convertLongToTime(currentItem.deadline,"MMM. dd. yyyy.")
        createdUser = currentItem.creatorUserName
        assignedUser = currentItem.assignedToUserName
        descriptionUpdateEditText.setText(currentItem.description)
    }

    private fun setEditTaskRequest() {
        editTaskRequest.taskId=currentItem.taskId
        editTaskRequest.title = currentItem.title
        editTaskRequest.description = currentItem.description
        editTaskRequest.assignedToUserId = currentItem.assignedToUserId
        editTaskRequest.priority = currentItem.priority
        editTaskRequest.deadline = currentItem.deadline
        editTaskRequest.groupId = currentItem.groupId
        editTaskRequest.status = currentItem.status
    }

    private fun initViewItems(view: View) {
        updateTaskButton = view.findViewById(R.id.updateTaskButton)
        selectDateUpdateTextView = view.findViewById(R.id.selectDateUpdateTextView)
        taskNameUpdateEditText = view.findViewById(R.id.taskNameUpdateEditText)
        descriptionUpdateEditText = view.findViewById(R.id.descriptionUpdateEditText)
        callBackUpdateTaskButton = view.findViewById(R.id.callBackUpdateTaskButton)
    }

    private fun convertLongToTime(time: Long, simpleFormat: String): String {
        val date = Date(time)
        val format = SimpleDateFormat(simpleFormat)
        return format.format(date)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setSpinners(view: View, currentTask: Task) {
        val projectType = groups?.map{ it.groupName }
        val spinner = view.findViewById<Spinner>(R.id.projectUpdateSpinner)
        if (spinner != null && projectType != null) {
            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, projectType)
            spinner.adapter = adapter
            spinner.setSelection(currentTask.groupId.toInt()-1)
            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    if(groups != null){
                        groupId = groups!![position].groupId
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }else {
            Toast.makeText(
                this.requireContext(),
                "The projects could not be loaded!",
                Toast.LENGTH_LONG
            ).show()
        }

        val assignee = users?.map{ "${it.firstName} ${it.lastName}"}
        val assigneeSpinner = view.findViewById<Spinner>(R.id.assigneeUpdateSpinner)
        if (assigneeSpinner != null && assignee != null) {
            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, assignee)
            assigneeSpinner.adapter = adapter
            assigneeSpinner.setSelection(currentTask.assignedToUserId.toInt()-1)
            assigneeSpinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    if(users != null){
                        editTaskRequest.assignedToUserId = users!![position].id
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        } else {
            Toast.makeText(
                this.requireContext(),
                "The users could not be loaded!",
                Toast.LENGTH_LONG
            ).show()
        }
        val priorities = resources.getStringArray(R.array.Priority)
        val prioritySpinner = view.findViewById<Spinner>(R.id.priorityUpdateSpinner)
        if (prioritySpinner != null) {
            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, priorities)
            prioritySpinner.adapter = adapter
            prioritySpinner.setSelection(currentTask.priority.ordinal)
            prioritySpinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    editTaskRequest.priority = TaskPriorities.values()[position]
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        view.findViewById<CardView>(R.id.filter_deadline_update_card_view).setOnClickListener{
            DatePickerDialog(
                requireContext(),
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

    }

    private fun stringConvert(text : String): String{
        val newStr: StringBuffer = StringBuffer(text)
        for (i in text.indices) {
            if (Character.isLowerCase(text[i])) {
                newStr.setCharAt(i, Character.toUpperCase(text[i]))
            } else if (Character.isUpperCase(text[i])) {
                newStr.setCharAt(i, Character.toLowerCase(text[i]))
            }
        }
        return newStr.toString()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        Log.e("Calendar"," $year $month $dayOfMonth")
        calendar.set(year,month,dayOfMonth)
        displayFormattedDate(calendar.timeInMillis)
        deadline = calendar.timeInMillis
        editTaskRequest.deadline = calendar.timeInMillis
    }

    private fun displayFormattedDate(timeInMillis: Long) {
        val formatted = SimpleDateFormat("MMM. dd. yyyy.", Locale.US)
        selectDateUpdateTextView.text = formatted.format(timeInMillis)
    }

}