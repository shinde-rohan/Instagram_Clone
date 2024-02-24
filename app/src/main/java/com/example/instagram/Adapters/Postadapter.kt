package com.example.instagram.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagram.R
import com.example.instagram.databinding.PostRvBinding
import com.example.instagram.models.Post
import com.example.instagram.models.User
import com.example.instagram.utils.USER_NODE
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class Postadapter (var context : Context, var postList:ArrayList<Post>):
    RecyclerView.Adapter<Postadapter.ViewHolder>(){
    inner class ViewHolder(var binding: PostRvBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = PostRvBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            Firebase.firestore.collection(USER_NODE).document(postList.get(position).uid).get().addOnSuccessListener {
                var user = it.toObject<User>()
                Glide.with(context).load(user!!.image).placeholder(R.drawable.user).into(holder.binding.profileImage)
                holder.binding.profileName.text = user.name
            }
        }catch (e:Exception){

        }
        Glide.with(context).load(postList!!.get(position).postUrl).placeholder(R.drawable.loading).into(holder.binding.post)
        try {

            val text: String = TimeAgo.using(postList.get(position).time.toLong())
            holder.binding.time.text = text
        }catch (e:Exception){
            holder.binding.time.text = ""
        }
        holder.binding.textView4.text = postList.get(position).caption

        holder.binding.like.setOnClickListener {
            holder.binding.like.setImageResource(R.drawable.like2)
        }
        holder.binding.share.setOnClickListener {
            var intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT,postList.get(position).postUrl)
            context.startActivity(intent)
        }
    }
}