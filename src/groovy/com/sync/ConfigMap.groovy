package com.sync

import com.common.AccountFlag
import com.master.AccountGroup
import com.master.AccountLedger
import com.master.VoucherType
import com.system.Company
import com.system.User
import com.transaction.PartyAccount
import com.transaction.Voucher
import com.transaction.VoucherBillDetails
import org.apache.commons.logging.LogFactory

/**
 * Created by gvc on 25-10-2015.
 */
class ConfigMap {

    private static final log = LogFactory.getLog(this)

    static def DESTINATION_DOMAIN_CLASS = "destinationDomainClass"
    static def PROPERTIES = "properties"
    static def SRC_PROP_NAME = "srcPropName"
    static def DOMAIN_CLASS = "domainClass"
    static def QUERY_MAP = "queryMap"
    static def CREATE_NEW_INSTANCE = "createNewInstance"
    static def CONFIG_MAP = "configMap"
    static def PROPERTY_VALUE = "\$value"
    static def DEPENDS_PARENT_CONFIG = "dependsParentConfig"
    static def DEPENDS_PARENT_DOMAIN_INSTANCE = "dependsParentDomainInstance"
    static def METHOD = "method"
    static def METHOD_PARAM_VALUE = "methodParamValue"
    static def HAS_MANY = "hasMany"
    static def PARENT_PROP_NAME = "parentPropName"
    static def SUB_PROPERTY_NAME = "subPropertyName"
    static String DEPENDS_SELF = "dependsSelf"
    static String PROPERTY_NAME = "propertyName"
    static String DATE_FORMAT = "dateFormat"
    static def AFTER_INSERT_METHOD = "\$method"
    static def INSERT_CONDITION = "insertCondition"
    static def BUSINESS_KEY = "businessKey"

    static String AFTER_INSERT = "\$afterInsert"
//ToDo remove propertyName
    private String propertyName
    private Map propertyConfig

    public ConfigMap(String propertyName, Map config = null) {
        this.propertyName = propertyName
        if (config) {
            this.propertyConfig = config
        } else {
            this.propertyConfig = this.config[propertyName]
        }

    }


