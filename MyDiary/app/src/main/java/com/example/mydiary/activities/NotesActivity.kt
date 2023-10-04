package com.example.mydiary.activities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.mydiary.R
import com.example.mydiary.database.NoteDataBase
import com.example.mydiary.databinding.ActivityNotesBinding
import com.example.mydiary.entities.Note
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date

class NotesActivity : AppCompatActivity() {
    var color:Int=Color.BLUE
    var imagepath:String=""

    private  lateinit var binding: ActivityNotesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityNotesBinding.inflate(layoutInflater)
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm")
        val currentDate = sdf.format(Date())
        binding.datetext.text=currentDate

        binding.save.setOnClickListener {
            println("save..................")

            saveNote()

        }

       notifyColor()
        showBottomSheet()
        showpic()


        setContentView(binding.root)
    }

    fun showBottomSheet()
    {
        binding.bottompart.bottosheetlayout.setOnClickListener {
            val bottomsheet=MyBottomSheetFragment()
            bottomsheet.show(supportFragmentManager,"BottomSheetDialog")
        }
    }



    fun notifyColor()
    {
        MyBottomSheetFragment.colorLiveState.observe(this) {

            binding.imageView6.setBackgroundColor(it)
            color=it
        }
    }
    private fun saveNote()
    {
        class someTask() : AsyncTask<Void, Void, String>() {
            var title=binding.notetitle.text.toString()
            var subtitle=binding.notesubtitle.text.toString()
            var date=binding.datetext.text.toString()
            var imgpat=imagepath
            var text=binding.notetext.text.toString()

            override fun doInBackground(vararg p0: Void?): String {
               var db= NoteDataBase.invoke(context = this@NotesActivity)
             db.getNoteeDao().insertToDb(Note(title = title, notetext = text, subtitle = subtitle, date_Time = date, imagepath = imgpat, color = color))
                return ""
            }

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                Toast.makeText(this@NotesActivity,"Note Saved",Toast.LENGTH_SHORT).show()

                setResult(1, Intent())
                finish()
            }

        }
        someTask().execute()

    }

    fun showpic()
    {
        MyBottomSheetFragment.picLiveState.observe(this) {

            imagepath=it
            binding.noteimage.visibility=View.VISIBLE
           var imagebit=prepareImage(Uri.parse(it),this)
            binding.noteimage.setImageBitmap(imagebit)
        }
    }

    companion object{
        fun prepareImage(imageUri: Uri,cxt:Context): Bitmap? {
            getInputStream(imageUri,cxt)?.let {
                val  imagebitmp=BitmapFactory.decodeStream(it)

                return imagebitmp
            }?:return null
        }

        fun getInputStream(imageUri: Uri,cxt:Context): InputStream?
        {
            return cxt.contentResolver.openInputStream(imageUri)
        }
    }


}