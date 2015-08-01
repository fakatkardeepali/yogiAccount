package com.system

import com.common.AccountFeature
import com.common.AccountFlag
import com.common.StatutoryInfo
import com.common.TaxSetting
import com.common.VoucherStatus
import com.master.AccountGroup
import com.master.AccountLedger
import com.master.InterestParameters
import com.master.Parameters
import com.master.VoucherType
import com.transaction.PartyAccount
import com.transaction.Voucher
import com.transaction.VoucherDetails
import com.utils.GridUtils
import grails.converters.JSON
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.json.JSONObject

import java.lang.reflect.Array

// Edited by Akshay Pingle
// Example    method name =  methodType + domainName + "By" + propertyName

@Transactional
class SystemService {

    def grailsApplication

    def getUserById(Long id) {
        return User.findById(id);
    }

    def getUserByUsernameAndPassword(String username, String password) {
        return User.findByUsernameAndPassword(username, password);
    }

    def findAllCompanyByOrganizationId(Long id) {
        return Company.findAllByOrganization(getOrganizationData(id));
    }

    def getCompanyById(Long id) {
//        return Company.findById(id);
        def company = Company.findById(id);
        if (company) {
            def data = [id                   : company.id,
                        organization  : company?.organization?.id ?: "",
                        name                 : company?.name ?: "",
                        city                 : company?.city ?: "",
                        address              : company?.address ?: "",
                        pincode              : company?.pincode ?: "",
                        country              : company?.countryId ?: "",
                        state                : company?.state?.id ?: "",
                        telephoneNo          : company?.telephoneNo ?: "",
                        email                : company?.email ?: "",
                        isCompanySplit: company.isCompanySplit,
                        financialFrom        : company?.financialFrom?.format("yyyy-MM-dd") ?: "",
                        booksBeginigFrom     : company?.booksBeginigFrom?.format("yyyy-MM-dd") ?: "",
                        currencySymbol       : company?.currencySymbol ?: "",
                        registrationNo       : company?.registrationNo ?: "",
                        numberForDecimalPlace: company?.numberForDecimalPlace ?: 0,
                        isSymbolsSuffix      : company?.isSymbolsSuffix ?: false,
                        vatNo                : company?.vatNo?:"",
                        taxNo                : company?.taxNo?:"",
                        cstNo                : company?.cstNo?:"",
                        eccNo                : company?.eccNo?:""
                    ]
            return data;
        } else {
            return null;
        }
    }

    def getCompanySessionById(Long id) {
        return Company.findById(id);
    }

    //bhupendra 05-02-2015
    //getting country data form database
    def getCountryByList() {
        return Country.list().sort { it.name };
    }

    def getCountryById(Long id) {
        return Country.findById(id);
    }

    def getStateAllByCountry(id) {
        return State.findAllByCountry(getCountryById(id)).sort { it.name };
    }


    def getGridData(def controllerClass, Map params) {
        return GridUtils.getGridData(controllerClass, grailsApplication, params);
    }

    def getOrganizationData(Long id) {
        return Organization.findById(id);
    }

    def getOrganizationList() {
        return Organization.list();
    }

    def getUserRoleByUser(Long id) {
//        return UserRole.get(id);
        return UserRole.findByUser(getUserById(id));
    }

    def findAllScreenByControllerAndParentScreen() {
        return Screen.findAllByControllerAndParentScreen(null, null).sort { it.sortList }
    }

    def findAllScreenByParentScreen(Screen screen) {
        return Screen.findAllByParentScreen(screen).sort { it.sortList }
    }

    def findRoleChildByRoleAndParentScreen(Long roleId, Long screenId) {
        RoleChild.findByRoleAndParentScreen(getRoleById(roleId), getScreenById(screenId));
    }

    def getRoleById(Long id) {
        return Role.findById(id)
    }

    def getScreenById(Long id) {
        return Screen.findById(id)
    }

    def findAllScreenByParentScreenWithSort(Long id) {
        Screen.findAllByParentScreen(getScreenById(id)).sort { it.sortList };
    }

