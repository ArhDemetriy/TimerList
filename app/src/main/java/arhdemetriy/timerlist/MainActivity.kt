package arhdemetriy.timerlist

import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import arhdemetriy.timerlist.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

   private val metaTimers: MetaArrayListLong by lazy { ViewModelProviders.of(this).get(MetaArrayListLong::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        metaTimers.observeCount(this, Observer {
            render()
            if (it <= 0) metaTimers.timerRunned = false
        })

        metaTimers.observeFirstTimer(this, Observer {
            //render()
        })

        metaTimers.observePosMainTimer(this, Observer {
            //render()
        })

        metaTimers.observeMainTimer(this, Observer {
           // render()
        })

        metaTimers.observeTimerRunned(this, Observer {
            if (it) timer.start() else timer.cancel()
            timerSwitch.isChecked = it
        })

        metaTimers.observeActiveTimer(this, Observer {
            workedTimer.text = it.toString()
        })



    }

    private fun render(){

        var s: String = ""
        /*if (it > 0)*/ metaTimers.getArrayforEach { n -> s += "$n\n" }
        timerView.text = s

    }

    private var m: Long = 0

    private val sec : Long = 1000

    private val timer = object : CountDownTimer(10000000, sec){
        override fun onFinish() {
        }

        override fun onTick(millisUntilFinished: Long) {
            timerTick()
        }

    }

    fun adder (viev: View){
        var s : String = writterTimer.text.toString()

        if (s != "0") {
            metaTimers.addTimerAt(s.toLong())
        }
        metaTimers.posMainTimer=metaTimers.count-1


    }

    fun delleter (viev: View){
        metaTimers.delTimerAt()
    }

    private fun timerTick (){

        if (metaTimers.count <= 0) {
            metaTimers.timerRunned=false
            timer.cancel()
            return}

        else if (metaTimers.activeTimer <=0 ) {
            val tg = ToneGenerator(AudioManager.STREAM_ALARM,100)
            tg.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT,500)
        }
        metaTimers.timerTick()
    }

    fun ceckedTimerSwitch(viev: View){
        if (timerSwitch.isChecked) {
            //metaTimers.posMainTimer = 0
           // metaTimers.posActiveTimer = 0
        }
        if (metaTimers.timerRunned xor timerSwitch.isChecked) metaTimers.timerRunned = timerSwitch.isChecked
        //if (metaTimers.timerRunned) timer.start()
    }
}


/*      val s: String = "true"
        val myToast = Toast.makeText( this, s, Toast.LENGTH_SHORT )
        myToast.show()
        */
