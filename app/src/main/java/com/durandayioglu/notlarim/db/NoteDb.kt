package com.durandayioglu.notlarim.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.durandayioglu.notlarim.dao.NoteDAO
import com.durandayioglu.notlarim.entity.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
abstract class NoteDb : RoomDatabase() {
    abstract fun getNoteDao(): NoteDAO

    companion object {
        private var INSTANCE: NoteDb? = null
        fun getInstance(context: Context): NoteDb? {
            if (INSTANCE == null) {
                synchronized(NoteDb::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        NoteDb::class.java, "note_db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}