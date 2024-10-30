package com.example.myapplication2

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // UI components
    private lateinit var etMssv: EditText
    private lateinit var etName: EditText
    private lateinit var rgGender: RadioGroup
    private lateinit var etEmail: EditText
    private lateinit var etPhone: EditText
    private lateinit var btnToggleCalendar: Button
    private lateinit var calendarView: CalendarView
    private lateinit var spinnerProvinces: Spinner
    private lateinit var spinnerDistricts: Spinner
    private lateinit var spinnerWards: Spinner
    private lateinit var cbSports: CheckBox
    private lateinit var cbMovies: CheckBox
    private lateinit var cbMusic: CheckBox
    private lateinit var cbAgree: CheckBox
    private lateinit var btnSubmit: Button

    // AddressHelper
    private lateinit var addressHelper: AddressHelper

    // Selected date
    private var selectedDate: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the content view to activity_main.xml
        setContentView(R.layout.activity_main)

        // Initialize UI components
        initUIComponents()

        if (btnToggleCalendar == null) {
            Log.d("MainActivity", "btnToggleCalendar is null")
        } else {
            Log.d("MainActivity", "btnToggleCalendar is initialized")
        }

        // Initialize AddressHelper
        addressHelper = AddressHelper(resources)

        // Set up address spinners
        setupProvinceSpinner()

        // Handle CalendarView toggle
        btnToggleCalendar.setOnClickListener {
            Log.d("MainActivity", "Button clicked")
            if (calendarView.visibility == View.GONE) {
                calendarView.visibility = View.VISIBLE
            } else {
                calendarView.visibility = View.GONE
            }
        }

        // Handle date selection
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            // You can format and store the selected date as needed
            selectedDate = calendarView.date
            btnToggleCalendar.text = "Ngày sinh: $dayOfMonth/${month + 1}/$year"
            calendarView.visibility = View.GONE
        }

        // Handle Submit button click
        btnSubmit.setOnClickListener {
            validateAndSubmit()
        }
    }

    private fun initUIComponents() {
        etMssv = findViewById(R.id.etMssv)
        etName = findViewById(R.id.etName)
        rgGender = findViewById(R.id.rgGender)
        etEmail = findViewById(R.id.etEmail)
        etPhone = findViewById(R.id.etPhone)
        btnToggleCalendar = findViewById(R.id.btnToggleCalendar)
        calendarView = findViewById(R.id.calendarView)
        spinnerProvinces = findViewById(R.id.spinnerProvinces)
        spinnerDistricts = findViewById(R.id.spinnerDistricts)
        spinnerWards = findViewById(R.id.spinnerWards)
        cbSports = findViewById(R.id.cbSports)
        cbMovies = findViewById(R.id.cbMovies)
        cbMusic = findViewById(R.id.cbMusic)
        cbAgree = findViewById(R.id.cbAgree)
        btnSubmit = findViewById(R.id.btnSubmit)
    }

    private fun setupProvinceSpinner() {
        val provinces = addressHelper.getProvinces()
        val provinceAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, provinces)
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerProvinces.adapter = provinceAdapter

        spinnerProvinces.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View?, position: Int, id: Long
            ) {
                val selectedProvince = provinces[position]
                setupDistrictSpinner(selectedProvince)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
    }

    private fun setupDistrictSpinner(province: String) {
        val districts = addressHelper.getDistricts(province)
        val districtAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, districts)
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDistricts.adapter = districtAdapter

        spinnerDistricts.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View?, position: Int, id: Long
            ) {
                val selectedDistrict = districts[position]
                setupWardSpinner(province, selectedDistrict)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
    }

    private fun setupWardSpinner(province: String, district: String) {
        val wards = addressHelper.getWards(province, district)
        val wardAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, wards)
        wardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerWards.adapter = wardAdapter
    }

    private fun validateAndSubmit() {
        // Validate inputs
        val mssv = etMssv.text.toString().trim()
        val name = etName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val phone = etPhone.text.toString().trim()

        val genderId = rgGender.checkedRadioButtonId
        val gender = when (genderId) {
            R.id.rbMale -> "Nam"
            R.id.rbFemale -> "Nữ"
            else -> ""
        }

        val province = spinnerProvinces.selectedItem as? String ?: ""
        val district = spinnerDistricts.selectedItem as? String ?: ""
        val ward = spinnerWards.selectedItem as? String ?: ""

        val hobbies = mutableListOf<String>()
        if (cbSports.isChecked) hobbies.add("Thể thao")
        if (cbMovies.isChecked) hobbies.add("Điện ảnh")
        if (cbMusic.isChecked) hobbies.add("Âm nhạc")

        // Initialize error flag
        var isValid = true

        // Validate each field
        if (mssv.isEmpty()) {
            etMssv.error = "Vui lòng nhập MSSV"
            isValid = false
        }

        if (name.isEmpty()) {
            etName.error = "Vui lòng nhập Họ tên"
            isValid = false
        }

        if (gender.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn Giới tính", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        if (email.isEmpty()) {
            etEmail.error = "Vui lòng nhập Email"
            isValid = false
        }

        if (phone.isEmpty()) {
            etPhone.error = "Vui lòng nhập Số điện thoại"
            isValid = false
        }

        if (selectedDate == 0L) {
            Toast.makeText(this, "Vui lòng chọn Ngày sinh", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        if (province.isEmpty() || district.isEmpty() || ward.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn Nơi ở hiện tại", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        if (hobbies.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn ít nhất một Sở thích", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        if (!cbAgree.isChecked) {
            Toast.makeText(this, "Vui lòng đồng ý với các điều khoản", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        if (isValid) {
            // All inputs are valid, proceed with submission
            Toast.makeText(this, "Thông tin đã được gửi thành công", Toast.LENGTH_SHORT).show()
            // You can proceed to send the data to a server or another activity
        }
    }
}
