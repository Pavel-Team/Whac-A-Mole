package ru.cpt.android.whac_a_mole.ui.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.cpt.android.whac_a_mole.R
import ru.cpt.android.whac_a_mole.util.Constants

private const val TAG = "GameView"

class GameView(context: Context, attrs: AttributeSet): View(context, attrs) {

    //Состояние игрового поля (ВРЕМЕННО: ЗАПОЛНЯТЬ В МАССИВЕ, УЧИТЫВАЯ Constants.COUNT_CELLS)
    private var state: Array<Array<Boolean>> = arrayOf(
        arrayOf(false, false, false),
        arrayOf(false, false, false),
        arrayOf(false, false, false)
    )
    private var score: MutableLiveData<Int> = MutableLiveData() //Число попаданий по кроту

    private var widthCell: Int = 0 //Ширина одной клетки поля
    private val paint: Paint = Paint()
    private val myMatrix: Matrix = Matrix()

    private val bitmapCell: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.cell)
    private val bitmapMole: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.mole)
    private val rectCell = Rect(0, 0, bitmapCell.width, bitmapCell.height)


    //Получение числа попаданий по кроту
    fun getScore(): LiveData<Int> {
        return score
    }

    //Обновление числа попаданий по кроту
    fun updateScore(value: Int) {
        score.postValue(value)
    }


    //Переотрисовка игрового поля
    //На вход принимает 3 параметра:
    //x: Int - номер обновляемой клетки по координате x
    //y: Int - номер обновляемой клетки по координате y
    //isMole: Boolean - наличие крота в данной клетке
    fun updateView(x: Int, y: Int, isMole: Boolean) {
        state[x][y] = isMole
        invalidate()
    }


    //Обновление значения игрового поля
    fun setState(state: Array<Array<Boolean>>) {
        this.state = state
        invalidate()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)

        //Изменяем размеры нашей View, делая её квадратной
        if (width < height) {
            widthCell = width / Constants.COUNT_CELLS
            setMeasuredDimension(widthMeasureSpec, widthMeasureSpec)
        } else {
            widthCell = height / Constants.COUNT_CELLS
            setMeasuredDimension(heightMeasureSpec, heightMeasureSpec)
        }
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            val numberCellX = (event.x / widthCell).toInt()   //Номер клетки по X, на которую нажал пользователь (от 0 до 2)
            val numberCellY = (event.y / widthCell).toInt()   //Номер клетки по Y, на которую нажал пользователь (от 0 до 2)

            //Если попали по кроту - убираем его с карты и добавляем очко
            if (state[numberCellX][numberCellY]) {
                updateView(numberCellX, numberCellY, false)
                score.postValue(score.value!!+1)
            }
        }
        return super.onTouchEvent(event)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawColor(Color.BLUE)

        for (x in state.indices) {
            for (y in state[x].indices) {
                //Отрисовка клетки поля (можно хранить один bitmap, на котором потом только отрисовывать по одной клетке крота/по одной клетке земли
                //  так быстрее скорость, но и больше требуемой оперативной памяти)
                canvas?.drawBitmap(
                    bitmapCell,
                    rectCell,
                    Rect(x*widthCell,y*widthCell,(x+1)*widthCell,(y+1)*widthCell),
                    paint
                )
                if (state[x][y]) {
                    canvas?.drawBitmap(
                        bitmapMole,
                        rectCell,
                        Rect(x*widthCell,y*widthCell,(x+1)*widthCell,(y+1)*widthCell),
                        paint
                    )
                }
            }
        }
    }
}