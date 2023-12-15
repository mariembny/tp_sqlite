package com.example.tp6

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Cursor
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditEtudiantActivity : AppCompatActivity() {
    private lateinit var nomEditText: EditText
    private lateinit var prenomEditText: EditText
    // Add other EditText fields for phone, email, login, and password

    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_etudiant)

        nomEditText = findViewById(R.id.editNom)
        prenomEditText = findViewById(R.id.editPrenom)
        // Initialize other EditText fields

        saveButton = findViewById(R.id.btnSave)
        cancelButton = findViewById(R.id.btnCancel)

        val studentId = intent.getIntExtra("STUDENT_ID", -1)

        if (studentId != -1) {
            // Load existing student data and populate EditText fields for editing
            loadStudentData(studentId)
        }

        saveButton.setOnClickListener {
            if (studentId != -1) {
                // Update existing student
                updateStudent(studentId)
            } else {
                // Add new student
                addStudent()
            }
        }

        cancelButton.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("Range")
    private fun loadStudentData(studentId: Int) {
        val dbHelper = EtudiantDBHelper(this)
        val db = dbHelper.readableDatabase

        // Retrieve student data from the database based on studentId
        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM ${EtudiantBC.EtudiantEntry.TABLE_NAME} WHERE ${EtudiantBC.EtudiantEntry._ID} = ?",
            arrayOf(studentId.toString())
        )

        if (cursor.moveToFirst()) {
            nomEditText.setText(cursor.getString(cursor.getColumnIndex(EtudiantBC.EtudiantEntry.COLUMN_NAME_NOM)))
            prenomEditText.setText(cursor.getString(cursor.getColumnIndex(EtudiantBC.EtudiantEntry.COLUMN_NAME_PRENOM)))
            // Populate other EditText fields
        }

        cursor.close()
    }

    private fun addStudent() {
        val dbHelper = EtudiantDBHelper(this)
        val db = dbHelper.writableDatabase

        // Insert a new student into the database using values from EditText fields
        val values = ContentValues().apply {
            put(EtudiantBC.EtudiantEntry.COLUMN_NAME_NOM, nomEditText.text.toString())
            put(EtudiantBC.EtudiantEntry.COLUMN_NAME_PRENOM, prenomEditText.text.toString())
            // Add other fields
        }

        val newRowId = db.insert(EtudiantBC.EtudiantEntry.TABLE_NAME, null, values)

        // Close the database
        db.close()

        // Finish the activity
        finish()
    }

    private fun updateStudent(studentId: Int) {
        val dbHelper = EtudiantDBHelper(this)
        val db = dbHelper.writableDatabase

        // Update existing student in the database based on studentId
        // Use values from EditText fields
        val values = ContentValues().apply {
            put(EtudiantBC.EtudiantEntry.COLUMN_NAME_NOM, nomEditText.text.toString())
            put(EtudiantBC.EtudiantEntry.COLUMN_NAME_PRENOM, prenomEditText.text.toString())
            // Add other fields
        }

        db.update(
            EtudiantBC.EtudiantEntry.TABLE_NAME,
            values,
            "${EtudiantBC.EtudiantEntry._ID} = ?",
            arrayOf(studentId.toString())
        )

        // Close the database
        db.close()

        // Finish the activity
        finish()
    }
}