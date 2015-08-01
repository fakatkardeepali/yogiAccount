package com.utils

/**
 * Created by gvc on 21/01/2015.
 */
class PagingUtils {
    static def getDefaultPagingParams(Map params) {
        def page = Integer.parseInt(params.page), pageSize = Integer.parseInt(params.pageSize);
        return [max: pageSize, offset: (page - 1) * pageSize];
    }
}
