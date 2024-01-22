package com.ntnu.ivansh.filmapplicationdata

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.google.gson.Gson
import java.io.File
import java.io.FileWriter
import java.io.InputStream
import java.io.OutputStreamWriter
import java.io.PrintWriter

class Database(context: Context) : DatabaseManager(context) {

	init {
		try {
			this.clear()
			val assetManager = context.assets
			val inputStream: InputStream = assetManager.open("db.txt")
			val outputStreamWriter = OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE))

			inputStream.reader().useLines { lines ->
				lines.forEach { line ->
					try {
						outputStreamWriter.write(line + "\n");
					}catch (e:Exception){
						Log.e("Error Writer",e.message.toString())
					}

					val values = line.split(",")
					if (values.size >= 4) {
						this.insert(values[0], values[1], listOf(values[2], values[3]))
					} else {
						Log.e("Error", "Invalid line format: $line")
					}
				}
			}
			outputStreamWriter.close();

		} catch (e: Exception) {
			e.printStackTrace()
		}
	}

	val allTitles: ArrayList<String>
		get() = performQuery(TABLE_FILM, arrayOf(FILM_TITLE))
	val allFilmsWithActors: ArrayList<String>
		get() = performQuery(TABLE_FILM, arrayOf(FILM_TITLE, FILM_ACTORS))



	fun getAllFilmsByDirector(directorName: String): ArrayList<String> {
		val selection = "$FILM_DIRECTOR = '$directorName'"

		return performQuery(TABLE_FILM, arrayOf(FILM_TITLE), selection)
	}



}
