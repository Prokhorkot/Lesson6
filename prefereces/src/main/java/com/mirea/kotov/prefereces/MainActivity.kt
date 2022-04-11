package com.mirea.kotov.prefereces

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private var editText: EditText? = null
    private var textView: TextView? = null
    private var buttonSave: Button? = null
    private var buttonLoad: Button? = null
    private var preferences: SharedPreferences? = null
    val SAVED_TEXT: String = "saved_text"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //region Defining variables

        editText = findViewById(R.id.editText)
        textView = findViewById(R.id.textView)
        buttonSave = findViewById(R.id.buttonSave)
        buttonLoad = findViewById(R.id.buttonLoad)
        preferences = getPreferences(MODE_PRIVATE)

        //endregion

        //region Event handlers

        buttonSave!!.setOnClickListener{onSaveText()}
        buttonLoad!!.setOnClickListener{onLoadText()}

        //endregion
    }

    private fun onSaveText(){
        val editor: SharedPreferences.Editor = preferences!!.edit()

        editor.putString(SAVED_TEXT, editText!!.text.toString())
        editor.apply()

        Toast.makeText(this, "Text saved", Toast.LENGTH_SHORT).show()
    }

    private fun onLoadText(){
        var text: String? = preferences?.getString(SAVED_TEXT, "Empty")
        textView!!.text = text
    }
}