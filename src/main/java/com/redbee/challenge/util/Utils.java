package com.redbee.challenge.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Service;

@Service
public class Utils {
	
	private DateFormat dateFormater = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa z", Locale.ENGLISH);
	
	/**
	 * Parses the string to calendar.
	 *
	 * @param dateStr date in string format
	 * @return Calendar
	 * @throws ParseException
	 */
	public Calendar parseStringToCalendar(String dateStr) throws ParseException {
		Date temporalDate = dateFormater.parse(dateStr);
		Calendar date = Calendar.getInstance();
		date.setTime(temporalDate);
		return date;
	}

}
