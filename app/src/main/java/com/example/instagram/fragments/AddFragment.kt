package com.example.instagram.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagram.Post.AddPostActivity
import com.example.instagram.Post.AddReelActivity
import com.example.instagram.R
import com.example.instagram.databinding.FragmentAddBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentAddBinding.inflate(inflater,container,false)
        binding.addPost.setOnClickListener {
            startActivity(Intent(requireContext(),AddPostActivity::class.java))
            activity?.finish()
        }
        binding.addReel.setOnClickListener {
            startActivity(Intent(requireContext(),AddReelActivity::class.java))
        }
        return binding.root
    }

    companion object {


    }
}