    def findRoleChildByRoleAndScreen(Long roleId, Long screenId) {
        return RoleChild.findByRoleAndScreen(getRoleById(roleId), getScreenById(screenId))
    }

    def getCompanyObjectById(Long id) {
        return Company.get(id);

    }

    def findAllUserByCompany(Long id, def user) {
        def userObj = getUserById(user.user.id)
        if (userObj.isAdmin) {
            return User.list();
        } else {
            return User.findAllByCompany(getCompanyObjectById(id));
        }
    }

    def findAllRoleByCompany(Long id) {
        return Role.findAllByCompany(getCompanyObjectById(id));
    }

    def getUserRoleById(Long id) {
        return UserRole.get(id)
    }


    def findUserById(Long id) {
        def data = User.findById(id);
        if (data) {
            def userData = [id                : data?.id ?: "",
                            username          : data?.username ?: "",
                            password          : data?.password ?: "",
                            organization      : data?.organization?.id ?: "",
                            company           : data?.company?.id ?: "",
                            isAdmin           : data?.isAdmin ?: false,
                            enable            : data?.enable ?: false,
                            isClient          : data?.isClient ?: false,
                            useMultipleCompany: data?.useMultipleCompany ?: false]

            return userData;
        }
    }

    def getAccountSettingByCompany(Company company) {
        return AccountFeature.findByCompany(company);
    }

    def getStatutorySettingByCompany(Company company) {
        return StatutoryInfo.findByCompany(company);
    }

    def getAllOptions() {
        return [allState: State.list(), tod: AccountFlag.findAllByRemark('tod'), too: AccountFlag.findAllByRemark('too'), loc: AccountFlag.findAllByRemark('loc')]
    }

    def getVoucherStatusByProperty(String property) {
        return VoucherStatus.findByVoucherProperty(property);
    }

    def getAllState() {
        return State.list();
    }

