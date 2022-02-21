package com.example.loginlesson26.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.loginlesson26.CustomPreference
import com.example.loginlesson26.LoginManager
import com.example.loginlesson26.R
import com.example.loginlesson26.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    lateinit var viewModelFactory: ViewModelFactory<LoginViewModel>
    private val viewModel: LoginViewModel by viewModels(
        factoryProducer = { viewModelFactory }
    )

    private val preferences by lazy {
        CustomPreference(requireContext())
    }
    private val loginManager by lazy {
        LoginManager(preferences)
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
            viewModel.onSignInClick(edUserName, edPassword)
        }

        binding.buttonLogout.setOnClickListener {
            binding.editTextLogin.text = null
            binding.editTextPassword.text = null
            loginManager.logout()
        }

        viewModel.userLiveData.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                loginManager.login(user)
            }
        }

        viewModel.authIsSuccessful.observe(viewLifecycleOwner) {
            if (it == true) {
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



