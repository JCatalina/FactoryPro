/////////////////////////////////////////////////////////////////////////////
// Name: Date Utilities Module                                             //
// Last Modified: May 4, 1997                                              //
// Author: Michael Chu (mmchu@pobox.com)                                   //
// Purpose: Provide date manipulation operations.                          //
//                                                                         //
// Copyright (C) 1997  Michael Chu                                         //
//                                                                         //
// This program is free software; you can redistribute it and/or modify    //
// it under the terms of the GNU General Public License as published by    //
// the Free Software Foundation; either version 2 of the License, or       //
// (at your option) any later version.                                     //
//                                                                         //
// This program is distributed in the hope that it will be useful,         //
// but WITHOUT ANY WARRANTY; without even the implied warranty of          //
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the           //
// GNU General Public License for more details.                            //
//                                                                         //
// You should have received a copy of the GNU General Public License       //
// along with this program; if not, write to the Free Software             //
// Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.               //
//                                                                         //
// Contact information:      Michael Chu                                   //
//                           mmchu@pobox.com                               //
/////////////////////////////////////////////////////////////////////////////
package com.factory.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//Class for the Date Utilities Module.
public class DateUtils {
	// fields
	private final static String DATE_SIMPLIFIED_FORMAT_STR = new String(
			"yyMMdd");
	private final static String DATE_COMPACT_FORMAT_STR = new String("yyyyMMdd");
	private final static String DATE_FORMAT_STR = new String("yyyy-MM-dd");
	private final static DateFormat DATE_FORMAT = new SimpleDateFormat(
			DATE_FORMAT_STR);
	private final static String TIME_FORMAT_STR = new String(
			"yyyy-MM-dd HH:mm:ss");
	private final static DateFormat TIME_FORMAT = new SimpleDateFormat(
			TIME_FORMAT_STR);
	private final static DateFormat DATE_COMPACT_FORMAT = new SimpleDateFormat(
			DATE_COMPACT_FORMAT_STR);
	private static DateFormat myformat = TIME_FORMAT;

	// *****************************************************************
	// *************************** METHODS *****************************
	// *****************************************************************

	/**
	 * 获取想要的格式的时间
	 * 
	 * @return String
	 */
	public static String getTypeDate(String type, Date date) {
		if (date == null)
			return null;

		String returnDate = null;

		if (StringUtils.isNotEmpty(type)) {
			myformat = new SimpleDateFormat(type);
			returnDate = myformat.format(date);
		} else {
			returnDate = TIME_FORMAT.format(date);
		}

		return returnDate;
	}

	/**
	 * 返回"yyyy-MM-dd"的日期字符串类型格式
	 * 
	 * @return String
	 */
	public static String getDateFormatStr() {
		return DATE_FORMAT_STR;
	}

	/**
	 * 返回"yyyy-MM-dd HH:mm:ss"的日期字符串类型格式
	 * 
	 * @return String
	 */
	public static String getTimeFormatStr() {
		return TIME_FORMAT_STR;
	}

	/**
	 * 返回"yyyyMMdd"的日期字符串类型格式
	 * 
	 * @return String
	 */
	public static String getDateCompactFormatStr() {
		return DATE_COMPACT_FORMAT_STR;
	}
	
	/**
	 * 返回"yyMMdd"的日期字符串类型格式
	 * 
	 * @return String
	 */
	public static String getDateSimplifiedFormatStr() {
		return DATE_SIMPLIFIED_FORMAT_STR;
	}
	
	/**
	 * 返回""yyyy-MM-dd""的日期格式
	 * 
	 * @return DateFormat
	 */
	public static DateFormat getDateFormat() {
		return DATE_FORMAT;
	}

	/**
	 * 返回""yyyy-MM-dd HH:mm:ss""的日期格式
	 * 
	 * @return DateFormat
	 */
	public static DateFormat getTimeFormat() {
		return TIME_FORMAT;
	}

