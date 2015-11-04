package com.utils

/**
 * Created by gvc on 04-11-2015.
 */
class ClassUtils {
    static def getMethodByName(Class klass,String methodName){
        return klass.methods.find{it.name.equals(methodName)}
    }
}
