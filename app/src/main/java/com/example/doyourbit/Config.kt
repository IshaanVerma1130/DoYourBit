package com.example.doyourbit

public class Config {

    companion object {
        val BASE_URL = "http://doyourbit.herokuapp.com"
        val USER_SIGNUP = "$BASE_URL/signup/user"
        val NGO_SIGNUP = "$BASE_URL/signup/ngo"
        val LOGIN = "$BASE_URL/login"
        val NGO_REQUEST = "$BASE_URL/request/"
        val USER_DONATE = "$BASE_URL/donate/"
        val NGO_UPDATE = "$BASE_URL/update/ngo/"

        var nameList = ArrayList<String>()
        var addressList = ArrayList<String>()
        var phoneList = ArrayList<String>()
    }
}