package arhdemetriy.test.testrecyclerview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {

    private var timerList : MutableLiveData<ArrayList<BaseTimer>> = MutableLiveData()

    //инициализируем список и заполняем его данными пользователей
    init {
        timerList.value = ArrayList()
    }

    fun getListUsers() = timerList

    //для обновления списка передаем второй список пользователей
    fun updateListUsers() {
    }
}