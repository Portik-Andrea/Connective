package com.example.a3trackerapplication.ui.mytasks

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.a3trackerapplication.R
import com.example.a3trackerapplication.models.Group
import com.example.a3trackerapplication.models.NewTaskRequest
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

class NewTaskFragment : Fragment(),DatePickerDialog.OnDateSetListener {

    private val calendar = Calendar.getInstance()
    private var title: String = ""
    private var description: String = ""
    private var deadline : Long = 0
    private var assignedToUserId: Long = 0
    private var priority : String = ""
    private var groupId : Long = 0L
    private var users : List<User>? = null
    private var groups: List<Group>? = null

    private lateinit var userListViewModel: UserListViewModel
    private lateinit var newTaskViewModel: NewTaskViewModel
    private lateinit var groupViewModel: GroupViewModel

    private lateinit var createTaskButton: Button
    private lateinit var selectDateTextView : TextView
    private lateinit var taskNameEditText : EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var callBackNewTaskButton: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val usersFactory = UserListViewModelFactory(UserRepository())
        userListViewModel = ViewModelProvider(this, usersFactory)[UserListViewModel::class.java]
        val factory = NewTaskViewModelFactory(TaskRepository())
        newTaskViewModel = ViewModelProvider(this, factory)[NewTaskViewModel::class.java]
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_task, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewItems()
        userListViewModel.readUsers()
        userListViewModel.userList.observe(viewLifecycleOwner) {
            users = userListViewModel.userList.value!!.sortedBy { it.id }
            groupViewModel.getGroups()
            groupViewModel.myGroups.observe(viewLifecycleOwner){
                groups = groupViewModel.myGroups.value!!.sortedBy { it.groupId }
                setSpinners(view)
            }

        }

        createTaskButton.setOnClickListener {
            title= taskNameEditText.text.toString()
            description = descriptionEditText.text.toString()
            if(deadline < 1  || taskNameEditText.text.isEmpty() || descriptionEditText.text.isEmpty() || assignedToUserId<1 || priority=="" || groupId<1L ){
                Toast.makeText(
                    this.requireContext(),
                    "Task data is missing!",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                title= taskNameEditText.text.toString()
                description = descriptionEditText.text.toString()
                newTaskViewModel.createTask(NewTaskRequest(title,description,assignedToUserId,groupId,priority,deadline,TaskStatus.NEW.toString()))
            }
        }
        newTaskViewModel.createTaskResult.observe(viewLifecycleOwner) {
            if (it == "SUCCESS") {
                Toast.makeText(
                    this.requireContext(),
                    "The new task is successfully created!",
                    Toast.LENGTH_LONG
                ).show()
                taskNameEditText.setText("")
                descriptionEditText.setText("")
            }
            else{
                Toast.makeText(
                    this.requireContext(),
                    it.toString(),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        callBackNewTaskButton.setOnClickListener {
            findNavController().navigate(R.id.action_newTaskFragment_to_myTasksFragment)
        }
    }
    private fun initViewItems() {
        createTaskButton = requireView().findViewById(R.id.createTaskButton)
        taskNameEditText = requireView().findViewById(R.id.taskNameEditText)
        descriptionEditText = requireView().findViewById(R.id.descriptionEditText)
        callBackNewTaskButton = requireView().findViewById(R.id.callBackNewTaskButton)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setSpinners(view: View) {
        val projectType = groups?.map{ it.groupName}
        val spinner = view.findViewById<Spinner>(R.id.projectSpinner)
        if (spinner != null && projectType != null) {
            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, projectType)
            spinner.adapter = adapter
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
        val assigneeSpinner = view.findViewById<Spinner>(R.id.assigneeSpinner)
        if (assigneeSpinner != null && assignee != null) {
            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, assignee)
            assigneeSpinner.adapter = adapter

            assigneeSpinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    if(users != null){
                        assignedToUserId = users!![position].id
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

        // access the spinner
        val prioritySpinner = view.findViewById<Spinner>(R.id.prioritySpinner)
        if (prioritySpinner != null) {
            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, priorities)
            prioritySpinner.adapter = adapter

            prioritySpinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    priority = TaskPriorities.values()[position].toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        view.findViewById<CardView>(R.id.filter_deadline_card_view).setOnClickListener{
            DatePickerDialog(
                requireContext(),
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        calendar.set(year,month,dayOfMonth)
        displayFormattedDate(calendar.timeInMillis)
        deadline = calendar.timeInMillis
    }

    private fun displayFormattedDate(timeInMillis: Long) {
        val formatted = SimpleDateFormat("MMM. dd. yyyy.", Locale.US)
        selectDateTextView = requireView().findViewById(R.id.selectDateTextView)
        selectDateTextView.text = formatted.format(timeInMillis)
    }


}