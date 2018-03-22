package com.example.jwnumberssetterdata

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), CreateStoreCallback, InstallNumbersToStoreCallback {
    @Inject
    lateinit var viewModel: NumbersViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.INSTANCE.appComponent().inject(this)
        viewModel.activity = this
        usersEmail.setText(getPreferences(Context.MODE_PRIVATE).getString("login", ""))
        usersPassword.setText(getPreferences(Context.MODE_PRIVATE).getString("password", ""))

        createStore.setOnClickListener({
            val login = usersEmail.text.toString()
            val password = usersPassword.text.toString()
            if (password.isEmpty() || login.isEmpty()) return@setOnClickListener
            getPreferences(Context.MODE_PRIVATE).edit().putString("login", login).putString("password", password).apply()
            viewModel.createStore(login, password)
        })

        openStore.setOnClickListener({
            val login = usersEmail.text.toString()
            val password = usersPassword.text.toString()
            if (password.isEmpty() || login.isEmpty()) return@setOnClickListener
            getPreferences(Context.MODE_PRIVATE).edit().putString("login", login).putString("password", password).apply()
            viewModel.installNumbersToStore(login, password)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.activity = null
    }

    override fun onSuchCreateStore() {
        showSuchConnect(R.string.suches_create_store)
    }

    override fun onFailCreateStore() {
        showNotSuchConnect(R.string.not_suches_create_store)
    }

    override fun onSuchInstallNumbersToStore() {
        showSuchConnect(R.string.suches_open_store)
    }

    override fun onFailInstallNumbersToStore() {
        showNotSuchConnect(R.string.not_suches_open_store)
    }

    private fun showNotSuchConnect(phrase: Int) {
        indicatorOfResultConnects.text = this@MainActivity.resources.getString(phrase)
        indicatorOfResultConnects.setTextColor(ContextCompat.getColor(this@MainActivity, android.R.color.holo_red_light))
    }

    private fun showSuchConnect(phrase: Int) {
        indicatorOfResultConnects.text = this@MainActivity.resources.getString(phrase)
        indicatorOfResultConnects.setTextColor(ContextCompat.getColor(this@MainActivity, android.R.color.holo_green_light))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.instruction_menu_item -> {
                val view = TextView(this)
                view.textSize = 20F
                view.setPadding(20, 20, 20, 20)
                view.setText(R.string.instruction)
                AlertDialog.Builder(this).setView(view).create().show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}