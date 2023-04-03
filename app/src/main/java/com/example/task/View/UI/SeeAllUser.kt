package com.example.task.View.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task.Model.UserList
import com.example.task.R
import com.example.task.View.Adapters.UserAdapter
import com.example.task.ViewModel.DatabaseViewModel
import com.example.task.databinding.ActivityMainBinding
import com.example.task.databinding.ActivitySeeAllUser2Binding
import com.google.firebase.FirebaseApp
import com.vdx.designertoast.DesignerToast

class SeeAllUser : AppCompatActivity(), UserAdapter.OnUserListener {
    private lateinit var databaseViewModel: DatabaseViewModel
    private lateinit var binding: ActivitySeeAllUser2Binding
    private lateinit var userList: ArrayList<UserList>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeeAllUser2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)

        databaseViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )
            .get(DatabaseViewModel::class.java)


        userList = ArrayList<UserList>()

        fetchAlluser()
    }

    private fun fetchAlluser() {
        binding.pro.visibility = View.VISIBLE
        databaseViewModel.getAllUserList()
        databaseViewModel.allUserList?.observe(this, Observer {
            userList.clear()
            binding.pro.visibility = View.GONE
            for (dataSnapshot1 in it.children) {
                val chatList: UserList = dataSnapshot1.getValue(UserList::class.java)!!
                userList.add(chatList)
            }
            if (userList.size > 0) {
                val mLayoutManager1: RecyclerView.LayoutManager =
                    LinearLayoutManager(
                        this,
                        RecyclerView.VERTICAL,
                        false
                    )
                binding.recyclerView.setLayoutManager(mLayoutManager1)
                binding.recyclerView.setItemAnimator(DefaultItemAnimator())
                var adapter =
                    UserAdapter(this, userList, this);
                binding.recyclerView.setAdapter(adapter)

            } else {
                DesignerToast.Success(
                    this,
                    "Data is not exists",
                    Gravity.TOP,
                    Toast.LENGTH_SHORT
                )

            }

        })

        databaseViewModel.successAddUserDb?.observe(this, Observer {

            binding.pro.visibility = View.GONE
            if (it) {
                DesignerToast.Warning(
                    this,
                    "Delete SuccessFully",
                    Gravity.TOP,
                    Toast.LENGTH_SHORT
                )
            } else {
                DesignerToast.Warning(
                    this,
                    "Data Not Exists",
                    Gravity.TOP,
                    Toast.LENGTH_SHORT
                )
            }


        })

    }

    override fun onUserDetailsClickedListener(position: Int, data1: ArrayList<UserList>) {
        var data = Bundle()
        data.putSerializable("data",data1[position])
        startActivity(Intent(this,UserDetails::class.java).putExtras(data))
    }

    override fun onUserDeleteClickedListener(position: Int, data: ArrayList<UserList>) {

        binding.pro.visibility = View.VISIBLE
        databaseViewModel.DeleteUSerById(data[position].id.toString())


    }
}