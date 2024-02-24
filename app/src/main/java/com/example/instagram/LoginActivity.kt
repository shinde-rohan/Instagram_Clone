package com.example.instagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.instagram.databinding.ActivityLoginBinding
import com.example.instagram.models.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.login.setOnClickListener {
            if(binding.loginEmail.text.toString().isEmpty() || binding.loginPassword.text.toString().isEmpty()){
                Toast.makeText(this,"Please fill all the details", Toast.LENGTH_LONG).show()
            }else{
                var user = User(binding.loginEmail.text.toString(),binding.loginPassword.text.toString())

                Firebase.auth.signInWithEmailAndPassword(user.email!!,user.password!!).addOnCompleteListener {
                    if(it.isSuccessful){
                        startActivity(Intent(this,HomeActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this,it.exception?.localizedMessage,Toast.LENGTH_LONG).show()

                    }
                }
            }
        }
    }
}