    private Map config = [
            Party          : [
                    domainClass: AccountLedger,
                    businessKey:[partyId:"id"],
                    properties : [
                            address      : "officeAddress",
                            telephoneNo  : "telephoneNo1",
                            partyId      : "id",
                            cstNo        : "cstTin",
                            salesTaxNo   : "serviceTaxNo",
                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                            underGroup   : [domainClass: AccountGroup, srcPropName: ["partyType.enumDescription": "name", "company": [depends: "Self"]], method: AccountGroup.findByPartyTypeAndCompany]
                    ]

            ], /*end of Party*/

            InvoiceEntry   : [
                    domainClass: Voucher,
                    properties : [
                            date              : [domainClass: Date, srcPropName: "invoiceDate", dateFormat: "yyyy-MM-dd"],
                            referenceNo       : "challanNo",
                            narration         : "description",
                            amount            : "grandTotal",
                            amountStatus      : [$value: "Dr"],
                            partyName         : [domainClass: AccountLedger, srcPropName: ["customer.name": "name"], queryMap: true],
                            company           : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                            lastUpdatedBy     : [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                            voucherType       : [domainClass: VoucherType, srcPropName: ["company": [depends: "Self"], $value: ["name": "Sale"]], queryMap: true],
                            voucherNo         : [domainClass: Voucher, method: "getVoucherNumber", srcPropName: [0: [propertyName: "voucherType", subPropertyName: "id", dependsSelf: true], 1: [propertyName: "date", dependsSelf: true], 2: [$value: null]]],
                            voucherBillDetails: [
                                    createNewInstance: true,
                                    hasMany          : true,
                                    properties       : [
                                            typeRef      : [$value: 3],
                                            billNo       : [dependsParentConfig: true, srcPropName: "referenceNo"],
                                            billDate     : [dependsParentConfig: true, srcPropName: "date"],
                                            crDays       : [parentPropName: "partyName", subPropertyName: "creditDays"],   //getDomainSubproperty  domain helpers
                                            amount       : [dependsParentConfig: true, srcPropName: "amount"],
                                            amountStatus : [$value: "Dr"],
                                            narration    : [$value: ""],
                                    ]

                            ],
                            $afterInsert      : [
                                    [
                                            properties     : [
                                                    ledger       : "netAmountLedgerId",
                                                    debit        : "netAmount",
                                                    narration    : [$value: ""],
                                            ],

                                    ],
                                    [
                                            properties     : [
                                                    ledger       : "packingLedgerId",
                                                    debit        : "packingAmount",
                                                    narration    : [$value: ""],
                                            ],

                                    ],
                                    [
                                            properties     : [
                                                    ledger       : "freightLedgerId",
                                                    debit        : "freightAmount",
                                                    narration    : [$value: ""],
                                            ],

                                    ],
                                    [
                                            properties     : [
                                                    ledger       : "insuranceLedgerId",
                                                    debit        : "insuranceAmount",
                                                    narration    : [$value: ""],
                                            ],

                                    ],
                                    [
                                            properties     : [
                                                    ledger       : "tax.exciseId",
                                                    debit        : "cenvatAmount",
                                                    narration    : [$value: ""],
                                            ],

                                    ],
                                    [
                                            properties     : [
                                                    ledger       : "tax.serviceTaxId",
                                                    debit        : "serviceTaxAmount",
                                                    narration    : [$value: ""],
                                            ]

                                    ],
                                    [
                                            properties     : [
                                                    ledger       : "tax.edCessId",
                                                    debit        : "edCessAmount",
                                                    narration    : [$value: ""],
                                            ]

                                    ],
                                    [
                                            properties     : [
                                                    ledger       : "tax.hsedCessId",
                                                    debit        : "shEdCessAmount",
                                                    narration    : [$value: ""],
                                            ],

                                    ],
                                    [
                                            properties     : [
                                                    ledger       : "tax.vatId",
                                                    debit        : "saleTaxAmount",
                                                    narration    : [$value: ""],
                                            ],

                                    ],
                                    [
                                            properties     : [
                                                    ledger       : "tax.cstId",
                                                    debit        : "cstAmount",
                                                    narration    : [$value: ""],
                                            ],

                                    ],
                                    [
                                            properties     : [
                                                    ledger       : "tax.tdsId",
                                                    debit        : "tdsAmount",
                                                    narration    : [$value: ""],
                                            ],

                                    ],
                                    [
                                            properties     : [
                                                    ledger       : "tax.lbtId",
                                                    debit        : "lbtAmount",
                                                    narration    : [$value: ""],
                                            ],

                                    ],
                                    [
                                            properties     : [
                                                    ledger       : "tax.othersId",
                                                    debit        : "othersAmount",
                                                    narration    : [$value: ""],
                                            ],

                                    ]

                            ]// end of afterinsert list

                    ]

            ],/*end of InvoiceEntry*/

            // same voucher details instance for packingLedgerId,packingAmount
//                                                               freightLedgerId,freightAmount
//                                                               insuranceLedgerId,insuranceAmount
//                            exciseId nullable: true
//                            serviceTaxId nullable: true
//                            edCessId nullable: true
//                            hsedCessId nullable: true
//                            vatId nullable: true
//                            cstId nullable: true
//                            tcsId nullable: true
//                            tdsId nullable: true
//                            lbtId nullable: true
//                            othersId nullable: true


            BillPassing    :
                    [
                            domainClass: Voucher,
                            properties : [
                                    voucherNo         : [domainClass: Voucher, method: "getVoucherNumber", srcPropName: [0: [propertyName: "voucherType", subPropertyName: "id", dependsSelf: true], 1: [propertyName: "date", dependsSelf: true], 2: [$value: null]]],
                                    date              : "billDate",
                                    referenceNo       : "billNo",
                                    narration         : [$value: ""],
                                    amount            : "grandTotal",
                                    amountStatus      : [$value: "Dr"],
                                    partyName         : [domainClass: AccountLedger, srcPropName: ["supplierName.name": "name"], queryMap: true],
                                    company           : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                    lastUpdatedBy     : [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                    voucherType       : [domainClass: VoucherType, srcPropName: ["company": [depends: "Self"], $value: ["name": "Sales"]], queryMap: true],
                                    partyAccount      : [
                                            domainClass      : PartyAccount,
                                            createNewInstance: true,
                                            hasMany          : true,
                                            properties       : [
                                                    partyName    : [dependsParentConfig: true],
                                                    company      : [dependsParentConfig: true],
                                                    typeOfRef    : [method: AccountFlag.findByNameClosure, methodParamValue: "New Ref."],
                                                    billNo       : [dependsParentConfig: true, srcPropName: "referenceNo"],
                                                    billDate     : [dependsParentConfig: true, srcPropName: "date"],
                                                    crDays       : [parentPropName: "partyName", subPropertyName: "creditDays"],   //getDomainSUbproperty  domain helpers
                                                    amount       : [dependsParentConfig: true, srcPropName: "amount"],
                                                    amountStatus : [$value: "Cr"],
                                                    narration    : [$value: ""],
                                                    remainAmount : [dependsParentConfig: true, srcPropName: "amount"],
                                                    lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true]
                                            ]
                                    ],
                                    voucherBillDetails: [
                                            domainClass      : VoucherBillDetails,
                                            createNewInstance: true,
                                            hasMany          : true,
                                            properties       : [
                                                    partyName    : [dependsParentConfig: true],
                                                    typeOfRef    : [method: AccountFlag.findByNameClosure, methodParamValue: "New Ref."],
                                                    billNo       : [dependsParentConfig: true, srcPropName: "referenceNo"],
                                                    billDate     : [dependsParentConfig: true, srcPropName: "date"],
                                                    crDays       : [parentPropName: "partyName", subPropertyName: "creditDays"],   //getDomainSubproperty  domain helpers
                                                    amount       : [dependsParentConfig: true, srcPropName: "amount"],
                                                    amountStatus : [$value: "Dr"],
                                                    narration    : [$value: ""],
                                                    lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true]
                                            ]

                                    ],
                                    $afterInsert      : [
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["netAmountLedgerId": "id"], queryMap: true],
                                                            amount       : "netAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate"
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["packingLedgerId": "id"], queryMap: true],
                                                            amount       : "packingAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate",
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["freightLedgerId": "id"], queryMap: true],
                                                            amount       : "freightAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate",
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["insuranceLedgerId": "id"], queryMap: true],
                                                            amount       : "insuranceAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate",
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["exciseId": "id"], queryMap: true],
                                                            amount       : "exciseAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate"
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["serviceTaxId": "id"], queryMap: true],
                                                            amount       : "serviceTaxAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate"
                                                    ]

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["edCessId": "id"], queryMap: true],
                                                            amount       : "edCessAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate"
                                                    ]

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["hsedCessId": "id"], queryMap: true],
                                                            amount       : "shEdCessAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate",
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["vatId": "id"], queryMap: true],
                                                            amount       : "vatAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate"
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["cstId": "id"], queryMap: true],
                                                            amount       : "cstAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate",
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["tcsId": "id"], queryMap: true],
                                                            amount       : "tcsAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate"
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["tdsId": "id"], queryMap: true],
                                                            amount       : "tdsAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate"
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["netAmountLedgerId": "id"], queryMap: true],
                                                            amount       : "lbtAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate"
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["othersId": "id"], queryMap: true],
                                                            amount       : "othersAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate"
                                                    ],

                                            ]

                                    ]

                            ]
                    ],


            InvoiceEntryLC :
                    [
                            domainClass: Voucher,
                            properties : [
                                    voucherNo         : [domainClass: Voucher, method: "getVoucherNumber", srcPropName: [0: [propertyName: "voucherType", subPropertyName: "id", dependsSelf: true], 1: [propertyName: "date", dependsSelf: true], 2: [$value: null]]],
                                    date              : "invoiceDate",
                                    referenceNo       : "invoiceNo",
                                    narration         : [$value: ""],
                                    amount            : "grandTotal",
                                    amountStatus      : [$value: "Dr"],
                                    partyName         : [domainClass: AccountLedger, srcPropName: ["customerName.name": "name"], queryMap: true],
                                    company           : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                    lastUpdatedBy     : [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                    voucherType       : [domainClass: VoucherType, srcPropName: ["company": [depends: "Self"], $value: ["name": "Sales"]], queryMap: true],
                                    partyAccount      : [
                                            domainClass      : PartyAccount,
                                            createNewInstance: true,
                                            hasMany          : true,
                                            properties       : [
                                                    partyName    : [dependsParentConfig: true],
                                                    company      : [dependsParentConfig: true],
                                                    typeOfRef    : [method: AccountFlag.findByNameClosure, methodParamValue: "New Ref."],
                                                    billNo       : [dependsParentConfig: true, srcPropName: "referenceNo"],
                                                    billDate     : [dependsParentConfig: true, srcPropName: "date"],
                                                    crDays       : [parentPropName: "partyName", subPropertyName: "creditDays"],   //getDomainSubproperty  domain helpers
                                                    amount       : [dependsParentConfig: true, srcPropName: "amount"],
                                                    amountStatus : [$value: "Dr"],
                                                    narration    : [$value: ""],
                                                    remainAmount : [dependsParentConfig: true, srcPropName: "amount"],
                                                    lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true]
                                            ]
                                    ],
                                    voucherBillDetails: [
                                            domainClass      : VoucherBillDetails,
                                            createNewInstance: true,
                                            hasMany          : true,
                                            properties       : [
                                                    partyName    : [dependsParentConfig: true],
                                                    typeOfRef    : [method: AccountFlag.findByNameClosure, methodParamValue: "New Ref."],
                                                    billNo       : [dependsParentConfig: true, srcPropName: "referenceNo"],
                                                    billDate     : [dependsParentConfig: true, srcPropName: "date"],
                                                    crDays       : [parentPropName: "partyName", subPropertyName: "creditDays"],   //getDomainSubproperty  domain helpers
                                                    amount       : [dependsParentConfig: true, srcPropName: "amount"],
                                                    amountStatus : [$value: "Dr"],
                                                    narration    : [$value: ""],
                                                    lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true]
                                            ]

                                    ],
                                    $afterInsert      : [
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["netAmountLedgerId": "id"], queryMap: true],
                                                            amount       : "netAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate"
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["packingLedgerId": "id"], queryMap: true],
                                                            amount       : "packingAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate",
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["freightLedgerId": "id"], queryMap: true],
                                                            amount       : "freightAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate",
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["insuranceLedgerId": "id"], queryMap: true],
                                                            amount       : "insuranceAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate",
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["exciseId": "id"], queryMap: true],
                                                            amount       : "exciseAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate"
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["serviceTaxId": "id"], queryMap: true],
                                                            amount       : "serviceTaxAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate"
                                                    ]

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["edCessId": "id"], queryMap: true],
                                                            amount       : "edCessAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate"
                                                    ]

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["hsedCessId": "id"], queryMap: true],
                                                            amount       : "shEdCessAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate",
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["vatId": "id"], queryMap: true],
                                                            amount       : "vatAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate"
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["cstId": "id"], queryMap: true],
                                                            amount       : "cstAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate",
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["tcsId": "id"], queryMap: true],
                                                            amount       : "tcsAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate"
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["tdsId": "id"], queryMap: true],
                                                            amount       : "tdsAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate"
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["netAmountLedgerId": "id"], queryMap: true],
                                                            amount       : "lbtAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate"
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["othersId": "id"], queryMap: true],
                                                            amount       : "othersAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate"
                                                    ],

                                            ]

                                    ]
                            ]

                    ],


            ProFormaInvoice:
                    [
                            domainClass: Voucher,
                            properties : [
                                    voucherNo         : [domainClass: Voucher, method: "getVoucherNumber", srcPropName: [0: [propertyName: "voucherType", subPropertyName: "id", dependsSelf: true], 1: [propertyName: "date", dependsSelf: true], 2: [$value: null]]],
                                    date              : "invoiceDate",
                                    referenceNo       : "invoiceNo",
                                    narration         : [$value: ""],
                                    amount            : "grandTotal",
                                    amountStatus      : [$value: "Dr"],
                                    partyName         : [domainClass: AccountLedger, srcPropName: ["customer.name": "name"], queryMap: true],
                                    company           : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                    lastUpdatedBy     : [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                    voucherType       : [domainClass: VoucherType, srcPropName: ["company": [depends: "Self"], $value: ["name": "Sales"]], queryMap: true],
                                    partyAccount      : [
                                            domainClass      : PartyAccount,
                                            createNewInstance: true,
                                            hasMany          : true,
                                            properties       : [
                                                    partyName    : [dependsParentConfig: true],
                                                    company      : [dependsParentConfig: true],
                                                    typeOfRef    : [method: AccountFlag.findByNameClosure, methodParamValue: "New Ref."],
                                                    billNo       : [dependsParentConfig: true, srcPropName: "referenceNo"],
                                                    billDate     : [dependsParentConfig: true, srcPropName: "date"],
                                                    crDays       : [parentPropName: "partyName", subPropertyName: "creditDays"],   //getDomainSubproperty  domain helpers
                                                    amount       : [dependsParentConfig: true, srcPropName: "amount"],
                                                    amountStatus : [$value: "Dr"],
                                                    narration    : [$value: ""],
                                                    remainAmount : [dependsParentConfig: true, srcPropName: "amount"],
                                                    lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true]
                                            ]
                                    ],
                                    voucherBillDetails: [
                                            domainClass      : VoucherBillDetails,
                                            createNewInstance: true,
                                            hasMany          : true,
                                            properties       : [
                                                    partyName    : [dependsParentConfig: true],
                                                    typeOfRef    : [method: AccountFlag.findByNameClosure, methodParamValue: "New Ref."],
                                                    billNo       : [dependsParentConfig: true, srcPropName: "referenceNo"],
                                                    billDate     : [dependsParentConfig: true, srcPropName: "date"],
                                                    crDays       : [parentPropName: "partyName", subPropertyName: "creditDays"],   //getDomainSubproperty  domain helpers
                                                    amount       : [dependsParentConfig: true, srcPropName: "amount"],
                                                    amountStatus : [$value: "Dr"],
                                                    narration    : [$value: ""],
                                                    lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true]
                                            ]

                                    ],

                                    $afterInsert      : [
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["netAmountLedgerId": "id"], queryMap: true],
                                                            amount       : "netAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate"
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["packingLedgerId": "id"], queryMap: true],
                                                            amount       : "packingAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate",
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["freightLedgerId": "id"], queryMap: true],
                                                            amount       : "freightAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate",
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["insuranceLedgerId": "id"], queryMap: true],
                                                            amount       : "insuranceAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate",
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["exciseId": "id"], queryMap: true],
                                                            amount       : "exciseAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate"
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["serviceTaxId": "id"], queryMap: true],
                                                            amount       : "serviceTaxAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate"
                                                    ]

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["edCessId": "id"], queryMap: true],
                                                            amount       : "edCessAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate"
                                                    ]

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["hsedCessId": "id"], queryMap: true],
                                                            amount       : "shEdCessAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate",
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["vatId": "id"], queryMap: true],
                                                            amount       : "vatAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate"
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["cstId": "id"], queryMap: true],
                                                            amount       : "cstAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate",
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["tcsId": "id"], queryMap: true],
                                                            amount       : "tcsAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate"
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["tdsId": "id"], queryMap: true],
                                                            amount       : "tdsAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate"
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["netAmountLedgerId": "id"], queryMap: true],
                                                            amount       : "lbtAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate"
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["othersId": "id"], queryMap: true],
                                                            amount       : "othersAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate"
                                                    ],

                                            ]

                                    ]
                            ]
                    ],

            PurchaseReturn :
                    [
                            domainClass: Voucher,
                            properties : [
                                    voucherNo         : [domainClass: Voucher, method: "getVoucherNumber", srcPropName: [0: [propertyName: "voucherType", subPropertyName: "id", dependsSelf: true], 1: [propertyName: "date", dependsSelf: true], 2: [$value: null]]],
                                    date              : "invoiceDate",
                                    referenceNo       : "invoiceNo",
                                    narration         : [$value: ""],
                                    amount            : "grandTotal",
                                    amountStatus      : [$value: "Dr"],
                                    partyName         : [domainClass: AccountLedger, srcPropName: ["customer.name": "name"], queryMap: true],
                                    company           : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                    lastUpdatedBy     : [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                    voucherType       : [domainClass: VoucherType, srcPropName: ["company": [depends: "Self"], $value: ["name": "Sales"]], queryMap: true],
                                    partyAccount      : [
                                            domainClass      : PartyAccount,
                                            createNewInstance: true,
                                            hasMany          : true,
                                            properties       : [
                                                    partyName    : [dependsParentConfig: true],
                                                    company      : [dependsParentConfig: true],
                                                    typeOfRef    : [method: AccountFlag.findByNameClosure, methodParamValue: "New Ref."],
                                                    billNo       : [dependsParentConfig: true, srcPropName: "referenceNo"],
                                                    billDate     : [dependsParentConfig: true, srcPropName: "date"],
                                                    crDays       : [parentPropName: "partyName", subPropertyName: "creditDays"],   //getDomainSubproperty  domain helpers
                                                    amount       : [dependsParentConfig: true, srcPropName: "amount"],
                                                    amountStatus : [$value: "Dr"],
                                                    narration    : [$value: ""],
                                                    remainAmount : [dependsParentConfig: true, srcPropName: "amount"],
                                                    lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true]
                                            ]
                                    ],
                                    voucherBillDetails: [
                                            domainClass      : VoucherBillDetails,
                                            createNewInstance: true,
                                            hasMany          : true,
                                            properties       : [
                                                    partyName    : [dependsParentConfig: true],
                                                    typeOfRef    : [method: AccountFlag.findByNameClosure, methodParamValue: "New Ref."],
                                                    billNo       : [dependsParentConfig: true, srcPropName: "referenceNo"],
                                                    billDate     : [dependsParentConfig: true, srcPropName: "date"],
                                                    crDays       : [parentPropName: "partyName", subPropertyName: "creditDays"],   //getDomainSubproperty  domain helpers
                                                    amount       : [dependsParentConfig: true, srcPropName: "amount"],
                                                    amountStatus : [$value: "Dr"],
                                                    narration    : [$value: ""],
                                                    lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true]
                                            ]

                                    ],

                                    $afterInsert      : [
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["netAmountLedgerId": "id"], queryMap: true],
                                                            amount       : "netAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate"
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["packingLedgerId": "id"], queryMap: true],
                                                            amount       : "packingAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate",
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["freightLedgerId": "id"], queryMap: true],
                                                            amount       : "freightAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate",
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["insuranceLedgerId": "id"], queryMap: true],
                                                            amount       : "insuranceAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate",
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["exciseId": "id"], queryMap: true],
                                                            amount       : "exciseAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate"
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["serviceTaxId": "id"], queryMap: true],
                                                            amount       : "serviceTaxAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate"
                                                    ]

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["edCessId": "id"], queryMap: true],
                                                            amount       : "edCessAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate"
                                                    ]

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["hsedCessId": "id"], queryMap: true],
                                                            amount       : "shEdCessAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate",
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["vatId": "id"], queryMap: true],
                                                            amount       : "vatAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate"
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["cstId": "id"], queryMap: true],
                                                            amount       : "cstAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate",
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["tcsId": "id"], queryMap: true],
                                                            amount       : "tcsAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate"
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["tdsId": "id"], queryMap: true],
                                                            amount       : "tdsAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate"
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["netAmountLedgerId": "id"], queryMap: true],
                                                            amount       : "lbtAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate"
                                                    ],

                                            ],
                                            [
                                                    domainClass: Voucher,
                                                    properties : [
                                                            partyName    : [domainClass: AccountLedger, srcPropName: ["othersId": "id"], queryMap: true],
                                                            amount       : "othersAmount",
                                                            rate         : [$value: 0],
                                                            amountStatus : [$value: "Cr"],
                                                            narration    : [$value: ""],
                                                            lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId": "username"], queryMap: true],
                                                            company      : [domainClass: Company, srcPropName: ["company.regNo": "registrationNo"], queryMap: true],
                                                            voucher      : [dependsParentDomainInstance: true],
                                                            date         : "invoiceDate"
                                                    ],

                                            ]

                                    ]
                            ]
                    ]


    ]

    def getConfig() {
        return propertyConfig
    }

    def getProperties() {
        return getConfig()[PROPERTIES]
    }

    def getDomainClass() {
        return getConfig()[DOMAIN_CLASS]
    }

    def getDomainClassInstance() {
        return getDomainClass().newInstance()
    }

    def getDomainClassProperties() {
        return getDomainClassInstance().properties
    }

    def getPropertyValue(String propertyName) {
        return getProperties()[propertyName]
    }

    def getPropertyDomainClass(String propertyName) {
        return getPropertyValue(propertyName)[DOMAIN_CLASS]
    }

    def getPropertySourceName(String propertyName) {
        return getPropertyValue(propertyName)[SRC_PROP_NAME]
    }

    def getPropertyMethod(String propertyName) {
        return getPropertyValue(propertyName)[METHOD]
    }

    def getPropertyMethodParameter(String propertyName) {
        return getPropertyValue(propertyName)[METHOD_PARAM_VALUE]
    }

    def getConfigPropertyList() {
        return getProperties().keySet()
    }

    def getBusinessKey() {
        return getConfig()[BUSINESS_KEY]
    }

}
