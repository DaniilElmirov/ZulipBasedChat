package com.elmirov.homework_1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.elmirov.homework_1.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

	private lateinit var binding: ActivitySecondBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivitySecondBinding.inflate(layoutInflater)
		setContentView(binding.root)
	}
}