    public Integer createCompanyFrom(String from, String to, Long userId, Long mainCompanyId) {
        try {
            // previous Company
            Company mainCompany = getCompanyObjectById(mainCompanyId)

            // new Company
            Company comp = new Company(organization: mainCompany.organization,
                    name: mainCompany.name + " - (from " + from + ")",
                    financialFrom: Date.parse("yyyy-MM-dd", from),
                    booksBeginigFrom: Date.parse("yyyy-MM-dd", from),
                    dateCreated: new Date(), lastUpdated: new Date(),
                    lastUpdatedBy: User.findById(userId),
                    isCompanySplit: false, company: mainCompany)
            comp.save();

            def accountFeatureInstance = AccountFeature.findByCompany(mainCompany)

            AccountFeature ac = new AccountFeature()
            ac.incomeExpenseStatementInsteadOfPL = accountFeatureInstance.incomeExpenseStatementInsteadOfPL
            ac.allowMultipleCurrency = accountFeatureInstance.allowMultipleCurrency
            ac.useDebitCreditNotes = accountFeatureInstance.useDebitCreditNotes
            ac.maintainBillWiseDetails = accountFeatureInstance.maintainBillWiseDetails
            ac.enableChequePrinting = accountFeatureInstance.enableChequePrinting
            ac.forNontradingAccountAlso = accountFeatureInstance.forNontradingAccountAlso
            ac.usedAdvanceParameters = accountFeatureInstance.usedAdvanceParameters
            ac.activeIntersetCalculation = accountFeatureInstance.activeIntersetCalculation
            ac.useNameInReport = accountFeatureInstance.useNameInReport
            ac.useAliasNameInReport = accountFeatureInstance.useAliasNameInReport
            ac.useAliasNameAlongWithNameinForms = accountFeatureInstance.useAliasNameAlongWithNameinForms
            ac.dateFormat = accountFeatureInstance.dateFormat
            ac.decimalCharacterToUseInAmount = accountFeatureInstance.decimalCharacterToUseInAmount
            ac.usePerfixSymbolForDebitAmount = accountFeatureInstance.usePerfixSymbolForDebitAmount
            ac.usePostfixSymbolForDebitAmount = accountFeatureInstance.usePostfixSymbolForDebitAmount
            ac.usePerfixSymbolFoCreditAmount = accountFeatureInstance.usePerfixSymbolFoCreditAmount
            ac.usePostfixSymbolForCreditAmount = accountFeatureInstance.usePostfixSymbolForCreditAmount
            ac.useAddressForLedgerAccounts = accountFeatureInstance.useAddressForLedgerAccounts
            ac.useContactsDetailsForLedgerAccounts = accountFeatureInstance.useContactsDetailsForLedgerAccounts
            ac.useSingleEntryModeForPurchase = accountFeatureInstance.useSingleEntryModeForPurchase
            ac.useSingleEntryModeForSale = accountFeatureInstance.useSingleEntryModeForSale
            ac.useSingleEntryModeForPayment = accountFeatureInstance.useSingleEntryModeForPayment
            ac.useSingleEntryModeForRecepit = accountFeatureInstance.useSingleEntryModeForRecepit
            ac.useSingleEntryModeForContra = accountFeatureInstance.useSingleEntryModeForContra
            ac.useSingleEntryModeForDebit = accountFeatureInstance.useSingleEntryModeForDebit
            ac.useSingleEntryModeForCredit = accountFeatureInstance.useSingleEntryModeForCredit
            ac.usePaymentRecepitAsContra = accountFeatureInstance.usePaymentRecepitAsContra
            ac.useCrDrInsteadOfToBy = accountFeatureInstance.useCrDrInsteadOfToBy
            ac.warnOnNegativeCashBalance = accountFeatureInstance.warnOnNegativeCashBalance
            ac.allowCashAccountsInJournals = accountFeatureInstance.allowCashAccountsInJournals
            ac.showbillwiseDetails = accountFeatureInstance.showbillwiseDetails
            ac.allowIncomeAccountsInSaleVouchers = accountFeatureInstance.allowIncomeAccountsInSaleVouchers
            ac.showLedgerBalance = accountFeatureInstance.showLedgerBalance
            ac.showBalanceAsOnVoucherDate = accountFeatureInstance.showBalanceAsOnVoucherDate
            ac.allowOthersDetails = accountFeatureInstance.allowOthersDetails
            ac.allowExpensesFixedAssetsInPurchaseAccounts = accountFeatureInstance.allowExpensesFixedAssetsInPurchaseAccounts
            ac.company = comp
            ac.save()



            def StatutoryInfo statutoryInstance = StatutoryInfo.findByCompany(mainCompany)
            def statutory = new StatutoryInfo()
            statutory.company = comp;
            statutory.allowSelectOfVatTaxDuringEntry = statutoryInstance.allowSelectOfVatTaxDuringEntry
            statutory.activateE1OrE2TransactionVat = statutoryInstance.activateE1OrE2TransactionVat
            statutory.allowAlteraionOfTdsRateForLowerDeductions = statutoryInstance.allowAlteraionOfTdsRateForLowerDeductions
            statutory.allowAlteraionOfTdsNatureOfPaymentInExpense = statutoryInstance.allowAlteraionOfTdsNatureOfPaymentInExpense
            statutory.enableValueAddedTax = statutoryInstance.enableValueAddedTax
            statutory.vatAlterDetails = statutoryInstance.vatAlterDetails
            statutory.state = statutoryInstance.state
            statutory.typeOfDealer = statutoryInstance.typeOfDealer
            statutory.regularVatApplicableFrom = statutoryInstance.regularVatApplicableFrom
            statutory.tdsEnableTaxDeductedAtSource = statutoryInstance.tdsEnableTaxDeductedAtSource
            statutory.tdsAlterDetails = statutoryInstance.tdsAlterDetails
            statutory.tdsTaxAssessmentNo = statutoryInstance.tdsTaxAssessmentNo
            statutory.tdsIncomeTaxCircleward = statutoryInstance.tdsIncomeTaxCircleward
            statutory.tdsDeductorCollectorType = statutoryInstance.tdsDeductorCollectorType
            statutory.tdsResponsiblePerson = statutoryInstance.tdsResponsiblePerson
            statutory.tdsDesignation = statutoryInstance.tdsDesignation
            statutory.enableServiceTax = statutoryInstance.enableServiceTax
            statutory.serviceTaxAlterDetails = statutoryInstance.serviceTaxAlterDetails
            statutory.serviceTaxRegNo = statutoryInstance.serviceTaxRegNo
            statutory.dateOfReg = statutoryInstance.dateOfReg
            statutory.assesseeCode = statutoryInstance.assesseeCode
            statutory.premisesCodeNo = statutoryInstance.premisesCodeNo
            statutory.typeOfOrganization = statutoryInstance.typeOfOrganization
            statutory.isLargeTaxPayer = statutoryInstance.isLargeTaxPayer
            statutory.largeTaxPayerUnit = statutoryInstance.largeTaxPayerUnit
            statutory.divisionCode = statutoryInstance.divisionCode
            statutory.divisionName = statutoryInstance.divisionName
            statutory.rangeCode = statutoryInstance.rangeCode
            statutory.rangeName = statutoryInstance.rangeName
            statutory.commisionerateCode = statutoryInstance.commisionerateCode
            statutory.commisionerateName = statutoryInstance.commisionerateName
            statutory.tcsEnableTaxCollectedAtSource = statutoryInstance.tcsEnableTaxCollectedAtSource
            statutory.tcsAlterDetails = statutoryInstance.tcsAlterDetails
            statutory.tcsTaxAssessmentNo = statutoryInstance.tcsTaxAssessmentNo
            statutory.tcsIncomeTaxCircleward = statutoryInstance.tcsIncomeTaxCircleward
            statutory.tcsDeductorCollectorType = statutoryInstance.tcsDeductorCollectorType
            statutory.tcsResponsiblePerson = statutoryInstance.tcsResponsiblePerson
            statutory.tcsDesignation = statutoryInstance.tcsDesignation
            statutory.vatTinComposition = statutoryInstance.vatTinComposition
            statutory.vatTinRegular = statutoryInstance.vatTinRegular
            statutory.localSalesTaxNumber = statutoryInstance.localSalesTaxNumber
            statutory.interStateSalesTaxNumber = statutoryInstance.interStateSalesTaxNumber
            statutory.panTaxNo = statutoryInstance.panTaxNo
            statutory.save()

            def groupData = AccountGroup.findAllByCompany(mainCompany)
            List<AccountGroup> groupArray = new ArrayList<AccountGroup>();
            if (groupData.size() > 0) {
                groupData.each { g ->
                    groupArray.add(new AccountGroup(name: g.name, alias: g.alias, underGroup: AccountGroup.findByCompanyAndName(comp, g?.underGroup?.name ?: ""),
                            isDisplay: g.isDisplay, isLikeSubgroup: g.isLikeSubgroup, isNetDrCrBalForReport: g.isNetDrCrBalForReport,
                            isGroupUnderPrimary: g.isGroupUnderPrimary, doesIsAffectOnGrossProfit: g.doesIsAffectOnGrossProfit,
                            lastUpdatedBy: g.lastUpdatedBy, lastUpdated: g.lastUpdated, dateCreated: g.dateCreated, property: g.property,
                            isUsedForCalculations: g.isUsedForCalculations, company: comp))
                    AccountGroup.saveAll(groupArray)
                    groupArray.clear()
                }
            }

            def ledgerData = AccountLedger.findAllByCompany(mainCompany)
//            List<AccountLedger> ledgerArray = new ArrayList<AccountLedger>()
            if (ledgerData.size() > 0) {
                ledgerData.each { l ->
                    AccountLedger al = new AccountLedger(name: l.name, underGroup: AccountGroup.findByNameAndCompany(l.underGroup.name, comp),
                            lastUpdatedBy: l.lastUpdatedBy, company: comp, openingBalance: l.openingBalance, status: l.status,
                            alias: l.alias, note: l.note, reconciliationDate: l.reconciliationDate, maintainBill: l.maintainBill, creditDays: l.creditDays,
                            accountNo: l.accountNo, accountName: l.accountName, branchName: l.branchName, bsrCode: l.bsrCode,
                            ifscCode: l.ifscCode, mailingName: l.mailingName, address: l.address, state: l.state, pinCode: l.pinCode,
                            contactPerson: l.contactPerson, telephoneNo: l.telephoneNo, mobileNo: l.mobileNo, faxNo: l.faxNo,
                            email: l.email, provideBankDetails: l.provideBankDetails, panNo: l.panNo, salesTaxNo: l.salesTaxNo, cstNo: l.cstNo,
                            typeOfDuty: l.typeOfDuty, dutyHead: l.dutyHead, percentageOfCalculation: l.percentageOfCalculation, methodOfCalculation: l.methodOfCalculation,
                            roundMethod: l.roundMethod, activeInterestCalculation: l.activeInterestCalculation, isTdsDeductee: l.isTdsDeductee,
                            deducteeType: l.deducteeType, isServiceTax: l.isServiceTax, category: l.category, notificationNo: l.notificationNo,
                            isAbatement: l.isAbatement, percentage: l.percentage, isVat: l.isVat, taxClass: l.taxClass, isTcsApplicable: l.isTcsApplicable,
                            buyerLessee: l.buyerLessee, noCollectionApplicable: l.noCollectionApplicable, sectionNumber: l.sectionNumber, tcsLowerRate: l.tcsLowerRate,
                            ignoreSurchargeExceptionLimit: l.ignoreSurchargeExceptionLimit)
                    if (l.interestParameter.size() > 0) {
                        l.interestParameter.each { c ->
                            al.addToInterestParameter(InterestParameters.saveChild(c as JSONObject, comp.id))
                        }
                    }
                    al.save(flush: true)
                    def partyData = PartyAccount.findAllByPartyNameAndVoucherIsNull(l);
                    if (partyData.size() > 0) {
                        partyData.each { d ->
                            new PartyAccount(company: comp, partyName: al, amountStatus: d.amountStatus, amount: d.amount, typeOfRef: d.typeOfRef, billDate: d.billDate, billNo: d.billNo, crDays: d.crDays, dateCreated: new Date(), lastUpdated: new Date(), lastUpdatedBy: d.lastUpdatedBy, narration: d.narration, voucher: null).save()
                        }
                    }
                }
            }

            def voucherTypeData = VoucherType.findAllByCompany(mainCompany)
            if (voucherTypeData.size() > 0) {
                voucherTypeData.each { c ->
                    def voucherTypeInstance = new VoucherType(name: c.name, alias: c.alias, typeOfVoucher: VoucherType.findByCompanyAndName(comp, c?.typeOfVoucher?.name),
                            lastUpdatedBy: c.lastUpdatedBy, lastUpdated: new Date(), dateCreated: new Date(), company: comp, defaultPrintTitle: c.defaultPrintTitle,
                            isPreventDuplicates: c.isPreventDuplicates, isTypeUpdate: c.isTypeUpdate, lastNumber: (c?.methodOfVoucherNumbering == '2') ? c.lastNumber : 0,
                            methodOfVoucherNumbering: c.methodOfVoucherNumbering, narrationForEachEntry: c.narrationForEachEntry,
                            prefixWithZero: c.prefixWithZero, printAfterSavingVoucher: c.printAfterSavingVoucher, property: c.property,
                            startNumber: (c?.methodOfVoucherNumbering == '2') ? c.startNumber : 0, useAdavanceConfiguration: c.useAdavanceConfiguration, useCommonNarration: c.useCommonNarration,
                            useEffectiveDatesForVouchers: c.useEffectiveDatesForVouchers, withOfNumericalPart: c.withOfNumericalPart)
                    if (c.parameters.size() > 0) {
                        c.parameters.each { p ->
                            voucherTypeInstance.addToParameters(Parameters.buildFromJSON(c as JSONObject, comp))
                        }
                    }
                    voucherTypeInstance.save(flush: true)
                }
            }
            def voucherData
            if (to.trim()) {
                voucherData = Voucher.findAllByCompanyAndDateBetween(mainCompany, Date.parse("yyyy-MM-dd", from), Date.parse("yyyy-MM-dd", to) - 1)
            } else {
                voucherData = Voucher.findAllByCompanyAndDateBetween(mainCompany, Date.parse("yyyy-MM-dd", from), Date.parse("yyyy-MM-dd", new Date().format("yyyy-MM-dd")))
            }
            if (voucherData.size() > 0) {
                voucherData.each { v ->
                    def vNumber = "";
                    def parameterData = null;
                    def vType = VoucherType.findByCompanyAndName(comp, v.voucherType.name);
                    if (vType.parameters.size() > 0) {
                        parameterData = Parameters.findByVoucherTypeAndApplicableFromGreaterThanEqualsAndApplicableToGreaterThan(vType, v.date, v.date)
                    }
                    if ((vType.methodOfVoucherNumbering == "1") && vType.useAdavanceConfiguration) {
                        vNumber = (parameterData.prefix ?: "") + (parameterData?.latestNumber ?: 0) + (parameterData.postfix ?: "")
                    } else if (vType.methodOfVoucherNumbering == "1") {
                        vNumber = vType.lastNumber + 1
                    } else {
                        vNumber = v.voucherNo
                    }
                    def vouchersInstance = new Voucher(
                            voucherNo: vNumber,
                            amount: v.amount, company: comp, amountStatus: v.amountStatus, buyerAddress: v.buyerAddress, buyers: v.buyers,
                            cosigneeAddress: v.cosigneeAddress, cosigneeName: v.cosigneeName, date: v.date, dateCreated: new Date(),
                            deliveryNoteNo: v.deliveryNoteNo, despatchDocNo: v.despatchDocNo, despatchThrough: v.despatchThrough, destination: v.destination,
                            isPostDated: v.isPostDated, lastUpdated: new Date(), lastUpdatedBy: v.lastUpdatedBy, mode_TremsOfPayment: v.mode_TremsOfPayment,
                            narration: v.narration, oredrNo: v.oredrNo, otherReference: v.otherReference, partyName: AccountLedger.findByCompanyAndName(comp, v.partyName.name),
                            referenceNo: v.referenceNo, rowStatus: v.rowStatus, salesLedger: v.salesLedger, termsOfDelivery: v.termsOfDelivery,
                            tIn_SalesTaxNo: v.tIn_SalesTaxNo, voucherType: VoucherType.findByCompanyAndName(comp, v.voucherType.name))
                    if (v.partyAccount.size() > 0) {
                        v.partyAccount.each { p ->
                            p.partyName = AccountLedger.findByCompanyAndName(comp, p.partyName.name)
//                           p.company=comp
                            vouchersInstance.addToPartyAccount(PartyAccount.buildFromJSON(p as JSONObject, comp, vouchersInstance.partyName, vouchersInstance.lastUpdatedBy))
                        }
                    }
                    if (v.voucherDetails.size() > 0) {
                        v.voucherDetails.each { d ->
//                            d.company=comp
                            d.particulars = AccountLedger.findByCompanyAndName(comp, d.particulars.name)
                            vouchersInstance.addToVoucherDetails(VoucherDetails.buildFromJSON(d as JSONObject, comp, vouchersInstance))
                        }
                    }
                    if ((vType.methodOfVoucherNumbering == "1") && vType.useAdavanceConfiguration) {
                        parameterData.latestNumber = (parameterData.latestNumber ?: 0) + 1
                        parameterData.save()
                    } else if (vType.methodOfVoucherNumbering == "1") {
                        vType.lastNumber = vType.lastNumber + 1
                        vType.save()
                    }
//                    vType.lastNumber = vType.methodOfVoucherNumbering=='2'?0:vType.lastNumber+1
//                    vType.save()

                    vouchersInstance.save(flush: true)
                }
            }
            def taxData = TaxSetting.findAllByCompany(mainCompany);
            if (taxData.size() > 0) {
                taxData.each { t ->
                    new TaxSetting(tax: t.tax, company: comp, dateCreated: new Date(), lastUpdated: new Date(), isDisplay: t.isDisplay).save();
                }
            }
            mainCompany.isCompanySplit = true
            mainCompany.save()
            return 1
        } catch (Exception e) {
            print e
            return 0
        }
    }

