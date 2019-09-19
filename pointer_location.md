pointer_location


1 : 133 1502

2: 541 1530

3: 872 1540

4 : 130 1677

5: 499  1699

6: 858 1670

7: 157 1864

8: 526 1867

9: 890 1879

0: 540 2022

075369

adb shell input tap x,y

adb shell input tap 540 2022
adb shell input tap 157 1864
adb shell input tap 499 1699
adb shell input tap 872 1540
adb shell input tap 858 1670
adb shell input tap 890 1879

https://blog.bihe0832.com/adb-shell-input.html



---

```java
 /**点击某个视图*/
     public static void perforGlobalClick(AccessibilityNodeInfo info) {
        Rect rect = new Rect();
        info.getBoundsInScreen(rect);
        perforGlobalClick(rect.centerX(), rect.centerY());
    }

    public static void perforGlobalClick(int x, int y) {
        execShellCmd("input tap " + x + " " + y);
    }
    /**
     * 执行shell命令
     *
     * @param cmd
     */
    public static void execShellCmd(String cmd) {

        try {

            // 申请获取root权限，这一步很重要，不然会没有作用
            Process process = Runtime.getRuntime().exec("su");

            // 获取输出流
            OutputStream outputStream = process.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeBytes(cmd);
            dataOutputStream.flush();
            dataOutputStream.close();
            outputStream.close();
//            process.waitFor();



        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

public void execShell(String cmd){
        try{
            //权限设置
            Process p = Runtime.getRuntime().exec("sh");  //su为root用户,sh普通用户
            //获取输出流
            OutputStream outputStream = p.getOutputStream();
            DataOutputStream dataOutputStream=new DataOutputStream(outputStream);
            //将命令写入
            dataOutputStream.writeBytes(cmd);
            //提交命令
            dataOutputStream.flush();
            //关闭流操作
            dataOutputStream.close();
            outputStream.close();
       }
       catch(Throwable t)
        {
             t.printStackTrace();
            }
    }

```