	/**
	 * 返回"yyyyMMdd"的日期格式
	 * 
	 * @return DateFormat
	 */
	public static DateFormat getDateCompactFormat() {
		return DATE_COMPACT_FORMAT;
	}


	/**
	 * 返回当前系统的运行时间 返回当前系统的运行时间，时间格式为"yyyy-MM-dd HH:mm:ss"
	 * 
	 * @return String
	 */
	public static String getCurrentDatetime() {
		Date now = new Date();
		return TIME_FORMAT.format(now);
	}

	/**
	 * 返回当前系统的运行日期 返回当前系统的运行日期，日期格式为"yyyy-MM-dd"
	 * 
	 * @return String
	 */
	public static String getCurrentDate() {
		Date now = new Date();
		return DATE_FORMAT.format(now);
	}

	// returns a String representing the given day of the week number.
	// will return abbreviated version (3 characters) if shortVersion is
	// true.
	// we assume Sunday = 0; ...; Saturday = 6.
	public static String dayOfWeekNumberToString(int dayOfWeekNumber,
			boolean shortVersion) {
		// new day of week string.
		String dayOfWeekString;

		// store the day of week string appropriate to the day of week number.
		switch (dayOfWeekNumber) {
		case 0:
			// depending on whether or not to abbreviate the return, store
			// correct version.
			if (shortVersion) {
				// store "Sun" as day of week string.
				dayOfWeekString = "Sun";
			} else {
				// store "Sunday" as day of week string.
				dayOfWeekString = "Sunday";
			}

			break;
		case 1:
			// depending on whether or not to abbreviate the return, store
			// correct version.
			if (shortVersion) {
				// store "Mon" as day of week string.
				dayOfWeekString = "Mon";
			} else {
				// store "Monday" as day of week string.
				dayOfWeekString = "Monday";
			}

			break;
		case 2:
			// depending on whether or not to abbreviate the return, store
			// correct version.
			if (shortVersion) {
				// store "Tue" as day of week string.
				dayOfWeekString = "Tue";
			} else {
				// store "Tuesday" as day of week string.
				dayOfWeekString = "Tuesday";
			}

			break;
		case 3:
			// depending on whether or not to abbreviate the return, store
			// correct version.
			if (shortVersion) {
				// store "Wed" as day of week string.
				dayOfWeekString = "Wed";
			} else {
				// store "Wednesday" as day of week string.
				dayOfWeekString = "Wednesday";
			}

			break;
		case 4:
			// depending on whether or not to abbreviate the return, store
			// correct version.
			if (shortVersion) {
				// store "Thu" as day of week string.
				dayOfWeekString = "Thu";
			} else {
				// store "Thursday" as day of week string.
				dayOfWeekString = "Thursday";
			}

			break;
		case 5:
			// depending on whether or not to abbreviate the return, store
			// correct version.
			if (shortVersion) {
				// store "Fri" as day of week string.
				dayOfWeekString = "Fri";
			} else {
				// store "Friday" as day of week string.
				dayOfWeekString = "Friday";
			}

			break;
		case 6:
			// depending on whether or not to abbreviate the return, store
			// correct version.
			if (shortVersion) {
				// store "Sat" as day of week string.
				dayOfWeekString = "Sat";
			} else {
				// store "Saturday" as day of week string.
				dayOfWeekString = "Saturday";
			}

			break;
		default:
			// store an error string.
			dayOfWeekString = "UNKNOWNDAYOFWEEK";

			break;
		}

		// return the day of week string.
		return dayOfWeekString;
	}

