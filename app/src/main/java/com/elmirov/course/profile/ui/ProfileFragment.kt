package com.elmirov.course.profile.ui

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
import com.elmirov.course.core.factory.ViewModelFactory
import com.elmirov.course.profile.presentation.ProfileState
import com.elmirov.course.profile.presentation.ProfileViewModel
import com.elmirov.course.util.collectLifecycleFlow
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

        applyState(isOwn())
        setNavigationIconClickListener()
    }

    private fun isOwn(): Boolean =
        requireArguments().getBoolean(KEY_OWN_PROFILE)

    private fun applyState(isOwn: Boolean) {
        collectLifecycleFlow(viewModel.profile) { state ->
            when (state) {
                ProfileState.Content -> applyContent(isOwn) //TODO переделать нормально

                ProfileState.Loading -> applyLoading(isOwn)
            }
        }
    }

    private fun applyContent(isOwn: Boolean) {
        binding.apply {

            if (isOwn) {
                toolbar.isVisible = false
                logOut.isVisible = true
            } else {
                toolbar.isVisible = true
                logOut.isVisible = false
            }

            avatar.isVisible = true
            name.isVisible = true
            meetingStatus.isVisible = true
            onlineStatus.isVisible = true

            shimmer.isVisible = false
            shimmer.stopShimmer()
        }
    }

    private fun applyLoading(isOwn: Boolean) {
        binding.apply {

            if (isOwn) {
                toolbar.isVisible = false
                logOut.isVisible = false
            } else {
                toolbar.isVisible = true
                logOut.isVisible = false
            }

            avatar.isVisible = false
            name.isVisible = false
            meetingStatus.isVisible = false
            onlineStatus.isVisible = false

            shimmer.isVisible = true
            shimmer.startShimmer()
        }
    }

    private fun setNavigationIconClickListener() {
        binding.toolbar.setNavigationOnClickListener {
            viewModel.back()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}