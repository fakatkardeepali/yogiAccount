package com.utils

/**
 * Created by gvc on 15-09-2015.
 */
class MapUtils {
    static getMapValueByDeepProperty(Map map,String prop){
        def deepKeys = prop.split("\\.")
        return deepKeys.inject (map){val,key->
            return val[key]
        }
    }
}
