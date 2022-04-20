package com.example.contactsapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception


class DBHelper (context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "contacts.db"
        private const val TBL_CONTACTS = "tbl_Contacts"
        private const val ID = "_id"
        private const val NAME = "Name"
        private const val PHONE = "Phone"
        private const val ADDRESS = "Address"
        private const val BIO = "Bio"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createtblContacts = ("CREATE TABLE " + TBL_CONTACTS + "(" + ID + " INTEGER PRIMARY KEY,"
                + NAME + " TEXT," + PHONE + " TEXT," + ADDRESS + " TEXT," + BIO + " TEXT" + ")")
        db?.execSQL(createtblContacts)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_CONTACTS")
        onCreate(db)
    }

    fun insertContact(contact: ContactModel): Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, contact.id)
        contentValues.put(NAME, contact.name)
        contentValues.put(PHONE, contact.phone)
        contentValues.put(ADDRESS, contact.address)
        contentValues.put(BIO, contact.bio)

        val success = db.insert(TBL_CONTACTS, null, contentValues)
        db.close()
        return success
    }

    fun getAllContacts(): ArrayList<ContactModel>{
        val contactList: ArrayList<ContactModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_CONTACTS"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }


        var id: Int
        var name: String
        var phone: String
        var address: String
        var bio: String

        if (cursor.moveToFirst())
        {
            do {
                id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"))
                name = cursor.getString(cursor.getColumnIndexOrThrow("Name"))
                phone =cursor.getString(cursor.getColumnIndexOrThrow("Phone"))
                address = cursor.getString(cursor.getColumnIndexOrThrow("Address"))
                bio = cursor.getString(cursor.getColumnIndexOrThrow("Bio"))

                val contact = ContactModel(id = id, name = name, phone = phone, address = address, bio = bio)
                contactList.add(contact)

            }while (cursor.moveToNext())
        }
        return contactList

    }
}