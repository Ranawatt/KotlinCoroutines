package com.example.mindorkscoroutine.learn.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mindorkscoroutine.R
import com.example.mindorkscoroutine.data.local.entity.User
import com.example.mindorkscoroutine.data.model.ApiUser
import kotlinx.android.synthetic.main.item_layout.view.*

class UserAdapter(private val users: ArrayList<ApiUser>) : RecyclerView.Adapter<UserAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(user: ApiUser) {
            itemView.textViewUserName.text = user.name
            itemView.textViewUserEmail.text = user.email
            Glide.with(itemView.imageViewAvatar.context)
                .load(user.avatar)
                .into(itemView.imageViewAvatar)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false))

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size

    fun addData(user: List<ApiUser>) {
        users.addAll(user)
    }
}