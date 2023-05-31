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
import com.example.a3trackerapplication.MyApplication
import com.example.a3trackerapplication.R
import com.example.a3trackerapplication.TaskSelected
import com.example.a3trackerapplication.models.EditTaskRequest
import com.example.a3trackerapplication.models.Task
import com.example.a3trackerapplication.models.TaskPriorities
import com.example.a3trackerapplication.models.TaskStatus
import com.example.a3trackerapplication.models.User
import com.example.a3trackerapplication.repositories.TaskRepository
import com.example.a3trackerapplication.repositories.UserRepository
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

    private var taskId: Long? = null

    private var users : List<User>? = null
    private var createdUser: String = ""
    private var assignedUser: String = ""
    private var deadline : Long = 0
    private val calendar = Calendar.getInstance()

    private var editTaskRequest = EditTaskRequest(0L,"","",0L,TaskPriorities.LOW,0L,TaskStatus.BLOCKED)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = MyTasksViewModelFactory(TaskRepository())
        myTasksViewModel = ViewModelProvider(this, factory)[MyTasksViewModel::class.java]

        val userListFactory = UserListViewModelFactory(UserRepository())
        userListViewModel = ViewModelProvider(this, userListFactory)[UserListViewModel::class.java]

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_task, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.apply {
            initViewItems(view)
            userListViewModel.readUsers()
            userListViewModel.userList.observe(viewLifecycleOwner) {
                users = userListViewModel.userList.value!!
                Log.d("xxx", "GetMy user list  myTasks fragment $users")
                if(taskId!=null){
                    editTaskViewModel.getTask(taskId!!)
                    editTaskViewModel.selectTask.observe(viewLifecycleOwner){
                        currentItem = editTaskViewModel.selectTask.value!!
                        registerListeners()
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
                    Log.d("xxx", "GetMy setSpinners ${currentItem.title}")
                    editTaskRequest.title = taskNameUpdateEditText.text.toString()
                    editTaskRequest.description = descriptionUpdateEditText.text.toString()
                    editTaskViewModel.updateTask(editTaskRequest)
                }
            }
            editTaskViewModel.editTaskResult.observe(viewLifecycleOwner){
                Toast.makeText(
                    requireContext(),
                    "Update task",
                    Toast.LENGTH_SHORT
                ).show()
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
        setSpinners(requireView(),currentItem)
        descriptionUpdateEditText.setText(currentItem.description)
    }

    private fun setEditTaskRequest() {
        editTaskRequest.taskId=currentItem.taskId
        editTaskRequest.title = currentItem.title
        editTaskRequest.description = currentItem.description
        editTaskRequest.assignedToUserId = currentItem.assignedToUserId
        editTaskRequest.priority = currentItem.priority
        editTaskRequest.deadline = currentItem.deadline
        //editTaskRequest.departmentId = currentItem.department_ID
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
        Log.d("xxx", "GetMy setSpinners $currentTask")
        val projectType = resources.getStringArray(R.array.ProjectType)

        // access the spinner
        val spinner = view.findViewById<Spinner>(R.id.projectUpdateSpinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, projectType)
            spinner.adapter = adapter
            //spinner.setSelection(currentTask.department_ID-1)

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    //editTaskRequest.departmentId = position+1
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        val assignee = users?.map{ "${it.lastName} ${it.firstName}"}
        // access the spinner
        val assigneeSpinner = view.findViewById<Spinner>(R.id.assigneeUpdateSpinner)
        if (assigneeSpinner != null && assignee != null) {
            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, assignee)
            assigneeSpinner.adapter = adapter
            val assignedId = currentTask.assignedToUserId
            /*if(assignedId>4){
                assigneeSpinner.setSelection(currentTask.assignedToUserId.toInt()-3)
            }else{
                assigneeSpinner.setSelection(currentTask.assignedToUserId.toInt()-1)
            }*/
            assigneeSpinner.setSelection(currentTask.assignedToUserId.toInt())
            Log.d("xxx", "GetMy setSpinners user id ${currentTask.assignedToUserId.toInt()}")
            Log.d("xxx", "GetMy setSpinners user name ${spinner.selectedItemPosition}")

            assigneeSpinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
//                    Toast.makeText(this@MainActivity,
//                        getString(R.string.selected_item) + " " +
//                                "" + languages[position], Toast.LENGTH_SHORT).show()
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

        // access the spinner
        val prioritySpinner = view.findViewById<Spinner>(R.id.priorityUpdateSpinner)
        if (prioritySpinner != null) {
            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, priorities)
            prioritySpinner.adapter = adapter
            prioritySpinner.setSelection(currentItem.priority.ordinal)

            prioritySpinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    editTaskRequest.priority =TaskPriorities.values()[position]// position+1
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