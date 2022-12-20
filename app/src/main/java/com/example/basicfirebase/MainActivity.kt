package com.example.basicfirebase

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.basicfirebase.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = Firebase.database.reference
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val data = snapshot.getValue(String::class.java)
                    binding.textData.text = "Firebase Remote $data"
                } else {
                    binding.textData.text = "No data available"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@MainActivity,
                    "There was a mistake reading the data",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        val dataRef = database.child("hola_firebase").child("data")
        dataRef.addValueEventListener(listener)

        binding.btnSend.setOnClickListener {
            val data = binding.etData.text.toString()
            dataRef.setValue(data)
                .addOnSuccessListener {
                    Toast.makeText(
                        this@MainActivity,
                        "Data saved",
                        Toast.LENGTH_LONG
                    ).show()
                }
                .addOnFailureListener {
                    Toast.makeText(
                        this@MainActivity,
                        "There was a mistake updating the data",
                        Toast.LENGTH_LONG
                    ).show()
                }
                .addOnCompleteListener {
                    Toast.makeText(
                        this@MainActivity,
                        "Data complete",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }

        binding.btnSend.setOnLongClickListener {
            dataRef.removeValue()
            true
        }
    }
}
