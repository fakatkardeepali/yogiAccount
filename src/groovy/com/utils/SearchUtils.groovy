package com.utils

import grails.converters.JSON

/**
 * Created by gvc on 21/01/2015.
 */
class SearchUtils {

    static List getDefaultSearchQueryParams(Map params) {
        def search = JSON.parse(params?.search ?: '[]');
        def searchQueryParams = new ArrayList<String>();
        if (search.size() > 0) {
            search.each { queryParam ->

                if (queryParam) {
                    if (queryParam?.searchText) {
                        if (queryParam?.searchText?.contains("*")) {
                            searchQueryParams.add(queryParam.dataNM.trim() + " like '" + queryParam.searchText.replace("*", "%") + "'");
                        } else if (queryParam?.searchText?.contains("<")) {
                            searchQueryParams.add(queryParam.dataNM.trim() + " < '" + queryParam.searchText.replace("<", "") + "'");
                        } else if (queryParam?.searchText?.contains(">")) {
                            searchQueryParams.add(queryParam.dataNM.trim() + " > '" + queryParam.searchText.replace(">", "") + "'");
                        } else {
                            searchQueryParams.add(queryParam.dataNM.trim() + " like '%" + queryParam.searchText + "%'");
                        }
                    }
                }
            }
        }

        return searchQueryParams;
    }

}
