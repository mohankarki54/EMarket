package emarket.emarket.Service;

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
                    value = "Last updated " + String.valueOf(diffDays) + " days ago";
                } else {
                    value = "Last updated " + String.valueOf(diffDays) + " day ago";
                }
            } else if (diffHours != 0) {
                if (diffHours != 1) {
                    value = "Last updated " + String.valueOf(diffHours) + " hours ago";
                } else {
                    value = "Last updated " + String.valueOf(diffHours) + " hour ago";
                }

            } else if (diffMinutes != 0) {
                if (diffMinutes != 1) {
                    value = "Last updated " + String.valueOf(diffMinutes) + " minutes ago";
                } else {
                    value = "Last updated " + String.valueOf(diffMinutes) + " minute ago";
                }

            } else if (diffSeconds != 0) {
                if (diffSeconds != 1) {
                    value = "Last updated " + String.valueOf(diffSeconds) + " seconds ago";
                } else {
                    value = "Last updated " + String.valueOf(diffSeconds) + " second ago";
                }
            }
        }
        return value;
    }
}
