package com.example.instagram.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagram.LoginActivity
import com.example.instagram.Post.AddPostActivity
import com.example.instagram.R
import com.example.instagram.databinding.FragmentSignOutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SignOutFragment : BottomSheetDialogFragment() {
    private lateinit var binding : FragmentSignOutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignOutBinding.inflate(inflater,container,false)
        binding.signOut.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            activity?.finish()
        }
        return binding.root
    }

    companion object {

    }
}