	// returns a String representing the given month number.
	// we assume January = 0; ...; December = 11.
	public static String monthNumberToString(int monthNumber) {
		// new month string.
		String monthString;

		// store the month string appropriate to the month number.
		switch (monthNumber) {
		case 0:
			// store "January" as month string.
			monthString = "January";

			break;
		case 1:
			// store "February" as month string.
			monthString = "February";

			break;
		case 2:
			// store "March" as month string.
			monthString = "March";

			break;
		case 3:
			// store "April" as month string.
			monthString = "April";

			break;
		case 4:
			// store "May" as month string.
			monthString = "May";

			break;
		case 5:
			// store "June" as month string.
			monthString = "June";

			break;
		case 6:
			// store "July" as month string.
			monthString = "July";

			break;
		case 7:
			// store "August" as month string.
			monthString = "August";

			break;
		case 8:
			// store "September" as month string.
			monthString = "September";

			break;
		case 9:
			// store "October" as month string.
			monthString = "October";

			break;
		case 10:
			// store "November" as month string.
			monthString = "November";

			break;
		case 11:
			// store "December" as month string.
			monthString = "December";

			break;
		default:
			// store an error string.
			monthString = "UNKNOWNMONTH";

			break;
		}

		// return the month string.
		return monthString;
	}

	// returns the number of days in this month.
	// we assume January = 0; ...; December = 11.
	public static int monthNumberToDays(int monthNumber, int currentYear) {
		// number of days in the month.
		int dayNumber;

		// store the number of days appropriate to the month number.
		switch (monthNumber) {
		case 0:
			// store 31 as number of days for January.
			dayNumber = 31;

			break;
		case 1:
			// if it is a leap year, then store 29, otherwise, store 28.
			if (isLeapYear(currentYear)) {
				// store 29 as number of days for February.
				dayNumber = 29;
			} else {
				// store 28 as number of days for February.
				dayNumber = 28;
			}

			break;
		case 2:
			// store 31 as number of days for March.
			dayNumber = 31;

			break;
		case 3:
			// store 30 as number of days for April.
			dayNumber = 30;

			break;
		case 4:
			// store 31 as number of days for May.
			dayNumber = 31;

			break;
		case 5:
			// store 30 as number of days for June.
			dayNumber = 30;

			break;
		case 6:
			// store 31 as number of days for July.
			dayNumber = 31;

			break;
		case 7:
			// store 31 as number of days for August.
			dayNumber = 31;

			break;
		case 8:
			// store 30 as number of days for September.
			dayNumber = 30;

			break;
		case 9:
			// store 31 as number of days for October.
			dayNumber = 31;

			break;
		case 10:
			// store 30 as number of days for November.
			dayNumber = 30;

			break;
		case 11:
			// store 31 as number of days for December.
			dayNumber = 31;

			break;
		default:
			// store 0 as error.
			dayNumber = 0;

			break;
		}

		// return the number of days in the month.
		return dayNumber;
	}

	// returns whether or not the year is a leap year.
	// slight hack as it compares the first days of the months of
	// February and March and if they fall on different days of the
	// week, then it is a leap year.
	// yearNumber should be in the following (XX where it is 19XX).
	public static boolean isLeapYear(int yearNumber) {
		// used to store first day of February.
		Date firstDayOfFebruary;

		// used to store first day of March.
		Date firstDayOfMarch;

		// create/set the first day of February.
		firstDayOfFebruary = new Date(yearNumber, 1, 1, 0, 0, 0);

		// create/set the first day of March.
		firstDayOfMarch = new Date(yearNumber, 2, 1, 0, 0, 0);

		// if the day of the week the first days fall on are different, it
		// is a leap year!
		if (firstDayOfFebruary.getDay() != firstDayOfMarch.getDay()) {
			// return that it is leap year!
			return true;
		} else {
			// return this it is not leap year!
			return false;
		}
	}

