package com.example.mindorkscoroutine.learn.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mindorkscoroutine.R
import com.example.mindorkscoroutine.data.local.entity.User
import com.example.mindorkscoroutine.data.model.ApiUser
import kotlinx.android.synthetic.main.item_layout.view.*

class UserAdapter(private val users: ArrayList<User>, private var searchableUsers: ArrayList<User>? = null)
    : RecyclerView.Adapter<UserAdapter.DataViewHolder>(), Filterable{

    init {
        searchableUsers = users
    }
    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(user: User) {
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

    fun addData(user: List<User>) {
        users.addAll(user)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString();
                if (charSearch.isEmpty()){
                    searchableUsers = users as ArrayList<User>
                }else{
                    val resultList = ArrayList<User>()
                    for (row in resultList){
                        if (row.name!!.toLowerCase().contains(charSearch.toLowerCase())){
                            resultList.add(row)
                        }
                    }
                    searchableUsers = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = searchableUsers
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                searchableUsers = results?.values as ArrayList<User>?
                notifyDataSetChanged()
            }
        }
    }

}