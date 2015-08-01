package com.utils

/**
 * Created by pc-3 on 6/2/15.
 */
class DomainUtils {

    static def getDomainClassByName(String domainName, def grailsApplication) {
        // getting Class Object
        Class domainClass = grailsApplication.getDomainClass(domainName).clazz
        return domainClass;
    }

    // getting Class name as String
    static def getDomainNameByController(def controllerClass) {
        String domainName = controllerClass.packageName + "." + controllerClass.name
        return domainName
    }


}
