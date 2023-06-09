package com.example.a3trackerapplication.ui.settings

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.a3trackerapplication.R
import com.example.a3trackerapplication.models.UpdateUserRequest
import com.example.a3trackerapplication.models.UserType
import com.example.a3trackerapplication.repositories.UserRepository
import java.io.ByteArrayOutputStream
import java.util.regex.Pattern


class MyProfileFragment : Fragment() {
    private lateinit var myProfileViewModel: MyProfileViewModel
    private lateinit var callBackMyProfileButton: ImageButton
    private lateinit var userNameEditText: EditText
    private lateinit var roleTypeTextView: TextView
    private lateinit var myMentorTextView: TextView
    private lateinit var emailAddressTextView: TextView
    private lateinit var phoneEditText: EditText
    private lateinit var locationEditText: EditText
    private lateinit var imageView: ImageView
    private lateinit var editProfileButton: Button
    private lateinit var selectMentorButton: Button
    private lateinit var addPhotoImageView: ImageView

    var location: String? = null
    var phoneNumber: String? = null

    private val selectImageFromGalleryResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK){
            if (result.data != null){
                val imageUri = result.data!!.data
                imageView.setImageURI(imageUri)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = MyProfileViewModelFactory(UserRepository())
        myProfileViewModel = ViewModelProvider(this, factory).get(MyProfileViewModel::class.java)

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
                userNameEditText.setText(user.firstName+" "+user.lastName)
                roleTypeTextView.text = user.departmentName
                emailAddressTextView.text = user.email
                if(user.imageUrl!= null){
                    var decodeBitmap = decodeBase64ToImage(user.imageUrl!!)
                    imageView.setImageBitmap(decodeBitmap)
                }
                if(user.phoneNumber!= null){
                    phoneEditText.setText("${user.phoneNumber}")
                    phoneNumber = user.phoneNumber
                }
                if(user.location!= null){
                    locationEditText.setText("${user.location}")
                    location = user.location
                }
                if(user.type == UserType.MENTEE && user.mentorId<1L){
                    selectMentorButton.visibility = View.VISIBLE
                }
                if(user.mentorId>=1L){
                    myProfileViewModel.selectMentor(user.mentorId)
                    myProfileViewModel.mentor.observe(viewLifecycleOwner){
                        val mentor = myProfileViewModel.mentor.value!!
                        myMentorTextView.text = "Mentor: ${mentor.lastName} ${mentor.firstName}"
                    }
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
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI )
            selectImageFromGalleryResult.launch(intent)
        }

        editProfileButton.setOnClickListener {
            var name = userNameEditText.text.toString().split(" ")

            if (locationValidate(locationEditText.text.toString()))
                location = locationEditText.text.toString()
            else{
                locationEditText.error = "Invalid location address!"
            }

            if (mobileValidate(phoneEditText.text.toString()))
                phoneNumber = phoneEditText.text.toString()
            else {
                phoneEditText.error = "Invalid Phone number!"
            }
            val imageUrl = encodeImageToBase64(imageView)
            if (name.isEmpty() || name.equals("Name")) {
                Toast.makeText(
                    this.requireContext(),
                    "Please, enter your name",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                var lastName = name[0]
                var firstName = name[1]

                myProfileViewModel.updateMyProfile(UpdateUserRequest(lastName,firstName,location,phoneNumber, imageUrl))
            }
        }
        myProfileViewModel.updateResult.observe(viewLifecycleOwner) {
            if(it){
                Toast.makeText(
                    this.requireContext(),
                    "Update profile is success!",
                    Toast.LENGTH_LONG
                ).show()
            }
            else{
                Toast.makeText(
                    this.requireContext(),
                    "Update profile is failed!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        selectMentorButton.setOnClickListener {
            findNavController().navigate(R.id.action_myProfileFragment_to_selectMentorFragment)
        }
        callBackMyProfileButton.setOnClickListener {
            findNavController().navigate(R.id.action_myProfileFragment_to_settingsFragment)
        }
    }


    private fun encodeImageToBase64(imageView: ImageView): String{
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream)
        val imageBytes = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(imageBytes, Base64.DEFAULT)
    }
    private fun decodeBase64ToImage(image: String): Bitmap{
        val decodeBytes = Base64.decode(image,Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodeBytes,0,decodeBytes.size)
    }

    private fun initViewItems() {
        callBackMyProfileButton = this.requireView().findViewById(R.id.callBackMyProfileButton)
        userNameEditText = this.requireView().findViewById(R.id.userNameEditText)
        roleTypeTextView = this.requireView().findViewById(R.id.roleTypeTextView)
        myMentorTextView = this.requireView().findViewById(R.id.myMentorTextView)
        emailAddressTextView = this.requireView().findViewById(R.id.emailAddressTextView)
        phoneEditText = this.requireView().findViewById(R.id.phoneEditText)
        locationEditText = this.requireView().findViewById(R.id.locationEditText)
        imageView = this.requireView().findViewById(R.id.circleImageView)
        addPhotoImageView = this.requireView().findViewById(R.id.addPhotoImageView)
        editProfileButton = this.requireView().findViewById(R.id.editProfileButton)
        selectMentorButton = this.requireView().findViewById(R.id.selectMentorButton)
    }

    private fun mobileValidate(text: String?): Boolean {
        val p = Pattern.compile("^\\+407[0-9]{8}$")
        val m = p.matcher(text)
        return m.matches()
    }

    private fun locationValidate(text: String?): Boolean{
        val p = Pattern.compile("^[a-zA-Z\\s]+$")
        val m = p.matcher(text)
        return m.matches()
    }

}

