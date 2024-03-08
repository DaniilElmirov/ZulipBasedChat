package com.elmirov.homework_1.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.elmirov.homework_1.adapter.ContactAdapter
import com.elmirov.homework_1.databinding.ActivityFirstBinding

class FirstActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirstBinding

    private val contactAdapter by lazy {
        ContactAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.first) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val contract = ActivityResultContracts.StartActivityForResult()
        val launcher = registerForActivityResult(contract) {
            applyResult(it)
        }

        binding.next.setOnClickListener {
            launcher.launch(SecondActivity.newIntent(this))
        }
    }

    private fun applyResult(result: ActivityResult) {
        if (result.resultCode == RESULT_OK) {
            val contacts =
                result.data?.getStringArrayExtra(SecondActivity.EXTRA_CONTACTS) ?: arrayOf<String>()

            setupAdapter(contacts.toList())
        }
    }

    private fun setupAdapter(contacts: List<String>) {
        binding.recycler.adapter = contactAdapter
        contactAdapter.contacts = contacts
    }

    override fun onDestroy() {
        binding.recycler.adapter = null
        super.onDestroy()
    }
}