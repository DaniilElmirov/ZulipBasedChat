package com.elmirov.course.chat.screen

import android.view.View
import com.elmirov.course.R
import com.elmirov.course.chat.ui.ChatFragment
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KTextView
import io.github.kakaocup.kakao.toolbar.KToolbar
import org.hamcrest.Matcher

object ChatScreen : KScreen<ChatScreen>() {
    override val layoutId: Int = R.layout.fragment_chat
    override val viewClass: Class<*> = ChatFragment::class.java

    val toolbar = KToolbar { withId(R.id.toolbar) }
    val topic = KTextView { withId(R.id.topic) }

    val newMessage = KEditText { withId(R.id.new_message) }
    val sendOrAttach = KImageView { withId(R.id.send_or_attach) }

    val addReactionAction = KTextView {withId(R.id.add_reaction)}
    val copyAction = KTextView {withId(R.id.copy)}

    val messages = KRecyclerView(
        { withId(R.id.chat) },
        { itemType(::MessageItem) }
    )

    class MessageItem(matcher: Matcher<View>) : KRecyclerItem<MessageItem>(matcher) {
        val date = KTextView { withId(R.id.date) }
        val outgoing = KTextView { withId(R.id.message_text) }
    }
}