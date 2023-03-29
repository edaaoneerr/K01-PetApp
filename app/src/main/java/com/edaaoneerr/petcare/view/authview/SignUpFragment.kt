package com.edaaoneerr.petcare.view.authview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.edaaoneerr.petcare.databinding.FragmentSignUpBinding
import com.edaaoneerr.petcare.viewmodel.SignUpViewModel
import org.json.JSONObject
import org.mindrot.jbcrypt.BCrypt


class SignUpFragment : Fragment() {


    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.root
        return view


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.userEmailAddress.addTextChangedListener(signUpViewModel.emailWatcher)
        binding.userPassword.addTextChangedListener(signUpViewModel.passwordWatcher)
        binding.userPasswordAgain.addTextChangedListener(signUpViewModel.passwordAgainWatcher)
        binding.userPhoneNumber.addTextChangedListener(signUpViewModel.phoneNumberWatcher)

        binding.signUpButton.setOnClickListener {

            if (binding.userPhoneNumber.text != null) {
                val jsonObject = JSONObject()
                jsonObject.put("email", binding.userEmailAddress.text)
                val hashedPassword =
                    BCrypt.hashpw(binding.userPassword.text.toString(), BCrypt.gensalt())
                jsonObject.put("phone_number", binding.userPhoneNumber.text)
                jsonObject.put("user_password", hashedPassword)
                jsonObject.put("is_vet", false)

                signUpViewModel.rawJSON(jsonObject)
                signUpViewModel.getUsers()
            } else {
                val jsonObject = JSONObject()
                jsonObject.put("email", binding.userEmailAddress.text)
                val hashedPassword =
                    BCrypt.hashpw(binding.userPassword.text.toString(), BCrypt.gensalt())
                jsonObject.put("user_password", hashedPassword)
                jsonObject.put("is_vet", false)

                signUpViewModel.rawJSON(jsonObject)
                signUpViewModel.getUsers()
            }

            val action = SignUpFragmentDirections.actionSignUpFragmentToHomePageFragment()
            Navigation.findNavController(it).navigate(action)


        }

        binding.loginCheckedText.setOnClickListener {

            val action = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
            Navigation.findNavController(it).navigate(action)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}