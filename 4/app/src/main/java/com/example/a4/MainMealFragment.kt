package com.example.a4

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class MainMealFragment : Fragment(R.layout.fragment_main_meal) {
    private val vm: OrderViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroupMainMeal)
        val btnDone = view.findViewById<Button>(R.id.btnDoneMainMeal)

        vm.order.observe(viewLifecycleOwner) { order ->
            when (order.mainMeal) {
                "Burger" -> radioGroup.check(R.id.rbBurger)
                "Fried Chicken" -> radioGroup.check(R.id.rbFriedChicken)
                "Chicken Nuggets" -> radioGroup.check(R.id.rbChickenNuggets)
            }
        }

        btnDone.setOnClickListener {
            val checkedId = radioGroup.checkedRadioButtonId
            if (checkedId != -1) {
                val selectedRb = view.findViewById<RadioButton>(checkedId)
                vm.setMainMeal(selectedRb.text.toString())
                Toast.makeText(requireContext(), "Main meal selected: ${selectedRb.text}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Please select a main meal", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
