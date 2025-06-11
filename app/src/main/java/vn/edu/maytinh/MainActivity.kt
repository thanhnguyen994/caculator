package vn.edu.maytinh

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private var currentInput = ""
    private var lastOperator = ""
    private var result = 0.0
    private var isNewOp = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvResult = findViewById(R.id.tvResult)

        val buttons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
            R.id.btnDot, R.id.btnPlus, R.id.btnMinus, R.id.btnMultiply, R.id.btnDivide,
            R.id.btnEqual, R.id.btnC, R.id.btnCE, R.id.btnBS, R.id.btnPlusMinus
        )

        buttons.forEach { id ->
            findViewById<Button>(id).setOnClickListener { onButtonClick(it as Button) }
        }
    }

    private fun onButtonClick(button: Button) {
        when (val text = button.text.toString()) {
            in "0".."9", "." -> numberClick(text)
            "+", "-", "x", "/" -> operatorClick(text)
            "=" -> calculateResult()
            "C" -> clearAll()
            "CE" -> clearEntry()
            "BS" -> backspace()
            "+/-" -> toggleSign()
        }
    }

    private fun numberClick(value: String) {
        if (isNewOp) {
            tvResult.text = ""
            isNewOp = false
        }
        if (value == "." && tvResult.text.contains(".")) return
        tvResult.append(value)
    }

    private fun operatorClick(op: String) {
        result = tvResult.text.toString().toDoubleOrNull() ?: 0.0
        lastOperator = op
        isNewOp = true
    }

    private fun calculateResult() {
        val secondOperand = tvResult.text.toString().toDoubleOrNull() ?: 0.0
        when (lastOperator) {
            "+" -> result += secondOperand
            "-" -> result -= secondOperand
            "x" -> result *= secondOperand
            "/" -> result /= secondOperand
        }
        tvResult.text = result.toString()
        isNewOp = true
    }

    private fun clearAll() {
        tvResult.text = "0"
        result = 0.0
        lastOperator = ""
        isNewOp = true
    }

    private fun clearEntry() {
        tvResult.text = "0"
        isNewOp = true
    }

    private fun backspace() {
        val current = tvResult.text.toString()
        if (current.length > 1) {
            tvResult.text = current.dropLast(1)
        } else {
            tvResult.text = "0"
            isNewOp = true
        }
    }

    private fun toggleSign() {
        val current = tvResult.text.toString()
        if (current.isNotEmpty() && current != "0") {
            if (current.startsWith("-")) {
                tvResult.text = current.substring(1)
            } else {
                tvResult.text = "-$current"
            }
        }
    }
}