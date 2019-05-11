package com.durandayioglu.notlarim.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.durandayioglu.notlarim.entity.NoteEntity

@Dao
interface NoteDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNewNote(noteEntity: NoteEntity)

    @Delete
    fun deleteNote(noteEntity: NoteEntity)

    @Query("SELECT * FROM NoteTable")
    fun getAllNotes(): LiveData<List<NoteEntity>>

    @Query("DELETE FROM NoteTable")
    fun deleteAllNotes()


}