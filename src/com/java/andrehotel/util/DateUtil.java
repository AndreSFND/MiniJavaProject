package com.java.andrehotel.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static Date convertToDate(String date) throws ParseException {
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date formattedDate = formatter.parse(date);
		
		return formattedDate;
		
	}
	
}
