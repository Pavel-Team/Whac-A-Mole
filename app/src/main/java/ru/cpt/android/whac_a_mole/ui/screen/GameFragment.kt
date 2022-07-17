package ru.cpt.android.whac_a_mole.ui.screen

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import ru.cpt.android.whac_a_mole.R
import ru.cpt.android.whac_a_mole.preferences.ApplicationPreferences
import ru.cpt.android.whac_a_mole.ui.view.GameView
import ru.cpt.android.whac_a_mole.util.Constants
import ru.cpt.android.whac_a_mole.viewmodel.GameViewModel
import kotlin.math.roundToInt

private const val TAG = "GameFragment"

class GameFragment: Fragment() {

    private var gameView: GameView? = null

    private val viewModel: GameViewModel by activityViewModels()
    private lateinit var timerGame: CountDownTimer //Таймер с временем раунда (также можно было сделать отдельный таймер для кротов, но ТЗ этого не требует)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_game, container, false)
        gameView = root.findViewById(R.id.game_view)
        viewModel.getScore().value?.let { gameView?.updateScore(it) } //В случае с поворотом экрана: обновляем счетчик во View с числом попадания по кроту - на значение из viewModel
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Подписываемся на обновление значений
        viewModel.getStateGame().observe(viewLifecycleOwner, Observer {
            gameView?.setState(it)
        })
        viewModel.getScore().observe(viewLifecycleOwner, Observer {
            view.findViewById<TextView>(R.id.score).text = it.toString()
        })
        viewModel.getTime().observe(viewLifecycleOwner, Observer {
            view.findViewById<TextView>(R.id.time).text = (it / 1000).toString()
        })
        gameView?.getScore()?.observe(viewLifecycleOwner, Observer {
            viewModel.updateScore(it)
        })


        //Запускаем таймер игры
        timerGame =
            object : CountDownTimer(viewModel.getTime().value!!, Constants.TIME_UPDATE_TIMER) {
                override fun onTick(leftTime: Long) {
                    viewModel.updateTime(leftTime) //Обновляем viewModel
                    viewModel.initNewStateGame()   //Сбрасываем всех живых кротов

                    //Если вероятность появления крота успешна - добавляем нового крота в рандомную клетку
                    if (Math.random() > Constants.PROBABILITY_MOLE) {
                        val x = (Math.random() * (Constants.COUNT_CELLS-1)).roundToInt()
                        val y = (Math.random() * (Constants.COUNT_CELLS-1)).roundToInt()
                        viewModel.updateStateGame(x, y, true)
                    }
                }

                override fun onFinish() {
                    viewModel.initNewGame() //Сброс состояния игры
                    findNavController().navigate(R.id.action_fragment_game_to_fragment_result_game)
                }
            }
        timerGame.start()
    }


    override fun onDestroyView() {
        super.onDestroyView()

        //Если фрагмент уничтожается ПРИ ЛЮБЫХ ОБСТОЯТЕЛЬСТВАХ, все равно сохраняем результат
        val scoreUser = viewModel.getScore().value
        if (scoreUser!=null && ApplicationPreferences.getScore(requireContext()) < scoreUser)
            ApplicationPreferences.setScore(requireContext(), scoreUser)

        //Избавляемся от утечек + останавливаем таймер
        gameView = null
        timerGame.cancel()
    }
}