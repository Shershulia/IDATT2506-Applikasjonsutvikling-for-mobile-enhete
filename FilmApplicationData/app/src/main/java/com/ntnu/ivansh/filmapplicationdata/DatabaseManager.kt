package com.ntnu.ivansh.filmapplicationdata

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.google.gson.Gson

open class DatabaseManager(context: Context) :
		SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

	companion object {

		const val DATABASE_NAME = "FilmDatabase"
		const val DATABASE_VERSION = 1

		const val ID = "_id"

		const val TABLE_FILM = "FILM"
		const val FILM_TITLE = "title"
		const val FILM_DIRECTOR = "director"
		const val FILM_ACTORS = "actors"

	}

	/**
	 *  Create the tables
	 */
	override fun onCreate(db: SQLiteDatabase) {
		db.execSQL(
				"""create table $TABLE_FILM (
						$ID integer primary key autoincrement, 
						$FILM_TITLE text unique not null,
						$FILM_DIRECTOR text not null,
						$FILM_ACTORS text not null
						);"""
		          )

	}


	override fun onUpgrade(db: SQLiteDatabase, arg1: Int, arg2: Int) {
		db.execSQL("DROP TABLE IF EXISTS $TABLE_FILM")
		onCreate(db)
	}


	fun clear() {
		writableDatabase.use { onUpgrade(it, 0, 0) }
	}



	fun insert(title: String, director: String, actors: List<String>) {

		val jsonActors = Gson().toJson(actors)
		val values = ContentValues()
		values.put(FILM_TITLE,title)
		values.put(FILM_DIRECTOR,director)
		values.put(FILM_ACTORS,jsonActors)
		this.writableDatabase.use {
			it.insert(TABLE_FILM,null,values)
		}
	}




	/**
	 * Perform a simple query
	 *
	 * Not the query() function has almost all parameters as *null*, you should check up on these.
	 * maybe you don't even need the performRawQuery() function?
	 */
	fun performQuery(table: String, columns: Array<String>, selection: String? = null):
			ArrayList<String> {
		assert(columns.isNotEmpty())
		readableDatabase.use { database ->
			query(database, table, columns, selection).use { cursor ->
				return readFromCursor(cursor, columns.size)
			}
		}
	}

	/**
	 * Read the contents from the cursor and return it as an arraylist
	 */
	private fun readFromCursor(cursor: Cursor, numberOfColumns: Int): ArrayList<String> {
		val result = ArrayList<String>()
		cursor.moveToFirst()
		while (!cursor.isAfterLast) {
			val item = StringBuilder("")
			for (i in 0 until numberOfColumns) {
				item.append("${cursor.getString(i)} ")
			}
			result.add("$item")
			cursor.moveToNext()
		}
		return result
	}

	/**
	 * Use a query with default arguments
	 */
	private fun query(
		database: SQLiteDatabase, table: String, columns: Array<String>, selection: String?
	                 ): Cursor {
		return database.query(table, columns, selection, null, null, null, null, null)
	}
}
