package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    var lastNumeric: Boolean = false
    var lastDot: Boolean = false
    var lastOperator: Boolean = false
    var lastEqual: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun onDigit(view: View) {

        val tvInput = findViewById<TextView>(R.id.tvInput)
//        if (lastEqual) {
//            tvInput.text = ""
//            lastEqual = false
//        }
        tvInput.append((view as Button).text)
        lastNumeric = true
        lastOperator = false

    }
    fun onClear(view: View) {
        val tvInput = findViewById<TextView>(R.id.tvInput)
        tvInput.text = ""
        lastNumeric = false
        lastDot = false
    }
    fun onDecimalPoint(view: View) {
        val tvInput = findViewById<TextView>(R.id.tvInput)
        if (lastNumeric && !lastDot) {
            tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }
    private fun removeZeroAfterDot (result: String) : String {
        var value = result
        if (result.contains(".0"))
            value = result.substring(0, result.length - 2)

        return value
    }
    fun onOperator(view: View) {
        val tvInput = findViewById<TextView>(R.id.tvInput)
        if (lastNumeric  && !isOperatorAdded(tvInput.text.toString())) {
            tvInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }
    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("*") || value.contains("+") || value.contains("+")
        }
    }
    fun onEqual(view: View) {
        lastEqual = true
        val tvInput = findViewById<TextView>(R.id.tvInput)
        if (lastNumeric) {
            var prefix = ""
            var tvValue = tvInput.text.toString()
            try {
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }

                if (tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")
                    var x = splitValue[0]
                    var y = splitValue[1]
                    if (!prefix.isEmpty()) {
                        x = prefix + x
                    }
                    tvInput.text = removeZeroAfterDot((x.toDouble() - y.toDouble()).toString())
                }
                else if (tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")
                    var x = splitValue[0]
                    var y = splitValue[1]
                    tvInput.text = removeZeroAfterDot((x.toDouble() + y.toDouble()).toString())
                }
                else if (tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")
                    var x = splitValue[0]
                    var y = splitValue[1]
                    tvInput.text = removeZeroAfterDot((x.toDouble() * y.toDouble()).toString())
                }
                else if (tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")
                    var x = splitValue[0]
                    var y = splitValue[1]
                    tvInput.text = removeZeroAfterDot((x.toDouble() / y.toDouble()).toString())
                }
            } catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }

    }
}