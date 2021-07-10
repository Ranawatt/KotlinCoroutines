package com.example.mindorkscoroutine.learn.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mindorkscoroutine.R
import com.example.mindorkscoroutine.RecyclerViewClick
import com.example.mindorkscoroutine.data.local.entity.User
import kotlinx.android.synthetic.main.item_layout.view.*

class UserAdapter(private val users: ArrayList<User>)
    : RecyclerView.Adapter<UserAdapter.DataViewHolder>(), Filterable{

    private var searchableUsers: ArrayList<User>

    init {
        this.searchableUsers = users
    }
    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private lateinit var recyclerViewClick: RecyclerViewClick
        init {
            this.recyclerViewClick = recyclerViewClick
        }
        fun bind(user: User) {
            itemView.textViewUserName.text = user.name
            itemView.textViewUserEmail.text = user.email
            Glide.with(itemView.imageViewAvatar.context)
                .load(user.avatar)
                .into(itemView.imageViewAvatar)
            itemView.setOnClickListener {
                recyclerViewClick.onItemClick(absoluteAdapterPosition)
            }
            itemView.setOnLongClickListener {
                recyclerViewClick.onLongItemClick(absoluteAdapterPosition)
            }
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
            // run on background thread
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val resultList = ArrayList<User>()
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()){
                    resultList.addAll(searchableUsers)
                }else{
                    for (user in searchableUsers){
                        if (user.name!!.toLowerCase().contains(charSearch)
                            && user.name.startsWith(charSearch, true)){
                            resultList.add(user)
                        }
                    }
//                    searchableUsers = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = resultList
                return filterResults
            }
            // run on ui thread
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                searchableUsers = (results?.values as ArrayList<User>)
                notifyDataSetChanged()
            }
        }
    }

}