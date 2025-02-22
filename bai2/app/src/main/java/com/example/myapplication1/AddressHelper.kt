package com.example.myapplication1

import android.content.res.Resources
import org.json.JSONObject
import java.io.InputStreamReader

class AddressHelper(resources: Resources) {

    private val data: JSONObject

    init {
        val inputStream = resources.openRawResource(R.raw.tree)
        val reader = InputStreamReader(inputStream)
        val content = reader.readText()
        reader.close()

        data = JSONObject(content)
    }

    fun getProvinces(): List<String> {
        val list = mutableListOf<String>()
        val keys = data.keys()
        for (key in keys)
            list.add(data.getJSONObject(key).getString("name"))
        return list
    }

    fun getDistricts(province: String): List<String> {
        val list = mutableListOf<String>()
        var jProvince: JSONObject? = null

        val keys = data.keys()
        for (key in keys)
            if (data.getJSONObject(key).getString("name") == province) {
                jProvince = data.getJSONObject(key)
                break
            }

        if (jProvince != null) {
            val jDistricts = jProvince.getJSONObject("quan-huyen")
            val districtKeys = jDistricts.keys()
            for (key in districtKeys)
                list.add(jDistricts.getJSONObject(key).getString("name"))
        }

        return list
    }

    fun getWards(province: String, district: String): List<String> {
        val list = mutableListOf<String>()
        var jProvince: JSONObject? = null
        var jDistrict: JSONObject? = null

        val provinceKeys = data.keys()
        for (key in provinceKeys)
            if (data.getJSONObject(key).getString("name") == province) {
                jProvince = data.getJSONObject(key)
                break
            }

        if (jProvince != null) {
            val jDistricts = jProvince.getJSONObject("quan-huyen")
            val districtKeys = jDistricts.keys()
            for (key in districtKeys)
                if (jDistricts.getJSONObject(key).getString("name") == district) {
                    jDistrict = jDistricts.getJSONObject(key)
                    break
                }
        }

        if (jDistrict != null) {
            val jWards = jDistrict.getJSONObject("xa-phuong")
            val wardKeys = jWards.keys()
            for (key in wardKeys)
                list.add(jWards.getJSONObject(key).getString("name"))
        }

        return list
    }
}
