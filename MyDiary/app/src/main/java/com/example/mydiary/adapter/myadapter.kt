package com.example.mydiary.adapter

import android.app.PendingIntent.getActivity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mydiary.R
import com.example.mydiary.activities.NotesActivity
import com.example.mydiary.entities.Note
import com.makeramen.roundedimageview.RoundedImageView


class myCustomAdapter(var notes:List<Note>,var cxt:Context) : RecyclerView.Adapter<myCustomAdapter.ViewHolder>() {


    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_container, parent, false)

        return ViewHolder(view)
    }


    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView){
        val title: TextView = itemView.findViewById(R.id.containertitle)
        val date: TextView = itemView.findViewById(R.id.containerdate)
        val subtitle: TextView = itemView.findViewById(R.id.containersubtitle)
        val notetext: TextView = itemView.findViewById(R.id.containertext)
        val background:LinearLayout=ItemView.findViewById(R.id.notebackground)
        val noteimage: RoundedImageView = itemView.findViewById(R.id.notecontainerimage)



    }



    // return the number of the items in the list
    override fun getItemCount(): Int {
        return notes.size
    }


    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val mynote = notes[position]

        // sets the image to the imageview from our itemHolder class
        holder.title.text=mynote.title
        holder.subtitle.text=mynote.subtitle
        holder.notetext.text=mynote.notetext
        holder.date.text=mynote.date_Time
        //val drawable: Drawable? = ResourcesCompat.getDrawable(cxt.resources, R.drawable.redcolor, null)

        var drw:Drawable= holder.background.background
        drw.setTint(mynote.color)
        holder.background.background=drw
        println("note data..........................")
        println(mynote.toString())
        if (!(mynote.imagepath==""||mynote.imagepath.isEmpty()))
        {

            holder.noteimage.visibility=View.VISIBLE
            var imageBitmap: Bitmap? =NotesActivity.prepareImage(Uri.parse(mynote.imagepath),cxt)
            if(imageBitmap==null)
            {
                holder.noteimage.setImageResource(R.drawable.ic_baseline_image_not_supported_24)
            }
            else
            {
                holder.noteimage.setImageBitmap(imageBitmap)
            }

        }









    }



}