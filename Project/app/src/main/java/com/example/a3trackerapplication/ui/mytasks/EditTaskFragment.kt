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
import com.example.a3trackerapplication.MyApplication
import com.example.a3trackerapplication.R
import com.example.a3trackerapplication.TaskSelected
import com.example.a3trackerapplication.models.EditTaskRequest
import com.example.a3trackerapplication.models.Task
import com.example.a3trackerapplication.models.User
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

    private var users : List<User>? = null
    private var createdUser: String = ""
    private var assignedUser: String = ""
    private var deadline : Long = 0
    private val calendar = Calendar.getInstance()

    private var editTaskRequest = EditTaskRequest(0L,"","",0L,-1,0L,-1,-1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = MyTasksViewModelFactory(UserRepository())
        myTasksViewModel = ViewModelProvider(this, factory)[MyTasksViewModel::class.java]

        val userListFactory = UserListViewModelFactory(UserRepository())
        userListViewModel = ViewModelProvider(this, userListFactory)[UserListViewModel::class.java]

        val editTaskFactory = EditTaskViewModelFactory(UserRepository())
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
                Log.d("xxx", "GetMy user list  myTasks fragment ${users}")
                createdUser = searchUserName(TaskSelected.created_by_user_ID)
                assignedUser = searchUserName(TaskSelected.assigned_to_user_ID)
                myTasksViewModel.getTasks()
                myTasksViewModel.myTasks.observe(viewLifecycleOwner) {
                    var list = myTasksViewModel.myTasks.value
                    Log.d("xxx", "GetMy users in description $createdUser")
                    Log.d("xxx", "GetMy task in description ${myTasksViewModel.myTasks.value}")
                    Log.d(
                        "xxx",
                        "GetMy task1 position in description ${MyApplication.taskPosition}"
                    )
                    if (list != null) {
                        currentItem = list.get(MyApplication.taskPosition)
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
                    Log.d("xxx", "GetMy setSpiners ${currentItem.title}")
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
                findNavController().navigate(R.id.action_editTaskFragment_to_taskDescriptionFragment)
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun registerListeners() {
        setEditTaskRequest()
        taskNameUpdateEditText.setText(currentItem.title)
        selectDateUpdateTextView.text = convertLongToTime(currentItem.deadline,"MMM. dd. yyyy.")
        setSpinners(requireView(),currentItem)
        descriptionUpdateEditText.setText(currentItem.description)
    }

    private fun setEditTaskRequest() {
        editTaskRequest.taskId=currentItem.ID
        editTaskRequest.title = currentItem.title
        editTaskRequest.description = currentItem.description
        editTaskRequest.assignedToUserId = currentItem.asigned_to_user_ID
        editTaskRequest.priority = currentItem.priority
        editTaskRequest.deadline = currentItem.deadline
        editTaskRequest.departmentId = currentItem.department_ID
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

    private fun searchUserName(id: Long):String{
        var name = ""
        users?.forEach {
            if(it.id== id){
                name="${it.lastName} ${it.firstName}"
            }
        }
        return name
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setSpinners(view: View, currentTask: Task,) {
        Log.d("xxx", "GetMy setSpiners $currentTask")
        val projectType = resources.getStringArray(R.array.ProjectType)

        // access the spinner
        val spinner = view.findViewById<Spinner>(R.id.projectUpdateSpinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, projectType)
            spinner.adapter = adapter
            spinner.setSelection(currentTask.department_ID-1)

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    editTaskRequest.departmentId = position+1
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
            val assignedId = currentTask.asigned_to_user_ID.toInt()
            if(assignedId>4){
                assigneeSpinner.setSelection(currentTask.asigned_to_user_ID.toInt()-3)
            }else{
                assigneeSpinner.setSelection(currentTask.asigned_to_user_ID.toInt()-1)
            }
            Log.d("xxx", "GetMy setSpiners user id ${currentTask.asigned_to_user_ID.toInt()}")
            Log.d("xxx", "GetMy setSpiners user name ${spinner.selectedItemPosition}")

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
            prioritySpinner.setSelection(currentTask.priority-1)

            prioritySpinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    editTaskRequest.priority = position+1
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