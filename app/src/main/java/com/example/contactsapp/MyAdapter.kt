package com.example.contactsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(): RecyclerView.Adapter<MyAdapter.MyViewHolder>(){
    private var ContactsList: ArrayList<ContactModel> = ArrayList()

    constructor(ContactsList: ArrayList<ContactModel>) : this() {
        this.ContactsList = ContactsList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val view = inflator.inflate(R.layout.item_view, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val contact = ContactsList[position]
        holder.txtName.text = contact.name
        holder.txtLocation.text = contact.address
        holder.txtPhone.text = contact.phone
        holder.txtBio.text = contact.bio
        holder.img.setImageResource(R.drawable.avatar)
    }

    override fun getItemCount(): Int {
        return ContactsList.size
    }

    fun addItems(contactsList: ArrayList<ContactModel>) {
        this.ContactsList = contactsList
        notifyDataSetChanged()
    }


    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var txtName = itemView.findViewById<TextView>(R.id.UserName)
        var txtLocation = itemView.findViewById<TextView>(R.id.location)
        var txtPhone = itemView.findViewById<TextView>(R.id.phone)
        var txtBio = itemView.findViewById<TextView>(R.id.bio)
        var img = itemView.findViewById<ImageView>(R.id.imageView_avatar)
    }
}

