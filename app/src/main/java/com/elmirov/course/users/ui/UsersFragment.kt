package com.elmirov.course.users.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.elmirov.course.CourseApplication
import com.elmirov.course.base.ElmBaseFragment
import com.elmirov.course.core.user.domain.entity.User
import com.elmirov.course.databinding.FragmentUsersBinding
import com.elmirov.course.users.presentation.UsersEffect
import com.elmirov.course.users.presentation.UsersEvent
import com.elmirov.course.users.presentation.UsersState
import com.elmirov.course.users.presentation.UsersStoreFactory
import com.elmirov.course.users.ui.adapter.UsersAdapter
import vivid.money.elmslie.android.renderer.elmStoreWithRenderer
import vivid.money.elmslie.core.store.Store
import javax.inject.Inject

class UsersFragment : ElmBaseFragment<UsersEffect, UsersState, UsersEvent>() {

    companion object {
        fun newInstance(): UsersFragment =
            UsersFragment()
    }

    private var _binding: FragmentUsersBinding? = null
    private val binding
        get() = _binding!!

    private val component by lazy {
        (requireActivity().application as CourseApplication).component
    }

    @Inject
    lateinit var usersStoreFactory: UsersStoreFactory

    override val store: Store<UsersEvent, UsersEffect, UsersState> by elmStoreWithRenderer(
        elmRenderer = this
    ) {
        usersStoreFactory.create()
    }

    private val usersAdapter by lazy {
        UsersAdapter(
            onUserClick = {
                store.accept(UsersEvent.Ui.OnUserClick(it))
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

        store.accept(UsersEvent.Ui.Init)
        setListeners()
    }

    override fun render(state: UsersState) {
        if (state.loading)
            applyLoading()

        state.content?.let {
            applyContent(it)
        }
    }

    private fun setListeners() {
        binding.refresh.setOnClickListener {
            store.accept(UsersEvent.Ui.OnRefreshClick)
        }
    }

    override fun handleEffect(effect: UsersEffect): Unit = when (effect) {
        UsersEffect.ShowError -> applyError()
    }

    private fun applyContent(content: List<User>) {
        usersAdapter.submitList(content)

        binding.apply {
            error.isVisible = false

            users.isVisible = true

            shimmer.isVisible = false
            shimmer.stopShimmer()
        }
    }

    private fun applyLoading() {
        binding.apply {
            error.isVisible = false

            users.isVisible = false

            shimmer.isVisible = true
            shimmer.startShimmer()
        }
    }

    private fun applyError() {
        binding.apply {
            error.isVisible = true

            users.isVisible = false

            shimmer.isVisible = false
            shimmer.stopShimmer()
        }
    }

    override fun onDestroy() {
        binding.users.adapter = null
        _binding = null
        super.onDestroy()
    }
}