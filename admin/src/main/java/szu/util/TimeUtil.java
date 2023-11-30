package szu.util;

public class TimeUtil {
    public static String secondsToHHMMSS(int totalSeconds){
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static Integer HHMMSSToSeconds(String timeString){
        int hours = Integer.parseInt(timeString.substring(0, 2));
        int minutes = Integer.parseInt(timeString.substring(2, 4));
        int seconds = Integer.parseInt(timeString.substring(4, 6));
        return hours * 3600 + minutes * 60 + seconds;
    }
}
