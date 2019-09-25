package com.treeforcom.koin_sample.utils

import java.text.SimpleDateFormat
import java.util.*

object DateHelper {
    fun getData(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        val date = Date()
        return dateFormat.format(date)
    }
}