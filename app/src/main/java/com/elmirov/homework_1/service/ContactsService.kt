package com.elmirov.homework_1.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.provider.ContactsContract
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.elmirov.homework_1.activity.SecondActivity
import kotlin.concurrent.thread

class ContactsService : Service() {

    private companion object {
        const val CORRECTION_FACTOR = 10
    }

    private val localBroadcastManager by lazy {
        LocalBroadcastManager.getInstance(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        thread {
            val contacts = getContacts()

            imitateWork()

            sendWorkResult(contacts)
        }

        return START_NOT_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? = null

    private fun getContacts(): Array<String> {

        val contacts = mutableListOf<String>()

        val cursor = application.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        while (cursor?.moveToNext() == true) {
            val contactName = cursor.getString(
                cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME)
            )

            val phoneIndex = cursor.getColumnIndex(
                ContactsContract.CommonDataKinds.Phone.NUMBER
            )
            val phoneNumber = cursor.getString(phoneIndex)

            contacts.add("$contactName\n$phoneNumber")
        }

        cursor?.close()

        return contacts.toSet().toTypedArray()
    }

    private fun imitateWork() {
        for (i in 1..10) {
            Thread.sleep(100)

            Intent(SecondActivity.ACTION_LOADING).apply {
                putExtra(SecondActivity.EXTRA_PERCENT, i * CORRECTION_FACTOR)
                localBroadcastManager.sendBroadcast(this)
            }
        }
    }

    private fun sendWorkResult(contacts: Array<String>) {
        Intent(SecondActivity.ACTION_LOADING_DONE).apply {
            putExtra(SecondActivity.EXTRA_CONTACTS, contacts)
            localBroadcastManager.sendBroadcast(this)
        }
    }
}