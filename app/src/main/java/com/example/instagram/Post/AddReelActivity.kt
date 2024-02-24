package com.example.instagram.Post

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.instagram.HomeActivity
import com.example.instagram.R
import com.example.instagram.databinding.ActivityAddReelBinding
import com.example.instagram.models.Post
import com.example.instagram.models.Reel
import com.example.instagram.models.User
import com.example.instagram.utils.POST
import com.example.instagram.utils.POST_FOLDER
import com.example.instagram.utils.REEL
import com.example.instagram.utils.REEL_FOLDER
import com.example.instagram.utils.USER_NODE
import com.example.instagram.utils.uploadImage
import com.example.instagram.utils.uploadVideo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class AddReelActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddReelBinding
    var videoUrl : String?= null
    private lateinit var progressDialog : ProgressDialog
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()){ uri ->

        uri?.let{
            uploadVideo(uri, REEL_FOLDER,progressDialog){
                    url ->
                if(url != null){
                    videoUrl = url
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        progressDialog= ProgressDialog(this)
        binding = ActivityAddReelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.materialToolbar.setNavigationOnClickListener {
            finish()
        }
        binding.selectPost.setOnClickListener {
            launcher.launch("video/*")
        }
        binding.postBtn.setOnClickListener {
            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
                var user : User? = it.toObject<User>()
                var reel : Reel = Reel(videoUrl!!,binding.writePost.text.toString(),user?.image!!)

                Firebase.firestore.collection(REEL).document().set(reel).addOnSuccessListener {
                    Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ REEL).document().set(reel).addOnSuccessListener {
                        startActivity(Intent(this@AddReelActivity, HomeActivity::class.java))
                        finish()
                    }
                }
            }
            }

        binding.cancelBtn.setOnClickListener {
            startActivity(Intent(this@AddReelActivity, HomeActivity::class.java))
            finish()
        }
    }
}