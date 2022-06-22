package vhirata.com.geekout.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import vhirata.com.geekout.model.User

const val DB_NAME = "users.db"

// User Table
const val TABLE_USERS = "users"
const val COL_USER_ID = "userID_column"
const val COL_USERNAME = "username_column"
const val COL_PASSWORD = "password_column"

class UserDBHelper(var context: Context) : SQLiteOpenHelper(context, DB_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.disableWriteAheadLogging()

        val CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_USERS ($COL_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_USERNAME TEXT NOT NULL, $COL_PASSWORD TEXT NOT NULL);"

        db?.execSQL(CREATE_USERS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS;");

        onCreate(db)
    }

    fun insertData(user: User): Boolean {
        var db = this.writableDatabase
        val values = ContentValues()

        values.put(COL_USERNAME, user.username)
        values.put(COL_PASSWORD, user.password)

        val result = db.insert(TABLE_USERS, null, values)

        return result != (-1).toLong()

    }

    fun checkUserOnDB(username: String): Boolean {
        var db = this.writableDatabase
        var cursor = db.rawQuery("SELECT * FROM $TABLE_USERS WHERE $COL_USERNAME = ?", arrayOf(username))

        return cursor.count > 0
    }

    fun verifyLogin(user: User): Boolean {
        var db = this.writableDatabase

        var cursor = db.rawQuery("SELECT * FROM $TABLE_USERS WHERE $COL_USERNAME = ? AND $COL_PASSWORD = ?", arrayOf(user.username, user.password))

        return cursor.count > 0

    }

}