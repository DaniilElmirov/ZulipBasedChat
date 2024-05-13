package com.elmirov.course.channels.screen

import android.view.View
import com.elmirov.course.R
import com.elmirov.course.channels.ui.subscribed.SubscribedChannelsFragment
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher

object SubscribedScreen : KScreen<SubscribedScreen>() {
    override val layoutId: Int = R.layout.fragment_page_channels
    override val viewClass: Class<*> = SubscribedChannelsFragment::class.java

    val channels = KRecyclerView(
        { withId(R.id.channels) },
        {
            itemType(::ChannelScreenItem)
            itemType(::TopicScreenItem)
        }
    )

    class ChannelScreenItem(matcher: Matcher<View>) : KRecyclerItem<ChannelScreenItem>(matcher) {
        val channel = KTextView(matcher) { withId(R.id.channel_name) }
    }

    class TopicScreenItem(matcher: Matcher<View>) : KRecyclerItem<TopicScreenItem>(matcher) {
        val topic = KTextView(matcher) { withId(R.id.name) }
    }
}