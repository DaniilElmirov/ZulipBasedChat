package com.elmirov.course.profile.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import coil.load
import coil.transform.RoundedCornersTransformation
import com.elmirov.course.CourseApplication
import com.elmirov.course.R
import com.elmirov.course.base.ElmBaseFragment
import com.elmirov.course.core.user.domain.entity.User
import com.elmirov.course.databinding.FragmentProfileBinding
import com.elmirov.course.profile.presentation.elm.ProfileEffect
import com.elmirov.course.profile.presentation.elm.ProfileEvent
import com.elmirov.course.profile.presentation.elm.ProfileScreenState
import com.elmirov.course.profile.presentation.elm.ProfileStoreFactory
import com.elmirov.course.util.dpToPix
import vivid.money.elmslie.android.renderer.elmStoreWithRenderer
import vivid.money.elmslie.core.store.Store
import javax.inject.Inject

class OwnProfileFragment : ElmBaseFragment<ProfileEffect, ProfileScreenState, ProfileEvent>() {

    companion object {

        fun newInstance(): OwnProfileFragment = OwnProfileFragment()
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

    private val component by lazy {
        (requireActivity().application as CourseApplication).component
    }

    @Inject
    lateinit var profileStoreFactory: ProfileStoreFactory
    override val store: Store<ProfileEvent, ProfileEffect, ProfileScreenState> by elmStoreWithRenderer(
        elmRenderer = this
    ) {
        profileStoreFactory.create()
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        store.accept(ProfileEvent.Ui.Init)
    }

    override fun render(state: ProfileScreenState) {
        if (state.loading)
            applyLoading()

        state.content?.let {
            applyContent(it)
        }
    }

    override fun handleEffect(effect: ProfileEffect): Unit = when (effect) {
        ProfileEffect.Back -> Unit
        ProfileEffect.ShowError -> Unit //TODO обработка ошибки
    }

    private fun applyLoading() {
        binding.apply {
            toolbar.isVisible = false

            avatar.isVisible = false
            name.isVisible = false
            onlineStatus.isVisible = false

            shimmer.isVisible = true
            shimmer.startShimmer()
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
            toolbar.isVisible = false

            avatar.isVisible = true
            name.isVisible = true
            onlineStatus.isVisible = true

            shimmer.isVisible = false
            shimmer.stopShimmer()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}