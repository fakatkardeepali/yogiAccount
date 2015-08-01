package com.utils

/**
 * Created by pc-3 on 6/2/15.
 */
class GridUtils {
    static def getGridData(def controllerClass, def grailsApplication, Map params) {

        String domainName = DomainUtils.getDomainNameByController(controllerClass)
        Class domainClass = DomainUtils.getDomainClassByName(domainName, grailsApplication)
        def extraParams = ParamUtils.getExtraQueryParams(params)
        Map pagingParams = PagingUtils.getDefaultPagingParams(params)
        def queries = SearchUtils.getDefaultSearchQueryParams(params)
        def hqxlTxt;
        if (queries.size() > 0) {
            pagingParams.offset = 0;
            if (extraParams.size() > 0) {
                hqxlTxt = 'from ' + domainName + ' where ' + queries.join(' and ') + " and " + extraParams.join(' and ')
            } else {
                hqxlTxt = 'from ' + domainName + ' where ' + queries.join(' and ');
            }
        } else {
            if (extraParams.size() > 0) {
                hqxlTxt = 'from ' + domainName + ' where ' + extraParams.join(' and ')
            } else {
                hqxlTxt = 'from ' + domainName
            };

        }
        println "hqxlTxt    --> " + hqxlTxt
        String filterHql = FilterUtils.getFilterQuery(hqxlTxt);
        def results = domainClass.findAll(filterHql, pagingParams);
        int count = domainClass.findAll(filterHql).size();
        return [result: results, count: count]
    }


}
