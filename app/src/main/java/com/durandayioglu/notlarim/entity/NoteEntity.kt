package com.durandayioglu.notlarim.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NoteTable")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "_id")
    val _id: Int = 0,

    @ColumnInfo(name = "noteTitle")
    val noteTitle: String,

    @ColumnInfo(name = "noteDetail")
    val noteDetail: String,

    @ColumnInfo(name = "noteCreatedDate")
    val noteCreatedDate: String,

    @ColumnInfo(name = "noteIcon")
    val noteIcon: Int


)