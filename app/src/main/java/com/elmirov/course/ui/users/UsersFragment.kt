package com.elmirov.course.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.elmirov.course.databinding.FragmentUsersBinding
import com.elmirov.course.domain.User
import com.elmirov.course.presentation.users.UsersState
import com.elmirov.course.presentation.users.UsersViewModel
import com.elmirov.course.ui.users.adapter.UsersAdapter
import com.elmirov.course.util.collectLifecycleFlow

class UsersFragment : Fragment() {

    companion object {
        fun newInstance(): UsersFragment =
            UsersFragment()
    }

    private var _binding: FragmentUsersBinding? = null
    private val binding
        get() = _binding!!

    private val usersAdapter by lazy {
        UsersAdapter()
    }

    private val viewModel: UsersViewModel by viewModels()

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
            progressBar.isVisible = false

            search.isVisible = true
            users.isVisible = true
        }
    }

    private fun applyLoading() {
        binding.apply {
            progressBar.isVisible = true

            search.isVisible = false
            users.isVisible = false
        }
    }

    override fun onDestroy() {
        binding.users.adapter = null
        _binding = null
        super.onDestroy()
    }
}