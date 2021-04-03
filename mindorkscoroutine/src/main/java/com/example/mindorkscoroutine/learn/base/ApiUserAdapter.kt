package com.example.mindorkscoroutine.learn.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mindorkscoroutine.R
import com.example.mindorkscoroutine.data.model.ApiUser
import kotlinx.android.synthetic.main.item_layout.view.*

class ApiUserAdapter(private val users: ArrayList<ApiUser>):
    RecyclerView.Adapter<ApiUserAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(apiUser: ApiUser) {
            itemView.textViewUserName.text = apiUser.name
            itemView.textViewUserEmail.text = apiUser.email
            Glide.with(itemView.imageViewAvatar.context)
                .load(apiUser.avatar)
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