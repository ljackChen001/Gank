package com.ui.component.wheelview;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.ui.gank.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Datetime extends LinearLayout {

	private DatePicker datePicker;

	private TimePicker timePicker;

	private static SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public Datetime(Context context, String time) {
		super(context);
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(f.parse(time));
		} catch (Throwable e) {
			e.printStackTrace();
		}

		LayoutInflater.from(context).inflate(R.layout.book_datetime, this);

		datePicker = (DatePicker) findViewById(R.id.datetime_date);
		datePicker.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), null);

		timePicker = (TimePicker) findViewById(R.id.datetime_time);
		timePicker.setIs24HourView(true);
		timePicker.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));
		timePicker.setCurrentMinute(c.get(Calendar.MINUTE));
	}

	public String getDatetimeStr() {
		int y = datePicker.getYear();
		int m = datePicker.getMonth() + 1;
		int d = datePicker.getDayOfMonth();

		int h = timePicker.getCurrentHour();
		int mm = timePicker.getCurrentMinute();

		return y + "-" + f(m) + "-" + f(d) + " " + f(h) + ":" + f(mm);
	}

	private String f(int v) {
		return v < 10 ? "0" + v : "" + v;
	}
}
