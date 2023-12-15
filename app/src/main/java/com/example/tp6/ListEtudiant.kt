package com.example.tp6

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import androidx.appcompat.app.AppCompatActivity

class ListEtudiant : AppCompatActivity() {
    private lateinit var listEtudiant: ListView
    private lateinit var adapter: SimpleCursorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_etudiant)

        listEtudiant = findViewById(R.id.idlistetu)
        adapter = createAdapter()
        listEtudiant.adapter = adapter

        listEtudiant.setOnItemClickListener { _, _, position, _ ->
            val studentId = adapter.getItemId(position).toInt()
            openEditActivity(studentId)
        }
    }

    private fun createAdapter(): SimpleCursorAdapter {
        val dbHelper = EtudiantDBHelper(this)
        val db = dbHelper.readableDatabase

        val cursor: Cursor = db.rawQuery("SELECT * FROM ${EtudiantBC.EtudiantEntry.TABLE_NAME}", null)

        val fromColumns = arrayOf(
            EtudiantBC.EtudiantEntry.COLUMN_NAME_NOM,
            EtudiantBC.EtudiantEntry.COLUMN_NAME_PRENOM
        )

        val toViews = intArrayOf(R.id.nom, R.id.prenom)

        val adapter = SimpleCursorAdapter(
            this,
            R.layout.ligne_etudiant,
            cursor,
            fromColumns,
            toViews,
            0
        )

        // Set the click listener in the getView method
        adapter.viewBinder = object : SimpleCursorAdapter.ViewBinder {
            override fun setViewValue(view: View?, cursor: Cursor?, columnIndex: Int): Boolean {
                if (view?.id == R.id.btnEdit) {
                    view.setOnClickListener {
                        val position = (view.parent as AdapterView<*>).getPositionForView(view)
                        val studentId = adapter.getItemId(position).toInt()
                        openEditActivity(studentId)
                    }
                    return true
                }
                return false
            }
        }

        return adapter
    }

    private fun openEditActivity(studentId: Int) {
        val intent = Intent(this, EditEtudiantActivity::class.java)
        intent.putExtra("STUDENT_ID", studentId)
        startActivity(intent)
    }

    private fun getDbHelper(): EtudiantDBHelper {
        return EtudiantDBHelper(this)
    }
}
