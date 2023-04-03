package com.example.task.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.task.repository.FirebaseInstanceDatabase
import com.google.firebase.database.DataSnapshot

class DatabaseViewModel : ViewModel {

    private var instance: FirebaseInstanceDatabase? = null
    var successAddUserDb: LiveData<Boolean>? = null
    var allUserList: LiveData<DataSnapshot>? = null
    var deleteUSer: LiveData<String>? = null


    constructor() {
        instance = FirebaseInstanceDatabase()
    }

    fun addUserDatabase(
        userId: String,
        first_name: String,
        last_name: String,
        gender: String,
        profession: String,
        timestamp: String,
    ) {
        successAddUserDb = instance!!.addUserInDatabase(
            userId,
            first_name, last_name, gender, profession, timestamp
        )
    }

    fun getAllUserList() {
        allUserList = instance!!.getAllUserList()
    }

    fun DeleteUSerById(id:String) {
        successAddUserDb = instance!!.DeleteUSerById(id)
    }


}