	// returns the date by decrementing the given date by one day.
	// if a number if given, then multiple days are decremented.
	public static Date decrementByDay(Date date) {
		// new date.
		Date newDate;

		// start date on given date.
		newDate = new Date(date.getYear(), date.getMonth(), date.getDate(),
				date.getHours(), date.getMinutes(), date.getSeconds());

		// if date is already at beginning of valid range, then don't do
		// anything.
		if (!((newDate.getYear() == 70) && (newDate.getMonth() == 0) && (newDate
				.getDate() == 1))) {
			// if we are not at the beginning of the month, then simply go
			// to previous day in the month. otherwise, go to previous month.
			if (newDate.getDate() > 1) {
				// simply set to previous day.
				newDate = new Date(newDate.getYear(), newDate.getMonth(),
						newDate.getDate() - 1, newDate.getHours(),
						newDate.getMinutes(), newDate.getSeconds());
			} else {
				// first go back one month.
				newDate = decrementByMonth(newDate, 1);

				// set date to end of month.
				newDate = new Date(
						newDate.getYear(),
						newDate.getMonth(),
						monthNumberToDays(newDate.getMonth(), newDate.getYear()),
						newDate.getHours(), newDate.getMinutes(), newDate
								.getSeconds());
			}
		}

		return (new Date(newDate.getYear(), newDate.getMonth(),
				newDate.getDate(), newDate.getHours(), newDate.getMinutes(),
				newDate.getSeconds()));
	}

	public static Date decrementByDay(Date date, int decrementNum) {
		// counter to iteratively decrement.
		int decrementCounter;

		// new date.
		Date newDate;

		// start date on given date.
		newDate = new Date(date.getYear(), date.getMonth(), date.getDate(),
				date.getHours(), date.getMinutes(), date.getSeconds());

		// iteratively decrement date.
		for (decrementCounter = 1; decrementCounter <= decrementNum; decrementCounter++) {
			newDate = decrementByDay(newDate);
		}

		return (new Date(newDate.getYear(), newDate.getMonth(),
				newDate.getDate(), newDate.getHours(), newDate.getMinutes(),
				newDate.getSeconds()));
	}

	// returns the date by incrementing the given date by one day.
	// if a number if given, then multiple days are incremented.
	public static Date incrementByDay(Date date) {
		// new date.
		Date newDate;

		// start date on given date.
		newDate = new Date(date.getYear(), date.getMonth(), date.getDate(),
				date.getHours(), date.getMinutes(), date.getSeconds());

		// if date is already at end of valid range, then don't do
		// anything.
		if (!((newDate.getYear() == 137) && (newDate.getMonth() == 11) && (newDate
				.getDate() == 31))) {
			// if we are not at the end of the month, then simply go
			// to previous day in the month. otherwise, go to previous month.
			if (newDate.getDate() < monthNumberToDays(newDate.getMonth(),
					newDate.getYear())) {
				// simply set to next day.
				newDate = new Date(newDate.getYear(), newDate.getMonth(),
						newDate.getDate() + 1, newDate.getHours(),
						newDate.getMinutes(), newDate.getSeconds());
			} else {
				// set date to beginning of month.
				newDate = new Date(newDate.getYear(), newDate.getMonth(), 1,
						newDate.getHours(), newDate.getMinutes(),
						newDate.getSeconds());

				// first go forward one month.
				newDate = incrementByMonth(newDate, 1);
			}
		}

		return (new Date(newDate.getYear(), newDate.getMonth(),
				newDate.getDate(), newDate.getHours(), newDate.getMinutes(),
				newDate.getSeconds()));
	}

	public static Date incrementByDay(Date date, int incrementNum) {
		// counter to iteratively increment.
		int incrementCounter;

		// new date.
		Date newDate;

		// start date on given date.
		newDate = new Date(date.getYear(), date.getMonth(), date.getDate(),
				date.getHours(), date.getMinutes(), date.getSeconds());

		// iteratively increment date.
		for (incrementCounter = 1; incrementCounter <= incrementNum; incrementCounter++) {
			newDate = incrementByDay(newDate);
		}

		return (new Date(newDate.getYear(), newDate.getMonth(),
				newDate.getDate(), newDate.getHours(), newDate.getMinutes(),
				newDate.getSeconds()));
	}

