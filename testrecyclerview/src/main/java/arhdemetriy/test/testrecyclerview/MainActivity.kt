package arhdemetriy.test.testrecyclerview

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.timer_item.view.*

class MainActivity : AppCompatActivity() {
    //инициализируем ViewModel ленивым способом
    private val userViewModel by lazy { ViewModelProviders.of(this).get(UserViewModel::class.java)}
    private val adapt = TimerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //инициализируем адаптер и присваиваем его списку

        timerList.layoutManager = LinearLayoutManager(this)
        timerList.adapter = adapt

        //подписываем адаптер на изменения списка

        userViewModel.getListUsers().observe(this, Observer {
            adapt.refreshUsers(it)
        })

        userViewModel.getListUsers().value = fakeValue(70)



    }

    private fun fakeValue(n: Int): ArrayList<BaseTimer>{
        var i: Long = n.toLong()
        val a: ArrayList<BaseTimer> = ArrayList()
        var t: BaseTimer = BaseTimer(i--)
        repeat(n) {
            a.add(t)
            t = BaseTimer(i--)
        }

        return a
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

