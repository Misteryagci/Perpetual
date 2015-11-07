package com.tchocolat.kaanyagci.perpetual;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by kaanyagci on 06/11/15.
 */
public class UserFunctions {
    public static ArrayList<String> day_names;

    public static String current_date = getCurrentDate();
    public static void tommorrow() {
        String[] current_date_tab = current_date.split("\\.");
        int gun, ay, yil, maxday;
        gun = Integer.parseInt(current_date_tab[0]);
        ay = Integer.parseInt(current_date_tab[1]);
        yil = Integer.parseInt(current_date_tab[2]);

        //Max dayin hesaplanmasi
        //30 ceken aylardan biriyse
        if ((ay == 4) || (ay == 6) || (ay == 9) || (ay == 11))
            maxday = 30;
        else {
            if (ay == 2) {
                if (yil % 4 == 0)
                    maxday = 29;
                else
                    maxday = 28;
            } else
                maxday = 31;
        }
        if (gun == maxday) {
            if (ay == 12) {
                yil++;
                ay = 1;
                gun = 1;
            } else {
                ay++;
                gun = 1;
            }
        } else
            gun++;
        current_date = "" + gun + "." + ay + "." + yil;
    }
    public static void setUpDayNames() {
        day_names = new ArrayList<String>();
        day_names.add("MONDAY");
        day_names.add("TUESDAY");
        day_names.add("WEDNESDAY");
        day_names.add("THURSDAY");
        day_names.add("FRIDAY");
        day_names.add("SATURDAY");
        day_names.add("SUNDAY");
    }

    public static void oneMonthLater() {
        String[] date_table = UserFunctions.current_date.split("\\.");
        String first_day_next_month;
        int month = Integer.parseInt(date_table[1]);
        if (month < 12)
            first_day_next_month = "1." + (month + 1) + "." + date_table[2];
        else
            first_day_next_month = "1.1" + "." + (Integer.parseInt(date_table[2]) + 1);
        int max_days = getMaxDayFromString(first_day_next_month);
        String[] res_date_tab = first_day_next_month.split("\\.");
        int day = Integer.parseInt(date_table[0]);
        if (day > max_days)
            res_date_tab[0] = "" + max_days;
        else
            res_date_tab[0] = "" + day;
        String res = res_date_tab[0] + "." + res_date_tab[1] + "." + res_date_tab[2];
        UserFunctions.current_date = res;
    }
    public static int getMaxDayFromString(String date) {

        Calendar c = setCalendarFromString(date);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);

    }
    private static String bastakiSifiriKaldir(String s) {
        String res = s;
        if (s.length() == 2) {
            if (("" + s.charAt(0)).equals("0"))
                res = "" + s.charAt(1);
        }
        return res;
    }
    public static String getCurrentDate() {
        String res = "";


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        res = dateFormat.format(date); //2014/08/06 15:59:48
        res = res.split("\\s+")[0];
        String[] date_tab = res.split("\\/");
        String yil = date_tab[0];
        String ay = bastakiSifiriKaldir(date_tab[1]);
        String gun = bastakiSifiriKaldir(date_tab[2]);
        res = "" + gun + "." + ay + "." + yil;
        return res;
    }
    public static Calendar setCalendarFromString(String date) {
        String[] date_tab = date.split("\\.");
        int day = Integer.parseInt(date_tab[0]);
        int month = Integer.parseInt(date_tab[1]);
        int year = Integer.parseInt(date_tab[2]);

        //Set up calender class
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        switch (month) {
            case 1:
                cal.set(Calendar.MONTH, Calendar.JANUARY);
                break;
            case 2:
                cal.set(Calendar.MONTH, Calendar.FEBRUARY);
                break;
            case 3:
                cal.set(Calendar.MONTH, Calendar.MARCH);
                break;
            case 4:
                cal.set(Calendar.MONTH, Calendar.APRIL);
                break;
            case 5:
                cal.set(Calendar.MONTH, Calendar.MAY);
                break;
            case 6:
                cal.set(Calendar.MONTH, Calendar.JUNE);
                break;
            case 7:
                cal.set(Calendar.MONTH, Calendar.JULY);
                break;
            case 8:
                cal.set(Calendar.MONTH, Calendar.AUGUST);
                break;
            case 9:
                cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
                break;
            case 10:
                cal.set(Calendar.MONTH, Calendar.OCTOBER);
                break;
            case 11:
                cal.set(Calendar.MONTH, Calendar.NOVEMBER);
                break;
            case 12:
                cal.set(Calendar.MONTH, Calendar.DECEMBER);
        }
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal;
    }


    public static void yesterday() {
        String[] current_date_tab = current_date.split("\\.");
        int gun, ay, yil, maxday, ay_org;
        gun = Integer.parseInt(current_date_tab[0]);
        ay = Integer.parseInt(current_date_tab[1]);
        yil = Integer.parseInt(current_date_tab[2]);

        //Max dayin hesaplanmasi
        //30 ceken aylardan biriyse
        if ((ay == 5) || (ay == 7) || (ay == 10) || (ay == 12))
            maxday = 30;
        else {
            if (ay == 3) {
                if (yil % 4 == 0)
                    maxday = 29;
                else
                    maxday = 28;
            } else
                maxday = 31;
        }
        if (gun == 1) {
            if (ay == 1) {
                yil--;
                ay = 12;
                gun = 31;
            } else {
                ay--;
                gun = maxday;
            }
        } else
            gun--;
        current_date = "" + gun + "." + ay + "." + yil;
    }

}
