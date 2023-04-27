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
    val authenticationResult = MutableLiveData<AuthenticationResult>()

    fun authenticateUser(user: Users) {
        launch {
            val users = getUsers()
            val matchingUser = users.find { it.userEmail == user.userEmail }

            val phoneUser = users.find { it.userPhoneNumber == user.userPhoneNumber }

            if (matchingUser != null && phoneUser == null) {
                if (BCrypt.checkpw(
                        user.userPassword,
                        matchingUser.userPassword
                    )
                ) {
                    authenticationResult.value = AuthenticationResult.EMAIL_AUTHENTICATED
                } else {
                    authenticationResult.value = AuthenticationResult.INVALID_CREDENTIALS
                }
            } else if (phoneUser != null && matchingUser == null) {
                authenticationResult.value = AuthenticationResult.PHONE_AUTHENTICATED

            } else if (matchingUser != null && phoneUser != null) {
                if (BCrypt.checkpw(
                        user.userPassword,
                        matchingUser.userPassword
                    )
                ) {
                    authenticationResult.value = AuthenticationResult.FULLY_AUTHENTICATED
                } else {
                    authenticationResult.value = AuthenticationResult.INVALID_CREDENTIALS
                }

            } else {
                authenticationResult.value = AuthenticationResult.NOT_AUTHENTICATED
                Toast.makeText(getApplication(), "Böyle bir kullanıcı yok.", Toast.LENGTH_LONG)
                    .show()
            }

        }
    }

    private suspend fun getUsers(): List<Users> {
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

enum class AuthenticationResult {
    PHONE_AUTHENTICATED,
    EMAIL_AUTHENTICATED,
    FULLY_AUTHENTICATED,
    INVALID_CREDENTIALS,
    NOT_AUTHENTICATED
}




