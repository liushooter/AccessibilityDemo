package io.litex.wechathelper.access

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import java.util.concurrent.TimeUnit


/**
 * Created by 周智慧 on 22/01/2018.
 */

class HelperService : AccessibilityService() {

    val zhifuBtn = "com.tencent.mm:id/fpv"  // 立即支付按钮 id
    val priceTxt = "com.tencent.mm:id/fng"  // 价格文本
    val whoSellTxt = "com.tencent.mm:id/fpa"  // 卖家
    val getMoneyTxt = "com.tencent.mm:id/fne" // 收款方

    override fun onInterrupt() {
        onServiceConnected()
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
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

        var wxList = win.findAccessibilityNodeInfosByText("发现")


        if (null != wxList) {

            Log.e(">>> wxlist size", wxList.size.toString())

            for ( node in wxList ){
                Log.e(" node isClickable", node.isClickable.toString())
            }


            if( 1 == wxList.size){
                var node =  wxList[0]

                if(node.isClickable) {
                    TimeUnit.MILLISECONDS.sleep(500L) // 0.5 second
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

            ///////

            if( 2 == wxList.size ) {

                var scans =  win.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/ded")

                Log.e("scan size", scans.size.toString())

                for ((inx, ele) in scans.withIndex()) {

                    Log.e("inx", inx.toString())
                    Log.e("scan isClickable", ele.isClickable.toString())
                }

                if (scans.size >=2 ) {
                    TimeUnit.MILLISECONDS.sleep(500L) // 0.5 second
                    scans[1].performAction(AccessibilityNodeInfo.ACTION_CLICK) // 点击事件
                }

            }

            ///// 进入到 支付流程 ///////
//            查询 支付 btn
//            如果有进入下一个流程：
//            读取 谁家卖的产品
//                 金额
//                 收款方



            var zhifus = win.findAccessibilityNodeInfosByViewId(zhifuBtn) // win 来自 rootInActiveWindow


            Log.e("=============== zhifus", zhifus.size.toString() )

            if(zhifus.size > 0 ){

                for (ele in zhifus){

                    Log.e("zhihu >>>>>" , ele.isClickable.toString())

                    if(ele.isClickable){
                        Log.e("+++++", "please click")
                    }
                }



                var prices = win.findAccessibilityNodeInfosByViewId(priceTxt)
                Log.e("price", prices.size.toString() )

                var whos = win.findAccessibilityNodeInfosByViewId(whoSellTxt)
                Log.e("whos", whos.size.toString() )

                var getMoneyWho  = win.findAccessibilityNodeInfosByViewId(getMoneyTxt)
                Log.e("getMoneyWho", getMoneyWho.size.toString() )

            }

        }



    }


}

