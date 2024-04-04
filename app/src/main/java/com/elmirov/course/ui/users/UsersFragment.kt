package com.elmirov.course.ui.users

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.elmirov.course.CourseApplication
import com.elmirov.course.databinding.FragmentUsersBinding
import com.elmirov.course.domain.User
import com.elmirov.course.presentation.ViewModelFactory
import com.elmirov.course.presentation.users.UsersState
import com.elmirov.course.presentation.users.UsersViewModel
import com.elmirov.course.ui.users.adapter.UsersAdapter
import com.elmirov.course.util.collectLifecycleFlow
import javax.inject.Inject

class UsersFragment : Fragment() {

    companion object {
        fun newInstance(): UsersFragment =
            UsersFragment()
    }

    private var _binding: FragmentUsersBinding? = null
    private val binding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[UsersViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as CourseApplication).component
    }

    private val usersAdapter by lazy {
        UsersAdapter(
            onUserClick = {
                viewModel.openUserProfile()
            }
        )
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.users.adapter = usersAdapter

        applyState()
    }

    private fun applyState() {
        collectLifecycleFlow(viewModel.users) { state ->
            when (state) {
                is UsersState.Content -> applyContent(state.data)

                UsersState.Loading -> applyLoading()
            }
        }
    }

    private fun applyContent(content: List<User>) {
        usersAdapter.submitList(content)

        binding.apply {
            users.isVisible = true

            shimmer.isVisible = false
            shimmer.stopShimmer()
        }
    }

    private fun applyLoading() {
        binding.apply {
            users.isVisible = false

            shimmer.isVisible = true
            shimmer.startShimmer()
        }
    }

    override fun onDestroy() {
        binding.users.adapter = null
        _binding = null
        super.onDestroy()
    }
}