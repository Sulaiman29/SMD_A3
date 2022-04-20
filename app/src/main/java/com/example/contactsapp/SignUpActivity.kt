package com.example.contactsapp

import android.content.Intent
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast

class SignUpActivity : AppCompatActivity() {

    private lateinit var edName: EditText
    private lateinit var edPhone: EditText
    private lateinit var edAddress: EditText
    private lateinit var edBio: EditText
    private lateinit var btnAdd: ImageButton
    private lateinit var btnViewContacts: Button

    private lateinit var sqliteHelper: DBHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        initView()
        sqliteHelper = DBHelper(this)

        btnAdd.setOnClickListener { addContact() }

        //Setting up connection from this activity to Main activity
        btnViewContacts.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun addContact()
    {
        val name = edName.text.toString()
        val phone = edPhone.text.toString()
        val address = edAddress.text.toString()
        val bio = edBio.text.toString()

        if(name.isEmpty() || phone.isEmpty() || address.isEmpty() || bio.isEmpty())
        {
            Toast.makeText(this, "Please enter all the required fields", Toast.LENGTH_SHORT).show()
        }else
        {
            val contact = ContactModel(name = name, phone = phone, address = address, bio = bio)
            val status = sqliteHelper.insertContact(contact)

            //Check insert success or not success
            if (status> -1)
            {
                Toast.makeText(this, "Contact Added...", Toast.LENGTH_SHORT).show()
                clearEditText()

            }else
            {
                Toast.makeText(this, "Record not saved...", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun clearEditText()
    {
        edName.setText("")
        edPhone.setText("")
        edAddress.setText("")
        edBio.setText("")
        edName.requestFocus()
    }

    private fun initView()
    {
        edName = findViewById(R.id.editTextPersonName)
        edPhone = findViewById(R.id.editTextPhone)
        edAddress = findViewById(R.id.editTextTextPostalAddress)
        edBio = findViewById(R.id.editTextTextMultiLine)
        btnAdd = findViewById(R.id.imageButton_addContact)
        btnViewContacts = findViewById(R.id.button_ViewContacts)
    }
}