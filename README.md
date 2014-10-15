![Skates](http://static.wixstatic.com/media/04a4fc_bbd24139a63647388379754366962dec.jpg_srz_p_980_460_75_22_0.50_1.20_0.00_jpg_srz)


# ACTON 风火轮 Android SDK 开发指南

ACTON 风火轮是由 ACTON 公司自主设计研发的全球第一款智能可穿戴电动鞋。风火轮使用时无需特殊鞋子，无需任何遥控设备。内置高效锂电、电机及智能控制系统，用户前倾加速，后倾减速及制动，并有多种滑行模式可选。

通过移动端 app 相连，用户可实时查看风火轮状态，跟踪滑行路径，更有基于地理位置的现实互动游戏。

本文档介绍了如何在您的 Android app 中使用 ACTON 风火轮 SDK。

## 系统需求

ACTON 风火轮通过低功耗蓝牙 (Bluetooth Low Energy) 与智能设备通讯。本 SDK 仅支持 Android API Level 18 (Android 4.3 'Jelly Bean') 或更高版本。

使用本 SDK 时，请在 `AndroidManifest.xml` 的 `<manifest>` 一节中添加以下内容：

	<uses-permission android:name="android.permission.BLUETOOTH" />
    <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" /> 

本 SDK 兼容 ADT 和 Android Studio。

## SDK 安装和使用

1. 从下载最新版 SDK

1. Download the latest version of ACTON RocketSkates SDK from our [release page](https://github.com/ActonInc/RocketSkates-Android-SDK/releases), or simply clone this project.

2. Copy `Acton_R_SDK.jar` into your project's 'libs' folder.

 ![project-libs](https://github.com/ActonInc/RocketSkates-Android-SDK/raw/master/img/project-libs.png)

3. Open your project's Properties->Java Build Path. Click on 'Libraries' tab and open 'Android Private Libraries', make sure that `Acton_R_SDK.jar` is there. 

 ![java-build-path](https://github.com/ActonInc/RocketSkates-Android-SDK/raw/master/img/java-build-path.png)

After these steps, your app would be able to use APIs provided by ACTON RocketSkates SDK.

## Samples

We created 3 sample projects for you to start with:

1. [Hello R Skates!](https://github.com/ActonInc/RocketSkates-Android-SDK/tree/master/samples/HelloRSkates)
 Scan for RocketSkates and connect to them.

 ![sample1](https://github.com/ActonInc/RocketSkates-Android-SDK/raw/master/img/sample1.png)

2. [Skate Control](https://github.com/ActonInc/RocketSkates-Android-SDK/tree/master/samples/SkateControl)
 Retrieve RocketSkates' real-time status, and change their mode.
 
 ![sample2](https://github.com/ActonInc/RocketSkates-Android-SDK/raw/master/img/sample2.png)

3. [RemoteControl](https://github.com/ActonInc/RocketSkates-Android-SDK/tree/master/samples/RemoteControl)
 Remote-control your RocketSkates.

 ![sample3](https://github.com/ActonInc/RocketSkates-Android-SDK/raw/master/img/sample3.png)

You can import these project into your ADT or Android Studio workspace, or simply copy some of their code into your project.

## Javadoc
Detailed Javadoc can be found in 'javadoc' folder.

## FAQ