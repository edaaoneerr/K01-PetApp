package com.edaaoneerr.petcare.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.edaaoneerr.petcare.model.Users
import com.edaaoneerr.petcare.service.userservice.UserAPIService
import com.google.gson.Gson
import com.google.gson.JsonParser
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt


class LoginViewModel(application: Application) : BaseViewModel(application) {

    private val userAPIService = UserAPIService()
    var isAuthenticated = MutableLiveData<Boolean?>()
    private val MY_PERMISSIONS_REQUEST_SEND_SMS = 1

    fun userControl(user: Users) {
        launch {
            val users = getUsers()
            val matchingUser = users.find { it.userEmail == user.userEmail }
            val phoneUser = users.find { it.userPhoneNumber == user.userPhoneNumber }

            if (matchingUser != null) {
                val passwordMatches = BCrypt.checkpw(user.userPassword, matchingUser.userPassword)

                if (passwordMatches) {
                    isAuthenticated.value = true
                } else {
                    if (phoneUser != null) {
                        isAuthenticated.value = true

                    }
                    isAuthenticated.value = false
                    Toast.makeText(getApplication(), "Yanlış şifre", Toast.LENGTH_LONG).show()
                }
            } else {
                if (phoneUser != null) {
                    isAuthenticated.value = true
                } else {
                    isAuthenticated.value = false
                    Toast.makeText(getApplication(), "Böyle bir kullanıcı yok", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }


    suspend fun getUsers(): List<Users> {
        val response = userAPIService.getUsers()

        if (response.isSuccessful) {
            val usersJson = JsonParser.parseString(response.body()?.string())
            val users = Gson().fromJson(usersJson, Array<Users>::class.java)
            return users.toList()
        } else {
            throw Exception("Failed to get users")
        }
    }

}




