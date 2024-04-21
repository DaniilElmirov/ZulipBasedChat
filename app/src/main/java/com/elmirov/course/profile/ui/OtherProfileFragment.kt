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
import com.elmirov.course.profile.presentation.elm.ProfileState
import com.elmirov.course.profile.presentation.ProfileViewModel
import com.elmirov.course.util.collectLifecycleFlow
import com.elmirov.course.util.dpToPix
import javax.inject.Inject

class OtherProfileFragment : Fragment() {

    companion object {

        private const val KEY_USER_ID = "KEY_USER_ID"

        fun newInstance(id: Int): OtherProfileFragment =
            OtherProfileFragment().apply {
                arguments = Bundle().apply {
                    putInt(KEY_USER_ID, id)
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
        val id = requireArguments().getInt(KEY_USER_ID)

        viewModel.loadOtherProfile(id)
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

            setOnlineStatus(content.onlineStatus)
        }

        setContentVisibility()
    }

    private fun setOnlineStatus(status: User.OnlineStatus) {
        binding.onlineStatus.apply {
            when (status) {
                User.OnlineStatus.ACTIVE -> {
                    text = getString(R.string.active)
                    setTextColor(resources.getColor(R.color.active_status_color, null))
                }

                User.OnlineStatus.IDLE -> {
                    text = getString(R.string.idle)
                    setTextColor(resources.getColor(R.color.idle_status_color, null))
                }

                User.OnlineStatus.OFFLINE -> {
                    text = getString(R.string.offline)
                    setTextColor(resources.getColor(R.color.offline_status_color, null))
                }
            }
        }
    }

    private fun setContentVisibility() {
        binding.apply {
            toolbar.isVisible = true

            avatar.isVisible = true
            name.isVisible = true
            onlineStatus.isVisible = true

            shimmer.isVisible = false
            shimmer.stopShimmer()
        }
    }

    private fun applyLoading() {
        binding.apply {
            toolbar.isVisible = true

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