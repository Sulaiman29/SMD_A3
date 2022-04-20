package com.example.contactsapp

import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    var cols = listOf<String>(
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER,
        ContactsContract.CommonDataKinds.Phone._ID
    ).toTypedArray()


    private var ContactsList = ArrayList<ContactModel>()
    private var madapter: MyAdapter = MyAdapter(ContactsList)

    private lateinit var btnAdd: ImageButton

    private lateinit var sqliteHelper: DBHelper

    private val REQUEST_CALL: Int = 1
    private lateinit var btnCall: ImageButton
    private lateinit var txtPhone: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sqliteHelper = DBHelper(this)

        //Recyclerview work from here----------------------------------------
        var recyclerView: RecyclerView= findViewById(R.id.myrecycler)

        recyclerView.layoutManager = LinearLayoutManager(this)

        //attach adapter to recyclerview to populate the view
        recyclerView.adapter = madapter



        //Check for permission
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, Array(1){android.Manifest.permission.READ_CONTACTS}, 111)
        }else{
            readContacts()
        }


        //Database work from here----------------------------------------
        sqliteHelper = DBHelper(this)

        val contactsList = sqliteHelper.getAllContacts()
        Log.e("pppp", "${contactsList.size}")

        //add items to recycler view
        madapter?.addItems(contactsList)






        //Setting up connection from this activity to signup activity
        btnAdd = findViewById(R.id.imageButton_Add)

        btnAdd.setOnClickListener {
            var intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        /*//Call Contact Button action
        btnCall = findViewById(R.id.imageButton_call)
        btnCall.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                makePhoneCall()
            }

        })*/

    }

    private fun makePhoneCall() {
        txtPhone = findViewById(R.id.phone)
        var number: String = txtPhone.text.toString()

        if (ContextCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(android.Manifest.permission.CALL_PHONE), REQUEST_CALL)
        } else {
            val dial = "tel:$number"
            startActivity(Intent(Intent.ACTION_CALL, Uri.parse(dial)))
        }

    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            readContacts()
    }

    private fun readContacts() {
        var result = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        cols, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)

        if(result?.moveToNext()!!)
        {
            //Toast.makeText(applicationContext, result.getString(1), Toast.LENGTH_LONG).show()
            saveContacts(result)


            //display_name, data1, _id

        }
    }

    private fun saveContacts(cursor: Cursor) {

        var id: Int
        var name: String
        var phone: String

        if (cursor.moveToFirst())
        {
            do {
                id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"))
                name = cursor.getString(cursor.getColumnIndexOrThrow("display_name"))
                phone =cursor.getString(cursor.getColumnIndexOrThrow("data1"))


                val contact = ContactModel(id = id, name = name, phone = phone, address = "", bio = "")
                val status = sqliteHelper.insertContact(contact)

            }while (cursor.moveToNext())
        }
    }
}