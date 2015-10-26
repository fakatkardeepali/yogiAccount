package com.sync

import com.common.AccountFlag
import com.master.AccountGroup
import com.master.AccountLedger
import com.system.Company
import com.system.User
import com.transaction.PartyAccount
import com.transaction.Voucher

/**
 * Created by gvc on 25-10-2015.
 */
class ConfigMap {

    static def DESTINATION_DOMAIN_CLASS = "destinationDomainClass"
    static def PROPERTIES = "properties"

    static def config = [
            Party       : [
                    destinationDomainClass: AccountLedger,
                    properties            : [
                            address      : "officeAddress",
                            telephoneNo  : "telephoneNo1",
                            partyId      : "id",
                            cstTin       : "cstNo",
                            salesTaxNo   : "serviceTaxNo",
                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                            underGroup   : [domainClass: AccountGroup, srcPropName: ["partyType.enumDescription": "name", "company": [depends: "QM"]], method: AccountGroup.findByPartyTypeAndCompany]
                    ]

            ], /*end of Party*/

            InvoiceEntry: [
                    destinationDomainClass: Voucher,
                    properties            : [
                            voucherNo    : [$value: ""],
                            date         : "invoiceDate",
                            referenceNo  : "challanNo",
                            narration    : "description",
                            amount       : "grandTotal",
                            amountStatus : [$value: "Dr"],
                            partyName    : [domainClass: AccountLedger, srcPropName: ["customer.name": "name"], queryMap: true],
                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                            partyAccount : [
                                    domainClass      : PartyAccount,
                                    createNewInstance: true,
                                    /*hasMany is list of maps*/
                                    hasMany          : [[
                                                                configMap: [
                                                                        partyName    : [srcPropName: "partyName", dependsParentConfig: true],
                                                                        company      : [srcPropName: "company", dependsParentConfig: true],
                                                                        typeOfRef    : [method: AccountFlag.findByNameClosure, methodParamValue: "New Ref."],
                                                                        billNo       : "invoiceNo",
                                                                        billDate     : "invoiceDate",
                                                                        crDays       : [selfPropName: "partyName.creditDays"],
                                                                        amount       : "grandTotal",
                                                                        amountStatus : [$value: "Dr"],
                                                                        narration    : [$value: ""],
                                                                        remainAmount : "grandTotal",
                                                                        lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true]
                                                                ]
                                                        ]]
                            ],
                            vouchedetails: [hasmany: [
                                    0: ["netamountledgerid", "netamount"],
                                    1: ["packingledgerid", "packingamount"]],
                                            3      : []

                            ]


                    ]

            ]/*end of InvoiceEntry*/
    ]

    static def getDomainConfigByName(String domainName){
        return config[domainName]
    }

    static def getProperties(String domainName) {
        return getDomainConfigByName(domainName)[PROPERTIES]
    }

    static def getDestinationClassInstance(String domainName){
        return (getDomainConfigByName(domainName)[DESTINATION_DOMAIN_CLASS]).newInstance()
    }

    static def getDestinationDomainClassProperties(String domainName) {
        return getDestinationClassInstance(domainName).properties
    }

}
