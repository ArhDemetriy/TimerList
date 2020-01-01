package arhdemetriy.test.testviewmodel

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import arhdemetriy.timerlist.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val vm: UserViewModel by lazy { ViewModelProviders.of(this).get(UserViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        vm.getNumber().observe(this, Observer {
            viewNumber.setText(it.toString())
        })

    }

    fun setNum(view: View){
        val s = readNumber.text.toString()
        if (s.length<=0) return

        vm.setNumber(s.toLong())

    }

    fun getNum(view: View){
        viewNumber.setText(vm.getNumber().toString())
    }
}
