package com.elmirov.course.chat.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elmirov.course.chat.domain.entity.Reaction
import com.elmirov.course.chat.ui.view.ReactionView
import com.elmirov.course.databinding.FragmentChooseReactionBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ChooseReactionFragment : BottomSheetDialogFragment() {

    companion object {

        const val TAG = "ChooseReactionFragment"

        private const val EMOJI_SIZE = 40f

        private const val KEY_MESSAGE_ID = "KEY_MESSAGE_ID"

        fun newInstance(messageId: Int): ChooseReactionFragment =
            ChooseReactionFragment().apply {
                arguments = Bundle().apply {
                    putInt(KEY_MESSAGE_ID, messageId)
                }
            }
    }

    private val emojis = arrayOf(
        Reaction("grinning", "1f600"),
        Reaction("smiley", "1f603"),
        Reaction("big_smile", "1f604"),
        Reaction("grinning_face_with_smiling_eyes", "1f601"),
        Reaction("laughing", "1f606"),
        Reaction("sweat_smile", "1f605"),
        Reaction("rolling_on_the_floor_laughing", "1f923"),
        Reaction("joy", "1f602"),
        Reaction("smile", "1f642"),
        Reaction("upside_down", "1f643"),
        Reaction("wink", "1f609"),
        Reaction("blush", "1f60a"),
        Reaction("innocent", "1f607"),
        Reaction("heart_eyes", "1f60d"),
        Reaction("heart_kiss", "1f618"),
        Reaction("kiss", "1f617"),
        Reaction("smiling_face", "263a"),
        Reaction("kiss_with_blush", "1f61a"),
        Reaction("kiss_smiling_eyes", "1f619"),
        Reaction("yum", "1f60b"),
        Reaction("stuck_out_tongue", "1f61b"),
        Reaction("stuck_out_tongue_wink", "1f61c"),
        Reaction("stuck_out_tongue_closed_eyes", "1f61d"),
        Reaction("money_face", "1f911"),
        Reaction("hug", "1f917"),
        Reaction("thinking", "1f914"),
        Reaction("silence", "1f910"),
        Reaction("neutral", "1f610"),
        Reaction("expressionless", "1f611"),
        Reaction("speechless", "1f636"),
        Reaction("smirk", "1f60f"),
        Reaction("unamused", "1f612"),
        Reaction("rolling_eyes", "1f644"),
        Reaction("grimacing", "1f62c"),
        Reaction("lying", "1f925"),
        Reaction("relieved", "1f60c"),
        Reaction("pensive", "1f614"),
        Reaction("sleepy", "1f62a"),
        Reaction("drooling", "1f924"),
        Reaction("sleeping", "1f634"),
        Reaction("cant_talk", "1f637"),
        Reaction("sick", "1f912"),
        Reaction("hurt", "1f915"),
        Reaction("nauseated", "1f922"),
        Reaction("sneezing", "1f927"),
        Reaction("dizzy", "1f635"),
        Reaction("cowboy", "1f920"),
        Reaction("sunglasses", "1f60e"),
        Reaction("nerd", "1f913"),
        Reaction("oh_no", "1f615"),
        Reaction("worried", "1f61f"),
        Reaction("frown", "1f641"),
        Reaction("sad", "2639"),
        Reaction("open_mouth", "1f62e"),
        Reaction("hushed", "1f62f"),
        Reaction("astonished", "1f632"),
        Reaction("flushed", "1f633"),
        Reaction("frowning", "1f626"),
        Reaction("anguished", "1f627"),
        Reaction("fear", "1f628"),
        Reaction("cold_sweat", "1f630"),
        Reaction("exhausted", "1f625"),
        Reaction("cry", "1f622"),
        Reaction("sob", "1f62d"),
        Reaction("scream", "1f631"),
    )

    private var _binding: FragmentChooseReactionBinding? = null
    private val binding
        get() = _binding!!

    var click: ((Int, Reaction, Boolean) -> Unit)? = null

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
                reaction = it
                countVisible = false
                size = EMOJI_SIZE
            }
            binding.reactions.addView(reactionView)
        }

        binding.reactions.setOnReactionClick {
            click?.invoke(
                requireArguments().getInt(KEY_MESSAGE_ID),
                it.reaction,
                it.isSelected,
            )
            dismiss()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}