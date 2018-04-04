package com.example.jwnumberssetterdata

import android.content.Context
import android.os.Environment
import android.util.Log
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.io.File


class NumbersViewModel(val context: Context, val numbersRepository: NumbersRepository) : CreateStoreCallback, InstallNumbersToStoreCallback {
    var activity: MainActivity? = null


    fun installNumbersToStore(login: String, password: String) {
        Single.create<Map<String, Map<String, String>>> { emitter ->
            emitter.onSuccess(getAllNumbersFromFile())
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onError = { onFailInstallNumbersToStore() },
                        onSuccess = { result ->
                            numbersRepository.installNumbersToStore(this, login, password, result)
                        }
                )
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
                if (it.size == 3)
                    result[it[0]] = mapOf(Pair("name", it[1]), Pair("place", it[2]))
            }
        }
        Log.d("CountReadValidUsers", "count: " + result.size)
        return result
    }
}