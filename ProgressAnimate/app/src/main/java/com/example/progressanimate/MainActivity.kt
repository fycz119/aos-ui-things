package com.example.progressanimate

import android.os.Bundle
import android.view.MotionEvent
import android.view.View.OnTouchListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private var lastX: Float = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        var mProgressView = ProgressView(this);
        mProgressView.setMaxProgress(100f)
        setContentView(mProgressView)

        mProgressView.setOnTouchListener(OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> lastX = event.x
                MotionEvent.ACTION_MOVE -> {
                    mProgressView.setCurrentProgress(
                        mProgressView.getCurrentProgress() +
                                (if (event.x > lastX) 0.5f else -0.5f)
                    )
                    lastX = event.x
                }
            }
            true
        })
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
    }
}