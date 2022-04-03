package com.example.kotlinnotebook

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinnotebook.databinding.RecyclerRowBinding

class NoteAdapter(val noteList: ArrayList<Note>) : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {

    class NoteHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent , false)
        return NoteHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.binding.recyclerViewTextView.text = noteList.get(position).name
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, NotebookActivity::class.java)
            intent.putExtra("info" , "saved")
            intent.putExtra("id",noteList[position].id)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return noteList.size
    }


}