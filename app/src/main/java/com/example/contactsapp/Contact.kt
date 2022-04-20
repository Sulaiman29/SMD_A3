package com.example.contactsapp

class Contact {
    private var name: String = ""
    private var location: String = ""
    private var phone: String = ""
    private var bio: String = ""


    init {
        //constructor
    }

    constructor(name: String, location: String, phone: String, bio: String) {
        this.name = name;
        this.location = location
        this.phone = phone
        this.bio = bio
    }

    public fun getName(): String {
        return name;
    }

    public fun setName(name: String) {
        this.name = name;
    }

    public fun getphone(): String {
        return phone;
    }

    public fun setphone(name: String) {
        this.phone = phone;
    }

    public fun getlocation(): String {
        return location;
    }

    public fun setlocation(name: String) {
        this.location = location;
    }

    public fun getbio(): String {
        return bio;
    }

    public fun setbio(name: String) {
        this.bio = bio;
    }
}
