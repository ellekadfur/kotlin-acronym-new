package com.app.acronyms

import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.lang.reflect.Type


const val ASSET_BASE_PATH = "../app/src/main/assets/"

@Throws(IOException::class)
fun readJsonFile(filename: String): String? {
    val br = BufferedReader(InputStreamReader(FileInputStream(ASSET_BASE_PATH + filename)))
    val sb = StringBuilder()
    var line = br.readLine()
    while (line != null) {
        sb.append(line)
        line = br.readLine()
    }
    return sb.toString()
}


fun <T> read(filename: String, clazz: Type): T? {
    val gson = GsonBuilder().create()
    val readJsonFile = readJsonFile("$filename.json")
    return gson.fromJson<T>(readJsonFile, clazz) as T
}