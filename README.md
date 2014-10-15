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

1. 从 [发布页面](https://github.com/ActonInc/RocketSkates-Android-SDK/releases) 下载最新版 SDK，或者克隆此 repo。

2. 将 `Acton_R_SDK.jar` 放进 Android 项目的 'libs' 文件夹。

 ![project-libs](https://github.com/ActonInc/RocketSkates-Android-SDK/raw/master/img/project-libs.png)

3. 打开项目属性面板 (Properties->Java Build Path)。 点击 'Libraries'，展开 'Android Private Libraries', 确认其中包含 `Acton_R_SDK.jar`。

 ![java-build-path](https://github.com/ActonInc/RocketSkates-Android-SDK/raw/master/img/java-build-path.png)

完成以上步骤后，您就可以使用 ACTON 风火轮 SDK 提供的接口了。

## 示例

1. [Hello R Skates!](https://github.com/ActonInc/RocketSkates-Android-SDK/tree/master/samples/HelloRSkates)
 搜索并连接附近的 ACTON 风火轮。

 ![sample1](https://github.com/ActonInc/RocketSkates-Android-SDK/raw/master/img/sample1.png)

2. [Skate Control](https://github.com/ActonInc/RocketSkates-Android-SDK/tree/master/samples/SkateControl)
 获取风火轮的实时状态，切换运行模式。
 
 ![sample2](https://github.com/ActonInc/RocketSkates-Android-SDK/raw/master/img/sample2.png)

3. [RemoteControl](https://github.com/ActonInc/RocketSkates-Android-SDK/tree/master/samples/RemoteControl)
 使用手机遥控风火轮

 ![sample3](https://github.com/ActonInc/RocketSkates-Android-SDK/raw/master/img/sample3.png)

## Javadoc (英文)
在 'javadoc' 文件夹中，有本 SDK 的完整 Javadoc。

## FAQ