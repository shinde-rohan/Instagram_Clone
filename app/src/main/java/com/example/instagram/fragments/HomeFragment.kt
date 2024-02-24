package com.example.instagram.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.instagram.Adapters.FollowAdapter
import com.example.instagram.Adapters.Postadapter
import com.example.instagram.R
import com.example.instagram.databinding.ActivityHomeBinding
import com.example.instagram.databinding.FragmentHomeBinding
import com.example.instagram.models.Post
import com.example.instagram.models.User
import com.example.instagram.utils.FOLLOW
import com.example.instagram.utils.POST
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter : Postadapter
    private var postList = ArrayList<Post>()
    private lateinit var followadapter : FollowAdapter
    private var followList = ArrayList<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.materialToolbar2)
        setHasOptionsMenu(true)
        adapter = Postadapter(requireContext(),postList)
        binding.homeRecy.layoutManager = LinearLayoutManager(requireContext())
        binding.homeRecy.adapter = adapter
        Firebase.firestore.collection(POST).get().addOnSuccessListener {
            var tempList = ArrayList<Post>()
            postList.clear()
            for(i in it.documents){
                var post:Post = i.toObject<Post>()!!
                tempList.add(post)
            }
            postList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }
        followadapter = FollowAdapter(requireContext(),followList)
        binding.followRv.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.followRv.adapter = followadapter
        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ FOLLOW).get().addOnSuccessListener {
            var tempList = ArrayList<User>()
            followList.clear()
            for(i in it.documents){
                var user:User = i.toObject<User>()!!
                tempList.add(user)
            }
            followList.addAll(tempList)
            followadapter.notifyDataSetChanged()
        }
        return binding.root
    }

    companion object {

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}