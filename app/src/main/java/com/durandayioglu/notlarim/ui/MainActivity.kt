package com.durandayioglu.notlarim.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.durandayioglu.notlarim.R
import com.durandayioglu.notlarim.adapter.NoteListAdapter
import com.durandayioglu.notlarim.dao.NoteDAO
import com.durandayioglu.notlarim.db.NoteDb
import com.durandayioglu.notlarim.entity.NoteEntity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val noteDb: NoteDb? by lazy { NoteDb.getInstance(this) }
    private val noteDao: NoteDAO? by lazy { noteDb?.getNoteDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Notlarım"

        var controller: LayoutAnimationController? = null

        with(recyclerView_noteList) {
            controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_fall_down)
            adapter = NoteListAdapter { _noteEntity ->

                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setTitle(_noteEntity.noteTitle)
                builder.setMessage(_noteEntity.noteDetail)
                builder.setPositiveButton("Notu Sil") { dialog, which ->
                    thread(start = true) {
                        noteDao?.deleteNote(_noteEntity)
                    }
                    Toast.makeText(applicationContext, "Not silindi.", Toast.LENGTH_SHORT).show()
                }
                builder.setNeutralButton("Kapat") { _, _ ->
                }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
            this.layoutAnimation = controller
            this.scheduleLayoutAnimation()
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        }

        noteDao?.getAllNotes()?.observe(this, Observer<List<NoteEntity>> { _allNotes ->
            controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_fall_down)
            (recyclerView_noteList.adapter as NoteListAdapter).setNewItem(_allNotes)
            recyclerView_noteList.layoutAnimation = controller
            recyclerView_noteList.scheduleLayoutAnimation()

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.new_note_menu_item -> {
                val intent = Intent(this, NewNoteActivity::class.java)
                startActivity(intent)
            }
            R.id.delete_all_notes -> {
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setTitle("Eminmisiniz?")
                builder.setMessage("Eğer onaylarsınız tüm notlarınız silinecek!")
                builder.setPositiveButton("Evet") { dialog, which ->
                    kotlin.concurrent.thread(start = true) {
                        noteDao?.deleteAllNotes()
                    }
                    Toast.makeText(this@MainActivity, "Tüm notlar silindi", Toast.LENGTH_SHORT).show()
                }
                builder.setNeutralButton("Vazgeç") { _, _ ->
                }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
