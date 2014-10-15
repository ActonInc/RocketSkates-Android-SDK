![Skates](http://static.wixstatic.com/media/04a4fc_bbd24139a63647388379754366962dec.jpg_srz_p_980_460_75_22_0.50_1.20_0.00_jpg_srz)


# ACTON RocketSkates SDK - Android Developer Guide

ACTON RocketSkates™, the world’s first smart electric skates, are designed to be lightweight, hands-free and tons of fun. RocketSkates are also packed with smart technology and they can connect with apps on users’ smartphones via Bluetooth.

This document introduces how to integrate ACTON RocketSkates SDK into your Android app project.

## Requirements

ACTON RocketSkates uses Bluetooth Low Energy to communicate with smart phones. In order to utilize this SDK, your Android project's target API level must be equal to or higher than 18 (Android 4.3).

Also, please make sure your `AndroidManifest.xml` has the following definitions inside `<manifest>`:

	<uses-permission android:name="android.permission.BLUETOOTH" />
    <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" /> 

ACTON RocketSkates SDK is compatible with ADT and Android Studio.

## SDK Installation

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