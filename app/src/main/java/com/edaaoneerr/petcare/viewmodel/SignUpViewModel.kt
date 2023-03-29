package com.edaaoneerr.petcare.viewmodel

import android.app.Application
import android.content.res.ColorStateList
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.edaaoneerr.petcare.R
import com.edaaoneerr.petcare.model.Users
import com.edaaoneerr.petcare.service.AppDatabase
import com.edaaoneerr.petcare.service.userservice.UserAPIService
import com.edaaoneerr.petcare.util.isValidPhoneNumber
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject


class SignUpViewModel(application: Application) : BaseViewModel(application) {

    var boxStrokeColor = MutableLiveData<Int>()
    var hintTextColor = MutableLiveData<ColorStateList?>()
    var endIconDrawable = MutableLiveData<Int>()

    var emailErrorText = MutableLiveData<CharSequence?>()
    var passwordErrorText = MutableLiveData<CharSequence?>()
    var passwordAgainErrorText = MutableLiveData<CharSequence?>()
    var phoneNumberErrorText = MutableLiveData<CharSequence?>()

    var password = MutableLiveData<String>()


    private val userAPIService = UserAPIService()
    private val disposable = CompositeDisposable()

    fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        if (password.length < 8) {
            return false
        }
        return true
    }

    val emailWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (!isValidEmail(p0.toString())) {
                println("Is not valid")
                boxStrokeColor.value = R.color.error
                endIconDrawable.value = R.drawable.ic_baseline_error_24
                emailErrorText.value = "Gecersiz e-posta adresi"
                hintTextColor.value =
                    ContextCompat.getColorStateList(getApplication(), R.color.error)

            } else {
                println("Valid")
                boxStrokeColor.value = R.color.vetGreen
                emailErrorText.value = null
                hintTextColor.value =
                    ContextCompat.getColorStateList(getApplication(), R.color.vetGreen)
                endIconDrawable.value = R.drawable.ic_baseline_check_circle_24
            }


        }

        override fun afterTextChanged(p0: Editable?) {


        }


    }

    val passwordWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (!isValidPassword(p0.toString())) {
                println("Is not valid password")
                passwordErrorText.value = "Şifre sekiz karakterden az olamaz"

            } else {
                println("Valid")
                passwordErrorText.value = null
                password.value = p0.toString()

            }


        }

        override fun afterTextChanged(p0: Editable?) {

            if (p0.toString().isEmpty()) {
                passwordErrorText.value = "Şifre boş bırakılamaz."

            }


        }


    }

    val passwordAgainWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (p0.toString() != password.value) {
                passwordAgainErrorText.value = "Şifre aynı değil, lütfen kontrol edin"
            } else {
                println("Valid")
                passwordAgainErrorText.value = null
            }
        }

        override fun afterTextChanged(p0: Editable?) {

            if (p0.toString().isEmpty()) {
                passwordAgainErrorText.value = "Şifre tekrar edilmelidir"

            }


        }


    }

    val phoneNumberWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (!p0.toString().isValidPhoneNumber()) {
                phoneNumberErrorText.value = "Gecersiz telefon numarası"

            } else {
                println("Valid")
                println(p0)
                phoneNumberErrorText.value = null

            }


        }

        override fun afterTextChanged(p0: Editable?) {


        }


    }

    fun getUsers() {
        disposable.add(
            userAPIService.getUserData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Users>>() {
                    override fun onSuccess(value: List<Users>) {
                        saveUserData(value)
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                })
        )
    }


    private fun saveUserData(userList: List<Users>) {

        launch {
            val dao = AppDatabase(getApplication()).userDao()
            dao.deleteAllUsers()
            val i = 0
            for (i in i..userList.size) {
                userList.toTypedArray()
                if (i == userList.size - 1)
                    break
            }

            dao.insertAllUsers(*userList.toTypedArray())
        }
    }

    fun rawJSON(jsonObject: JSONObject) {

        val jsonObjectString = jsonObject.toString()

        val requestBody =
            jsonObjectString.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            val response = userAPIService.createUser(requestBody)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )

                    Log.d("Pretty Printed JSON :", prettyJson)

                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }
    }

}