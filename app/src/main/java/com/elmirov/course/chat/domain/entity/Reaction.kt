package com.elmirov.course.chat.domain.entity

data class Reaction(
    val emojiName: String,
    val emojiCode: String,
) {
    override fun toString(): String =
        if (emojiCode.isEmpty()) "" else String(Character.toChars(emojiCode.toInt(16)))
}