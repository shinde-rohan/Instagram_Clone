package com.example.instagram.Post

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.instagram.HomeActivity
import com.example.instagram.R
import com.example.instagram.databinding.ActivityAddPostBinding
import com.example.instagram.models.Post
import com.example.instagram.models.User
import com.example.instagram.utils.POST
import com.example.instagram.utils.POST_FOLDER
import com.example.instagram.utils.USER_NODE
import com.example.instagram.utils.USER_PROFILE_FOLDER
import com.example.instagram.utils.uploadImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class AddPostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPostBinding
    var imageUrl : String?= null
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()){ uri ->

        uri?.let{
            uploadImage(uri, POST_FOLDER){
                url ->
                if(url != null){
                    binding.selectPost.setImageURI(uri)
                    imageUrl = url
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.materialToolbar)
       supportActionBar?.setDisplayHomeAsUpEnabled(true)
       supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.materialToolbar.setNavigationOnClickListener {
            finish()
        }
        binding.selectPost.setOnClickListener {
            launcher.launch("image/*")
        }
        binding.postBtn.setOnClickListener {
            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
                var user = it.toObject<User>()!!

            var post : Post = Post(postUrl = imageUrl!!,
                                    caption = binding.writePost.text.toString(),
                                 uid= Firebase.auth.currentUser!!.uid,
                                 time= System.currentTimeMillis().toString())

            Firebase.firestore.collection(POST).document().set(post).addOnSuccessListener {
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).document().set(post).addOnSuccessListener {
                    startActivity(Intent(this@AddPostActivity,HomeActivity::class.java))
                    finish()
                }
            }
        }
        }
        binding.cancelBtn.setOnClickListener {
            startActivity(Intent(this@AddPostActivity,HomeActivity::class.java))
            finish()
        }

    }
}