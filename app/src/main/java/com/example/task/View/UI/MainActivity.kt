package com.example.task.View.UI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.task.R
import com.example.task.ViewModel.DatabaseViewModel
import com.example.task.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.vdx.designertoast.DesignerToast

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, View.OnClickListener,
    RadioGroup.OnCheckedChangeListener {

    private lateinit var databaseViewModel: DatabaseViewModel
    private lateinit var binding: ActivityMainBinding
    var selectPro: String? = "Select Profession"
    var gender: String? = ""
    private var view: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        view = binding.root
        FirebaseApp.initializeApp(this)

        databaseViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )
            .get(DatabaseViewModel::class.java)

        val languages = resources.getStringArray(R.array.professionList)
        val adapter = ArrayAdapter(
            this,
            R.layout.spinner_item_layout1, languages
        )
        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = this

        binding.btnSubmit.setOnClickListener(this)
        binding.btnSee.setOnClickListener(this)

        binding.rgGender.setOnCheckedChangeListener(this)


    }

    private fun addChatInDataBase(
        userId: String,
        first_name: String,
        last_name: String,
        gender: String,
        profession: String,
        timestamp: String
    ) {

        binding.pro.visibility = View.VISIBLE

        databaseViewModel.addUserDatabase(
            userId,
            first_name,
            last_name,
            gender,
            profession,
            timestamp
        )

        databaseViewModel.successAddUserDb?.observe(this, Observer {

            binding.pro.visibility = View.GONE
            if (it) {
                binding.edFirstName.setText("")
                binding.edLastName.setText("")
                binding.rgGender.clearCheck()
                binding.spinner.setSelection(0)
                binding.checkBox.isChecked = false
                DesignerToast.Success(
                    this,
                    "Submit successfully",
                    Gravity.TOP,
                    Toast.LENGTH_SHORT
                )
            } else {
                DesignerToast.Error(
                    this,
                    "Submit not successfully",
                    Gravity.TOP,
                    Toast.LENGTH_SHORT
                )
            }
        })

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        selectPro = p0?.selectedItem.toString()
        Log.e("datat", p0?.selectedItem.toString())
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {

            R.id.btn_submit -> {
                var first_name = binding.edFirstName.text.toString()
                var last_name = binding.edLastName.text.toString()

                if (first_name.isEmpty()) {
                    binding.edFirstName.requestFocus()
                    DesignerToast.Warning(
                        this,
                        "Please enter First name",
                        Gravity.TOP,
                        Toast.LENGTH_SHORT
                    )
                } else if (last_name.isEmpty()) {
                    binding.edLastName.requestFocus()
                    DesignerToast.Warning(
                        this,
                        "Please enter Last name",
                        Gravity.TOP,
                        Toast.LENGTH_SHORT
                    )

                } else if (gender.toString() == "") {
                    DesignerToast.Warning(
                        this,
                        "Please select gender",
                        Gravity.TOP,
                        Toast.LENGTH_SHORT
                    )
                } else if (selectPro.toString() == "Select Profession") {
                    binding.spinner.requestFocus()
                    DesignerToast.Warning(
                        this,
                        "Please select Profession",
                        Gravity.TOP,
                        Toast.LENGTH_SHORT
                    )

                } else if (binding.checkBox.isChecked == false) {
                    DesignerToast.Warning(
                        this,
                        "Please select terms and conditions",
                        Gravity.TOP,
                        Toast.LENGTH_SHORT
                    )

                } else {
                    val tsLong = System.currentTimeMillis()
                    var timeStamp = tsLong.toString()
                    addChatInDataBase(
                        timeStamp,
                        first_name,
                        last_name,
                        gender ?: "",
                        selectPro ?: "",
                        timeStamp
                    )

                }
            }

            R.id.btn_see -> {
                startActivity(Intent(this, SeeAllUser::class.java))
            }
        }
    }

    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
        val radioButton = view?.findViewById<RadioButton>(p1)
        gender = radioButton?.text.toString().toLowerCase()
    }
}