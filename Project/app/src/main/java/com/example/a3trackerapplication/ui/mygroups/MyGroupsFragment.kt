package com.example.a3trackerapplication.ui.mygroups

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a3trackerapplication.R
import com.example.a3trackerapplication.adapter.GroupAdapter
import com.example.a3trackerapplication.adapter.OnGroupClickListener
import com.example.a3trackerapplication.adapter.TaskAdapter
import com.example.a3trackerapplication.models.Group
import com.example.a3trackerapplication.models.Task
import com.example.a3trackerapplication.repositories.GroupRepository
import com.example.a3trackerapplication.repositories.TaskRepository
import com.example.a3trackerapplication.ui.mytasks.MyTasksViewModel
import com.example.a3trackerapplication.ui.mytasks.MyTasksViewModelFactory


class MyGroupsFragment : Fragment(), OnGroupClickListener {
    private lateinit var groupViewModel: GroupViewModel
    private lateinit var list: List<Group>
    private lateinit var adapter: GroupAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = GroupViewModelFactory(GroupRepository())
        groupViewModel = ViewModelProvider(this, factory)[GroupViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_groups, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.apply {
            groupViewModel.getGroups()
            groupViewModel.groups.observe(viewLifecycleOwner) {
                list = groupViewModel.groups.value!!
                val recyclerView: RecyclerView = view.findViewById(R.id.groupsRecyclerView)
                recyclerView.adapter = GroupAdapter(list, this@MyGroupsFragment)
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.setHasFixedSize(true)
            }
        }
    }

    override fun onSelectClick(position: Int) {
        var clickedItem : Group = list[position]
        val bundle = bundleOf(
            "groupId" to clickedItem.groupId,
            "groupName" to clickedItem.groupName
        )
        findNavController().navigate(R.id.action_myGroupsFragment_to_groupMembersFragment,bundle)
    }


}