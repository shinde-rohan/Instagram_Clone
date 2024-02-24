package com.example.instagram.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.databinding.FragmentMyPostBinding
import com.example.instagram.databinding.MypostRvBinding
import com.example.instagram.models.Post
import com.squareup.picasso.Picasso

class MyPostAdapter(var context : Context, var postList:ArrayList<Post>):
    RecyclerView.Adapter<MyPostAdapter.ViewHolder>(){
    inner class ViewHolder(var binding: MypostRvBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       var binding = MypostRvBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Picasso.get().load(postList.get(position).postUrl).into(holder.binding.postImage)
    }
}