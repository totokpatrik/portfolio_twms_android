package com.twms.twms_f_m_android.ui

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.twms.twms_f_m_android.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        // TODO test with zebra device (on the emulator F1 is not triggering this event)
//        when (keyCode) {
//            KeyEvent.KEYCODE_F1 -> {
//
//                //your Action code
//                return true
//            }
//        }
//        EventBus.getDefault().post(Event(keyCode))
        return super.onKeyUp(keyCode, event)
    }
}