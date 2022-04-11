package com.mirea.kotov.room

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private fun TAG(): String = MainActivity::class.simpleName!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db: AppDatabase? = App.getInstance().getDatabase()
        val employeeDao = db?.employeeDao()
        var employee: Employee? = Employee(null,null,null)
        employee!!.id = 1
        employee!!.name = "John Smith"
        employee!!.salary = 10000

        employeeDao?.insert(employee)

        val employees = employeeDao?.getAll()

        employee = employeeDao?.getById(1)

        employee!!.salary = 20000
        employeeDao?.update(employee)
        Log.d(TAG(), employee!!.name + " " + employee!!.salary)
    }
}