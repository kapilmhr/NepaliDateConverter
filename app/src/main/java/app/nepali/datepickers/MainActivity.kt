package app.nepali.datepickers

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import app.nepali.nepalidatepicker.DateConverter
import app.nepali.nepalidatepicker.DatePicker.DatePickerDialog
import app.nepali.nepalidatepicker.Model
import app.nepali.nepalidatepicker.Utils.getDayOfWeek
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private var dateConverter:DateConverter
    init {
        dateConverter = DateConverter()

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       btnCalendar.setOnClickListener { v ->
            startActivity(
                Intent(
                    this@MainActivity,
                    CalendarActivity::class.java
                )
            )
        }

        val now = Calendar.getInstance()

        materialDatePickerButton.setOnClickListener {
            val dpd =
                DatePickerDialog.newInstance(this)
            dpd.show(supportFragmentManager, "DatePicker")

        }

        adToBsConvertButton.setOnClickListener {
            val yy: Int = convertEditTextYear.getText().toString().toInt()
            val mm: Int = convertEditTextMonth.getText().toString().toInt()
            val dd: Int = convertEditTextDay.getText().toString().toInt()
            try {
                val nepDate: Model = dateConverter.getNepaliDate(yy, mm, dd)
                val date = "" + nepDate.getYear().toString() + " " + resources.getString(
                    DateConverter.getNepaliMonthString(nepDate.getMonth())
                ) + " " +
                        nepDate.getDay().toString() + " " + getDayOfWeek(nepDate.getDayOfWeek())
               outputConvertTextView.setText(date)
            } catch (e: IllegalArgumentException) {
                Toast.makeText(this@MainActivity, "Date out of Range", Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val date = "You picked the following date: " + dayOfMonth + " " + resources.getString(
            DateConverter.getNepaliMonthString(monthOfYear)
        ) + " " + year

        println(date)

    }
}