package com.example.task.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import java.util.*

class FirebaseInstanceDatabase {

    private val instance = FirebaseDatabase.getInstance()

    fun addUserInDatabase(
        userId: String,
        first_name: String,
        last_name: String,
        gender: String,
        profession: String,
        timestamp: String,
    ): MutableLiveData<Boolean>? {
        val successAddUserDb = MutableLiveData<Boolean>()
        val hashMap = HashMap<String, String>()
        hashMap["id"] = userId
        hashMap["first_name"] = first_name
        hashMap["last_name"] = last_name
        hashMap["gender"] = gender
        hashMap["profession"] = profession
        hashMap["timestamp"] = timestamp
        instance.getReference("Users").child(userId).setValue(hashMap).addOnCompleteListener {
            successAddUserDb.value = true
        }.addOnFailureListener { successAddUserDb.value = false }
        return successAddUserDb
    }

    fun getAllUserList(): MutableLiveData<DataSnapshot>? {
        val getAllUserList = MutableLiveData<DataSnapshot>()
        val chatRef = instance.getReference("Users")
        chatRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                getAllUserList.setValue(dataSnapshot)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
        return getAllUserList
    }

    fun DeleteUSerById(id: String): MutableLiveData<Boolean>? {
        val successAddUserDb = MutableLiveData<Boolean>()
        val chatRef = instance.getReference("Users")
        val query: Query = chatRef.child(id)
        query.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    successAddUserDb.value = true
                    snapshot.getRef().removeValue();
                }else{
                    successAddUserDb.value = false

                }

            }

            override fun onCancelled(error: DatabaseError) {
                successAddUserDb.value = false
            }

        })

        return successAddUserDb
      }

}