	// returns the date by decrementing the given date by one week.
	// if a number if given, then multiple weeks are decremented.
	public static Date decrementByWeek(Date date) {
		// new date.
		Date newDate;

		// start date on given date.
		newDate = new Date(date.getYear(), date.getMonth(), date.getDate(),
				date.getHours(), date.getMinutes(), date.getSeconds());

		// if date is already at beginning of valid range, then don't do
		// anything.
		if (!((newDate.getYear() == 70) && (newDate.getMonth() == 0) && (newDate
				.getDate() <= 7))) {
			// go 7 days backward.
			newDate = decrementByDay(newDate, 7);
		}

		return (new Date(newDate.getYear(), newDate.getMonth(),
				newDate.getDate(), newDate.getHours(), newDate.getMinutes(),
				newDate.getSeconds()));
	}

	public static Date decrementByWeek(Date date, int decrementNum) {
		// counter to iteratively decrement.
		int decrementCounter;

		// new date.
		Date newDate;

		// start date on given date.
		newDate = new Date(date.getYear(), date.getMonth(), date.getDate(),
				date.getHours(), date.getMinutes(), date.getSeconds());

		// iteratively decrement date.
		for (decrementCounter = 1; decrementCounter <= decrementNum; decrementCounter++) {
			newDate = decrementByWeek(newDate);
		}

		return (new Date(newDate.getYear(), newDate.getMonth(),
				newDate.getDate(), newDate.getHours(), newDate.getMinutes(),
				newDate.getSeconds()));
	}

	// returns the date by incrementing the given date by one week.
	// if a number if given, then multiple weeks are incremented.
	public static Date incrementByWeek(Date date) {
		// new date.
		Date newDate;

		// start date on given date.
		newDate = new Date(date.getYear(), date.getMonth(), date.getDate(),
				date.getHours(), date.getMinutes(), date.getSeconds());

		// if date is already at end of valid range, then don't do
		// anything.
		if (!((newDate.getYear() == 137) && (newDate.getMonth() == 11) && (newDate
				.getDate() > 24))) {
			// go 7 days forward.
			newDate = incrementByDay(newDate, 7);
		}

		return (new Date(newDate.getYear(), newDate.getMonth(),
				newDate.getDate(), newDate.getHours(), newDate.getMinutes(),
				newDate.getSeconds()));
	}

	public static Date incrementByWeek(Date date, int incrementNum) {
		// counter to iteratively increment.
		int incrementCounter;

		// new date.
		Date newDate;

		// start date on given date.
		newDate = new Date(date.getYear(), date.getMonth(), date.getDate(),
				date.getHours(), date.getMinutes(), date.getSeconds());

		// iteratively increment date.
		for (incrementCounter = 1; incrementCounter <= incrementNum; incrementCounter++) {
			newDate = incrementByWeek(newDate);
		}

		return (new Date(newDate.getYear(), newDate.getMonth(),
				newDate.getDate(), newDate.getHours(), newDate.getMinutes(),
				newDate.getSeconds()));
	}

	// returns the date by decrementing the given date by one month.
	// if a number if given, then multiple months are decremented.
	public static Date decrementByMonth(Date date) {
		// new date.
		Date newDate;

		// start date on given date.
		newDate = new Date(date.getYear(), date.getMonth(), date.getDate(),
				date.getHours(), date.getMinutes(), date.getSeconds());

		// if date is already at beginning of valid range, then don't do
		// anything.
		if (!((newDate.getYear() == 70) && (newDate.getMonth() == 0))) {
			// if we are not at the beginning of the current year, then
			// just go to previous month. Otherwise, change years also.
			if (newDate.getMonth() > 0) {
				// go to previous month.
				newDate = new Date(newDate.getYear(), newDate.getMonth() - 1,
						newDate.getDate(), newDate.getHours(),
						newDate.getMinutes(), newDate.getSeconds());
			} else {
				// go to previous year.
				newDate = decrementByYear(newDate, 1);

				// go to the last month of the previous year.
				newDate = new Date(newDate.getYear(), 11, newDate.getDate(),
						newDate.getHours(), newDate.getMinutes(),
						newDate.getSeconds());
			}
		}

		return (new Date(newDate.getYear(), newDate.getMonth(),
				newDate.getDate(), newDate.getHours(), newDate.getMinutes(),
				newDate.getSeconds()));
	}

