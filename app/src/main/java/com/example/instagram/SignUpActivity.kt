package com.example.instagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.instagram.databinding.ActivitySignUpBinding
import com.example.instagram.models.User
import com.example.instagram.utils.USER_NODE
import com.example.instagram.utils.USER_PROFILE_FOLDER
import com.example.instagram.utils.uploadImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthSettings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var user : User
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()){ uri ->

        uri?.let{
             uploadImage(uri, USER_PROFILE_FOLDER){
                 if(it == null){

                 }else{
                     user.image = it
                     binding.profile.setImageURI(uri)
                 }
             }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val text = "<font color=#ff0000>Already Have An Account ?</font> <font color=#1E88E5>Login</font>"
        binding.alreadyAcc.setText(Html.fromHtml(text))
        user = User()

        if(intent.hasExtra("Mode")){
            if(intent.getIntExtra("Mode",-1)==1){
                binding.register.text = "Update Profile"

                Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
                    .addOnSuccessListener {
                        user = it.toObject<User>()!!
                        if(!user.image.isNullOrEmpty()){
                    Picasso.get().load(user.image).into(binding.profile)
                        }
                        binding.name.setText(user.name)
                        binding.email.setText(user.email)
                        binding.password.setText(user.password)
                    }
            }
        }
        binding.register.setOnClickListener {
            if(intent.hasExtra("Mode")){
                if(intent.getIntExtra("Mode",-1)==1){
                    Firebase.firestore.collection(USER_NODE)
                        .document(Firebase.auth.currentUser!!.uid).set(user).addOnSuccessListener {
                            startActivity(Intent(this,HomeActivity::class.java))
                            finish()
                        }
                }
            }
           else{
                var name = binding.name.text.toString().trim()
                var email = binding.email.text.toString().trim()
                var password = binding.password.text.toString().trim()
                if(name.isEmpty() || email.isEmpty() || password.isEmpty()){
                    Toast.makeText(this,"Please fill all the details",Toast.LENGTH_LONG).show()
                }else{
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener { result ->
                        if(result.isSuccessful){
                            user.name = name
                            user.email = email
                            user.password = password
                            Firebase.firestore.collection(USER_NODE)
                                .document(Firebase.auth.currentUser!!.uid).set(user)
                                .addOnSuccessListener {
//                                Toast.makeText(this,"Registration Successful",Toast.LENGTH_LONG).show()
                                    startActivity(Intent(this,HomeActivity::class.java))
                                    finish()
                                }

                        }else{
                            Toast.makeText(this,result.exception?.localizedMessage,Toast.LENGTH_LONG).show()
//                        Log.e("Error","login fail")
                        }
                    }
                }
           }
        }

        binding.profile.setOnClickListener {
            launcher.launch("image/*")
        }
        binding.alreadyAcc.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }
}