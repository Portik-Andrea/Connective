package com.example.a3trackerapplication.ui.settings

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a3trackerapplication.R
import com.example.a3trackerapplication.repositories.UserRepository
import java.io.File


class MyProfileFragment : Fragment() {
    private lateinit var myProfileViewModel: MyProfileViewModel
    private lateinit var userNameTextView: TextView
    private lateinit var roleTypeTextView: TextView
    private lateinit var emailAddrressEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var locationEditText: EditText
    private lateinit var imageView: ImageView
    private lateinit var addPhotoImageView: ImageView



    private val selectImageFromGalleryResult = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { imageView.setImageURI(uri)
            Log.d("xxx", "GetMyImg  Image1 $uri")
            profileImageUri = uri
            myProfileViewModel.imageUri=uri
            Log.d("xxx", "GetMyImg  Image2 ${myProfileViewModel.imageUri}")
        }
    }
    private var profileImageUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = MyProfileViewModelFactory(UserRepository())
        myProfileViewModel = ViewModelProvider(this, factory).get(MyProfileViewModel::class.java)
        profileImageUri = savedInstanceState?.getParcelable("profileImageUri")
        profileImageUri?.let {
            imageView.setImageURI(it)
        }
        Log.d("xxx", "GetMyImg ImageP $profileImageUri")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_profile, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewItems()
        myProfileViewModel.myUser()
        myProfileViewModel.user.observe(viewLifecycleOwner) {
            val user = myProfileViewModel.user.value
            Log.i("xxx","GetMy user "+ user.toString())
            if (user != null) {
                userNameTextView.text="${user.last_name} ${user.first_name}"
                roleTypeTextView.text ="${user.type}"
                emailAddrressEditText.setText("${user.email}")
                if(user.image!= null){
                    imageView.setImageDrawable(user.image as Drawable?)
                }
                if(user.phone_number!= null){
                    phoneEditText.setText("${user.phone_number}")
                }
                if(user.location!= null){
                    locationEditText.setText("${user.location}")
                }
            }
            else{
                Toast.makeText(
                    this.requireContext(),
                    "The data could not be displayed!",
                    Toast.LENGTH_LONG
                ).show()
            }

        }

        addPhotoImageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI )
            selectImageFromGallery()
        }

    }

    private fun initViewItems() {
        userNameTextView = this.requireView().findViewById(R.id.userNameTextView)
        roleTypeTextView = this.requireView().findViewById(R.id.roleTypeTextView)
        emailAddrressEditText = this.requireView().findViewById(R.id.emailAddressEditText)
        phoneEditText = this.requireView().findViewById(R.id.phoneEditText)
        locationEditText = this.requireView().findViewById(R.id.locationEditText)
        imageView = this.requireView().findViewById(R.id.circleImageView)
        addPhotoImageView = this.requireView().findViewById(R.id.addPhotoImageView)
    }

    private fun selectImageFromGallery() = selectImageFromGalleryResult.launch("image/*")



}

