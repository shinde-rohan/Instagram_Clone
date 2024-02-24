package com.example.instagram.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.instagram.databinding.MypostRvBinding
import com.example.instagram.models.Post
import com.example.instagram.models.Reel
import com.squareup.picasso.Picasso

class MyReelAdapter(var context : Context, var reelList:ArrayList<Reel>):
    RecyclerView.Adapter<MyReelAdapter.ViewHolder>(){
    inner class ViewHolder(var binding: MypostRvBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = MypostRvBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//        Picasso.get().load(postList.get(position).postUrl).into(holder.binding.postImage)
        Glide.with(context)
            .load(reelList.get(position).reelUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.binding.postImage)
    }
}