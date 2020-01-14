package arhdemetriy.timerlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.timer_item.view.*

class TimerAdapter : RecyclerView.Adapter<TimerAdapter.UserHolder>() {

    private val timers: ArrayList<BaseTimer> by lazy { ArrayList<BaseTimer>() }

    //создает ViewHolder и инициализирует views для списка
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        return UserHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.timer_item, parent, false)
        )
    }

    //связывает views с содержимым
    override fun onBindViewHolder(viewHolder: UserHolder, position: Int) {
        viewHolder.bind(timers[position])
    }

    override fun getItemCount() = timers.size

    //передаем данные и оповещаем адаптер о необходимости обновления списка
    fun refreshTimers(timers: ArrayList<BaseTimer>) {
        // < не создаём работу сборщику
        val o = this.timers.size
        val n = timers.size
        if (o<=n) {
            for (i in 0 until o){
                this.timers[i] = timers[i]
            }
            for (i in o until n){
                this.timers.add(timers[i])
            }
        }
        else { // </ не создаём работу сборщику >
            this.timers.clear()
            this.timers.addAll(timers)
        }
        notifyDataSetChanged()
    }

    fun setTimerByIndex(index: Int, nevTimer: Long): Long {
        if (index !in 0 until timers.size) return -1
        val oldTimer = timers[index].longTimer
        timers[index].longTimer = nevTimer
        notifyItemChanged(index)
        return oldTimer
    }

    fun addTimerAt(index: Int, nevTimer: Long): Unit {
        val b = if (nevTimer >= 0) BaseTimer(nevTimer) else BaseTimer(0)
        when {
            index < 0 -> {
                timers.add(0, b)
                notifyItemInserted(0)
            }
            index >= timers.size -> {
                timers.add(b)
                notifyItemInserted(timers.size-1)
            }
            else -> {
                timers.add(index, b)
                notifyItemInserted(index)
            }
        }
    }

    fun removeTimerAt(index: Int): Long {
        if (timers.size <= 0) return -1
        var t: BaseTimer
        when {
            index < 0 -> {
                t = timers.removeAt(0)
                notifyItemRemoved(0)
            }
            index >= timers.size -> {
                t = timers.removeAt(timers.size-1)
                notifyItemRemoved(timers.size-1)
            }
            else -> {
                t = timers.removeAt(index)
                notifyItemRemoved(index)
            }
        }
        return t.longTimer
    }

    //внутренний класс ViewHolder описывает элементы представления списка и привязку их к RecyclerView
    class UserHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(timer: BaseTimer) = with(itemView) {
            mashinTimer.setText(timer.longTimer.toString())
            humanTimer.setText(timer.strTimer)
        }
    }
}