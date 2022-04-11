package com.mirea.kotov.notebook

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private var saveTextButton: Button? = null
    private var editTextFileName: EditText? = null
    private var editTextFileContent: EditText? = null
    private var preferences: SharedPreferences? = null
    private val SAVED_NOTE = "saved_note"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //region Defining variables

        saveTextButton = findViewById(R.id.saveTextButton)
        editTextFileName = findViewById(R.id.editTextFileName)
        editTextFileContent = findViewById(R.id.editTextFileContent)
        preferences = getPreferences(MODE_PRIVATE)

        //endregion

        saveTextButton!!.post(Runnable {
            saveTextButton!!.setOnClickListener{onSaveText()}
        })
        editTextFileContent!!.post(Runnable{
            editTextFileName!!.setText(readTextFromFile()[0])
        })
        editTextFileContent!!.post(Runnable{
            editTextFileContent!!.setText(readTextFromFile()[1])
        })
    }

    private fun onSaveText(){
        var fileName: String = editTextFileName!!.text.toString() + ".txt"
        var fileContent: String = editTextFileContent!!.text.toString()

        val editor: SharedPreferences.Editor = preferences!!.edit()

        editor.putString(SAVED_NOTE, fileName)
        editor.apply()

        val outputStream: FileOutputStream

        try{
            outputStream = openFileOutput(fileName, MODE_PRIVATE)
            outputStream.write(fileContent.toByteArray())
            outputStream.close()

        } catch(e: Exception){
            e.printStackTrace()
        }

        Toast.makeText(this, "Text saved", Toast.LENGTH_SHORT).show()
    }

    private fun readTextFromFile(): Array<String?>{
        val defaultName: String = "Имя файла"
        val defaultContent: String = "Текст"
        var result: Array<String?> = arrayOf(defaultName, defaultContent)

        var fileName: String? = preferences?.getString(SAVED_NOTE, defaultName)
        if(fileName == "Имя файла") return result

        var fin: FileInputStream? = null
        try {
            fin = openFileInput(fileName)
            var bytes: ByteArray = ByteArray(fin.available())

            fin.read(bytes)
            var text: String? = String(bytes)

            result = arrayOf(fileName, text)
            return result

        } catch(e: IOException) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            Log.e("reading file", e.message!!)
        } finally {
            try {
                fin?.close()
            } catch (e: IOException) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                Log.e("reading file", e.message!!)
            }
        }
        return arrayOf(defaultName, defaultContent)
    }
}