package com.elmirov.homework_1.activity

import android.Manifest
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.elmirov.homework_1.R
import com.elmirov.homework_1.databinding.ActivitySecondBinding
import com.elmirov.homework_1.service.ContactsService

class SecondActivity : AppCompatActivity() {

    companion object {

        const val EXTRA_PERCENT = "percent"

        const val EXTRA_CONTACTS = "contacts"

        const val ACTION_LOADING = "loading"

        const val ACTION_LOADING_DONE = "loading_done"

        fun newIntent(context: Context): Intent =
            Intent(context, SecondActivity::class.java)
    }

    private lateinit var binding: ActivitySecondBinding

    private val localBroadcastManager by lazy {
        LocalBroadcastManager.getInstance(this)
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                ACTION_LOADING -> {
                    binding.progressBar.progress = intent.getIntExtra(EXTRA_PERCENT, 0)
                }

                ACTION_LOADING_DONE -> {
                    val contacts = intent.getStringArrayExtra(EXTRA_CONTACTS) ?: arrayOf()
                    showToast(contacts.size)
                    sendContacts(contacts)
                }
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initReceiver()
        initListeners()
    }

    private fun showToast(contactsNumber: Int) {
        Toast.makeText(this, contactsNumber.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun initReceiver() {
        val intentFilter = IntentFilter().apply {
            addAction(ACTION_LOADING)
            addAction(ACTION_LOADING_DONE)
        }

        localBroadcastManager.registerReceiver(receiver, intentFilter)
    }

    private fun initListeners() {
        binding.load.setOnClickListener {
            if (permissionGranted()) {
                it.isEnabled = false

                Intent(this, ContactsService::class.java).apply {
                    startService(this)
                }
            } else
                requestContactsPermission()
        }
    }

    private fun permissionGranted(): Boolean =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED

    private fun requestContactsPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS))
            showContactsPermissionRationale()
        else
            requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
    }

    private fun showContactsPermissionRationale() {
        AlertDialog.Builder(this)
            .setTitle(R.string.contacts_permission_title)
            .setMessage(R.string.contacts_request)
            .setPositiveButton(R.string.settings) { _, _ ->
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", packageName, null)
                )
                startActivity(intent)
            }
            .show()
    }

    private fun sendContacts(contacts: Array<String>) {
        Intent().apply {
            putExtra(EXTRA_CONTACTS, contacts)
            setResult(RESULT_OK, this)
        }
        finish()
    }

    override fun onDestroy() {
        localBroadcastManager.unregisterReceiver(receiver)
        super.onDestroy()
    }
}