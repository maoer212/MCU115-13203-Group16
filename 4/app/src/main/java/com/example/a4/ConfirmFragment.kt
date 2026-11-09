package com.example.a4

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class ConfirmFragment : Fragment(R.layout.fragment_confirm) {
    private val vm: OrderViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvSummary = view.findViewById<TextView>(R.id.tvOrderSummary)
        val btnConfirm = view.findViewById<Button>(R.id.btnConfirm)

        vm.order.observe(viewLifecycleOwner) { order ->
            val main = order.mainMeal ?: "-"
            val sides = if (order.sideDishes.isNotEmpty()) order.sideDishes.joinToString(", ") else "-"
            val drink = order.drink ?: "-"
            tvSummary.text = "Main: $main\nSides: $sides\nDrink: $drink"
        }

        btnConfirm.setOnClickListener {
            val order = vm.order.value
            if (order?.isComplete() != true) {
                Toast.makeText(
                    requireContext(),
                    "Please select a main meal, at least one side dish, and a drink.",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            AlertDialog.Builder(requireContext())
                .setTitle("Submit Order")
                .setMessage(
                    "Main: ${order.mainMeal}\n" +
                    "Sides: ${order.sideDishes.joinToString(", ")}\n" +
                    "Drink: ${order.drink}\n\nSubmit this order?"
                )
                .setPositiveButton("Submit") { d, _ ->
                    d.dismiss()
                    Toast.makeText(requireContext(), "Order submitted! 🥳", Toast.LENGTH_SHORT).show()
                    vm.resetOrder()
                    requireActivity().finish()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }
}
