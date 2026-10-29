package com.showcase.aboutdevice

import android.app.ActivityManager
import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.os.BatteryManager
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.provider.Settings
import java.io.File
import java.util.Locale
import kotlin.math.roundToInt
import kotlin.math.sqrt

data class Specs(
    val deviceName: String, val model: String, val soc: String,
    val ram: String, val ramExt: String?,
    val used: String, val total: String, val usedFrac: Float,
    val battery: String, val front: String, val rear: String,
    val display: String, val android: String, val patch: String, val build: String,
    val marketName: String
)

object SpecReader {
    private fun f(fmt: String, vararg a: Any) = String.format(Locale.US, fmt, *a)

    private fun prettySoc(): String {
        val model = if (Build.VERSION.SDK_INT >= 31) Build.SOC_MODEL else Build.HARDWARE
        val map = mapOf(
            "SM8850" to "Snapdragon® 8 Elite Gen 5 Mobile Platform",
            "SM8750" to "Snapdragon® 8 Elite Mobile Platform",
            "SM8650" to "Snapdragon® 8 Gen 3 Mobile Platform",
            "SM8550" to "Snapdragon® 8 Gen 2 Mobile Platform",
            "SM8475" to "Snapdragon® 8+ Gen 1 Mobile Platform",
            "SM8450" to "Snapdragon® 8 Gen 1 Mobile Platform",
            "SM7675" to "Snapdragon® 7+ Gen 3 Mobile Platform",
            "SM8635" to "Snapdragon® 8s Gen 3 Mobile Platform",
            "SM8735" to "Snapdragon® 8s Gen 4 Mobile Platform",
            "SM6225" to "Qualcomm® Snapdragon™ 680 Octa-core",
            "SM6225-AD" to "Qualcomm® Snapdragon™ 685 Octa-core",
            "MT6993" to "MediaTek Dimensity 9500",
            "MT6991" to "MediaTek Dimensity 9400",
            "MT6989" to "MediaTek Dimensity 9300",
            "MT6985" to "Dimensity 9200 Octa-core"
        )
        map[model.uppercase()]?.let { return it }
        return if (Build.VERSION.SDK_INT >= 31 && Build.SOC_MANUFACTURER.isNotBlank() && Build.SOC_MODEL.isNotBlank())
            "${Build.SOC_MANUFACTURER} ${Build.SOC_MODEL}" else Build.HARDWARE
    }

    fun read(c: Context): Specs {
        // Device name
        val name = try { Settings.Global.getString(c.contentResolver, "device_name") } catch (e: Throwable) { null }
            ?: Build.MODEL

        // RAM
        val am = c.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val mi = ActivityManager.MemoryInfo().also { am.getMemoryInfo(it) }
        val gib = mi.totalMem / 1073741824.0
        val std = listOf(2, 3, 4, 6, 8, 10, 12, 16, 18, 24, 32, 48, 64)
        val ramGb = std.firstOrNull { it >= gib } ?: gib.roundToInt()
        val swapKb = try {
            File("/proc/meminfo").readLines().first { it.startsWith("SwapTotal") }.filter { it.isDigit() }.toLong()
        } catch (e: Throwable) { 0L }
        val swapGb = swapKb / 1048576.0
        val ext = if (swapGb >= 1.0) f("%.2f GB", swapGb.roundToInt().toDouble()) else null

        // Storage
        val st = StatFs(Environment.getDataDirectory().path)
        val totB = st.totalBytes.toDouble()
        val availGb = st.availableBytes / 1e9
        val mk = listOf(16, 32, 64, 128, 256, 512, 1024, 2048).firstOrNull { it * 1e9 >= totB } ?: 2048
        val usedGb = (mk - availGb).coerceAtLeast(1.0)
        val usedStr = if (usedGb < 100) f("%.1f GB", usedGb) else "${usedGb.roundToInt()} GB"
        val totStr = if (mk >= 1024) f("%.2f TB", mk / 1024.0) else "$mk GB"

        // Battery
        var cap = try {
            val cls = Class.forName("com.android.internal.os.PowerProfile")
            val o = cls.getConstructor(Context::class.java).newInstance(c)
            (cls.getMethod("getBatteryCapacity").invoke(o) as Double).roundToInt()
        } catch (e: Throwable) { 0 }
        if (cap <= 0) {
            val bm = c.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
            val cc = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER)
            val pc = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
            if (cc > 0 && pc > 0) cap = ((cc / 1000.0) / pc).roundToInt() * 100
        }
        val bat = if (cap > 0) "$cap mAh (TYP)" else "N/A"

        // Cameras
        val front = mutableListOf<Int>(); val rear = mutableListOf<Int>()
        try {
            val cm = c.getSystemService(Context.CAMERA_SERVICE) as CameraManager
            for (id in cm.cameraIdList) {
                val ch = cm.getCameraCharacteristics(id)
                val sz = ch.get(CameraCharacteristics.SENSOR_INFO_PIXEL_ARRAY_SIZE) ?: continue
                val mp = (sz.width * sz.height / 1e6).roundToInt()
                if (ch.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT) front.add(mp) else rear.add(mp)
            }
        } catch (e: Throwable) { }
        val frontS = if (front.isEmpty()) "N/A" else front.sortedDescending().joinToString("+") { "${it}MP" }
        val rearS = if (rear.isEmpty()) "N/A" else rear.sortedDescending().joinToString("+") { "${it}MP" }

        // Display
        val dm = c.resources.displayMetrics
        val inch = sqrt(Math.pow(dm.widthPixels / dm.xdpi.toDouble(), 2.0) + Math.pow(dm.heightPixels / dm.ydpi.toDouble(), 2.0))

        return Specs(
            deviceName = name, model = Build.MODEL, soc = prettySoc(),
            ram = f("%.1f GB", ramGb.toDouble()), ramExt = ext,
            used = usedStr, total = totStr, usedFrac = (usedGb / mk).toFloat(),
            battery = bat, front = frontS, rear = rearS,
            display = f("%.1f in", inch),
            android = "Android ${Build.VERSION.RELEASE}",
            patch = Build.VERSION.SECURITY_PATCH ?: "-",
            build = Build.DISPLAY,
            marketName = listOf("ro.vendor.oplus.market.name", "ro.oppo.market.name", "ro.product.marketname", "ro.product.vendor.marketname")
                .map { prop(it) }.firstOrNull { it.isNotBlank() } ?: name
        )
    }
}
