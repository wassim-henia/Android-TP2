package com.example.tp2.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tp2.R
import com.example.tp2.enums.GenderEnum
import com.example.tp2.enums.StatusEnum
import com.example.tp2.model.Student
import java.util.*
import kotlin.collections.ArrayList

class StudentListAdapter(var students: ArrayList<Student>) :
    RecyclerView.Adapter<StudentListAdapter.ViewHolder>(), Filterable {
    var dataFilterList = ArrayList<Student>()

    init {
        dataFilterList = students
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.imageView)
        val textView = itemView.findViewById<TextView>(R.id.textView)
        val checkBoxPresent = itemView.findViewById<CheckBox>(R.id.checkBoxPresent)
        val checkBoxAbsent = itemView.findViewById<CheckBox>(R.id.checkBoxAbsent)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StudentListAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.student_item, parent, false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(viewHolder: StudentListAdapter.ViewHolder, position: Int) {
        val student: Student = dataFilterList.get(position)
        val textView = viewHolder.textView
        val imageView = viewHolder.imageView
        val checkBoxPresent = viewHolder.checkBoxPresent
        val checkBoxAbsent = viewHolder.checkBoxAbsent
        checkBoxAbsent.setOnClickListener{
            student.status = StatusEnum.absent
            checkBoxAbsent.isChecked = true
            checkBoxPresent.isChecked  = false
        }
        checkBoxPresent.setOnClickListener(){
            student.status = StatusEnum.present
            checkBoxAbsent.isChecked = false
            checkBoxPresent.isChecked  = true
        }
        textView.setText(student.name + ' ' + student.firstName)
        if (student.gender == GenderEnum.male) {
            imageView.setImageResource(R.drawable.man_student)
        } else {
            imageView.setImageResource(R.drawable.woman_student)
        }
        checkBoxPresent.isChecked = student.status.equals(StatusEnum.present)
        checkBoxAbsent.isChecked = student.status.equals(StatusEnum.absent)
    }

    override fun getItemCount(): Int {
        return dataFilterList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                for(student in students){
                    println(student.firstName)
                }
                var charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    dataFilterList = students
                } else {
                    var parts : List<String>  = charSearch.split(":")
                    val resultList = ArrayList<Student>()
                    charSearch = parts[1]
                    if(parts[0]=="name") {
                        for (student in students) {
                            if (student.firstName.lowercase(Locale.ROOT)
                                    .contains(charSearch.lowercase(Locale.ROOT)) || student.name.lowercase(
                                    Locale.ROOT
                                ).contains(charSearch.lowercase(Locale.ROOT))
                            ) {
                                resultList.add(student)
                            }
                        }
                    }
                    else {
                        for(student in students){
                            if(student.status.toString()==charSearch)
                                resultList.add(student)
                        }
                    }
                    dataFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = dataFilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                dataFilterList = results?.values as ArrayList<Student>
                notifyDataSetChanged()
            }

        }
    }
}