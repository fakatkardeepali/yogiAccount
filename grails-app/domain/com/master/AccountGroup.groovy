package com.master

import com.system.Company
import com.system.User
import grails.rest.Resource

@Resource(uri = "/accountGroup", formats = ['json', 'xml'])
//class AccountGroup implements Serializable{
class AccountGroup implements Serializable {

    String name
    String alias
    AccountGroup underGroup // parent group


    Boolean isLikeSubgroup = false
    Boolean isNetDrCrBalForReport = false
    Boolean isUsedForCalculations = false
    Boolean isGroupUnderPrimary = false
//    Boolean isUsedForCalculationsInPurchase = false
    Boolean doesIsAffectOnGrossProfit = false
    Boolean isDisplay = false

    Date lastUpdated, dateCreated
    User lastUpdatedBy
    Company company
    Integer uniqueKey = 0
    String property
//    static  hasMany = [subGroups:AccountGroup,accountLedgers:AccountLedger]


    static constraints = {
        name nullable: false
        alias nullable: true
        underGroup nullable: true
        property nullable: true
//        subGroups nullable: true
//        id bindable: true,unique: false
    }

    def beforeInsert() {
        uniqueKey = (last()?.uniqueKey ?: 0) + 1;
//        id = AccountGroup.findAllByCompany(company).size()>0?AccountGroup.findAllByCompany(company).last().id+1:1;
//        property = underGroup?underGroup.property:""
    }

    static def findByPartyTypeAndCompany = { map ->   //add second parameter here separated by comma
        def queryMap=[:]
        if(map.name=="Customer"){
            queryMap["name"] = "Sundry Debtors"
        }else{
            queryMap["name"] = "Sundry Creditors"
        }
        return findWhere(queryMap)
    }

//    static mapping = {
//        id generator: 'assigned',composite: ['id','company']
//    }

}
