package com.example.contactsapp
import  java.util.*

data class ContactModel(
  var id: Int = getAutoId(),
  var name: String = "",
  var phone: String = "",
  var address: String = "",
  var bio: String = ""
) {
    companion object{
        fun getAutoId(): Int{
            val random = Random()
            return random.nextInt(100)
        }
    }
}