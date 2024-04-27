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
import com.elmirov.course.profile.presentation.ProfileEffect
import com.elmirov.course.profile.presentation.ProfileEvent
import com.elmirov.course.profile.presentation.ProfileState
import com.elmirov.course.profile.presentation.ProfileStoreFactory
import com.elmirov.course.util.dpToPix
import vivid.money.elmslie.android.renderer.elmStoreWithRenderer
import vivid.money.elmslie.core.store.Store
import javax.inject.Inject

class OtherProfileFragment : ElmBaseFragment<ProfileEffect, ProfileState, ProfileEvent>() {

    //TODO нужен общий фрагмент для other и own profile, сделать его и прокидывать store через di сразу, а не через "фабрику"
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

    private var userId: Int = 0

    private val component by lazy {
        (requireActivity().application as CourseApplication).component.profileComponentFactory()
            .create()
    }

    @Inject
    lateinit var profileStoreFactory: ProfileStoreFactory

    override val store: Store<ProfileEvent, ProfileEffect, ProfileState> by elmStoreWithRenderer(
        elmRenderer = this
    ) {
        profileStoreFactory.create()
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
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        applyArguments()
        setClickListeners()
    }

    private fun applyArguments() {
        userId = requireArguments().getInt(KEY_USER_ID)

        store.accept(ProfileEvent.Ui.InitOther(userId))
    }

    private fun setClickListeners() {
        binding.toolbar.setNavigationOnClickListener {
            store.accept(ProfileEvent.Ui.BackClick)
        }

        binding.refresh.setOnClickListener {
            store.accept(ProfileEvent.Ui.OnRefreshOtherClick(userId))
        }
    }

    override fun render(state: ProfileState) {
        if (state.loading)
            applyLoading()

        state.content?.let {
            applyContent(it)
        }
    }

    override fun handleEffect(effect: ProfileEffect) = when (effect) {
        ProfileEffect.ShowError -> applyError()
    }

    private fun applyLoading() {
        binding.apply {
            error.isVisible = false
            toolbar.isVisible = true

            avatar.isVisible = false
            name.isVisible = false
            onlineStatus.isVisible = false

            shimmer.isVisible = true
            shimmer.startShimmer()
        }
    }

    private fun applyError() {
        binding.apply {
            error.isVisible = true
            toolbar.isVisible = true

            avatar.isVisible = false
            name.isVisible = false
            onlineStatus.isVisible = false

            shimmer.isVisible = false
            shimmer.stopShimmer()
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
            error.isVisible = false
            toolbar.isVisible = true

            avatar.isVisible = true
            name.isVisible = true
            onlineStatus.isVisible = true

            shimmer.isVisible = false
            shimmer.stopShimmer()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}