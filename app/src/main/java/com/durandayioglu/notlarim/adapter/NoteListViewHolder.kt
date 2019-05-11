package com.durandayioglu.notlarim.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.durandayioglu.notlarim.R
import com.durandayioglu.notlarim.entity.NoteEntity

class NoteListViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(
        R.layout.adapter_item_note_list,
        parent,
        false
    )
) {
    private val noteTitle: TextView
    private val noteDate: TextView
    private val noteIcon:ImageView

    init {
        noteTitle = itemView.findViewById(R.id.txtNoteTitle_Notes)
        noteDate = itemView.findViewById(R.id.txtNoteDate_Notes)
        noteIcon = itemView.findViewById(R.id.noteIcon)
    }

    fun bind(noteEntity: NoteEntity, onItemClickListener: (noteEntity: NoteEntity) -> Unit) {
        noteTitle.text = noteEntity.noteTitle
        noteDate.text = noteEntity.noteCreatedDate
        noteIcon.setBackgroundResource(noteEntity.noteIcon)

        itemView.setOnClickListener { onItemClickListener(noteEntity) }
    }
}