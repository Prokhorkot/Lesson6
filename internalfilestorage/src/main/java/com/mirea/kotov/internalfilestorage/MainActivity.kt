package com.mirea.kotov.internalfilestorage

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    companion object{
        private val TAG: String = MainActivity.javaClass.simpleName
    }

    private var tv: TextView? = null
    private val fileName: String = "mirea.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //region Defining variables

        tv = findViewById(R.id.textView)

        //endregion

        val string = "Hello mirea!"
        val outputStream: FileOutputStream

        try{
            outputStream = openFileOutput(fileName, MODE_PRIVATE)
            outputStream.write(string.toByteArray())
            outputStream.close()
        } catch(e: Exception){
            e.printStackTrace()
        }

        Thread(Runnable {
            try{
                TimeUnit.SECONDS.sleep(5)
            } catch(e: InterruptedException){
                e.printStackTrace()
            }

            tv!!.post(Runnable{tv!!.text = getTextFromFile()})
        }).start()
    }

    private fun getTextFromFile(): String? {
        var fin: FileInputStream? = null

        try{
            fin = openFileInput(fileName)
            var bytes: ByteArray = ByteArray(fin.available())

            fin.read(bytes)
            var text: String = String(bytes)

            Log.d(TAG, text)
            return text
        } catch(e: IOException){
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        } finally{
            try{
                fin?.close()
            } catch(e: IOException){
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }

        return null
    }
}