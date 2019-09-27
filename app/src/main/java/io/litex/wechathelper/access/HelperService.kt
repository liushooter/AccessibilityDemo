package io.litex.wechathelper.access

import android.accessibilityservice.AccessibilityService
import android.os.StrictMode
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.TimeUnit
import okhttp3.FormBody


class HelperService : AccessibilityService() {

    val Host = "http://192.168.50.231:7001/"

    val jdPayBtnViewId = "com.tencent.mm:id/fpv"  // 京东立即支付按钮

    val priceTxt = "com.tencent.mm:id/fng"  // 价格文本
    val whoSellTxt = "com.tencent.mm:id/fpa"  // 卖家
    val getMoneyTxt = "com.tencent.mm:id/fne" // 收款方
    val payCheckBox = "com.tencent.mm:id/fsd" // 付款选项

    val payViewId = "com.tencent.mm:id/fsm"

    val biliPayBtnViewId = "com.tencent.mm:id/b0f"  // bili支付并开通续费

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

//        dispatchEvent(event, rootInActiveWindow) // getRootInActiveWindow

        Log.e(TAG, "==============Start====================")

        val eventType = event?.getEventType()
        Log.e("eventType >>>", eventType.toString())

        var win = rootInActiveWindow


        /////// 进入扫描窗口 ///////
        findBtn(win)

//        event.source()

        if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) { // 当Window发生变化时发送此事件
            var newWin = rootInActiveWindow

            if (newWin == null) {
                Log.w(TAG, "the rootInActiveWindow is null")

            } else {
                Log.i(TAG, "页面切换")


                /////// 扫描 ///////
                scanBtn(newWin)

                /////// bili支付按钮 ///////
                biliClickPayBtn(newWin)

                /////// 输入密码 ///////
                enterPassword(newWin)

                /////// 支付状态 ///////
                getPayState(newWin)
            }

        }

        Log.e(TAG, "==============End====================")


