package sg.edu.rp.c346.id21023395.l04_reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextInputEditText name,mobileNo, groupSize;
    CheckBox smoking;
    DatePicker dp;
    TimePicker tp;
    Button confirmBtn, resetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.nameEdit);
        mobileNo = findViewById(R.id.mobileEdit);
        groupSize = findViewById(R.id.sizeEdit);
        smoking = findViewById(R.id.checkBox);
        dp = findViewById(R.id.datePicker);
        tp = findViewById(R.id.timePicker);
        confirmBtn = findViewById(R.id.confirmBtn);
        resetBtn = findViewById(R.id.resetBtn);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameStr = name.getText().toString();
                String mobileStr = mobileNo.getText().toString();
                String size = groupSize.getText().toString();

                int day = dp.getDayOfMonth();
                int month = dp.getMonth();
                int year = dp.getYear();

                int hour = tp.getCurrentHour();
                int min = tp.getCurrentMinute();

                // Restrict the reservation to a date and time that is after today
                Calendar selectedDateTime = Calendar.getInstance();
                selectedDateTime.set(year, month, day, hour, min);
                Calendar currentDateTime = Calendar.getInstance();

                if (!selectedDateTime.after(currentDateTime)) {
                    Toast.makeText(getApplicationContext(),
                            "Please select a date and time after today.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String yesNoSmokingArea = "";
                if (smoking.isChecked()){
                    yesNoSmokingArea = "Smoking Area";
                } else{
                    yesNoSmokingArea = "Non-Smoking Area";
                }

                String message = "";

                // Check for empty fields
                if (nameStr.isEmpty() || size.isEmpty() || mobileStr.isEmpty()){
                    message = "Please make sure all the required fields are entered";
                } else{
                    message = String.format("Hi %s! You have booked a table for %s in the %s on " +
                                    "%d/%d/%d at %d:%d. Your Phone Number is %s", nameStr,size,yesNoSmokingArea,
                            day, month+1, year, hour, min,mobileStr);
                }
                Toast.makeText(MainActivity.this, message,
                        Toast.LENGTH_SHORT).show();

            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText(null);
                mobileNo.setText(null);
                groupSize.setText(null);
                smoking.setChecked(false);
                dp.updateDate(2020, 5, 1);
                tp.setCurrentHour(19);
                tp.setCurrentMinute(30);
            }
        });

        // Limit the time to 8AM and 8PM
        tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                if (hourOfDay < 8 || hourOfDay >=21){
                    Toast.makeText(MainActivity.this,
                            "Reservations are only from 8AM to 8:59PM", Toast.LENGTH_SHORT).show();
                    if (hourOfDay < 8){
                        tp.setCurrentHour(8);
                        tp.setCurrentMinute(0);
                    } else if (hourOfDay >= 21) {
                        tp.setCurrentHour(20);
                        tp.setCurrentMinute(59);
                    }
                }
            }
        });

    }
}