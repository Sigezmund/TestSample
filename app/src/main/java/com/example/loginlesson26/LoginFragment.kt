package com.example.loginlesson26

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.loginlesson26.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: MainViewModel by viewModels()
    val preferences by lazy {
        CustomPreference(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.buttonSignIn.setOnClickListener {
            val edUserName = binding.editTextLogin.text.toString()
            val edPassword = binding.editTextPassword.text.toString()
            if (check(edUserName, edPassword)) {
                viewModel.onSignInClick(edUserName, edPassword)
            } else {
                Toast.makeText(requireContext(), "Incorrect login or password", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        viewModel.userLiveData.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                preferences.login = user.userName
                preferences.password = user.password
            }
        }
        viewModel.authIsSuccessful.observe(viewLifecycleOwner) {
            if (it) {
                preferences.login = ""
                preferences.password = ""
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.fragmentContainer, TrackListFragment.newInstance())
                    .commit()
            } else {
                Toast.makeText(requireContext(), "Network Error", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return binding.root
    }

    private fun check(username: String, password: String): Boolean {
        return username == "marynanavumenka" && password == "jctkmh8d!"
    }

    companion object {
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }
}



