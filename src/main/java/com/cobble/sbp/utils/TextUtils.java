package com.cobble.sbp.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static com.cobble.sbp.utils.Utils.*;

public class TextUtils {
    public static String unformatText(String string) {
        StringBuilder output = new StringBuilder();
        char[] chars = string.toCharArray();
        boolean prevChar = false;
        for(int i=0;i<chars.length;i++) {

            boolean equals = (chars[i] + "").equals(Reference.COLOR_CODE_CHAR + "");
            try {
                if(equals) {
                    if("mlnok".contains(chars[i+1]+"")) {
                        output.append(chars[i]);
                        continue;
                    }
                }
            }catch(Exception ignored) {}


            if(!equals) {
                if(!prevChar) {
                    output.append(chars[i]);
                }
                prevChar = false;

            } else if(equals) {
                prevChar = true;
            }
        }

        return output.toString();
    }

    private static final Pattern ANTI_COLOR_CODES = Pattern.compile("(?i)§[0-9A-FK-OR]");
    public static String unformatAllText(String string) {
        return ANTI_COLOR_CODES.matcher(string).replaceAll("");
    }

    public static String formatNums(int num) {
        NumberFormat myFormat = NumberFormat.getInstance();
        myFormat.setGroupingUsed(true);
        return myFormat.format(num);
    }

    public static String secondsToTime(long input) {
        long days = TimeUnit.MILLISECONDS.toDays(input);
        input-=TimeUnit.DAYS.toMillis(days);

        long hours = TimeUnit.MILLISECONDS .toHours(input);
        input -= TimeUnit.HOURS.toMillis(hours);

        long minutes = TimeUnit.MILLISECONDS.toMinutes(input);
        input -= TimeUnit.MINUTES.toMillis(minutes);

        long seconds = TimeUnit.MILLISECONDS.toSeconds(input);

            String secs = seconds+"";
            if(seconds < 10) {
                secs = "0"+seconds;
            }
            if(days > 0) {
                return days+"d"+hours+"h"+minutes+"m"+secs+"s";
            }
            else if(hours > 0) {
                return hours+"h"+minutes+"m"+secs+"s";
            } else {
                return minutes+":"+secs;
            }
    }

    public static void sendMessage(Object string) {
        sendSpecificMessage(Colors.DARK_AQUA+"["+Colors.AQUA+"SBP"+Colors.DARK_AQUA+"] "+Colors.YELLOW+string+Colors.WHITE);
    }

    public static void sendSpecificMessage(String string) {
        try {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(string));
        } catch(Exception ignored) {}
    }

    public static String removeIntLast(String str, int chars) {
        return str.substring(0, str.length() - chars);
    }

    public static Boolean checkIfArrayContains(String[] arrayList, String stringCheck) {
        boolean tempBoolean = false;
        for (String s : arrayList) {
            if (stringCheck.equals(s)) {
                tempBoolean = true;
                break;
            }
        }

        return tempBoolean;
    }

    public static Boolean checkIfCharLetter(String string) {

        String charList = "abcdefghijklmnopqrstuvwxyz ?:;'<>/\\\".,0123456789!()*^%$@&-+_=[]{}~`|#";
        return charList.contains(string.toLowerCase());

    }

    public static boolean checkIfCharInt(String string) {

        String charList = "0123456789";
        return charList.contains(string.toLowerCase());


    }

    public static void sendErrMsg(String string) {
        sendSpecificMessage(Colors.DARK_RED+"["+Colors.RED+"SBP"+Colors.DARK_RED+"] "+Colors.YELLOW+string);
    }
}
