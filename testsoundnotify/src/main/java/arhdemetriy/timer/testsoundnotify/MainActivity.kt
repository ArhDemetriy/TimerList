package arhdemetriy.timer.testsoundnotify

import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
/*    private val set = setOf<String>(
        "TYPE_NOTIFICATION",
        "TYPE_ALARM",
        "TYPE_RINGTONE",
        "TYPE_ALL"
    ) */
    private val  noty: ArrayList<Int> = ArrayList<Int>()
    private var n: Int = 0
        get() {
            if (field >= 2) field = 0
            return field++
        }
        set(value) {
            field = if (value in 0..3) value else 0 }

    init {
        noty.add(RingtoneManager.TYPE_NOTIFICATION)
        //noty.add(RingtoneManager.TYPE_ALARM)
        noty.add(RingtoneManager.TYPE_RINGTONE)
        //noty.add(RingtoneManager.TYPE_ALL)
    }


    fun pip(view: View ): Unit {
        try {
            var notify: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            var r = RingtoneManager.getRingtone(applicationContext, notify)
            r.play()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