//        when (eventType) {
//            AccessibilityEvent.TYPE_VIEW_CLICKED -> Log.e(">>> ev", "TYPE_VIEW_CLICKED")
//            AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED -> Log.e(">>> ev", "TYPE_NOTIFICATION_STATE_CHANGED")
//            AccessibilityEvent.TYPES_ALL_MASK -> Log.e(">>> ev", "TYPES_ALL_MASK")
//            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> Log.e(">>> ev", "TYPE_WINDOW_STATE_CHANGED")
//            AccessibilityEvent.CONTENT_CHANGE_TYPE_SUBTREE -> Log.e(">>> ev", "CONTENT_CHANGE_TYPE_SUBTREE")
//            AccessibilityEvent.TYPE_VIEW_LONG_CLICKED -> Log.e(">>> ev", "TYPE_VIEW_LONG_CLICKED")
//        }

    }


    fun findBtn(rootWin: AccessibilityNodeInfo) {

        if (rootWin != null) {

            var findBtnView = rootWin.findAccessibilityNodeInfosByText("发现")

            Log.w("findBtnView size", findBtnView.size.toString())


            if (1 == findBtnView.size) {
                var ele = findBtnView.first()

                if (ele.isClickable) {
                    TimeUnit.MILLISECONDS.sleep(500L) // 0.5 second
                    ele.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                }

                var parent: AccessibilityNodeInfo? = ele?.getParent()

                while (parent != null) {

                    if (parent.isClickable) {
                        parent.performAction(AccessibilityNodeInfo.ACTION_CLICK) // 点击事件
                        break
                    }

                    parent = parent.parent
                }

            }

        } else {
            Log.e("scanBtn", " rootInActiveWindow is null")
        }

    }

    fun scanBtn(rootWin: AccessibilityNodeInfo) {

        if (rootWin != null) {

            var scanBtnView = rootWin.findAccessibilityNodeInfosByText("发现")


            if (2 == scanBtnView.size) {

                var scans = rootWin.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/ded") // 扫一扫

                Log.e("scan size", scans.size.toString())

                for ((inx, ele) in scans.withIndex()) {
                    Log.e("inx", inx.toString())
                    Log.e("scan isClickable", ele.isClickable.toString())
                }

                if (scans.size >= 2) {
                    TimeUnit.MILLISECONDS.sleep(500L) // 0.5 second
                    scans[1].performAction(AccessibilityNodeInfo.ACTION_CLICK) // 点击事件
                }

            }

        }
    }

    fun biliClickPayBtn(rootWin: AccessibilityNodeInfo) {


        // TODO
        // 金额
        // 收款方
        // 付款方
        // 支付状态
        //

        var productNameTxts = rootWin.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/d0") // 购买大会员连续包月
        var priceTxts = rootWin.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/drj")  // 价格
        var listViews = rootWin.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/cro") // 用户账户
        var biliPayBtns = rootWin.findAccessibilityNodeInfosByViewId(jdPayBtnViewId) // 支付并开通续费
        // ele instanceof TextView

        if (biliPayBtns.size > 0) {

            var productInfo = ""

            if (1 == productNameTxts.size) {
                var txt = productNameTxts.first()

                Log.w("> 产品 className", txt.className.toString())
                Log.w("> 产品 text", txt.text.toString())

                productInfo += "?product=" + txt.text.toString()
            }


            if (1 == priceTxts.size) {
                var txt = priceTxts.first()

                Log.w("> 价格 txt text", txt.text.toString())

                productInfo += "&price=" + txt.text.toString()

            }


            productInfo += "&desc="

            for (ele in listViews) { // 4个元素
                Log.w("> listViews", ele.text.toString())
                productInfo += ele.text.toString()
            }


            Log.e(TAG, "http")
            getSync(Host + "getProduceInfo" + productInfo)
            Log.e(TAG, "http")


            if (1 == biliPayBtns.size) {

                var payBtn = biliPayBtns.first()

                if (payBtn.isClickable) {
                    payBtn.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                } else {
                    Log.e(TAG, "payBtn not click")
                }
            }


        }
    }


    fun enterPassword(rootWin: AccessibilityNodeInfo) {

        if (rootWin != null) {

            var payView = rootWin.findAccessibilityNodeInfosByViewId(payViewId)
            Log.w("payView size", payView.size.toString())


            if (payView.size > 0) {
                var resp = postSync(Host + "enterPassword", null)
                var bodyStr = resp.body!!.string()

                Log.e("<<<< http body >>>", bodyStr)

                TimeUnit.MILLISECONDS.sleep(1000L)
            }

        } else {
            Log.e("enterPassword", " rootInActiveWindow is null")
        }

    }


    fun getPayState(rootWin: AccessibilityNodeInfo) {

        if (rootWin != null) {

            var payStateTxts = rootWin.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/fhf")  // "支付成功" 文字
            if (1 == payStateTxts.size) {
                var ele = payStateTxts.first()
                ele.text.toString()

            }


            var payPriceTxts = rootWin.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/foa")  // 价格
            if (1 == payPriceTxts.size) {
                var ele = payStateTxts.first()
                ele.text.toString()

            }

            var payeeTxts = rootWin.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/fo8")  // "上海宽娱" 文字
            if (1 == payeeTxts.size) {
                var ele = payStateTxts.first()
                ele.text.toString()
            }


            var paySuccBtns = rootWin.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/fhi") // 完成按钮
            if (1 == paySuccBtns.size) { // 说明购买成功

                var ele = paySuccBtns.first()

                if (ele.isClickable) {

                    // ele.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    // TODO
                    // 点击成功进入下一个流程


                } else {

                }
            }

        } else {

            Log.e("getPayState", " rootInActiveWindow is null")

        }


    }

    // 同步 Http Get请求
    fun getSync(url: String): Response {
        val request = Request.Builder().url(url).build()
        val response = OkHttpClient().newCall(request).execute()
        //TODO

        // 网络状态ok
        // 服务端有个心跳  客户端一直请求
        //

        return response
    }

    fun postSync(url: String, params: HashMap<String, String>?): Response {

        //  https://medium.com/@rohan.s.jahagirdar/android-http-requests-in-kotlin-with-okhttp-5525f879b9e5

        val builder = FormBody.Builder()

        if (params != null) {

            val it = params.entries.iterator()

            while (it.hasNext()) {
                val pair = it.next() as Map.Entry<*, *>
                builder.add(pair.key.toString(), pair.value.toString())
            }

        }

        val formBody = builder.build()
        val request = Request.Builder()
                .url(url)
                .post(formBody)
                .build()

        val response = OkHttpClient().newCall(request).execute()

        return response
    }

}

