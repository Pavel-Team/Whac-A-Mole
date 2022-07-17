package ru.cpt.android.whac_a_mole.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.cpt.android.whac_a_mole.util.Constants

private const val TAG = "GameViewModel"

class GameViewModel: ViewModel() {

    private val liveDataStateGame: MutableLiveData<Array<Array<Boolean>>> = MutableLiveData() //Состояние игрового поля (массив 3х3 - есть ли крот в данной лунке)
    private val liveDataScore: MutableLiveData<Int> = MutableLiveData() //Число набранных очков
    private val liveDataTime: MutableLiveData<Long> = MutableLiveData() //Оставшееся время игры


    init {
        Log.d(TAG, "init")
        initNewGame()
    }


    //Установка начального значения игровогоп поля
    fun initNewStateGame() {
        liveDataStateGame.value = arrayOf(
            arrayOf(false, false, false),
            arrayOf(false, false, false),
            arrayOf(false, false, false)
        )
    }


    //Инициализация состояний начальными значениями
    fun initNewGame() {
        //Инициализируем начальные значения (ВРЕМЕННО: ЗАПОЛНЯТЬ В МАССИВЕ, УЧИТЫВАЯ Constants.COUNT_CELLS)
        initNewStateGame()
        liveDataScore.value = 0
        liveDataTime.value = Constants.ROUND_TIME
    }


    //Получение текущего состояния игры
    fun getStateGame(): LiveData<Array<Array<Boolean>>> {
        return liveDataStateGame
    }

    //Изменение всего состояния игры
    fun updateStateGame(state: Array<Array<Boolean>>) {
        liveDataStateGame.postValue(state)
    }

    //Изменение только одной единицы состояния игры
    //На вход принимает 3 параметра:
    //x: Int - номер обновляемой клетки по координате x
    //y: Int - номер обновляемой клетки по координате y
    //isMole: Boolean - наличие крота в данной клетке
    fun updateStateGame(x: Int, y: Int, isMole: Boolean) {
        val state = liveDataStateGame.value
        state?.get(x)?.set(y, isMole)
        if (state!=null)
            liveDataStateGame.postValue(state!!)
    }

    //Получние числа набранных очков
    fun getScore(): LiveData<Int> {
        return liveDataScore
    }

    //Установка нового значения для набранных очков
    fun updateScore(value: Int) {
        liveDataScore.postValue(value)
    }

    //Получние оствашегося время игры
    fun getTime(): LiveData<Long> {
        return liveDataTime
    }

    //Установка нового значения для оставшегося времени игры
    fun updateTime(value: Long) {
        liveDataTime.postValue(value)
    }

}