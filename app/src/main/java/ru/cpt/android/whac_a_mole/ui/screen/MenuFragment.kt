package ru.cpt.android.whac_a_mole.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.cpt.android.whac_a_mole.R
import ru.cpt.android.whac_a_mole.preferences.ApplicationPreferences

class MenuFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_menu, container, false)

        //Установка слушателя на кнопку "Play"
        root.findViewById<Button>(R.id.button_menu_play).setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View?) {
                val action = MenuFragmentDirections.actionFragmentMenuToFragmentGame()
                findNavController().navigate(action)
            }
        })

        //Установка рекорда пользователя из сохраненных настроек (работают намного быстрее чем SQLite)
        root.findViewById<TextView>(R.id.text_view_menu_score).text = ApplicationPreferences.getScore(requireContext()).toString()

        //Установка анимации для правил
        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_rules)
        val rules = root.findViewById<TextView>(R.id.text_view_rules)
        rules.startAnimation(animation)

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }
}