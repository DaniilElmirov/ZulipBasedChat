package com.elmirov.course

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.elmirov.course.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val emojis = intArrayOf(
        0x1f600,
        0x1f603,
        0x1f604,
        0x1f601,
        0x1f606,
        0x1f605,
        0x1f923,
        0x1f602,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.apply {
            setAvatar.setOnClickListener {
                messageLayout.setAvatar(R.drawable.ic_launcher_foreground)
            }

            setName.setOnClickListener {
                messageLayout.userName = "Какое-то имя"
            }

            setMessage.setOnClickListener {
                messageLayout.messageText =
                    "Какое-то сообщение Какое-то сообщение Какое-то сообщениеКакое-то сообщение Какое-то сообщение"
            }

            messageLayout.apply {
                onIconAddClick {
                    it.addReaction(getRandomEmoji(), getRandomCount())
                }
            }

            addReaction.setOnClickListener {
                messageLayout.addReaction(getRandomEmoji(), getRandomCount())
            }
        }
    }

    private fun getRandomEmoji(): String {
        val emoji = emojis[Random.nextInt(0, emojis.size)]
        return String(Character.toChars(emoji))
    }

    private fun getRandomCount(): Int =
        Random.nextInt(1, 100)
}