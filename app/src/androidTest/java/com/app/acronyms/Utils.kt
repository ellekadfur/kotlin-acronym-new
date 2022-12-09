package com.app.acronyms

import java.io.InputStreamReader

object Utils {
    fun getJsonContent(fileName: String): String {
        return InputStreamReader(this.javaClass.classLoader!!.getResourceAsStream(fileName)).use { it.readText() }
    }

}