package fr.ensitech.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public final class Dates {

	private final static String format = "dd/MM/yyyy";
	
	private Dates() {
	}
	
	public static final java.util.Date convertStringToDate(String dateStr) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.parse(dateStr);
	}

	public static final String convertDateToString(java.util.Date date) throws Exception {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			return dateFormat.format(date);
	}
	
	public final static java.sql.Date convertDateUtilToDateSql(java.util.Date date) throws Exception {
		return new java.sql.Date(date.getTime());
	}
	public final static java.util.Date convertDateSqlToDateUtil(java.sql.Date date) throws Exception {
		return new java.util.Date(date.getTime());
	}
}
