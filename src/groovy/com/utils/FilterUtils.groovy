package com.utils

/**
 * Created by akshay on 7/2/15.
 */
class FilterUtils {

    static getFilterQuery(String query) {
        if (query.contains("order by") || query.contains("group by")) {
            String reverseQuery = ""
            String trueString = ""
            String[] str = query.split(" ")
            for (int i = str.length - 1; i > -1; i--) {
                reverseQuery += str[i] + " ";
            }
            String revStr = reverseQuery.replaceFirst(" and", "")
            String[] reverseStr = revStr.split(" ")
            for (int i = reverseStr.length - 1; i > -1; i--) {
                trueString += reverseStr[i] + " ";
            }
            return trueString
        } else {
            return query
        }
    }
}
