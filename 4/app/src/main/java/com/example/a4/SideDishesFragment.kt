package com.example.a4

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class SideDishesFragment : Fragment(R.layout.fragment_side_dishes) {
    private val vm: OrderViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cbFries = view.findViewById<CheckBox>(R.id.cbFries)
        val cbSalad = view.findViewById<CheckBox>(R.id.cbSalad)
        val cbCornCups = view.findViewById<CheckBox>(R.id.cbCornCups)
        val btnDone = view.findViewById<Button>(R.id.btnDoneSideDishes)

        vm.order.observe(viewLifecycleOwner) { order ->
            cbFries.isChecked = order.sideDishes.contains("Fries")
            cbSalad.isChecked = order.sideDishes.contains("Salad")
            cbCornCups.isChecked = order.sideDishes.contains("Corn Cups")
        }

        btnDone.setOnClickListener {
            val selected = mutableListOf<String>()
            if (cbFries.isChecked) selected.add("Fries")
            if (cbSalad.isChecked) selected.add("Salad")
            if (cbCornCups.isChecked) selected.add("Corn Cups")

            if (selected.isNotEmpty()) {
                vm.setSideDishes(selected)
                Toast.makeText(requireContext(), "Side dishes selected: ${selected.joinToString(", ")}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Please select at least one side dish", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
