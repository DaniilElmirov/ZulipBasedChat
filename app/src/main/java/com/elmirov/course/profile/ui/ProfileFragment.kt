package com.elmirov.course.profile.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import coil.transform.RoundedCornersTransformation
import com.elmirov.course.CourseApplication
import com.elmirov.course.R
import com.elmirov.course.core.factory.ViewModelFactory
import com.elmirov.course.core.user.domain.entity.User
import com.elmirov.course.databinding.FragmentProfileBinding
import com.elmirov.course.profile.presentation.ProfileState
import com.elmirov.course.profile.presentation.ProfileViewModel
import com.elmirov.course.util.collectLifecycleFlow
import com.elmirov.course.util.dpToPix
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

        applyArguments()
        applyState()
        setNavigationIconClickListener()
    }

    private fun applyArguments() {
        val own = requireArguments().getBoolean(KEY_OWN_PROFILE)

        binding.toolbar.isVisible = !own
        viewModel.loadOwnProfile()
    }

    private fun applyState() {
        collectLifecycleFlow(viewModel.profile) { state ->
            when (state) {
                is ProfileState.Content -> applyContent(state.data)

                ProfileState.Loading -> applyLoading()

                ProfileState.Error -> Unit //TODO добавить обработку стейта

                ProfileState.Initial -> Unit
            }
        }
    }

    private fun applyContent(content: User) {
        binding.apply {
            avatar.load(content.avatarUrl) {
                error(R.drawable.ic_launcher_foreground)
                transformations(RoundedCornersTransformation(15.dpToPix(requireContext())))
            }
            name.text = content.name
            //TODO добавить получение статуса и цвеета взависимости от него
            onlineStatus.text = "online"
        }

        setContentVisibility()
    }

    private fun setContentVisibility() {
        binding.apply {
            avatar.isVisible = true
            name.isVisible = true
            onlineStatus.isVisible = true

            shimmer.isVisible = false
            shimmer.stopShimmer()
        }
    }

    private fun applyLoading() {
        binding.apply {
            avatar.isVisible = false
            name.isVisible = false
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