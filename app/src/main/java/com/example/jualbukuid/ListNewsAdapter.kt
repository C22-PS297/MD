package com.example.githubuserapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView



class ListUserAdapter(private val listUser: ArrayList<User>) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback


    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_user_github, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val ( username, name, photo, company) = listUser[position]
        holder.imgPhoto.setImageResource(photo)
        holder.tvname.text = name
        holder.tvusername.text = username
        holder.tvcompany.text = company
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUser[holder.adapterPosition]) }


    }

    override fun getItemCount(): Int = listUser.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        var tvusername: TextView = itemView.findViewById(R.id.tv_item_username)
        var tvname: TextView = itemView.findViewById(R.id.tv_item_name)
        var tvcompany: TextView = itemView.findViewById(R.id.tv_item_company)

    }

    interface OnItemClickCallback{
        fun onItemClicked(data : User)
    }
}