    def getCreateRoleRights(Long id) {
        def screenData = [];
        def child = [];
        def data = findAllScreenByControllerAndParentScreen()
        if (data.size()) {
            data.each { d ->
                int parentCounter = -1;
                child = [];
                def childData = findAllScreenByParentScreen(d)
                if (childData.size()) {
                    childData.each { c ->
                        parentCounter++
                        child.push([
                                bool       : false,
                                name       : c?.domainName ?: "",
                                id         : c?.id ?: "",
                                link       : c.link,
                                module     : [],
                                level      : 2,
                                parentIndex: parentCounter,
                                parentId   : c?.parentScreen?.id ?: "",
                                collapsed  : true,
                        ])
                    }
                }
                screenData.push([
                        id         : d.id,
                        name       : d?.name ?: "",
                        bool       : false,
                        module     : child,
                        link       : "",
                        level      : 1,
                        parentIndex: "",
                        parentId   : "",
                        collapsed  : true
                ])
            }
        }
        return screenData as JSON;
    }

    def getEditRoleRights(Long id) {
        def screenData = [];
        def child = [];
        def role = getRoleById(id);
        def data = findAllScreenByControllerAndParentScreen()
        if (data.size()) {
            data.each { d ->
                Boolean bool = false;
                int parentCounter = -1;
                child = [];
                def childData = findAllScreenByParentScreen(d)
                if (childData.size()) {
                    childData.each { c ->
                        parentCounter++
                        def roleRights = RoleChild.findByRoleAndScreen(role, c)
                        if (roleRights) {
                            child.push([
                                    bool       : roleRights?.status ?: false,
                                    name       : roleRights?.screen?.domainName ?: "",
                                    id         : roleRights?.screen?.id ?: "",
                                    link       : roleRights?.screen?.link ?: "",
                                    module     : [],
                                    level      : 2,
                                    parentIndex: parentCounter,
                                    parentId   : roleRights?.parentScreen?.id ?: "",
                                    collapsed  : true
                            ])
                            bool = true;
                        } else {
                            child.push([
                                    bool       : false,
                                    name       : c?.domainName ?: "",
                                    id         : c?.id ?: "",
                                    link       : c.link,
                                    module     : [],
                                    level      : 2,
                                    parentIndex: parentCounter,
                                    parentId   : c?.parentScreen?.id ?: "",
                                    collapsed  : true,
                            ])
                        }
                    }
                }
                screenData.push([
                        id         : d.id,
                        name       : d?.name ?: "",
                        bool       : bool,
                        module     : child,
                        link       : "",
                        level      : 1,
                        parentIndex: "",
                        parentId   : "",
                        collapsed  : true
                ])
            }
        }
        return screenData as JSON;
    }

    def getDisplayScreenByRole(Long id) {
        def screenData = [];
        def userRole = getUserRoleByUser(id)
        def data = findAllScreenByControllerAndParentScreen();
        def screen = RoleChild.findAllByParentScreenInListAndRole(data, userRole.role).unique {
            it.parentScreen.id
        }.sort { it.parentScreen.sortList }
        if (screen.size() > 0) {
            screen.each { c ->
                def child = [];
                def userRights = RoleChild.findAllByParentScreenAndRole(c.parentScreen, userRole.role).sort {
                    it.screen.sortList
                }
                if (userRights.size() > 0) {
                    userRights.each { d ->
                        child.push([
                                name: d?.screen?.domainName ?: "",
                                id  : d?.screen?.id ?: "",
                                link: d?.screen?.link ?: ""
                        ])
                    }
                }
                screenData.push([
                        name : c?.parentScreen?.name ?: "",
                        id   : c?.parentScreen?.id ?: "",
                        child: child
                ])
            }
        }
        return screenData;
    }
}

