package com.example.mydiary.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mydiary.adapter.myCustomAdapter
import com.example.mydiary.database.NoteDataBase
import com.example.mydiary.databinding.ActivityMainBinding
import com.example.mydiary.entities.Note

class MainActivity : AppCompatActivity() {
    private val notestate: MutableLiveData<List<Note>> by lazy {
        MutableLiveData()
    }
    val  noteLiveState: LiveData<List<Note>>
        get() = notestate
    private  lateinit var binding: ActivityMainBinding
    val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            // do something
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        binding.imageView5.setOnClickListener {
            println("clicked")
            var intent=Intent(applicationContext, NotesActivity::class.java)
            startActivityForResult(intent,1)
        }

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Pass any permission you want while launching
            requestPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        else
        {
            val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            binding.recyclerView.layoutManager=staggeredGridLayoutManager
            showNotes()
            noteLiveState.observe(this) {
                binding.recyclerView.adapter=myCustomAdapter(it,this@MainActivity)
            }
        }




        binding.searchtextfield.addTextChangedListener {ch ->
            var allNotes=noteLiveState.value
            println(allNotes.toString())
            if (allNotes!=null)
            {
                var filterNote = allNotes.filter {
                    it.title.toString().contains(ch.toString(),true)
                }

                var myadapter=myCustomAdapter(filterNote,this@MainActivity)
                binding.recyclerView.adapter=myadapter
            }


        }



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
                if (result != null) {
                    println(result.toString())
                    notestate.postValue(result)

                }

            }

        }
        someTask().execute()
    }
    fun showSearchRes()
    {
        binding.searchtextfield.addTextChangedListener {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data!=null)
        {
            showNotes()
        }

    }
}