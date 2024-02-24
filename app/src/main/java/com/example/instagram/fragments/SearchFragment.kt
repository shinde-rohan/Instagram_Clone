package com.example.instagram.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.Adapters.SearchAdapter
import com.example.instagram.R
import com.example.instagram.databinding.FragmentSearchBinding
import com.example.instagram.models.User
import com.example.instagram.utils.USER_NODE
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase


class SearchFragment : Fragment() {
    private lateinit var binding : FragmentSearchBinding
    private lateinit var adapter : SearchAdapter
    var userList = ArrayList<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater,container,false)
        adapter = SearchAdapter(requireContext(),userList)
        binding.searchRecy.layoutManager = LinearLayoutManager(requireContext())
        binding.searchRecy.adapter= adapter
        Firebase.firestore.collection(USER_NODE).get().addOnSuccessListener {
            var tempList = ArrayList<User>()
            userList.clear()

            for( i in it.documents){
                if(!(i.id.toString().equals(Firebase.auth.currentUser!!.uid.toString()))){
                    var user: User? = i.toObject<User>()
                    tempList.add(user!!)
                }

            }
            userList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }
        binding.searchBotton.setOnClickListener {
            var text = binding.searchView.text.toString()

            Firebase.firestore.collection(USER_NODE).whereEqualTo("name",text).get().addOnSuccessListener {
                var tempList = ArrayList<User>()
                userList.clear()

                if(!(it.isEmpty)){
                    for( i in it.documents){
                        if(!(i.id.toString().equals(Firebase.auth.currentUser!!.uid.toString()))){
                            var user: User? = i.toObject<User>()
                            tempList.add(user!!)
                        }

                    }
                }

                userList.addAll(tempList)
                adapter.notifyDataSetChanged()
            }
        }
        return binding.root

    }

    companion object {

    }
}