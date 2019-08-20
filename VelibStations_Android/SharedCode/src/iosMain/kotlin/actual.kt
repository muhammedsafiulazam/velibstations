package com.muhammedsafiulazam.mobile

import platform.UIKit.UIDevice

actual fun getPlatform(): String {
    return UIDevice.currentDevice.systemName()
}