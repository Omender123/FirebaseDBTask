package com.example.task.View.UI

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.task.Model.UserList
import com.example.task.databinding.ActivityUserDetailsBinding


class UserDetails : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = this.intent
        val bundle = intent.extras
        val data: UserList? = bundle!!.getSerializable("data") as UserList?

        Log.e("data", data.toString())

        binding.txtFirst.text = data?.first_name
        binding.txtLast.text = data?.last_name
        binding.txtGender.text = data?.gender
        binding.txtProfession.text = data?.profession


    }
}