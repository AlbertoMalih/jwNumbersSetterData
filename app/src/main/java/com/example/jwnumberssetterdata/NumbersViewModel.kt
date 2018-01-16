package com.example.jwnumberssetterdata

import android.content.Context
import android.os.Environment
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.io.File
import java.nio.charset.Charset


class NumbersViewModel(val context: Context) {
    val fireAuth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()
    var currentId = ""

    fun getAllNumbersFromFile(): Map<String, Map<String, String>> {
        val result = hashMapOf<String, Map<String, String>>()
        val file = File(Environment.getExternalStorageDirectory(), "/Download/data_for_jwHomeNumbers.txt")
        if (!file.exists()) return result

        val lines = StringBuilder("")
        for (currentString in file.readLines(Charsets.UTF_8)) lines.append(currentString)
        lines.toString().split("*")
                .map { it.split("#") }
                .filter { it.size == 2 }
                .forEach { result.put(it[0], mapOf(Pair("place", it[1]))) }

        return result
    }

    fun openStore(activity: MainActivity, login: String, password: String) {
        fireAuth.signInWithEmailAndPassword(login, password).addOnCompleteListener({ task ->
            if (task.isSuccessful) {
                firestore.collection("cities_Homes_Numbers").document(fireAuth.currentUser!!.uid).get()
                        .addOnCompleteListener({ task ->
                            if (task.isSuccessful && task.result.exists()) {
                                currentId = fireAuth.currentUser!!.uid
                                activity.showSuchConnect(R.string.suches_open_store)
                                firestore.collection("cities_Homes_Numbers").document(currentId)
                                        .update(getAllNumbersFromFile())
                            }
                        })
            } else {
                activity.showNotSuchConnect(R.string.not_suches_open_store)
            }
        })
    }

    fun createStore(activity: MainActivity, login: String, password: String) {
        fireAuth.createUserWithEmailAndPassword(login, password).addOnCompleteListener({ task ->
            if (task.isSuccessful) {
                firestore.collection("cities_Homes_Numbers").document(fireAuth.currentUser!!.uid)
                        .set(mapOf(Pair("notValidObject", "NotValidValue")))
                        .addOnCompleteListener({ task ->
                            if (task.isSuccessful) {
                                currentId = fireAuth.currentUser!!.uid
                                activity.showSuchConnect(R.string.suches_create_store)
                            } else fireAuth.currentUser?.delete()
                        })
            } else activity.showNotSuchConnect(R.string.not_suches_create_store)
        })
    }
}