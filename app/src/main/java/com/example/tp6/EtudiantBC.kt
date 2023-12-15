package com.example.tp6

import android.provider.BaseColumns

class EtudiantBC private constructor() {

    abstract class EtudiantEntry : BaseColumns {
        companion object {
            const val _ID = BaseColumns._ID
            const val TABLE_NAME = "etudiant"
            const val COLUMN_NAME_NOM = "nom"
            const val COLUMN_NAME_PRENOM = "prenom"
            const val COLUMN_NAME_PHONE = "phone"
            const val COLUMN_NAME_EMAIL = "email"
            const val COLUMN_NAME_LOGIN = "login"
            const val COLUMN_NAME_MDP = "mdp"
        }
    }

    companion object {
        const val TEXT_TYPE = " TEXT"
        const val INT_TYPE = " INTEGER"
        const val COMMA_SEP = ","
        const val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${EtudiantEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${EtudiantEntry.COLUMN_NAME_NOM}$TEXT_TYPE$COMMA_SEP" +
                    "${EtudiantEntry.COLUMN_NAME_PRENOM}$TEXT_TYPE$COMMA_SEP" +
                    "${EtudiantEntry.COLUMN_NAME_PHONE}$TEXT_TYPE$COMMA_SEP" +
                    "${EtudiantEntry.COLUMN_NAME_EMAIL}$TEXT_TYPE$COMMA_SEP" +
                    "${EtudiantEntry.COLUMN_NAME_LOGIN}$TEXT_TYPE$COMMA_SEP" +
                    "${EtudiantEntry.COLUMN_NAME_MDP}$TEXT_TYPE)"

        const val SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS ${EtudiantEntry.TABLE_NAME}"
    }
}