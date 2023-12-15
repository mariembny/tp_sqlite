package com.example.tp6
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class EtudiantDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "PFE.db"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(EtudiantBC.SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(EtudiantBC.SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun deleteEtudiantByLogin(login: String): Int {
        val db = writableDatabase
        val selection = "${EtudiantBC.EtudiantEntry.COLUMN_NAME_LOGIN} = ?"
        val selectionArgs = arrayOf(login)
        val deletedRows = db.delete(EtudiantBC.EtudiantEntry.TABLE_NAME, selection, selectionArgs)
        db.close()
        return deletedRows
    }
}