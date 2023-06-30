package com.example.a3trackerapplication.ui.settings

import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a3trackerapplication.R
import com.example.a3trackerapplication.adapter.MentorAdapter
import com.example.a3trackerapplication.adapter.OnItemClickListener
import com.example.a3trackerapplication.models.User
import com.example.a3trackerapplication.repositories.UserRepository
import java.nio.ByteBuffer


class SelectMentorFragment : Fragment(), OnItemClickListener {
    private lateinit var mentorList: List<User>
    private lateinit var adapter: MentorAdapter
    private lateinit var selectMentorViewModel: SelectMentorViewModel
    private lateinit var myProfileViewModel: MyProfileViewModel

    private lateinit var progressBar: ProgressBar

    private lateinit var callBackSelectMentorButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = SelectMentorViewModelFactory(UserRepository())
        selectMentorViewModel = ViewModelProvider(this, factory).get(SelectMentorViewModel::class.java)

        val myProfileFactory = MyProfileViewModelFactory(UserRepository())
        myProfileViewModel = ViewModelProvider(this, myProfileFactory).get(MyProfileViewModel::class.java)

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
        return inflater.inflate(R.layout.fragment_select_mentor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = view.findViewById(R.id.progressBarSelectMentor)
        progressBar.visibility = View.VISIBLE
        callBackSelectMentorButton = view.findViewById(R.id.callBackSelectMentorButton)
        selectMentorViewModel.getAllMentors()
        selectMentorViewModel.allMentors.observe(viewLifecycleOwner){
            mentorList = selectMentorViewModel.allMentors.value!!
            progressBar.visibility = View.INVISIBLE
            val recycleView: RecyclerView = view.findViewById(R.id.mentor_recycler_view)
            adapter = MentorAdapter(mentorList,this)
            recycleView.adapter = adapter
            recycleView.layoutManager = LinearLayoutManager(this.context)
            recycleView.setHasFixedSize(true)
        }
        callBackSelectMentorButton.setOnClickListener {
            findNavController().navigate(R.id.action_selectMentorFragment_to_myProfileFragment)
        }
    }

    override fun onSelectClick(position: Int){
        var mentor = mentorList[position]
        //var encodeMentorId = encryptId(mentor.id)
        myProfileViewModel.selectMentor(mentor.id)
        myProfileViewModel.mentor.observe(viewLifecycleOwner){
            if(it.id==mentor.id){
                Toast.makeText(
                    this.requireContext(),
                    "${it.firstName} is your mentor!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun encryptId(id: Long): String {
        val bytes = id.toString().toByteArray(Charsets.UTF_8)
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    private fun encodeLongToByteArray(value: Long): ByteArray {
        val buffer = ByteBuffer.allocate(java.lang.Long.BYTES)
        //buffer.order(ByteOrder.BIG_ENDIAN)
        buffer.putLong(value)
        return buffer.array()
    }

}