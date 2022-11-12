package com.example.tp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp2.enums.GenderEnum
import com.example.tp2.enums.StatusEnum
import com.example.tp2.model.Student
import com.example.tp2.views.StudentListAdapter
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    val spinner: Spinner by lazy { findViewById(R.id.spinner) }
    val presentBtn : Button by lazy {findViewById(R.id.presentButton)}
    val absentBtn : Button by lazy {findViewById(R.id.absentButton)}
    lateinit var txtEdit: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var studentsCours = arrayListOf<Student>(
            Student("Wassim", "HENIA", GenderEnum.male, StatusEnum.present),
            Student("Oumayma", "aaaa", GenderEnum.female, StatusEnum.absent),
            Student("Oumayma 2", "sss", GenderEnum.female, StatusEnum.absent)
        )
        var studentsTp = arrayListOf<Student>(
            Student("Oussama", "lorum", GenderEnum.male, StatusEnum.absent),
            Student("Israa", "ipsum", GenderEnum.female, StatusEnum.present)
        )
        var adapters = arrayListOf<StudentListAdapter>(
            StudentListAdapter(studentsCours),
            StudentListAdapter(studentsTp)
        )
        var studentListAdapter: StudentListAdapter = adapters[0]
        var rvStudentsList = findViewById<View>(R.id.recyclerView) as RecyclerView
        var filter = studentListAdapter.filter
        rvStudentsList.adapter = studentListAdapter
        rvStudentsList.layoutManager = LinearLayoutManager(this)
        var matieres = listOf<String>("Cours", "Tp")
        spinner.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, matieres)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(
                    applicationContext,
                    "OnItemSelectedListener : " + adapterView?.getItemAtPosition(position)
                        .toString(),
                    Toast.LENGTH_SHORT
                ).show();
                studentListAdapter = adapters[position]
                rvStudentsList.adapter = studentListAdapter
                filter = studentListAdapter.filter
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }
        }
        txtEdit = findViewById(R.id.editTextTextPersonName)
        txtEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                filter.filter("name:$p0")
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        presentBtn.setOnClickListener{
            filter.filter("status:present")
        }
        absentBtn.setOnClickListener{
            filter.filter("status:absent")
        }
    }
}