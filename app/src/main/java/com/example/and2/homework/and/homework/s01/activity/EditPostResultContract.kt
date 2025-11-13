package com.example.and2.homework.and.homework.s01.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class EditPostResultContract : ActivityResultContract<String, String?>() {
    override fun createIntent(
        context: Context,
        input: String
    ): Intent {
        val intent = Intent(context, IntentHandlerActivity::class.java)
        intent.putExtra(Intent.EXTRA_TEXT, input)
        intent.action = Intent.ACTION_SEND
        return intent
    }

    override fun parseResult(
        resultCode: Int,
        intent: Intent?
    ): String? {
        return if (resultCode == Activity.RESULT_OK) {
            intent?.getStringExtra(Intent.EXTRA_TEXT)
        } else {
            null
        }
    }

}