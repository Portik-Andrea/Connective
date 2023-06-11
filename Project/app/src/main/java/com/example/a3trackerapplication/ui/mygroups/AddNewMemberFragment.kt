package com.example.a3trackerapplication.ui.mygroups

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a3trackerapplication.R
import com.example.a3trackerapplication.adapter.NewMemberAdapter
import com.example.a3trackerapplication.adapter.OnUserClickListener
import com.example.a3trackerapplication.adapter.UserAdapter
import com.example.a3trackerapplication.models.AddMember
import com.example.a3trackerapplication.models.User
import com.example.a3trackerapplication.repositories.GroupRepository
import com.example.a3trackerapplication.repositories.UserRepository
import com.example.a3trackerapplication.util.UserListViewModel
import com.example.a3trackerapplication.util.UserListViewModelFactory

class AddNewMemberFragment : Fragment(), OnUserClickListener {
    private lateinit var callBackNewMemberButton: ImageButton
    private lateinit var searchUserEditText: EditText
    private lateinit var searchUserButton: ImageButton
    private lateinit var usersRecyclerView: RecyclerView
    private lateinit var groupMembersViewModel: GroupMembersViewModel
    private lateinit var userListViewModel: UserListViewModel

    private var groupId: Long? = null
    private var groupName: String? = null
    private var users : List<User>? = null
    private var searchUser: List<User>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = GroupMembersViewModelFactory(GroupRepository())
        groupMembersViewModel = ViewModelProvider(this, factory)[GroupMembersViewModel::class.java]

        val userListFactory = UserListViewModelFactory(UserRepository())
        userListViewModel = ViewModelProvider(this, userListFactory)[UserListViewModel::class.java]

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
            if (it["groupId"] != null) {
                groupId = it.getLong("groupId")
            }
            if (it["groupName"] != null) {
                groupName = it.getString("groupName")
            }
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new_member, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.apply {
            initViewItems()
            userListViewModel.readUsers()
            userListViewModel.userList.observe(viewLifecycleOwner) {
                users = userListViewModel.userList.value!!.sortedBy { it.id }
                searchUserButton.setOnClickListener {
                    val searchTerm: String = searchUserEditText.text.toString()
                    if(searchTerm.isEmpty() || searchTerm.length<3){
                        searchUserEditText.error = "Invalid Username"
                    }
                    searchUser = users!!.filter { user ->
                        val userFullName = "${user.firstName} ${user.lastName}"
                        userFullName.contains(searchTerm, ignoreCase = true)
                    }
                    val recyclerView: RecyclerView = view.findViewById(R.id.usersRecyclerView)
                    recyclerView.adapter = NewMemberAdapter(searchUser!!,this@AddNewMemberFragment)
                    recyclerView.layoutManager = LinearLayoutManager(context)
                    recyclerView.setHasFixedSize(true)
                }
            }


            callBackNewMemberButton.setOnClickListener {
                val bundle = bundleOf(
                    "groupId" to groupId,
                    "groupName" to groupName
                )
                findNavController().navigate(R.id.action_addNewMemberFragment_to_groupMembersFragment,bundle)
            }
        }
    }

    private fun initViewItems() {
        callBackNewMemberButton = this.requireView().findViewById(R.id.callBackNewMemberButton)
        searchUserEditText = this.requireView().findViewById(R.id.searchUserEditText)
        searchUserButton = this.requireView().findViewById(R.id.searchUserButton)
        usersRecyclerView = this.requireView().findViewById(R.id.usersRecyclerView)
    }

    override fun onSelectClick(position: Int){
        var user = searchUser?.get(position)
        Log.d("xxx", "Getselect user response $user")
        //var encodeMentorId = encryptId(mentor.id)
        groupMembersViewModel.addMember(AddMember(groupId!!,user!!.id))
        groupMembersViewModel.addNewMemberResponse.observe(viewLifecycleOwner){
            Toast.makeText(
                this.requireContext(),
                it,
                Toast.LENGTH_LONG
            ).show()

        }
    }
}