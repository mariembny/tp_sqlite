package com.example.tp6

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var Nom: EditText
    private lateinit var Prenom: EditText
    private lateinit var Tel: EditText
    private lateinit var Email: EditText
    private lateinit var Login: EditText
    private lateinit var MDP: EditText
    private lateinit var btnValider: Button
    private lateinit var btnAnnuler: Button
    private lateinit var btnSupprimer: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Nom = findViewById(R.id.Nom)
        Prenom = findViewById(R.id.Prenom)
        Tel = findViewById(R.id.Phone)
        Email = findViewById(R.id.Email)
        Login = findViewById(R.id.Login)
        MDP = findViewById(R.id.Mdp)
        btnValider = findViewById(R.id.btnValider)
        btnAnnuler = findViewById(R.id.btnAnnuler)
        btnSupprimer = findViewById(R.id.btnSupprimer)

        btnValider.setOnClickListener {
            Log.d("com.example.tp6.MainActivity", "Bouton Valider cliqué")

            if (champsSaisieVides()) {
                afficherDialogueChampsVides()
            } else {
                try {
                    insererEtudiant()
                    redirigerVersListeEtudiant()
                } catch (e: Exception) {
                    Log.e("com.example.tp6.MainActivity", "Erreur lors de l'insertion : ${e.message}")
                }
            }
        }

        btnAnnuler.setOnClickListener {
            afficherDialogueAnnulation()
        }

        btnSupprimer.setOnClickListener {
            // Handle deletion here, e.g., show a confirmation dialog
            afficherDialogueSuppression()
        }
    }
    private fun afficherDialogueSuppression() {
        AlertDialog.Builder(this)
            .setMessage("Voulez-vous vraiment supprimer cet étudiant?")
            .setTitle("Confirmation de suppression")
            .setNegativeButton("Non") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .setPositiveButton("Oui") { _, _ ->
                // Call the function to delete the student
                deleteEtudiant(Login.text.toString())
            }
            .show()
    }

    private fun deleteEtudiant(login: String) {
        val dbHelper = EtudiantDBHelper(this)
        val deletedRows = dbHelper.deleteEtudiantByLogin(login)

        if (deletedRows > 0) {
            // Deletion successful
            // Perform any additional actions if needed
            Toast.makeText(this, "Etudiant supprimé avec succès", Toast.LENGTH_SHORT).show()
        } else {
            // No rows deleted
            // Handle the case where no rows match the deletion criteria
            Toast.makeText(this, "Aucun étudiant trouvé pour la suppression", Toast.LENGTH_SHORT).show()
        }

        // Don't forget to close the database helper when you're done
        dbHelper.close()
    }
    private fun champsSaisieVides(): Boolean {
        return Nom.text.isEmpty() || Prenom.text.isEmpty() || Tel.text.isEmpty() ||
                Email.text.isEmpty() || Login.text.isEmpty() || MDP.text.isEmpty()
    }

    private fun afficherDialogueChampsVides() {
        AlertDialog.Builder(this)
            .setMessage("Tous les champs doivent être remplis")
            .setTitle("Attention")
            .setPositiveButton("OK") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .show()
    }

    private fun insererEtudiant() {
        val values = ContentValues().apply {
            put(EtudiantBC.EtudiantEntry.COLUMN_NAME_NOM, Nom.text.toString())
            put(EtudiantBC.EtudiantEntry.COLUMN_NAME_PRENOM, Prenom.text.toString())
            put(EtudiantBC.EtudiantEntry.COLUMN_NAME_PHONE, Tel.text.toString())
            put(EtudiantBC.EtudiantEntry.COLUMN_NAME_EMAIL, Email.text.toString())
            put(EtudiantBC.EtudiantEntry.COLUMN_NAME_LOGIN, Login.text.toString())
            put(EtudiantBC.EtudiantEntry.COLUMN_NAME_MDP, MDP.text.toString())
        }

        val mDbHelper = EtudiantDBHelper(applicationContext)
        val db = mDbHelper.writableDatabase

        try {
            db.insert(EtudiantBC.EtudiantEntry.TABLE_NAME, null, values)
        } finally {
            db.close()
            mDbHelper.close()
        }
    }

    private fun redirigerVersListeEtudiant() {
        val intent = Intent(this, ListEtudiant::class.java)
        startActivity(intent)
    }

    private fun afficherDialogueAnnulation() {
        AlertDialog.Builder(this)
            .setMessage("Voulez-vous vraiment annuler?")
            .setTitle("Confirmation")
            .setNegativeButton("Non") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .setPositiveButton("Oui") { _, _ ->
                // Action à effectuer si l'utilisateur clique sur "Oui"
                viderChampsSaisie()
            }
            .show()
    }

    private fun viderChampsSaisie() {
        Nom.text.clear()
        Prenom.text.clear()
        Tel.text.clear()
        Email.text.clear()
        Login.text.clear()
        MDP.text.clear()
    }
}
