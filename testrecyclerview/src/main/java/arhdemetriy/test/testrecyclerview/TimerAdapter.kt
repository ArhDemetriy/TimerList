package arhdemetriy.test.testrecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.timer_item.view.*

class TimerAdapter : RecyclerView.Adapter<TimerAdapter.UserHolder>() {

    private var timers: ArrayList<BaseTimer> = ArrayList()

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
    fun refreshUsers(timers: ArrayList<BaseTimer>) {
        this.timers = timers
        notifyDataSetChanged()
    }


    //внутренний класс ViewHolder описывает элементы представления списка и привязку их к RecyclerView
    class UserHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(timer: BaseTimer) = with(itemView) {
            mashinTimer.setText(timer.longTimer.toString())
            humanTimer.setText(timer.strTimer)
        }
    }
}