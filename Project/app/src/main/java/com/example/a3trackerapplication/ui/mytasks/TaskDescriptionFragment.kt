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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.a3trackerapplication.MyApplication
import com.example.a3trackerapplication.R
import com.example.a3trackerapplication.TaskSelected
import com.example.a3trackerapplication.models.EditTaskRequest
import com.example.a3trackerapplication.models.LoginResult
import com.example.a3trackerapplication.models.Task
import com.example.a3trackerapplication.models.User
import com.example.a3trackerapplication.repositories.UserRepository
import com.example.a3trackerapplication.ui.login.LoginViewModel
import com.example.a3trackerapplication.ui.login.LoginViewModelFactory
import com.example.a3trackerapplication.util.UserListViewModel
import com.example.a3trackerapplication.util.UserListViewModelFactory
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
    private lateinit var userListViewModel: UserListViewModel
    private lateinit var editTaskViewModel: EditTaskViewModel
    private var users : List<User>? = null
    private var createdUser: String = ""
    private var assignedUser: String = ""

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
        return inflater.inflate(R.layout.fragment_task_description, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.apply{
            initViewItems(view)
            userListViewModel.readUsers()
            userListViewModel.userList.observe(viewLifecycleOwner) {
                users = userListViewModel.userList.value!!
                Log.d("xxx", "GetMy user list  myTasks fragment ${users}")
                createdUser = searchUserName(TaskSelected.created_by_user_ID)
                assignedUser = searchUserName(TaskSelected.assigned_to_user_ID)
                myTasksViewModel.getTasks()
                myTasksViewModel.myTasks.observe(viewLifecycleOwner) {
                    var list =myTasksViewModel.myTasks.value
                    Log.d("xxx", "GetMy users in description $createdUser")
                    Log.d("xxx", "GetMy task in description ${myTasksViewModel.myTasks.value}")
                    Log.d("xxx", "GetMy task1 position in description ${MyApplication.taskPosition}")
                    if(list!=null ){
                        currentItem=list.get(MyApplication.taskPosition)
                        registerListeners()
                    }
                }
            }
            callBackTaskDescButton.setOnClickListener{
                MyApplication.taskPosition = -1
                val preferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
                val editor = preferences.edit()
                editor.remove("taskPosition")
                editor.apply()
                TaskSelected.created_by_user_ID = 0L
                TaskSelected.assigned_to_user_ID = 0L
                findNavController().navigate(R.id.action_taskDescriptionFragment_to_myTasksFragment)
            }
            editImageButton.setOnClickListener {
                findNavController().navigate(R.id.action_taskDescriptionFragment_to_editTaskFragment)
            }

        }
    }

    @SuppressLint("SetTextI18n")
    private fun registerListeners() {
        titleTextView.text = currentItem.title
        setProjectType(currentItem.department_ID,projectTextView)
        val createTime = convertLongToTime(currentItem.created_time,"HH:mm MMMM dd yyyy")
        createdByUserTextView.text = "$createdUser $createTime"
        createTimeTextView.text = createTime
        assigneeToUserTextView.text = assignedUser
        deadlineTextView.text = convertLongToTime(currentItem.deadline,"yyyy.MMMM.dd")
        descriptionTextView.text = currentItem.description
        //setStatus(currentItem.status, statusTextView)
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

    private fun searchUserName(id: Long):String{
        var name = ""
        users?.forEach {
            if(it.ID== id){
                name="${it.last_name} ${it.first_name}"
            }
        }
        return name
    }
    @SuppressLint("SetTextI18n")
    private fun setPriority(priority: Int, textView: TextView){
        when(priority){
            1 -> {
                textView.text="High prio"
                textView.setTextColor(Color.parseColor("#D30000"))
            }
            2 -> {
                textView.text= "Medium prio"
                textView.setTextColor(Color.parseColor("#ED7014"))
            }
            3 -> {
                textView.text="Low prio"
                textView.setTextColor(Color.parseColor("#3BB143"))
            }
            else -> {
                textView.text=""
            }
        }
    }

    private fun setProjectType(departmentId: Int, projectTextView: TextView) {
        when(departmentId){
            1 -> projectTextView.text = "HR project"
            2 -> projectTextView.text = "Dev project"
        }
    }

    private fun setSpinners(view: View, itemStatus: Int) {
        val status = resources.getStringArray(R.array.Status)

        // access the spinner
        val spinner = view.findViewById<Spinner>(R.id.statusSpinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, status)
            spinner.adapter = adapter
            spinner.setSelection(itemStatus)

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    if(position!=itemStatus && position>0){
                        var editTaskRequest = EditTaskRequest(currentItem.ID,
                            currentItem.title,
                            currentItem.description,
                            currentItem.asigned_to_user_ID,
                            currentItem.priority,
                            currentItem.deadline,
                            currentItem.department_ID,
                            position
                        )
                        editTaskViewModel.updateTask(editTaskRequest)
                        Log.d("xxx", "GetMy edit task request")
                    }
                    editTaskViewModel.editTaskResult.observe(viewLifecycleOwner) {
                        Log.d("xxx", "GetMy edit task result ")
                        // Save data to preferences
                        /*if( it == LoginResult.INVALID_CREDENTIALS){
                            Toast.makeText(
                                requireContext(),
                                "Invalid credentials",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.d("xxx", "GetMy edit task request is Invalid credentials")
                        }
                        if ( it == LoginResult.SUCCESS ) {
                            Toast.makeText(
                                requireContext(),
                                "Task status updated",
                                Toast.LENGTH_SHORT
                            ).show()
                        }*/
                        Toast.makeText(
                            requireContext(),
                            "Task status updated",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        }
    }
}