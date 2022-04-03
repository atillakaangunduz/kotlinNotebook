package com.example.kotlinnotebook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinnotebook.databinding.ActivityMainBinding
import kotlin.Exception


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var noteList : ArrayList<Note>
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        noteList = ArrayList<Note>()
        noteAdapter = NoteAdapter(noteList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = noteAdapter

        try {
            val database = this.openOrCreateDatabase("Notes", MODE_PRIVATE,null)

            val cursor = database.rawQuery("SELECT * FROM notes",null)
            val headerIx = cursor.getColumnIndex("header")
            val idIx = cursor.getColumnIndex("id")

            while (cursor.moveToNext()){
                val name = cursor.getString(headerIx)
                val id = cursor.getInt(idIx)
                val note = Note(name , id)
                noteList.add(note)
            }
            noteAdapter.notifyDataSetChanged()
            cursor.close()

        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflatermenu = menuInflater
        inflatermenu.inflate(R.menu.notebook_menu,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_list_item){
            val intent = Intent(this@MainActivity,NotebookActivity::class.java)
            intent.putExtra("info", "new")
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}