package arhdemetriy.timerlist.swipmovetest

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycleritem.view.*

class MyAdapter: RecyclerView.Adapter<MyAdapter.UserHolder>(),ItemTouchHelperAdapter  {

    private val items: ArrayList<String> by lazy { ArrayList<String>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        return UserHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycleritem, parent, false)
        )
    }

    override fun onBindViewHolder(viewHolder: UserHolder, position: Int) {
        viewHolder.bind(items[position])
    }

    override fun getItemCount() = items.size

    class UserHolder(itemView: View) : RecyclerView.ViewHolder(itemView),ItemTouchHelperViewHolder {
        fun bind(name: String) = with(itemView) {
            nameItem.text = name
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(0)
        }
    }

    override fun onItemDismiss(position: Int) {
        if (items.size <= position) return
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        //if ((fromPosition == toPosition) || ((items.size <= fromPosition) || (items.size <= toPosition))) return
        val temp = items.removeAt(fromPosition)
        items.add(
            if (toPosition > fromPosition)
                toPosition -1
            else toPosition
            ,temp)
        notifyItemMoved(fromPosition, toPosition)
    }

    fun refreshNames(items: ArrayList<String>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun clearNames(): Unit {
        if (items.size <= 2) return
        val s = items.get(1)
        items.clear()
        items.add(s)
        notifyDataSetChanged()
    }
}
