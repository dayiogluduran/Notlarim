package com.durandayioglu.notlarim.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.durandayioglu.notlarim.entity.NoteEntity

class NoteListAdapter(
    var noteList: List<NoteEntity>? = null,
    val onItemClickListener: (noteEntity: NoteEntity) -> Unit
) : RecyclerView.Adapter<NoteListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteListViewHolder = NoteListViewHolder(parent)

    override fun getItemCount(): Int {
        noteList?.let { _noteEntity ->
            return _noteEntity.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: NoteListViewHolder, position: Int) {
        noteList?.let { holder.bind(it[position], onItemClickListener) }
    }

    fun setNewItem(noteList: List<NoteEntity>) {
        this.noteList = noteList
        notifyDataSetChanged()
    }

}