	public static Date decrementByMonth(Date date, int decrementNum) {
		// counter to iteratively decrement.
		int decrementCounter;

		// new date.
		Date newDate;

		// start date on given date.
		newDate = new Date(date.getYear(), date.getMonth(), date.getDate(),
				date.getHours(), date.getMinutes(), date.getSeconds());

		// iteratively decrement date.
		for (decrementCounter = 1; decrementCounter <= decrementNum; decrementCounter++) {
			newDate = decrementByMonth(newDate);
		}

		return (new Date(newDate.getYear(), newDate.getMonth(),
				newDate.getDate(), newDate.getHours(), newDate.getMinutes(),
				newDate.getSeconds()));
	}

	// returns the date by incrementing the given date by one month.
	// if a number if given, then multiple months are incremented.
	public static Date incrementByMonth(Date date) {
		// new date.
		Date newDate;

		// start date on given date.
		newDate = new Date(date.getYear(), date.getMonth(), date.getDate(),
				date.getHours(), date.getMinutes(), date.getSeconds());

		// if date is already at end of valid range, then don't do
		// anything.
		if (!((newDate.getYear() == 137) && (newDate.getMonth() == 11))) {
			// if we are not at the end of the current year, then
			// just go to next month. Otherwise, change years also.
			if (newDate.getMonth() < 11) {
				// go to next month.
				newDate = new Date(newDate.getYear(), newDate.getMonth() + 1,
						newDate.getDate(), newDate.getHours(),
						newDate.getMinutes(), newDate.getSeconds());
			} else {
				// go to next year.
				newDate = incrementByYear(newDate, 1);

				// go to the first month of the next year.
				newDate = new Date(newDate.getYear(), 0, newDate.getDate(),
						newDate.getHours(), newDate.getMinutes(),
						newDate.getSeconds());
			}
		}

		return (new Date(newDate.getYear(), newDate.getMonth(),
				newDate.getDate(), newDate.getHours(), newDate.getMinutes(),
				newDate.getSeconds()));
	}

	public static Date incrementByMonth(Date date, int incrementNum) {
		// counter to iteratively increment.
		int incrementCounter;

		// new date.
		Date newDate;

		// start date on given date.
		newDate = new Date(date.getYear(), date.getMonth(), date.getDate(),
				date.getHours(), date.getMinutes(), date.getSeconds());

		// iteratively increment date.
		for (incrementCounter = 1; incrementCounter <= incrementNum; incrementCounter++) {
			newDate = incrementByMonth(newDate);
		}

		return (new Date(newDate.getYear(), newDate.getMonth(),
				newDate.getDate(), newDate.getHours(), newDate.getMinutes(),
				newDate.getSeconds()));
	}

	// returns the date by decrementing the given date by one year.
	// if a number if given, then multiple years are decremented.
	public static Date decrementByYear(Date date) {
		// new date.
		Date newDate;

		// start date on given date.
		newDate = new Date(date.getYear(), date.getMonth(), date.getDate(),
				date.getHours(), date.getMinutes(), date.getSeconds());

		// if date is already at beginning of valid range, then don't do
		// anything.
		if (!((newDate.getYear() == 70))) {
			// decrement the year.
			newDate = new Date(newDate.getYear() - 1, newDate.getMonth(),
					newDate.getDate(), newDate.getHours(),
					newDate.getMinutes(), newDate.getSeconds());
		}

		return (new Date(newDate.getYear(), newDate.getMonth(),
				newDate.getDate(), newDate.getHours(), newDate.getMinutes(),
				newDate.getSeconds()));
	}

	public static Date decrementByYear(Date date, int decrementNum) {
		// counter to iteratively decrement.
		int decrementCounter;

		// new date.
		Date newDate;

		// start date on given date.
		newDate = new Date(date.getYear(), date.getMonth(), date.getDate(),
				date.getHours(), date.getMinutes(), date.getSeconds());

		// iteratively decrement date.
		for (decrementCounter = 1; decrementCounter <= decrementNum; decrementCounter++) {
			newDate = decrementByYear(newDate);
		}

		return (new Date(newDate.getYear(), newDate.getMonth(),
				newDate.getDate(), newDate.getHours(), newDate.getMinutes(),
				newDate.getSeconds()));
	}

