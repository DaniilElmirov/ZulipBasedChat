package com.elmirov.course.core.adapter.delegate

interface DelegateItem {

    fun content(): Any

    fun id(): Int

    fun compareToOther(other: DelegateItem): Boolean
}