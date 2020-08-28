package emarket.emarket.bean;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TimeCalculation {
    public String timeDifference(Date current, Date start){
        String value = " ";
        if(start != null) {
            long diff = current.getTime() - start.getTime();
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            if (diffDays != 0) {
                if (diffDays != 1) {
                    value = "Last updated " + String.valueOf(diffDays) + " days";
                } else {
                    value = "Last updated " + String.valueOf(diffDays) + " day";
                }
            } else if (diffHours != 0) {
                if (diffHours != 1) {
                    value = "Last updated " + String.valueOf(diffHours) + " hours";
                } else {
                    value = "Last updated " + String.valueOf(diffHours) + " hour";
                }

            } else if (diffMinutes != 0) {
                if (diffMinutes != 1) {
                    value = "Last updated " + String.valueOf(diffMinutes) + " minutes";
                } else {
                    value = "Last updated " + String.valueOf(diffMinutes) + " minute";
                }

            } else if (diffSeconds != 0) {
                if (diffSeconds != 1) {
                    value = "Last updated " + String.valueOf(diffSeconds) + " seconds";
                } else {
                    value = "Last updated " + String.valueOf(diffSeconds) + " second";
                }
            }
        }
        return value;
    }
}
