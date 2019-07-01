package uk.co.cjapps.azurepusher

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.pusher.pushnotifications.PushNotifications
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PushNotifications.start(applicationContext, "YOUR_INSTANCE_ID")
        PushNotifications.addDeviceInterest("hello")
    }
}
