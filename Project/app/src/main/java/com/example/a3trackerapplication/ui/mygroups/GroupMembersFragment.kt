package com.example.a3trackerapplication.ui.mygroups

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a3trackerapplication.MyApplication
import com.example.a3trackerapplication.R
import com.example.a3trackerapplication.adapter.GroupAdapter
import com.example.a3trackerapplication.adapter.UserAdapter
import com.example.a3trackerapplication.models.Group
import com.example.a3trackerapplication.models.User
import com.example.a3trackerapplication.models.UserType
import com.example.a3trackerapplication.repositories.GroupRepository
import com.example.a3trackerapplication.repositories.UserRepository
import com.example.a3trackerapplication.ui.settings.MyProfileViewModel
import com.example.a3trackerapplication.ui.settings.MyProfileViewModelFactory


class GroupMembersFragment : Fragment() {
    private lateinit var selectGroupTextView: TextView
    private lateinit var callBackSelectGroupButton: ImageButton
    private lateinit var addUserToGroupButton: ImageButton
    private lateinit var progressBar: ProgressBar
    private lateinit var groupMembersViewModel: GroupMembersViewModel
    private var groupId: Long? = null
    private var groupName: String? = null
    private lateinit var list: List<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = GroupMembersViewModelFactory(GroupRepository())
        groupMembersViewModel = ViewModelProvider(this, factory)[GroupMembersViewModel::class.java]

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
        return inflater.inflate(R.layout.fragment_group_members, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.apply {
            initViewItems()
            selectGroupTextView.text = "$groupName group"
            if(MyApplication.userType == UserType.MENTOR.toString()){
                addUserToGroupButton.visibility = View.VISIBLE
            }
            progressBar = view.findViewById(R.id.membersProgressBar)
            progressBar.visibility = View.VISIBLE
            groupMembersViewModel.getGroupMembers(groupId!!)
            groupMembersViewModel.members.observe(viewLifecycleOwner) {
                list = groupMembersViewModel.members.value!!
                progressBar.visibility = View.INVISIBLE
                val recyclerView: RecyclerView = view.findViewById(R.id.membersRecyclerView)
                recyclerView.adapter = UserAdapter(list)
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.setHasFixedSize(true)
            }
            callBackSelectGroupButton.setOnClickListener {
                findNavController().navigate(R.id.action_groupMembersFragment_to_myGroupsFragment)
            }
            addUserToGroupButton.setOnClickListener {
                val bundle = bundleOf(
                    "groupId" to groupId,
                    "groupName" to groupName
                )
                findNavController().navigate(R.id.action_groupMembersFragment_to_addNewMemberFragment,bundle)
            }
        }
    }
    private fun initViewItems() {
        callBackSelectGroupButton = this.requireView().findViewById(R.id.callBackSelectGroupButton)
        selectGroupTextView = this.requireView().findViewById(R.id.selectGroupTextView)
        addUserToGroupButton = this.requireView().findViewById(R.id.addUserToGroupButton)
    }

}