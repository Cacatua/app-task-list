package com.kktua.taskslist.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DbHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "DB_TASKS"
        const val TABLE_TASK = "task"
        private const val TAG = "INFO DB"

//        private var INSTANCE: DbHelper? = null

//        fun getDataBase(context: Context?): DbHelper {
//            if (INSTANCE == null) {
//                INSTANCE = DbHelper(context)
//            }
//            return INSTANCE as DbHelper
//        }
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        try {
            p0?.execSQL(showCreateTableTask())
            Log.i(TAG, "onCreate: Sucesso ao criar a tabela")
        } catch (e: Exception) {
            Log.i(TAG, "onCreate: Erro ao criar a tabela " + e.message)
        }
    }

    private fun showCreateTableTask() = "CREATE TABLE IF NOT EXISTS $TABLE_TASK " +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT" +
            ",nome TEXT NOT NULL );"

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        try {
            p0?.execSQL("DROP TABLE IF EXISTS $TABLE_TASK")
            onCreate(p0)
            Log.i(TAG, "onCreate: Sucesso ao atualizar app")
        } catch (e: Exception) {
            Log.i(TAG, "onCreate: Erro ao atualizar app " + e.message)
        }
    }
}