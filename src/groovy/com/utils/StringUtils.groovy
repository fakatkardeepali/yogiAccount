package com.utils

/**
 * Created by pc-2 on 25/11/15.
 */
class StringUtils {
    static String capitalizeFirstLetter(String originalString){
        String firstLetter = originalString.substring(0,1)
        String remainingSubstring = originalString.substring(1)
        return firstLetter.toUpperCase()+remainingSubstring
    }
}
