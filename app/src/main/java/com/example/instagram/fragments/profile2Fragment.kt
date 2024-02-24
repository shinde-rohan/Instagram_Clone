package com.example.instagram.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagram.Adapters.ViewPagerAdapter
import com.example.instagram.R
import com.example.instagram.SignUpActivity
import com.example.instagram.databinding.FragmentProfile2Binding

import com.example.instagram.models.User
import com.example.instagram.utils.USER_NODE
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso


class profile2Fragment : Fragment() {

    private lateinit var binding : FragmentProfile2Binding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfile2Binding.inflate(inflater,container,false)

        binding.editProfile.setOnClickListener {
            val intent = Intent(activity,SignUpActivity::class.java)
            intent.putExtra("Mode",1)
            activity?.startActivity(intent)
        }
        viewPagerAdapter = ViewPagerAdapter(requireActivity().supportFragmentManager)
        viewPagerAdapter.addFragments(MyPostFragment(),"My Post")
        viewPagerAdapter.addFragments(MyReelsFragment(),"My Reels")

        binding.viewPager.adapter = viewPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.signOut.setOnClickListener {
            val bottomSheetFragment = SignOutFragment()
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
        }
        return binding.root
    }

    companion object {


    }
    override fun onStart() {
        super.onStart()
        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
            .addOnSuccessListener {
                val user : User = it.toObject<User>()!!
                binding.profileName.text = user.name
                binding.profileBio.text = user.email

                if(!user.image.isNullOrEmpty()){
                    Picasso.get().load(user.image).into(binding.profileImage)
                }

            }
    }
}