package io.litex.wechathelper.access

import android.util.Log
import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

/**
 * Created by 周智慧 on 22/01/2018.
 */

class HelperService : AccessibilityService() {

    override fun onInterrupt() {
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
//        val serviceInfo = AccessibilityServiceInfo()
//        serviceInfo.eventTypes = AccessibilityEvent.TYPES_ALL_MASK//typeNotificationStateChanged|typeWindowStateChanged|typeWindowContentChanged
//        serviceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC//feedbackGeneric
//        serviceInfo.packageNames = arrayOf("com.tencent.mm")//com.tencent.mm
//        serviceInfo.notificationTimeout = 100
//        serviceInfo.flags = AccessibilityServiceInfo.DEFAULT
////        //android:canRetrieveWindowContent="true"
////        serviceInfo.canRetrieveWindowContent = true
//        setServiceInfo(serviceInfo)
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {

//        dispatchEvent(event, rootInActiveWindow) // getRootInActiveWindow

        Log.e(TAG, "==============Start====================")
//
//        val eventType = event?.getEventType()
//
//        when (eventType) {
//            AccessibilityEvent.TYPE_VIEW_CLICKED -> Log.e(">>> ev","TYPE_VIEW_CLICKED")
//            AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED -> Log.e(">>> ev", "TYPE_NOTIFICATION_STATE_CHANGED")
//            AccessibilityEvent.TYPES_ALL_MASK -> Log.e(">>> ev", "TYPES_ALL_MASK")
//            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> Log.e(">>> ev", "TYPE_WINDOW_STATE_CHANGED")
//            AccessibilityEvent.CONTENT_CHANGE_TYPE_SUBTREE -> Log.e(">>> ev", "CONTENT_CHANGE_TYPE_SUBTREE")
//            AccessibilityEvent.TYPE_VIEW_LONG_CLICKED -> Log.e(">>> ev", "TYPE_VIEW_LONG_CLICKED")
//
//        }
//
//        Log.e(TAG, "==============End====================")




        var win = rootInActiveWindow

//        val isClickable = win.isClickable

//
//
        var wxList = win.findAccessibilityNodeInfosByText("发现")

        Log.e(">>> wxlist size", wxList.size.toString())

        for ( node in wxList ){

            Log.e(" node isClickable", node.isClickable.toString())

        }


        if( 1 == wxList.size ){
            var node =  wxList[0]

            if(node.isClickable) {
                node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            }


            var parent: AccessibilityNodeInfo? = node?.getParent()
            while (parent != null) {

                if (parent.isClickable) {
                    parent.performAction(AccessibilityNodeInfo.ACTION_CLICK) // 点击事件
                    break
                }

                parent = parent.parent
            }

        }


        if( 2 == wxList.size ) {

            var scans =  win.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/ded")



            Log.e("scan size", scans.size.toString())

            for ((inx, ele) in scans.withIndex()) {

                Log.e("inx", inx.toString())
                Log.e("scan isClickable", ele.isClickable.toString())
            }

            if (scans.size >=2 ) {
                scans[1].performAction(AccessibilityNodeInfo.ACTION_CLICK) // 点击事件
            }

        }


        var zz =  win.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/c1")

        Log.e(">>> zz size", zz.size.toString())

        if( 1 == zz.size ){

//            Log.e("nodes[0].contentDesc", nodes[0].contentDescription.toString())

//            Log.e("parent isClickable", nodes[0].parent.isClickable.toString() )

//            nodes[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)


//
        }

    }


}

