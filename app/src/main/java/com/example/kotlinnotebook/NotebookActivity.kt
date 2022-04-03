package com.example.kotlinnotebook

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.kotlinnotebook.databinding.ActivityNotebookBinding
import kotlinx.android.synthetic.main.activity_notebook.*
import java.lang.Exception

class NotebookActivity : AppCompatActivity() {
    private lateinit var binding : ActivityNotebookBinding
    private lateinit var database: SQLiteDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotebookBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        database = this.openOrCreateDatabase("Notes", MODE_PRIVATE,null)
        val intent = intent
        val info = intent.getStringExtra("info")

        if (info.equals("new")){
            binding.headerText.setText(" ")
            binding.noteText.setText("")
            binding.noteText2.setText("")
            binding.noteText3.setText("")
            binding.noteText4.setText("")
            binding.noteText5.setText("")
            binding.button.visibility = View.VISIBLE
        }else{

            binding.button.visibility = View.INVISIBLE
            val selectedId = intent.getIntExtra("id",1)
            val cursor = database.rawQuery("SELECT * FROM notes WHERE id = ?", arrayOf(selectedId.toString()))
            val headerIx = cursor.getColumnIndex("header")
            val todotextIX = cursor.getColumnIndex("todotext")
            val todotext2IX = cursor.getColumnIndex("todotext2")
            val todotext3IX = cursor.getColumnIndex("todotext3")
            val todotext4IX = cursor.getColumnIndex("todotext4")
            val todotext5IX = cursor.getColumnIndex("todotext5")


            while (cursor.moveToNext()){
                binding.headerText.setText(cursor.getString(headerIx))
                binding.noteText.setText(cursor.getString(todotextIX))
                binding.noteText2.setText(cursor.getString(todotext2IX))
                binding.noteText3.setText(cursor.getString(todotext3IX))
                binding.noteText4.setText(cursor.getString(todotext4IX))
                binding.noteText5.setText(cursor.getString(todotext5IX))

            }

            cursor.close()

        }

    }


    fun  saveButton(view : View){
      val header = binding.headerText.text.toString()
      val todotext = binding.noteText.text.toString()
      val todotext2 = binding.noteText2.text.toString()
      val todotext3 = binding.noteText3.text.toString()
      val todotext4= binding.noteText4.text.toString()
      val todotext5 = binding.noteText5.text.toString()

        try {

            database.execSQL("CREATE TABLE IF NOT EXISTS notes(id INTEGER PRIMARY KEY, header VARCHAR,todotext VARCHAR, todotext2 VARCHAR , todotext3 VARCHAR,todotext4 VARCHAR,todotext5 VARCHAR )")
            val sqlString = "INSERT INTO notes (header , todotext ,todotext2,todotext3,todotext4,todotext5) VALUES (?,?,?,?,?,?)"
            val statement = database.compileStatement(sqlString)
            statement.bindString(1,header)
            statement.bindString(2,todotext)
            statement.bindString(3,todotext2)
            statement.bindString(4,todotext3)
            statement.bindString(5,todotext4)
            statement.bindString(6,todotext5)
            statement.execute()




        }catch (e:Exception){
            e.printStackTrace()
        }

        val intent = Intent(this@NotebookActivity , MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)

    }
}