	// returns the date by incrementing the given date by one year.
	// if a number if given, then multiple years are incremented.
	public static Date incrementByYear(Date date) {
		// new date.
		Date newDate;

		// start date on given date.
		newDate = new Date(date.getYear(), date.getMonth(), date.getDate(),
				date.getHours(), date.getMinutes(), date.getSeconds());

		// if date is already at end of valid range, then don't do
		// anything.
		if (!((newDate.getYear() == 137))) {
			// increment the year.
			newDate = new Date(newDate.getYear() + 1, newDate.getMonth(),
					newDate.getDate(), newDate.getHours(),
					newDate.getMinutes(), newDate.getSeconds());
		}

		return (new Date(newDate.getYear(), newDate.getMonth(),
				newDate.getDate(), newDate.getHours(), newDate.getMinutes(),
				newDate.getSeconds()));
	}

	public static Date incrementByYear(Date date, int incrementNum) {
		// counter to iteratively increment.
		int incrementCounter;

		// new date.
		Date newDate;

		// start date on given date.
		newDate = new Date(date.getYear(), date.getMonth(), date.getDate(),
				date.getHours(), date.getMinutes(), date.getSeconds());

		// iteratively increment date.
		for (incrementCounter = 1; incrementCounter <= incrementNum; incrementCounter++) {
			newDate = incrementByYear(newDate);
		}

		return (new Date(newDate.getYear(), newDate.getMonth(),
				newDate.getDate(), newDate.getHours(), newDate.getMinutes(),
				newDate.getSeconds()));
	}

	// returns the first day of the week of the week the day is in.
	// NOTE: if the first day of the week is outside of the valid date
	// values (01/01/1970 -> 12/31/2037) then it will just return
	// (01/01/1970).
	public static Date firstDayOfThisWeek(Date date) {
		// new first day of week.
		Date firstDayOfWeek;

		// create/set the first day of the week.
		firstDayOfWeek = new Date(date.getYear(), date.getMonth(),
				date.getDate(), 0, 0, 0);

		// get the first day of the week.
		firstDayOfWeek = decrementByDay(firstDayOfWeek, firstDayOfWeek.getDay());

		// return the first day of the week.
		return (new Date(firstDayOfWeek.getYear(), firstDayOfWeek.getMonth(),
				firstDayOfWeek.getDate(), firstDayOfWeek.getHours(),
				firstDayOfWeek.getMinutes(), firstDayOfWeek.getSeconds()));
	}

	// returns if one Date is greater than another.
	// if firstDate > secondDate.
	public static boolean dateIsGreater(Date firstDate, Date secondDate) {
		if (firstDate.getYear() > secondDate.getYear()) {
			return true;
		} else if (firstDate.getYear() < secondDate.getYear()) {
			return false;
		}
		if (firstDate.getMonth() > secondDate.getMonth()) {
			return true;
		} else if (firstDate.getMonth() < secondDate.getMonth()) {
			return false;
		}
		if (firstDate.getDate() > secondDate.getDate()) {
			return true;
		} else if (firstDate.getDate() < secondDate.getDate()) {
			return false;
		}
		if (firstDate.getHours() > secondDate.getHours()) {
			return true;
		} else if (firstDate.getHours() < secondDate.getHours()) {
			return false;
		}
		if (firstDate.getMinutes() > secondDate.getMinutes()) {
			return true;
		} else if (firstDate.getMinutes() < secondDate.getMinutes()) {
			return false;
		}
		if (firstDate.getSeconds() > secondDate.getSeconds()) {
			return true;
		} else if (firstDate.getSeconds() < secondDate.getSeconds()) {
			return false;
		}

		// if we made it this far, probably less than or equal.
		return false;
	}

