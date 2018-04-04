package com.example.jwnumberssetterdata

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

const val NAME_OF_USERS_COLLECTION = "cities_Homes_Numbers_test_1"

class NumbersRepository {
    private val fireAuth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val emptyField = mapOf(Pair("emptyObject", "emptyValue"))


    fun createStore(callback: CreateStoreCallback, login: String, password: String) {
        fireAuth.createUserWithEmailAndPassword(login, password).addOnSuccessListener {
            firestore.collection(NAME_OF_USERS_COLLECTION).document(fireAuth.currentUser!!.uid).set(emptyField)
                    .addOnSuccessListener {
                        firestore.collection(NAME_OF_USERS_COLLECTION).document(fireAuth.currentUser!!.uid).collection("cur_numbers").document("emptyObject").set(emptyField)
                                .addOnSuccessListener {
                                    callback.onSuchCreateStore()
                                }.addOnFailureListener { callback.onFailCreateStore() }
                    }.addOnFailureListener { callback.onFailCreateStore() }
        }.addOnFailureListener { callback.onFailCreateStore() }
    }

    fun installNumbersToStore(callback: InstallNumbersToStoreCallback, login: String, password: String, numbersFromFile: Map<String, Map<String, String>>) {
        fireAuth.signInWithEmailAndPassword(login, password).addOnSuccessListener {
            firestore.collection(NAME_OF_USERS_COLLECTION).document(fireAuth.currentUser!!.uid).collection("cur_numbers").get()
                    .addOnSuccessListener {
                        Observable.create<Boolean> { emitter ->
                            var isInsertedAllNumbers = true
                            for (numberPair in numbersFromFile) {
                                firestore.collection(NAME_OF_USERS_COLLECTION).document(fireAuth.currentUser!!.uid).collection("cur_numbers")
                                        .document(numberPair.key).set(numberPair.value).addOnFailureListener { isInsertedAllNumbers = false }
                            }
                            if (isInsertedAllNumbers) emitter.onComplete()
                            else emitter.onComplete()
                        }
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribeBy(
                                        onError = { callback.onFailInstallNumbersToStore() },
                                        onComplete = { callback.onSuchInstallNumbersToStore() }
                                )
                    }.addOnFailureListener { callback.onFailInstallNumbersToStore() }
        }.addOnFailureListener { callback.onFailInstallNumbersToStore() }
    }
}

interface InstallNumbersToStoreCallback {
    fun onSuchInstallNumbersToStore()
    fun onFailInstallNumbersToStore()
}

interface CreateStoreCallback {
    fun onSuchCreateStore()
    fun onFailCreateStore()
}