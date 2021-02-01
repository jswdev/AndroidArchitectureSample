package com.example.faceeditor.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.faceeditor.R
import com.example.faceeditor.database.Member
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AdapterFaceItems(private var context: Context, private var data: ArrayList<Member>) : RecyclerView.Adapter<AdapterFaceItems.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cell = LayoutInflater.from(context).inflate(R.layout.layout_face_items_cell, parent, false)
        val viewHolder = ViewHolder(cell)
        viewHolder.imageView = cell.findViewById(R.id.faceImage)
        viewHolder.nameTextView = cell.findViewById(R.id.faceName)


        return viewHolder
    }

    override fun getItemCount(): Int {

        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = data[position]
        Glide.with(context).load(model.image).into(holder.imageView)
        holder.nameTextView.text = model.name

        holder.itemView.setOnClickListener{

            //Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show()
        }

        holder.itemView.setOnLongClickListener {

            MaterialAlertDialogBuilder(context)
                .setTitle("do you want to remove this item?")
                .setPositiveButton("cancel") { _, _ ->

                }
                .setNegativeButton("accept") { _, _ ->

                    removeItem(holder.adapterPosition)
                    //Toast.makeText(context, "remove success", Toast.LENGTH_SHORT).show()
                }.show()

            return@setOnLongClickListener true
        }
    }

    private fun removeItem(position: Int) {

        data.removeAt(position)
        notifyItemRemoved(position)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var nameTextView: TextView
        lateinit var imageView: ImageView
    }
}

