package com.durandayioglu.notlarim.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.durandayioglu.notlarim.R
import com.durandayioglu.notlarim.dao.NoteDAO
import com.durandayioglu.notlarim.db.NoteDb
import com.durandayioglu.notlarim.entity.NoteEntity
import kotlinx.android.synthetic.main.activity_new_note.*
import java.text.DateFormat
import java.util.*
import kotlin.concurrent.thread

class NewNoteActivity : AppCompatActivity() {

    private val noteDb: NoteDb? by lazy { NoteDb.getInstance(this) }
    private val noteDao: NoteDAO? by lazy { noteDb?.getNoteDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)
        supportActionBar?.title = "Yeni Not Oluştur"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_new_note, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.save_note_menu_item -> {
                if (txtNoteTitle.text.isNotEmpty() && txtNoteDetail.text.isNotEmpty()) {
                    val noteEntity = NoteEntity(
                            noteTitle = txtNoteTitle.text.toString(),
                            noteDetail = txtNoteDetail.text.toString(),
                            noteCreatedDate = DateFormat.getDateInstance().format(Date()),
                            noteIcon = R.drawable.ic_speaker_notes
                    )
                    thread(start = true) {
                        noteDao?.addNewNote(noteEntity)
                    }
                    Toast.makeText(this, "Notunuz başarıyla kayıt edildi.", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@NewNoteActivity, "Lütfen boş alan bırakmayınız.", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
