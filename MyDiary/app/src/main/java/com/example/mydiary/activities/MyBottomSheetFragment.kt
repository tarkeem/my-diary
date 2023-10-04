package com.example.mydiary.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mydiary.R
import com.example.mydiary.databinding.ColorbottomsheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MyBottomSheetFragment: BottomSheetDialogFragment()
{
    companion object{
        private val colorstate: MutableLiveData<Int> by lazy {
            MutableLiveData()
        }
        val  colorLiveState:LiveData<Int>get() = colorstate


        private val picstate: MutableLiveData<String> by lazy {
            MutableLiveData()
        }
        val  picLiveState:LiveData<String>get() = picstate
    }
    lateinit var binding: ColorbottomsheetBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= ColorbottomsheetBinding.inflate(layoutInflater)

        binding.textView2.setOnClickListener {
            println("jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj")
        }
        chooseColor()
        choosePic()
        return binding.root
    }


    fun chooseColor()
    {
        binding.color2.setOnClickListener {
            binding.frame1.removeView(binding.doneimage)
            binding.frame3.removeView(binding.doneimage)
            binding.frame2.addView(binding.doneimage)

           colorstate.postValue(Color.RED)
        }
        binding.color1.setOnClickListener {
            binding.frame2.removeView(binding.doneimage)
            binding.frame3.removeView(binding.doneimage)
            binding.frame1.addView(binding.doneimage)

            colorstate.postValue(Color.BLUE)
        }
        binding.color3.setOnClickListener {
            binding.frame2.removeView(binding.doneimage)
            binding.frame1.removeView(binding.doneimage)
            binding.frame3.addView(binding.doneimage)
            colorstate.postValue(Color.YELLOW)
        }


    }

    fun choosePic()
    {
        binding.imagchoose.setOnClickListener {
            var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.
            EXTERNAL_CONTENT_URI).also { intenter ->
                intenter.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                startActivityForResult(intenter, 3)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        data!!.data.let {
            picstate.postValue(it.toString())
            println("...............................")
            println(it.toString())
        }

    }

}