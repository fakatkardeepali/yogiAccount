package com.common

import com.system.Company
import com.common.*
import grails.transaction.Transactional

// Edited by Akshay Pingle
// Example    method name =  methodType + domainName + "By" + propertyName

@Transactional
class SettingService {

    def getAccountfeatureByCompany(Company company) {

        return AccountFeature.createCriteria().get { eq("company", company) };

    }

    def getStatutoryByCompany(Company company) {
        return StatutoryInfo.findByCompany(company);
    }
}