	// returns if one Date (Time) is greater than another.
	// if firstDate > secondDate.
	// this version does not compare the calendar day, only clock time.
	public static boolean timeIsGreater(Date firstDate, Date secondDate) {
		if (firstDate.getHours() > secondDate.getHours()) {
			return true;
		} else if (firstDate.getHours() < secondDate.getHours()) {
			return false;
		}
		if (firstDate.getMinutes() > secondDate.getMinutes()) {
			return true;
		} else if (firstDate.getMinutes() < secondDate.getMinutes()) {
			return false;
		}
		if (firstDate.getSeconds() > secondDate.getSeconds()) {
			return true;
		} else if (firstDate.getSeconds() < secondDate.getSeconds()) {
			return false;
		}

		// if we made it this far, probably less than or equal.
		return false;
	}

	// returns if one Date is greater than or equal to another.
	// if firstDate >= secondDate.
	public static boolean dateIsGreaterEqual(Date firstDate, Date secondDate) {
		// if already greater than, then just return.
		if (dateIsGreater(firstDate, secondDate)) {
			return true;
		}

		// check for non-equality.
		if (firstDate.getYear() != secondDate.getYear()) {
			return false;
		}
		if (firstDate.getMonth() != secondDate.getMonth()) {
			return false;
		}
		if (firstDate.getDate() != secondDate.getDate()) {
			return false;
		}
		if (firstDate.getHours() != secondDate.getHours()) {
			return false;
		}
		if (firstDate.getMinutes() != secondDate.getMinutes()) {
			return false;
		}
		if (firstDate.getSeconds() != secondDate.getSeconds()) {
			return false;
		}

		// if we made it this far, probably equal.
		return true;
	}

	// returns if one Date (Time) is greater than or equal to another.
	// if firstDate >= secondDate.
	// this version does not compare the calendar day, only clock time.
	public static boolean timeIsGreaterEqual(Date firstDate, Date secondDate) {
		// if already greater than, then just return.
		if (timeIsGreater(firstDate, secondDate)) {
			return true;
		}

		// check for non-equality.
		if (firstDate.getHours() != secondDate.getHours()) {
			return false;
		}
		if (firstDate.getMinutes() != secondDate.getMinutes()) {
			return false;
		}
		if (firstDate.getSeconds() != secondDate.getSeconds()) {
			return false;
		}

		// if we made it this far, probably equal.
		return true;
	}

	/**
	 * 比较两个时间天数差 startTime - endTime
	 * 
	 * @param type
	 *            (无效)
	 * @return String
	 * @throws ParseException
	 * */
	public static int getDiffDate(String type, Date startTime, Date endTime)
			throws ParseException {
		if (StringUtils.isNotEmpty(type)) {
			myformat = new SimpleDateFormat(type);
		} else {
			myformat = new SimpleDateFormat(DATE_FORMAT_STR);
		}

		int i = 0;
		i = (int) ((myformat.parse(myformat.format(startTime)).getTime() - myformat
				.parse(myformat.format(endTime)).getTime()) / (1000 * 60 * 60 * 24));
		// return
		// myformat.parse(myformat.format(date1)).compareTo(myformat.parse(myformat.format(date2)));
		return i;
	}

	/**
	 * 得到两个时间的分钟差 date1 - date2 这个是个奇葩。但是整型了。
	 * 
	 * @return int
	 * @throws ParseException
	 * */
	public static int getLeftDateForMinue(Date date1, Date date2)
			throws ParseException {
		long times = date1.getTime() - date2.getTime();
		times = times / (1000 * 60);
		return (int) times;
	}

	/**
	 * Date类型转换为10位时间戳
	 * 
	 * @param time
	 * @return
	 */
	public static Integer DateToTimestamp(Date time) {
		Timestamp ts = new Timestamp(time.getTime());
		return (int) ((ts.getTime()) / 1000);
	}

	/** 获取10位数时间戳 */
	public static Integer getTenTime(Date date) {
		Timestamp ts = new Timestamp(date.getTime());
		return (int) ((ts.getTime()) / 1000);
	}
}
