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
    static def SRC_PROP_NAME="srcPropName"
    static def DOMAIN_CLASS="domainClass"
    static def QUERY_MAP="queryMap"
    static def CREATE_NEW_INSTANCE="createNewInstance"
    static def CONFIG_MAP="configMap"
    static def PROPERTY_VALUE="\$value"
    static def DEPENDS_PARENT_CONFIG="dependsParentConfig"
    static def METHOD="method"
    static def METHOD_PARAM_VALUE="methodParamValue"
    static def HAS_MANY="hasMany"

    private String domainName
    public ConfigMap(String domainName){
        this.domainName = domainName
    }

    static def config = [
            Party          : [
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

            InvoiceEntry   : [
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

            ],/*end of InvoiceEntry*/

            InvoiceEntryLC :
                    [
                            voucherNo   : "invoiceNo",
                            date        : "invoiceDate",
                            referenceNo : "challanNo",
                            amount      : "grandTotal",
                            amountStatus: "Dr",
                    ],


            ProFormaInvoice:
                    [
                            voucherNo   : "invoiceNo",
                            date        : "invoiceDate",
                            referenceNo : "challanNo",
                            amount      : "grandTotal",
                            amountStatus: "Dr",
                    ],

            PurchaseReturn :
                    [
                            voucherNo   : "invoiceNo",
                            date        : "invoiceDate",
                            referenceNo : "challanNo",
                            amount      : "grandTotal",
                            amountStatus: "Dr",
                    ],

            BillPassing    :
                    [
                            voucherNo   : "voucherNo",
                            date        : "billDate",
                            referenceNo : "billNo",
                            amount      : "grandTotal",
                            amountStatus: "Cr",
                    ]


    ]

     def getDomainConfigByName() {
        return config[domainName]
    }

     def getProperties() {
        return getDomainConfigByName()[PROPERTIES]
    }

     def getDestinationClassInstance() {
        return (getDomainConfigByName()[DESTINATION_DOMAIN_CLASS]).newInstance()
    }

     def getDestinationDomainClassProperties() {
        return getDestinationClassInstance().properties
    }
    def getPropertyValue(String propertyName){
        return getProperties()[propertyName]
    }
    def getSourcePropertyValue(String propertyName){
        return getPropertyValue(propertyName)[SRC_PROP_NAME]
    }
    def getDomainClass(String propertyName){
        return getPropertyValue(propertyName)[DOMAIN_CLASS]
    }

}
