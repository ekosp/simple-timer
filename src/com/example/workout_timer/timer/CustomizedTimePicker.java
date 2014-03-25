package com.example.workout_timer.timer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;
import com.example.workout_timer.R;

/**
 * Created by mislav on 3/13/14.
 */
public class CustomizedTimePicker extends AlertDialog implements NumberPicker.OnValueChangeListener {

    private NumberPicker hourPicker;
    private NumberPicker minutePicker;
    private NumberPicker secondPicker;
    private OnTimerSetListener timerSetListener;

    public CustomizedTimePicker(Context context, OnTimerSetListener timerSetListener, boolean cancelable) {

        super(context, cancelable, CustomCancelListener.getInstance());

        this.timerSetListener = timerSetListener;
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.time_picker_dialog, null);
        setView(view);

        hourPicker = (NumberPicker) view.findViewById(R.id.hour);
        minutePicker = (NumberPicker) view.findViewById(R.id.minute);
        secondPicker = (NumberPicker) view.findViewById(R.id.seconds);

        Button confirmButton = (Button) view.findViewById(R.id.confirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTimer(view);

            }
        });

        customizeNumberPicker(hourPicker, 0, 23);
        customizeNumberPicker(minutePicker, 0, 59);
        customizeNumberPicker(secondPicker, 0, 59);

    }

    private void customizeNumberPicker(NumberPicker numberPicker, int minValue, int maxValue) {

        if (numberPicker == null) {
            throw new IllegalArgumentException("Number picker is null!" + maxValue);
        }

        numberPicker.setFormatter(CustomFormatter.getInstance());
        numberPicker.setMinValue(minValue);
        numberPicker.setMaxValue(maxValue);
        numberPicker.setOnValueChangedListener(this);
        numberPicker.showContextMenu();
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
        // ignore
    }

    public void setTimerPicker(int hours, int minutes, int seconds) {

        hourPicker.setValue(hours);
        minutePicker.setValue(minutes);
        secondPicker.setValue(seconds);
    }

    public void setTimer(View view) {

        timerSetListener.onTimerSet(hourPicker.getValue(), minutePicker.getValue(), secondPicker.getValue());
        this.dismiss();
    }

    private static class CustomFormatter implements NumberPicker.Formatter {

        private static final CustomFormatter formatter;

        static {
            formatter = new CustomFormatter();
        }

        @Override
        public String format(int value) {
            return String.format("%02d", value);
        }

        public static NumberPicker.Formatter getInstance() {
            return formatter;
        }
    }

    private static class CustomCancelListener implements OnCancelListener {

        private static final OnCancelListener listener;

        static {
            listener = new CustomCancelListener();
        }


        public static OnCancelListener getInstance() {
            return listener;
        }

        @Override
        public void onCancel(DialogInterface dialogInterface) {
            // ignore
        }
    }
}
