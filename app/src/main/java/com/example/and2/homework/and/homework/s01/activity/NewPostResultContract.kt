package com.example.and2.homework.and.homework.s01.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.example.and2.homework.and.homework.s01.fragment.NewPostFragment

object NewPostResultContract : ActivityResultContract<Unit, String?>() {
    override fun createIntent(
        context: Context,
        input: Unit
    ): Intent {
        return Intent(context, NewPostFragment::class.java)
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