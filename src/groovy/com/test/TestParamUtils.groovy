package com.test

import com.utils.FilterUtils
import com.utils.ParamUtils

/**
 * Created by pc-3 on 6/2/15.
 */
class TestParamUtils {
    public static void main(String[] args) {
        String s = "select * from account where id=1 and name='alias' and order by id"
        def data = FilterUtils.getFilterQuery(s)
        print "hql -->  " + data;
    }
}
