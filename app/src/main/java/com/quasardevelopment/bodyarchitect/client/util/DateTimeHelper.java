package com.quasardevelopment.bodyarchitect.client.util;

import org.joda.time.*;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.format.*;
import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 12.04.13
 * Time: 18:40
 * To change this template use File | Settings | File Templates.
 */
public class DateTimeHelper {
    public static Date ConvertFromWebService(String strDate)  throws ParseException
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return format.parse(strDate)    ;
    }

    public static DateTime toLocal(DateTime dateTime)
    {
        return dateTime.toDateTime(DateTimeZone.getDefault());
    }

    public static String fromSeconds(long seconds)
    {
        PeriodFormatter fmt = new PeriodFormatterBuilder()
                .appendDays()
                .appendSeparator(".")
                .printZeroAlways()
                .minimumPrintedDigits(2)
                .appendHours()
                .appendSeparator(":")
                .printZeroAlways()
                .minimumPrintedDigits(2)
                .appendMinutes()
                .appendSeparator(":")
                .printZeroAlways()
                .minimumPrintedDigits(2)
                .appendSeconds()
                .toFormatter();

        Period period = new Period(seconds* 1000);
        String test=period.normalizedStandard().toString(fmt);
        return test;
    }

    public static DateTime ConvertFromWebServiceNew(String strDate)
    {
        //DateTimeFormatter parser1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss[.SSS]");
        DateTimeFormatter parser1 = new DateTimeFormatterBuilder()
                .append(ISODateTimeFormat.date())
                .appendLiteral('T')
                .append(ISODateTimeFormat.hourMinuteSecond())
                .appendOptional(fractionElement())
                .appendOptional(offsetElement())
                .toFormatter()
                .withZone(DateTimeZone.UTC);
        parser1.withChronology(ISOChronology.getInstanceUTC());
        //DateTimeFormatter parser2 = ISODateTimeFormat.dateTimeNoMillis();


        return parser1.parseDateTime(strDate);
    }

    private static DateTimeParser offsetElement() {
        return new DateTimeFormatterBuilder().appendTimeZoneOffset("Z",true, 2, 4).toParser();
    }

    private static DateTimeParser fractionElement() {
        return new DateTimeFormatterBuilder().appendLiteral('.').appendFractionOfSecond(3,9).toParser();
    }


    public static DateTime ToMonth(DateTime date)
    {
        DateTime firstDayInMonth= new DateTime(date.getYear(),date.getMonthOfYear(),1,0,0,date.getChronology());
        return firstDayInMonth;
    }

    public static DateTime AddMonths(DateTime date, int months)
    {
        date=date.plusMonths(months);
        return date;
    }

    public static  boolean isFuture(DateTime date)
    {
        return date.isAfterNow();
    }


    public static  DateTime removeTimeZone(DateTime date)
    {
        return date.withZoneRetainFields(DateTimeZone.UTC);
    }

    public static DateTime toDate(DateTime dateTime)
    {
          return dateTime.toLocalDate().toDateTimeAtStartOfDay(dateTime.getZone());
    }

    public static String toRelativeDate(LocalDateTime date)
    {
        PrettyTime p = new PrettyTime();
        return p.format(date.toDate());
    }

    public static String toRelativeDate(DateTime date)
    {
        //return DateUtils.getRelativeDateTimeString(MyApplication.getAppContext(),date.toDate().getTime(),DateUtils.SECOND_IN_MILLIS,0,0).toString();
        //return DateUtils.getRelativeTimeSpanString(date.toDate().getTime()).toString();
        PrettyTime p = new PrettyTime();
        return p.format(date.toDate());
        //todo:translate
//        Period period = new Period(date,DateTime.now(DateTimeZone.UTC));
//        Duration duration=period.toStandardDuration();
//        long totalMinutes=duration.getStandardMinutes();
//
//        String sufix = " ago";
//        if(!sufix.startsWith(" "))
//        {
//            sufix=" "+sufix;
//        }
//        if(totalMinutes<0)
//        {
//            totalMinutes=Math.abs(totalMinutes);
//            sufix=" from now";
//        }
//        String value;
//        if(totalMinutes<1)
//        {
//             value="Less than a minute";
//        }
//        else if(totalMinutes<2)
//        {
//            value="A minute";
//        }
//        else if(totalMinutes<45)
//        {
//            value=String.format("%d minutes",Math.round(Math.abs(totalMinutes)));
//        }
//        else if(totalMinutes<90)
//        {
//            value="One hour";
//        }
//        else if(totalMinutes<60 * 24)
//        {
//            value=String.format("%d hours",Math.round(Math.abs(period.getHours())));
//        }
//        else if(totalMinutes<60 * 48)
//        {
//            value="One day";
//        }
//        else if(totalMinutes<60 * 24*30)
//        {
//            value=String.format("%d days",Math.round(Math.abs(period.getDays())));
//        }
//        else if(totalMinutes<60 * 24*60)
//        {
//            value="One month";
//        }
//        else if(totalMinutes<60 * 24*365)
//        {
//            value=String.format("%d months",Math.round(Math.abs(period.getMonths())));
//        }
//        else if(totalMinutes<60 * 24*365*2)
//        {
//            value="One year";
//        }
//        else
//        {
//            value=String.format("%d years",Math.round(Math.abs(period.getYears())));
//        }
//        return value+sufix;


    }

    public static int GetAge(DateTime birthday)
    {
//        DateTime now = DateTime.now().toLocalDate().toDateTimeAtStartOfDay();
//
//        int age = now.getYear() - birthday.getYear();
//
//        if (birthday > now.AddYears(-age))
//        {
//            age--;
//        }
//        return age;
        return Years.yearsBetween(birthday, DateTime.now()).getYears();
    }

    public static boolean isToday(DateTime dateTime)
    {
        return dateTime.toLocalDate().equals(DateTime.now().toLocalDate());
    }
}
