package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var tvExpression: TextView
    private lateinit var tvResult: TextView

    private var currentInput = ""
    private var firstOperand: Double? = null
    private var pendingOperator: String? = null
    private var isNewInput = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvExpression = findViewById(R.id.tvExpression)
        tvResult = findViewById(R.id.tvResult)

        setupListeners()
    }

    private fun setupListeners() {
        val numberButtons = listOf(
            R.id.btn0 to "0", R.id.btn1 to "1", R.id.btn2 to "2", R.id.btn3 to "3",
            R.id.btn4 to "4", R.id.btn5 to "5", R.id.btn6 to "6", R.id.btn7 to "7",
            R.id.btn8 to "8", R.id.btn9 to "9"
        )

        for ((id, digit) in numberButtons) {
            findViewById<Button>(id).setOnClickListener { appendDigit(digit) }
        }

        findViewById<Button>(R.id.btnDot).setOnClickListener { appendDot() }

        val operatorButtons = listOf(
            R.id.btnAdd to "+",
            R.id.btnSubtract to "-",
            R.id.btnMultiply to "×",
            R.id.btnDivide to "÷"
        )

        for ((id, op) in operatorButtons) {
            findViewById<Button>(id).setOnClickListener { setOperator(op) }
        }

        findViewById<Button>(R.id.btnEquals).setOnClickListener { calculateResult() }
        findViewById<Button>(R.id.btnAC).setOnClickListener { clearAll() }
        findViewById<Button>(R.id.btnC).setOnClickListener { clearEntry() }
        findViewById<ImageButton>(R.id.btnBackspace).setOnClickListener { backspace() }
    }

    private fun appendDigit(digit: String) {
        if (isNewInput) {
            currentInput = if (digit == "0") "0" else digit
            isNewInput = false
        } else {
            if (currentInput == "0" && digit != "0") {
                currentInput = digit
            } else if (currentInput != "0" || digit != "0") {
                currentInput += digit
            }
        }
        tvResult.text = currentInput
    }

    private fun appendDot() {
        if (isNewInput) {
            currentInput = "0."
            isNewInput = false
        } else if (!currentInput.contains(".")) {
            currentInput += "."
        }
        tvResult.text = currentInput
    }

    private fun setOperator(op: String) {
        val value = currentInput.toDoubleOrNull()
        if (value != null) {
            if (firstOperand != null && pendingOperator != null && !isNewInput) {
                val intermediate = compute(firstOperand!!, value, pendingOperator!!)
                firstOperand = intermediate
                tvResult.text = formatValue(intermediate)
            } else {
                firstOperand = value
            }
        }
        pendingOperator = op
        isNewInput = true
        tvExpression.text = "${formatValue(firstOperand)} $op"
    }

    private fun calculateResult() {
        val secondOperand = currentInput.toDoubleOrNull()
        if (firstOperand != null && pendingOperator != null && secondOperand != null) {
            val result = compute(firstOperand!!, secondOperand, pendingOperator!!)
            tvExpression.text = "${formatValue(firstOperand)} $pendingOperator ${formatValue(secondOperand)} ="
            tvResult.text = formatValue(result)
            currentInput = result.toString()
            firstOperand = null
            pendingOperator = null
            isNewInput = true
        }
    }

    private fun compute(num1: Double, num2: Double, op: String): Double {
        return when (op) {
            "+" -> num1 + num2
            "-" -> num1 - num2
            "×" -> num1 * num2
            "÷" -> if (num2 != 0.0) num1 / num2 else Double.NaN
            else -> num2
        }
    }

    private fun clearAll() {
        currentInput = "0"
        firstOperand = null
        pendingOperator = null
        isNewInput = true
        tvExpression.text = ""
        tvResult.text = "0"
    }

    private fun clearEntry() {
        currentInput = "0"
        isNewInput = true
        tvResult.text = "0"
    }

    private fun backspace() {
        if (!isNewInput && currentInput.isNotEmpty()) {
            currentInput = currentInput.dropLast(1)
            if (currentInput.isEmpty() || currentInput == "-") {
                currentInput = "0"
                isNewInput = true
            }
            tvResult.text = currentInput
        }
    }

    private fun formatValue(value: Double?): String {
        if (value == null) return ""
        if (value.isNaN()) return "Error"
        return if (value % 1.0 == 0.0) {
            value.toLong().toString()
        } else {
            DecimalFormat("#.######").format(value)
        }
    }
}