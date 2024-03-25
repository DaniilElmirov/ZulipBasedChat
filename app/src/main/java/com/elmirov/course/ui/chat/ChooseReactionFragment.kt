package com.elmirov.course.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elmirov.course.databinding.FragmentChooseReactionBinding
import com.elmirov.course.ui.view.ReactionView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ChooseReactionFragment : BottomSheetDialogFragment() {

    companion object {

        const val TAG = "ChooseReactionFragment"

        private const val EMOJI_SIZE = 40f

        fun newInstance(): ChooseReactionFragment = ChooseReactionFragment()
    }

    private val emojis = intArrayOf(
        0x1f600,
        0x1f603,
        0x1f604,
        0x1f601,
        0x1f606,
        0x1f605,
        0x1f923,
        0x1f607,
        0x1f608,
        0x1f611,
        0x1f612,
        0x1f613,
        0x1f614,
        0x1f615,
        0x1f616,
        0x1f617,
        0x1f618,
        0x1f619,
    )

    private var _binding: FragmentChooseReactionBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseReactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (dialog as? BottomSheetDialog)?.behavior?.apply {
            state = BottomSheetBehavior.STATE_HIDDEN
        }

        emojis.forEach {
            val reactionView = ReactionView(requireContext()).apply {
                reaction = String(Character.toChars(it))
                countVisible = false
                size = EMOJI_SIZE
            }
            binding.reactions.addView(reactionView)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}