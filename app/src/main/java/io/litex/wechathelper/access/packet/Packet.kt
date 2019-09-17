package io.litex.wechathelper.access.packet

import android.app.Notification
import android.app.PendingIntent
import android.text.TextUtils
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import io.litex.wechathelper.access.TAG

/**
 * Created by 周智慧 on 22/01/2018.
 */
fun handleNotification(event: AccessibilityEvent?) {
    if (event == null) {
        return
    }
    val texts = event.text
    if (!texts.isEmpty()) {
        for (text in texts) {
            Log.e(">>>  event text ", text.toString() )
            val content = text.toString()
            if (content.contains("[微信红包]")) {
                if (event.parcelableData != null && event.parcelableData is Notification) {
                    val notification = event.parcelableData as Notification
                    val pendingIntent = notification.contentIntent
                    try {
                        pendingIntent.send()
                    } catch (e: PendingIntent.CanceledException) {
                        e.printStackTrace()
                    }

                }
            }
        }
    }
}


fun searchPacket(rootInActiveWindow: AccessibilityNodeInfo?) {
    Log.i(TAG, "searchPacket node: ${rootInActiveWindow} childCount: ${rootInActiveWindow?.childCount}   idName: ${rootInActiveWindow?.getViewIdResourceName()}")


    if (rootInActiveWindow?.text.toString() == "领取红包") {

        if (rootInActiveWindow?.isClickable == true) {
            rootInActiveWindow.performAction(AccessibilityNodeInfo.ACTION_CLICK) // 点击事件
        } else {

            var parent: AccessibilityNodeInfo? = rootInActiveWindow?.getParent() // 父节点
            while (parent != null) {
                if (parent.isClickable) {
                    parent.performAction(AccessibilityNodeInfo.ACTION_CLICK) // 点击事件
                    break
                }
                parent = parent.parent
            }
        }


    } else {
        for (i in 0 until (rootInActiveWindow?.childCount ?: -1)) {
            searchPacket(rootInActiveWindow?.getChild(i))
        }
    }
}

fun openPacket(rootInActiveWindow: AccessibilityNodeInfo?) {
    Log.i(TAG, "openPacket node: ${rootInActiveWindow} childCount: ${rootInActiveWindow?.childCount}   idName: ${rootInActiveWindow?.getViewIdResourceName()}")
    if (rootInActiveWindow?.isClickable == true && rootInActiveWindow?.className?.contains("android.widget.Button") == true) {
        rootInActiveWindow?.performAction(AccessibilityNodeInfo.ACTION_CLICK) // 点击事件
    }
//        node?.traversalAfter
    for (i in 0 until (rootInActiveWindow?.childCount ?: -1)) {
        openPacket(rootInActiveWindow?.getChild(i))
    }
}

fun closePacket(rootInActiveWindow: AccessibilityNodeInfo?) {
    Log.i(TAG, "closePacket node: ${rootInActiveWindow} childCount: ${rootInActiveWindow?.childCount}   idName: ${rootInActiveWindow?.getViewIdResourceName()}")
    if (rootInActiveWindow?.className == "android.widget.LinearLayout" && rootInActiveWindow?.isClickable && TextUtils.isEmpty(rootInActiveWindow?.text)) {
        //className: android.widget.LinearLayout; text: null; error: null; maxTextLength: -1; contentDescription: null; viewIdResName: com.tencent.mm:id/ho;
        rootInActiveWindow?.performAction(AccessibilityNodeInfo.ACTION_CLICK) // 点击事件
        return
    }

    for (i in 0 until (rootInActiveWindow?.childCount ?: -1)) {
        closePacket(rootInActiveWindow?.getChild(i))
    }
}