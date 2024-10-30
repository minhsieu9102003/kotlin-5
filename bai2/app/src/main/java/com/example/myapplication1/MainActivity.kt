package com.example.myapplication1

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    private lateinit var etSearch: EditText
    private lateinit var rvStudents: RecyclerView
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var studentList: List<Student>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the content view to activity_main.xml
        setContentView(R.layout.activity_main)

        // Initialize UI components
        etSearch = findViewById(R.id.etSearch)
        rvStudents = findViewById(R.id.rvStudents)

        // Initialize the student list
        initStudentList()

        // Set up the adapter with the initial full list
        studentAdapter = StudentAdapter(studentList)
        rvStudents.layoutManager = LinearLayoutManager(this)
        rvStudents.adapter = studentAdapter

        // Handle search functionality
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // No action needed here
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No action needed here
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val keyword = s.toString().trim()
                if (keyword.length > 2) {
                    // Filter the list
                    val filteredList = studentList.filter {
                        it.name.contains(keyword, ignoreCase = true) ||
                                it.mssv.contains(keyword, ignoreCase = true)
                    }
                    studentAdapter.updateList(filteredList)
                } else {
                    // Show the full list if keyword length <= 2
                    studentAdapter.updateList(studentList)
                }
            }
        })
    }

    // Initialize the list of students
    private fun initStudentList() {
        studentList = listOf(
            Student("Nguyễn Văn A", "MSSV001"),
            Student("Trần Thị B", "MSSV002"),
            Student("Lê Văn C", "MSSV003"),
            Student("Phạm Thị D", "MSSV004"),
            Student("Hoàng Văn E", "MSSV005"),
            Student("Đỗ Thị F", "MSSV006"),
            Student("Vũ Văn G", "MSSV007"),
            Student("Phan Thị H", "MSSV008"),
            Student("Bùi Văn I", "MSSV009"),
            Student("Đặng Thị J", "MSSV010")
        )
    }
}
