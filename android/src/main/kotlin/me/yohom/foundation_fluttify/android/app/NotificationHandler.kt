package me.yohom.foundation_fluttify.android.app

import android.app.Activity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import io.flutter.plugin.common.MethodChannel

fun NotificationHandler(method: String, args: Map<String, Any>, methodResult: MethodChannel.Result, activity: Activity?) {
    when (method) {
        "android.app.Notification::create" -> {
            if (activity == null) {
                methodResult.error("activity cannot be null", "activity cannot be null", "activity cannot be null")
            } else {
                val contentTitle = args["contentTitle"] as String
                val contentText = args["contentText"] as String
                val `when` = args["when"] as Long
                val channelId = args["channelId"] as String
                val channelName = args["channelName"] as String
                val enableLights = args["enableLights"] as Boolean
                val showBadge = args["showBadge"] as Boolean

                val notificationManager: NotificationManager = activity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                // Android O上对Notification进行了修改，如果设置的targetSDKVersion>=26建议使用此种方式创建通知栏
                val builder: Notification.Builder = if (Build.VERSION.SDK_INT >= 26) {
                    val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
                            .apply {
                                enableLights(enableLights) //是否在桌面icon右上角展示小圆点
                                lightColor = Color.BLUE //小圆点颜色
                                setShowBadge(showBadge) //是否在久按桌面图标时显示此渠道的通知
                            }

                    notificationManager.createNotificationChannel(notificationChannel)

                    Notification.Builder(activity.applicationContext, channelId)
                } else {
                    Notification.Builder(activity.applicationContext)
                }

                builder
                        .setContentTitle(contentTitle)
                        .setContentText(contentText)
                        .setWhen(`when`)

                methodResult.success(builder.build().hashCode())
            }
        }
        else -> methodResult.notImplemented()
    }
}