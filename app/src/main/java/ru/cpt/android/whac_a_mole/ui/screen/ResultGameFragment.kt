package ru.cpt.android.whac_a_mole.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.cpt.android.whac_a_mole.R
import ru.cpt.android.whac_a_mole.preferences.ApplicationPreferences

class ResultGameFragment: Fragment() {

    val args: ResultGameFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_result_game, container, false)

        //Выводим статистику
        root.findViewById<TextView>(R.id.text_view_hit).text = args.countClick.toString()
        root.findViewById<TextView>(R.id.text_view_score).text = ApplicationPreferences.getScore(requireContext()).toString()

        //Слушатели на кнопки
        root.findViewById<Button>(R.id.button_replay).setOnClickListener(object: View.OnClickListener {
            override fun onClick(p0: View?) {
                val action = ResultGameFragmentDirections.actionFragmentResultGameToFragmentGame()
                findNavController().navigate(action)
            }
        })
        root.findViewById<Button>(R.id.button_menu).setOnClickListener(object: View.OnClickListener {
            override fun onClick(p0: View?) {
                val action = ResultGameFragmentDirections.actionFragmentResultGameToFragmentMenu()
                findNavController().navigate(action)
            }
        })

        return root
    }
}