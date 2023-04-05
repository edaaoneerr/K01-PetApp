package com.edaaoneerr.petcare.view.authview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.edaaoneerr.petcare.databinding.FragmentSignUpBinding
import com.edaaoneerr.petcare.util.navigate
import com.edaaoneerr.petcare.viewmodel.SignUpViewModel
import org.json.JSONObject
import org.mindrot.jbcrypt.BCrypt


class SignUpFragment : Fragment() {


    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val signUpViewModel: SignUpViewModel by viewModels()
    private val actionToHomePage = SignUpFragmentDirections.actionSignUpFragmentToHomePageFragment()
    private val actionToLoginPage = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeSignUpData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addListenerToSignUpData()
        binding.signUpButton.setOnClickListener {
            signUpUser()
            navigate(it, actionToHomePage)
        }

        binding.loginCheckedText.setOnClickListener {
            navigate(it, actionToLoginPage)
        }
    }

    private fun signUpUser() {
        //Create a JSON object with user details
        val jsonObject = JSONObject()
        jsonObject.put("email", binding.userEmailAddress.text)
        val hashedPassword = BCrypt.hashpw(binding.userPassword.text.toString(), BCrypt.gensalt())
        jsonObject.put("user_password", hashedPassword)
        jsonObject.put("is_vet", false)

        //Check if phone number is not null and add it to JSON object
        if (binding.userPhoneNumber.text != null) {
            jsonObject.put("phone_number", binding.userPhoneNumber.text)
        }

        //Pass JSON object to ViewModel to make network call to sign up user
        signUpViewModel.rawJSON(jsonObject)
        signUpViewModel.getUsers()
    }

    private fun observeSignUpData() {
        signUpViewModel.boxStrokeColor.observe(this, Observer {
            binding.userEmailLayout.boxStrokeColor = it
        })
        signUpViewModel.endIconDrawable.observe(this, Observer {
            binding.userEmailLayout.setEndIconDrawable(it)
        })
        signUpViewModel.hintTextColor.observe(this) {
            binding.userEmailLayout.hintTextColor = it
        }
        signUpViewModel.emailErrorText.observe(this, Observer {
            binding.userEmailLayout.error = it
        })
        signUpViewModel.passwordErrorText.observe(this, Observer {
            binding.userPasswordLayout.error = it
        })
        signUpViewModel.passwordAgainErrorText.observe(this, Observer {
            binding.userPasswordAgainLayout.error = it
        })
        signUpViewModel.phoneNumberErrorText.observe(this, Observer {
            binding.userPhoneNumberLayout.error = it
        })
    }

    private fun addListenerToSignUpData() {
        binding.userEmailAddress.addTextChangedListener(signUpViewModel.emailWatcher)
        binding.userPassword.addTextChangedListener(signUpViewModel.passwordWatcher)
        binding.userPasswordAgain.addTextChangedListener(signUpViewModel.passwordAgainWatcher)
        binding.userPhoneNumber.addTextChangedListener(signUpViewModel.phoneNumberWatcher)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}