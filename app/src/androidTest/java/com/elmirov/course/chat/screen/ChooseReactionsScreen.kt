package com.elmirov.course.chat.screen

import com.elmirov.course.R
import com.elmirov.course.chat.ui.ChooseReactionFragment
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.common.views.KView

object ChooseReactionsScreen : KScreen<ChooseReactionsScreen>() {
    override val layoutId: Int = R.layout.fragment_choose_reaction
    override val viewClass: Class<*> = ChooseReactionFragment::class.java

    val reactions = KView {withId(R.id.reactions)}
}