package com.utils

/**
 * Created by pc-3 on 6/2/15.
 */
class ParamUtils {

    static def getExtraQueryParams(Map params) {
        def extraParams = params.extraParams
        if (extraParams) {
            def data = extraParams.collect { key, value ->
                if (value instanceof Boolean) {
                    return "${key}=${value}"
                } else if (value instanceof String && !key.trim().equals("orderBy") && !key.trim().equals("groupBy")) {
                    return "${key}='${value.trim()}'"
                } else if (key.trim().equals("orderBy") && "${value}".trim()) {
                    return "order by ${value.trim()}"
                } else if (key.trim().equals("groupBy") && "${value}".trim()) {
                    return "group by ${value.trim()}"
                }
            }
            return data
        } else {
            return []
        }
    }
}
