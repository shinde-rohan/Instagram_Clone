package com.example.instagram.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.R
import com.example.instagram.databinding.MypostRvBinding
import com.example.instagram.databinding.ReelDgBinding
import com.example.instagram.models.Reel
import com.squareup.picasso.Picasso

class ReelAdapter (var context : Context, var reelList:ArrayList<Reel>):
    RecyclerView.Adapter<ReelAdapter.ViewHolder>(){
    inner class ViewHolder(var binding: ReelDgBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ReelDgBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reelList.size
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(reelList.get(position).profileLink).placeholder(R.drawable.user).into(holder.binding.profileImage)
        holder.binding.reelName.setText(reelList.get(position).caption)
        holder.binding.videoView.setVideoPath(reelList.get(position).reelUrl)
        holder.binding.videoView.setOnPreparedListener {
        holder.binding.progressBar.visibility = View.GONE
            holder.binding.videoView.start()
        }
    }
}