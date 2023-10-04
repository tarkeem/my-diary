package com.example.mydiary.activities

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mydiary.adapter.myCustomAdapter
import com.example.mydiary.database.NoteDataBase
import com.example.mydiary.databinding.ActivityMainBinding
import com.example.mydiary.entities.Note

class MainActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        binding.imageView5.setOnClickListener {
            println("clicked")
            var intent=Intent(applicationContext, NotesActivity::class.java)
            startActivityForResult(intent,1)
        }
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.recyclerView.layoutManager=staggeredGridLayoutManager
        showNotes()
        setContentView(binding.root)//to make binding work

    }
    fun showNotes()
    {
        class someTask() : AsyncTask<Void, Void, List<Note>>() {


            override fun doInBackground(vararg p0: Void?): List<Note> {
                var db= NoteDataBase.invoke(context = this@MainActivity)

                return db.getNoteeDao().fetchFromDb()
            }

            override fun onPostExecute(result: List<Note>?) {
                super.onPostExecute(result)
               binding.recyclerView.adapter=myCustomAdapter(result!!,this@MainActivity)
            }

        }
        someTask().execute()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data!=null)
        {
            showNotes()
        }

    }
}