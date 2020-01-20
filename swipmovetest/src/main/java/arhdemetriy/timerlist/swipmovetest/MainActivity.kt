package arhdemetriy.timerlist.swipmovetest

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val adapt: MyAdapter by lazy {MyAdapter()}
    private val myItemTouchHelper: ItemTouchHelper by lazy { ItemTouchHelper(SimpleItemTouchHelperCallback(adapt)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameList.layoutManager = LinearLayoutManager(this)
        nameList.adapter = adapt
        myItemTouchHelper.attachToRecyclerView(nameList)

        setFakeData()

    }

    fun setFakeData(){
        val a: ArrayList<String> = ArrayList()
        a.add("one")
        a.add("two")
        a.add("free")
        a.add("fore")

        adapt.refreshNames(a)
    }

    fun clearData(): Unit {
        adapt.clearNames()
    }

    fun clearData(view: View): Unit {
        clearData()
    }

    fun setFakeData(view: View): Unit {
        setFakeData()
    }



}
