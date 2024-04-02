package com.elmirov.course.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.elmirov.course.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    companion object {

        private const val KEY_OWN_PROFILE = "KEY_OWN_PROFILE"

        fun newInstance(own: Boolean): ProfileFragment =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(KEY_OWN_PROFILE, own)
                }
            }
    }

    private var _binding: FragmentProfileBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parseArguments()
    }

    private fun parseArguments() {
        val own = requireArguments().getBoolean(KEY_OWN_PROFILE)

        if (own) {
            binding.toolbar.isVisible = false
            binding.logOut.isVisible = true
        } else {
            binding.toolbar.isVisible = true
            binding.logOut.isVisible = false
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}