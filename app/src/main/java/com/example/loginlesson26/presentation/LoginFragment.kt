package com.example.loginlesson26.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.loginlesson26.CustomPreference
import com.example.loginlesson26.LoginManager
import com.example.loginlesson26.R
import com.example.loginlesson26.data.AppDatabase
import com.example.loginlesson26.data.Repositories
import com.example.loginlesson26.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModelCreator {
        LoginViewModel(
            Repositories(
                AppDatabase.build(
                    requireContext()
                )
            )
        )
    }

    private val preferences = CustomPreference(requireContext())
    val loginManager = LoginManager(preferences)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.buttonSignIn.setOnClickListener {
            val edUserName = binding.editTextLogin.text.toString()
            val edPassword = binding.editTextPassword.text.toString()
            viewModel.onSignInClick(edUserName, edPassword)
        }

        binding.buttonLogout.setOnClickListener {
            loginManager.logout()
            loginManager.isLoggedInLiveData.value=false
        }

        viewModel.userLiveData.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                loginManager.login(user)
                loginManager.isLoggedInLiveData.value=true
            }
        }

        viewModel.authIsSuccessful.observe(viewLifecycleOwner) {
            if (it) {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.fragmentContainer, TrackListFragment.newInstance())
                    .commit()
            } else {
                Toast.makeText(requireContext(), R.string.error_auth, Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return binding.root
    }

    companion object {
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }
}



