package com.ntnu.ivansh.part2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity


class RandomActivity : ComponentActivity() {
    private var limit = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        limit = intent.getIntExtra("LIMIT",10);

        setResult(RESULT_OK, Intent()
            .putExtra("FIRST_RANDOM", (0..limit).random())
            .putExtra("SECOND_RANDOM", (0..limit).random()));

        finish()

    }
}