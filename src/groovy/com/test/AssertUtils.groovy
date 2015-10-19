package com.test

/**
 * Created by gvc on 15-09-2015.
 */
class AssertUtils {
    static def assertNotNull(object){
        assert object != null
    }

    static def assertNull(object){
        assert object == null
    }

    static def equals(src,dest){
        assert src.equals(dest)
    }

    static Object equalsLong(src,dest) {
        assert src==dest
    }
}
