package com.example.instagram.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.instagram.R
import com.example.instagram.databinding.MypostRvBinding
import com.example.instagram.databinding.SearchRvBinding
import com.example.instagram.models.Reel
import com.example.instagram.models.User
import com.example.instagram.utils.FOLLOW
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SearchAdapter(var context : Context, var userList:ArrayList<User>):
    RecyclerView.Adapter<SearchAdapter.ViewHolder>(){
    inner class ViewHolder(var binding: SearchRvBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = SearchRvBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var isFollow = false
//        Picasso.get().load(postList.get(position).postUrl).into(holder.binding.postImage)
        Glide.with(context)
            .load(userList.get(position).image)
            .placeholder(R.drawable.user)
            .into(holder.binding.profileImage)
        holder.binding.profileName.text = userList.get(position).name
        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ FOLLOW).whereEqualTo("email",userList.get(position)).get().addOnSuccessListener {
            if(it.documents.size==0){
                isFollow = false
            }else{
                holder.binding.followBtn.text=  "Unfollow"
                isFollow = true
            }
        }
        holder.binding.followBtn.setOnClickListener {
            if(isFollow){
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ FOLLOW).whereEqualTo("email",userList.get(position)).get().addOnSuccessListener {
                    Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ FOLLOW).document(it.documents.get(0).id).delete()
                    holder.binding.followBtn.text = "follow"
                    isFollow = false
                }
            }else{
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ FOLLOW).document()
                    .set(userList.get(position))
                holder.binding.followBtn.text = "Unfollow"
                isFollow = true
            }

        }
    }
}