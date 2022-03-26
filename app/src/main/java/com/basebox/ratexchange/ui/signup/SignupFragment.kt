package com.basebox.ratexchange.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.basebox.ratexchange.databinding.FragmentSignupBinding


/**
 * A simple [Fragment] subclass.
 * Use the [SignupFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignupFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        val root: View = binding.root
        handleClick(binding)
        return root
    }

    private fun handleClick(binding: FragmentSignupBinding) {
        val button = binding.btnLogin

        button.setOnClickListener {
            val action = SignupFragmentDirections.actionSignupFragmentToNavHome()
            findNavController().navigate(action)
        }
    }
}