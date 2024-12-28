package com.codewithfk.chatooz_android

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.zegocloud.zimkit.services.ZIMKit
import im.zego.zim.enums.ZIMErrorCode


class MainActivity : AppCompatActivity() {

    lateinit var userId: EditText
    lateinit var userName: EditText
    lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login_activity)
        userId = findViewById(R.id.userId)
        userName = findViewById(R.id.userName)
        submitButton = findViewById(R.id.submit)

        val sharedPrefs = getSharedPreferences("user", MODE_PRIVATE)
        val uId = sharedPrefs.getString("userId", null)
        val uName = sharedPrefs.getString("userName", null)

        if (uId != null && uName != null) {
            connectUser(uId, uName)
            return
        }


        submitButton.setOnClickListener {
            val userId = userId.text.toString()
            val userName = userName.text.toString()
            if (userId.isNotEmpty() && userName.isNotEmpty()) {
                val editor = getSharedPreferences("user", MODE_PRIVATE).edit()
                editor.putString("userId", userId)
                editor.putString("userName", userName)
                editor.apply()
                connectUser(userId, userName)
            }
        }
    }

    fun connectUser(userId: String, userName: String) {
        ZIMKit.connectUser(userId, userName, "https://storage.zego.im/IMKit/avatar/avatar-0.png") {
            if (it.code == ZIMErrorCode.SUCCESS) {
                toConversationActivity()
                finish()
            } else {
                val msg = it.message ?: "Unknown error"
                runOnUiThread {
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun toConversationActivity() {
        // Redirect to the conversation list (Activity) you created.
        val intent = Intent(
            this,
            ConversationActivity::class.java
        )
        startActivity(intent)
    }
}