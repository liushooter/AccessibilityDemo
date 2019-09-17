package io.litex.wechathelper

import android.os.Bundle
import android.widget.Toast
import android.content.Intent
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import io.litex.wechathelper.access.isAccessibilityServiceOn
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        hello?.also {
            val isOn = isAccessibilityServiceOn()


            if (isOn) {
                it.text ="服务已经开启,是否关闭?"
                // it.isEnabled = !isOn
            } else {
                it.text = "点击开启服务"
            }

            it.setOnClickListener {
                startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            }
        }
    }


    fun hint() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Alert")
        builder.setMessage("We have a message")

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            Toast.makeText(applicationContext,
                    android.R.string.yes, Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            Toast.makeText(applicationContext,
                    android.R.string.no, Toast.LENGTH_SHORT).show()
        }

        builder.setNeutralButton("Maybe") { dialog, which ->
            Toast.makeText(applicationContext,
                    "Maybe", Toast.LENGTH_SHORT).show()
        }

        builder.show()
    }
}
