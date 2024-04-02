package com.elmirov.course.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.elmirov.course.CourseApplication
import com.elmirov.course.databinding.FragmentProfileBinding
import com.elmirov.course.presentation.ViewModelFactory
import com.elmirov.course.presentation.profile.ProfileViewModel
import javax.inject.Inject

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

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ProfileViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as CourseApplication).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parseArguments()
        setNavigationIconClickListener()
    }

    private fun setNavigationIconClickListener() {
        binding.toolbar.setNavigationOnClickListener {
            viewModel.back()
        }
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