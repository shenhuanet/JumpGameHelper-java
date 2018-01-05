# JumpGameHelper
跳一跳小游戏辅助，Android手机通过ADB连接电脑，在电脑端操作微信"跳一跳"小游戏.

## 实现原理
1. 检测Android设备 Adb shell getprop ro.product.model
2. 获取手机分辨率 Adb shell wm size
3. 截图到控制台
```commandline
Adb shell screencap -p /sdcard/screenshot.png
Adb shell pull /sdcard/screenshot.png D:/screenshot.png

// adb shell screencap -p | perl -pe "binmode(STDOUT);s/\r\n/\n/g" > screenshot.png 可快速截图到PC
```
4. 判断小黑人的脚下位置坐标通过RGB色值计算 (G == 55 && R + B > 145 && R + B < 148) 该色值由截图BufferedImage取得
5. 通过用户对图片点击获取坐标，再通过两点间距离公式求得距离distance
6. 按下时间计算time公式: distance * (1468.125f / (phoneWidth * 1f))
7. 模拟发送按键 Adb shell input touchscreen swipe 100 100 200 200 <time>

## 预览
![overview](https://github.com/shenhuanet/JumpGameHelper-java/blob/master/overview.png)

## TODO
- [ ] 优化截图并计算中心位置
- [ ] 提高定时刷新率
- [ ] 自动模式+自动位置矫正

## About Me
CSDN：http://blog.csdn.net/klxh2009<br>
JianShu：http://www.jianshu.com/u/12a81897d5bc

## License

    Copyright 2017 shenhuanet

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
