package io.litex.wechathelper.access

import android.util.Log
import java.util.concurrent.TimeUnit
import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.R.string

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import android.os.StrictMode


class HelperService : AccessibilityService() {

    val zhifuBtn = "com.tencent.mm:id/fpv"  // 立即支付按钮 id
    val priceTxt = "com.tencent.mm:id/fng"  // 价格文本
    val whoSellTxt = "com.tencent.mm:id/fpa"  // 卖家
    val getMoneyTxt = "com.tencent.mm:id/fne" // 收款方
    val payCheckBox = "com.tencent.mm:id/fsd" // 付款选项

    override fun onInterrupt() {
//        onServiceConnected()
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {

        if (android.os.Build.VERSION.SDK_INT > 9) {
            val policy = StrictMode.ThreadPolicy.Builder()
                    .permitAll().build()

            StrictMode.setThreadPolicy(policy)
        }

        Log.e("SDK_INT", android.os.Build.VERSION.SDK_INT.toString())


//        dispatchEvent(event, rootInActiveWindow) // getRootInActiveWindow

        Log.e(TAG, "==============Start====================")

        val eventType = event?.getEventType()
        Log.e("eventType >>>", eventType.toString() )

        Log.e(TAG, "==============End====================")


//        when (eventType) {
//            AccessibilityEvent.TYPE_VIEW_CLICKED -> Log.e(">>> ev","TYPE_VIEW_CLICKED")
//            AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED -> Log.e(">>> ev", "TYPE_NOTIFICATION_STATE_CHANGED")
//            AccessibilityEvent.TYPES_ALL_MASK -> Log.e(">>> ev", "TYPES_ALL_MASK")
//            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> Log.e(">>> ev", "TYPE_WINDOW_STATE_CHANGED")
//            AccessibilityEvent.CONTENT_CHANGE_TYPE_SUBTREE -> Log.e(">>> ev", "CONTENT_CHANGE_TYPE_SUBTREE")
//            AccessibilityEvent.TYPE_VIEW_LONG_CLICKED -> Log.e(">>> ev", "TYPE_VIEW_LONG_CLICKED")
//
//        }
        

        var win = rootInActiveWindow


        if (false) {

            var wxList = win.findAccessibilityNodeInfosByText("发现")

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

            ///// 进入到 Apple 支付流程 ///////
//            查询 支付 btn
//            如果有进入下一个流程：
//            读取 谁家卖的产品
//                 金额
//                 收款方



            var zhifus = win.findAccessibilityNodeInfosByViewId(zhifuBtn) // win 来自 rootInActiveWindow


            Log.e("=============== zhifus", zhifus.size.toString() )

            if(zhifus.size > 0 ){

                var prices = win.findAccessibilityNodeInfosByViewId(priceTxt)
                Log.e("price", prices.size.toString() )

                if (1 == prices.size){
                    var priceStr  = prices.first().text
                    Log.e("price Text",  priceStr.toString())
                }

                var whos = win.findAccessibilityNodeInfosByViewId(whoSellTxt)
                Log.e("whos", whos.size.toString() )

                if (1 == whos.size){
                    var whoStr = whos.first().text
                    Log.e("price Text",  whoStr.toString())
                }


                var getMoneyWho = win.findAccessibilityNodeInfosByViewId(getMoneyTxt)
                Log.e("getMoneyWho", getMoneyWho.size.toString() )

                if ( 1 == getMoneyWho.size){
                    var getMoneyWhoStr = getMoneyWho.first().text

                    Log.e("price Text",  getMoneyWhoStr.toString())
                }


                if (1 == zhifus.size){

                    var zhifubtn = zhifus.first()

                    if( zhifubtn.isClickable ){
                        zhifubtn.performAction(AccessibilityNodeInfo.ACTION_CLICK) // 点击事件
                    }
                }

            }


            ///// 进入到另一种支付流程 ///////
            //    火车票


            ///// 进入到另一种支付流程 ///////
            //    bili

            val biliPaybtn = "com.tencent.mm:id/b0f"
            val biliPayPriceTxt = "com.tencent.mm:id/drj"

            var biliPayPrices = win.findAccessibilityNodeInfosByViewId(biliPayPriceTxt)
            var biliPays = win.findAccessibilityNodeInfosByViewId(biliPaybtn)

            Log.e(" biliPayPrices >>>>> ", biliPayPrices.size.toString())
            Log.e(" biliPays >>>>> ", biliPays.size.toString())


            if (biliPayPrices.size>0 && biliPays.size >0 ){ //

                var _price = biliPayPrices.first().text.toString()

                Log.e("bili price >>>>> ", _price)

                if(biliPays.first().isClickable) {
                    TimeUnit.MILLISECONDS.sleep(1000L) // 5 second
                    biliPays.first().performAction(AccessibilityNodeInfo.ACTION_CLICK)
                }
            }


//
//            ///// 进入到 支付余额不足 ///////
//
//            var cks = win.findAccessibilityNodeInfosByViewId(payCheckBox)
//            Log.e(">>>>  cks", cks.size.toString())
//
//            for (ele in cks ) {
//                Log.e("isClicked" , ele.isClickable.toString())
//                Log.e("isCheckable", ele.isCheckable.toString())
//                Log.e("text", ele.text.toString())
//            }


            ///// 进入 支付环节 ///////

        }


        ///// 输入密码 ///////
        // bili
        // 请输入支付密码


        if (win != null) {
            var comps = win.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/fsm")
            Log.e("comps size", comps.size.toString())


            if (comps.size > 0 ) {
                Log.e(">>>>>>> comps ", comps.size.toString())

                var url = "http://192.168.50.231:7001/"
                var resp = getSync(url)
                var bodyStr = resp.body!!.string()

                Log.e("<<<< httpbody >>>", bodyStr)

                TimeUnit.MILLISECONDS.sleep(1000L)

            }
        }

    }

    //同步Get请求
    fun getSync(url: String): Response {
        val request = Request.Builder().url(url).build()

        val response = OkHttpClient().newCall(request).execute()

        return response
    }

}

