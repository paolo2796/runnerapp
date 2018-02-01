package it.unisa.runnerapp.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class CheckUtils
{
    public static boolean checkPermissions(Context ctx,String... permissions)
    {
        boolean areGranted=true;
        int index=0;

        while (areGranted&&index<permissions.length)
        {
            areGranted=areGranted&&isPermissionGranted(ctx,permissions[index]);
            index+=1;
        }

        return areGranted;
    }

    public static boolean isPermissionGranted(Context ctx, String permission)
    {
        return ActivityCompat.checkSelfPermission(ctx,permission)== PackageManager.PERMISSION_GRANTED;
    }

    public static String parseDate(String pattern,Date date)
    {
        try
        {
            SimpleDateFormat formatter=new SimpleDateFormat(pattern);
            return formatter.format(date);
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    public static int getHour(Date date)
    {
        Calendar c=new GregorianCalendar();
        c.setTime(date);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinutes(Date date)
    {
        Calendar c=new GregorianCalendar();
        c.setTime(date);
        return c.get(Calendar.MINUTE);
    }

    public static int getAge(Date date)
    {
        Calendar dob=new GregorianCalendar();
        dob.setTime(date);
        Calendar today = Calendar.getInstance();
        int curYear = today.get(Calendar.YEAR);
        int dobYear = dob.get(Calendar.YEAR);
        int age = curYear - dobYear;
        int curMonth = today.get(Calendar.MONTH);
        int dobMonth = dob.get(Calendar.MONTH);
        if (dobMonth > curMonth)
        {
            age--;
        }
        else if (dobMonth == curMonth)
        {
            int curDay = today.get(Calendar.DAY_OF_MONTH);
            int dobDay = dob.get(Calendar.DAY_OF_MONTH);

            if (dobDay > curDay)
            {
                age--;
            }
        }

        return age;
    }

    public static String parseHourOrMinutes(int x)
    {
        String x_str;
        if(x>=0&&x<=9)
            x_str="0"+x;
        else
            x_str=""+x;
        return x_str;
    }
}
