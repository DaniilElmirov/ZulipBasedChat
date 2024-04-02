package com.elmirov.course.ui.adapter.delegate

interface DelegateItem {

    fun content(): Any

    fun id(): Int

    fun compareToOther(other: DelegateItem): Boolean
}