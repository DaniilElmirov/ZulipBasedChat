package com.elmirov.homework_1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.elmirov.homework_1.databinding.ActivityFirstBinding

class FirstActivity : AppCompatActivity() {

	private lateinit var binding: ActivityFirstBinding

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
	}
}