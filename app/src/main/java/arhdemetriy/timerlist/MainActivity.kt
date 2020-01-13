package arhdemetriy.timerlist

import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import arhdemetriy.timerlist.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.timer_item.view.*

class MainActivity : AppCompatActivity() {

    private val metaTimers: MetaArrayListLong by lazy { ViewModelProviders.of(this).get(MetaArrayListLong::class.java) }
    private val adapt = TimerAdapter()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        timerList.layoutManager = LinearLayoutManager(this)
        timerList.adapter = adapt

        metaTimers.timerRunned.observeValue(this, Observer {
            if (it) timer.start() else timer.cancel()
            timerSwitch.isChecked = it
        })

        metaTimers.activeTimer.observeValue(this, Observer {
            workedTimer.text = it.toString()
        })

        metaTimers.count.observeValue(this, Observer {
            render()
            if (it <= 0) metaTimers.timerRunned.value = false
        })

    }

    private fun render(){
        val a: ArrayList<BaseTimer> = ArrayList()
        metaTimers.getArrayforEach { n -> a.add(BaseTimer(n))}
        adapt.refreshTimers(a)
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
    }

    fun delleter (viev: View){
        metaTimers.delTimerAt()
    }

    private fun timerTick (){

        if (metaTimers.count.value <= 0) {
            metaTimers.timerRunned.value=false
            return}

        else if (metaTimers.activeTimer.value <=0 ) {
            val tg = ToneGenerator(AudioManager.STREAM_ALARM,100)
            tg.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT,500)
        }
        metaTimers.timerTick()
    }

    fun ceckedTimerSwitch(viev: View){
        val t0 = metaTimers.timerRunned.value
        val t1 = timerSwitch.isChecked
        Log.d("ceckedTimerSwitch","begin $t0, $t1")
        if (t0 xor t1) metaTimers.timerRunned.value = t1
    }

    fun clickOnMascineTimer(view: View): Unit {
        Log.v("clickOnMascineTimer","step 0 ${view.toString()}")
        val pos = timerList.getChildAdapterPosition(view)
        Log.v("clickOnMascineTimer","step 1 $pos")

        with(view) {
            adapt.setTimerByIndex(pos,mashinTimer.text.toString().toLong())
        }
    }

}


/*      val s: String = "true"
        val myToast = Toast.makeText( this, s, Toast.LENGTH_SHORT )
        myToast.show()
        */
