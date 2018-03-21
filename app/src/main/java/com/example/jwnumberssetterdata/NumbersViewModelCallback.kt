package com.example.jwnumberssetterdata

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.File
import javax.inject.Inject


class NumbersViewModelCallback(val context: Context) : CreateStoreCallback, InstallNumbersToStoreCallback {
    var activity: MainActivity? = null
    @Inject
    private lateinit var numbersRepository: NumbersRepository


    fun installNumbersToStore(login: String, password: String) {
        numbersRepository.installNumbersToStore(this, login, password, getAllNumbersFromFile())
    }

    fun createStore(login: String, password: String) {
        numbersRepository.createStore(this, login, password)
    }

    override fun onSuchCreateStore() {
        activity?.onSuchCreateStore()
    }

    override fun onFailCreateStore() {
        activity?.onFailCreateStore()
    }

    override fun onSuchInstallNumbersToStore() {
        activity?.onSuchInstallNumbersToStore()
    }

    override fun onFailInstallNumbersToStore() {
        activity?.onFailInstallNumbersToStore()
    }

    private fun getAllNumbersFromFile(): Map<String, Map<String, String>> {
        val result = hashMapOf<String, Map<String, String>>()
        val file = File(Environment.getExternalStorageDirectory(), "/data_for_jwHomeNumbers.txt")
        if (!file.exists()) return result

        for (currentLine in file.readLines(Charsets.UTF_8)) {
            currentLine.split("\t").let {
                if (it.size == 3) result[it[0]] = mapOf(Pair("name", it[1]), Pair("place", it[2]))
            }
        }
        Log.d("CountReadValidUsers", "count: " + result.size)
        return result
    }
}