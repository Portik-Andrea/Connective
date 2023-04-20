package com.example.a3trackerapplication.ui.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.a3trackerapplication.MyApplication
import com.example.a3trackerapplication.R
import com.example.a3trackerapplication.models.User
import com.example.a3trackerapplication.repositories.UserRepository
import com.example.a3trackerapplication.util.UserListViewModel
import com.example.a3trackerapplication.util.UserListViewModelFactory

class ActivitiesFragment : Fragment() {
    private lateinit var userListViewModel: UserListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = UserListViewModelFactory(UserRepository())
        userListViewModel = ViewModelProvider(this, factory).get(UserListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_activities, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userListViewModel.readUsers()
        userListViewModel.userList.observe(viewLifecycleOwner) {
            val userList = userListViewModel.userList.value
            Log.d("xxx", "GetMy user list  activities fragment ${userList}")
        }
    }

}