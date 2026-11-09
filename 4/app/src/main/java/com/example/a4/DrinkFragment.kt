package com.example.a4

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class DrinkFragment : Fragment(R.layout.fragment_drink) {
    private val vm: OrderViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroupDrink)
        val btnDone = view.findViewById<Button>(R.id.btnDoneDrink)

        vm.order.observe(viewLifecycleOwner) { order ->
            when (order.drink) {
                "Cola" -> radioGroup.check(R.id.rbCola)
                "Iced Tea" -> radioGroup.check(R.id.rbIcedTea)
                "Water" -> radioGroup.check(R.id.rbWater)
            }
        }

        btnDone.setOnClickListener {
            val checkedId = radioGroup.checkedRadioButtonId
            if (checkedId != -1) {
                val selectedRb = view.findViewById<RadioButton>(checkedId)
                vm.setDrink(selectedRb.text.toString())
                Toast.makeText(requireContext(), "Drink selected: ${selectedRb.text}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Please select a drink", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
