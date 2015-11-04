package utils

import com.utils.MapUtils


/**
 * Created by gvc on 20-08-2015.
 */
class JSONUtils {
    static def getMap(String jsonData){
        // transform json string in to map object
        def map = [:]
        return map
    }

    public static void main(String[] args) {
        //println TestData.domainInstanceProperties.partyType.enummDescription
        println(MapUtils.getMapValueByDeepProperty(TestData.domainInstanceProperties,"partyType.enummDescription"))

    }


}
