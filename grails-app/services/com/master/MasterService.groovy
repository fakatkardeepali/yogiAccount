package com.master

import com.common.AccountFlag
import com.common.StatutoryInfo
import com.common.TaxSetting
import com.system.Company
import com.system.SystemService
import com.transaction.PartyAccount
import grails.transaction.Transactional

// Edited by Akshay Pingle
// Example    method name =  methodType + domainName + "By" + propertyName

@Transactional
class MasterService {

    SystemService systemService

    def findAllAccountGroupByCompany(Company company) {
        return AccountGroup.findAllByCompanyAndIsDisplay(company, true).sort { it.name }
    }

    def findAllVoucherTypeByCompanyAndProperty(Company company, String property) {
        return VoucherType.findAllByCompanyAndProperty(company, property).sort { it.name }
    }

    def findAllGroupPrimaryByCompany(Company company) {
        def primary = AccountGroup.findByCompanyAndIsDisplayAndUnderGroupIsNull(company, false)
        def primaryGroup = AccountGroup.findAllByCompanyAndIsDisplayAndUnderGroupIsNotNull(company, false).sort {
            it.name
        }
        primaryGroup.add(primary);
        return primaryGroup;
    }

    def getAccountGroupObjectById(Long id) {
        def objectReturn = [];
        def data = AccountGroup.findById(id);
        if (data) {
            objectReturn = [
                    id                       : data.id,
                    name                     : data.name,
                    isGroupUnderPrimary      : data.isGroupUnderPrimary,
                    isLikeSubgroup           : data.isLikeSubgroup,
                    isNetDrCrBalForReport    : data.isNetDrCrBalForReport,
                    isUsedForCalculations    : data.isUsedForCalculations,
                    doesIsAffectOnGrossProfit: data.doesIsAffectOnGrossProfit,
                    underGroup               : data.underGroup.id,
            ];
        }

        return objectReturn
    }

    def getAccountGroupById(Long id) {
        return AccountGroup.findById(id);
    }

    def getLedgerById(Long id) {
        return AccountLedger.findById(id);
    }

    def findAllVoucherTypeByCompanyId(Long id) {
        return VoucherType.findAllByCompany(systemService.getCompanyObjectById(id));
    }

    def findVoucherTypeById(Long id) {
        def vType = [];
        def vTypeChild = [];
        def data = VoucherType.findById(id)
        if (data) {

            if (data.parameters.size() > 0) {
                data.parameters.each { c ->
                    vTypeChild.push([applicableFrom: c.applicableFrom.format("yyyy-MM-dd"),
                                     applicableTo  : c.applicableTo.format("yyyy-MM-dd"),
                                     startNumber   : c.startNumber ?: 1,
                                     prefix        : c?.prefix ?: "",
                                     postfix       : c?.postfix ?: ""])
                }
            }

            vType = [id                          : data.id,
                     name                        : data?.name ?: "",
                     alias                       : data?.alias ?: "",
                     typeOfVoucher               : data?.typeOfVoucher?.id ?: "",
                     isTypeUpdate                : data.isTypeUpdate ?: false,
                     isPreventDuplicates         : data.isPreventDuplicates ?: false,
                     useAdavanceConfiguration    : data.useAdavanceConfiguration ?: false,
                     useEffectiveDatesForVouchers: data.useEffectiveDatesForVouchers ?: false,
                     useCommonNarration          : data.useCommonNarration ?: false,
                     narrationForEachEntry       : data.narrationForEachEntry ?: false,
                     printAfterSavingVoucher     : data.printAfterSavingVoucher ?: false,
                     methodOfVoucherNumbering    : data.methodOfVoucherNumbering,
                     startNumber                 : data.startNumber ?: 1,
                     prefixWithZero              : data.prefixWithZero ?: 0,
                     child                       : vTypeChild];


            return vType;
        } else {
            return [];
        }

    }

    def getAllTaxByCompanyAndIsDisplayTrue(Company company) {
        def result = []
        def data = TaxSetting.findAllByCompanyAndIsDisplay(company, true);
        if (data) {
            data.each { d ->
                result.push([
                        name: d.tax.name,
                        id  : d.tax.id,
                ])
            }
        }
        return result;
    }

    def getAllTaxChildByParent(Long id) {
        return AccountFlag.findAllByParent(getAccountFlagObjectById(id))
    }

    def getAccountFlagObjectById(Long id) {
        return AccountFlag.get(id)
    }


    def getAllRoundMethodFromAccountFlag() {
        return AccountFlag.findAllByRemark("ROUND");
    }

    def updateTaxSettingObjectByStatutoryAndCompany(StatutoryInfo statutory, Company company) {
        // VAT update for show
        TaxSetting vatData = TaxSetting.createCriteria().get {
            createAlias('tax', 'a').add(org.hibernate.criterion.Restrictions.eq('a.name', "VAT"))
            eq('company', company)
        }
        if (vatData) {
            vatData.isDisplay = statutory.enableValueAddedTax
            vatData.save()
        }
        // CST update for show
        TaxSetting dataCST = TaxSetting.createCriteria().get {
            createAlias('tax', 'a').add(org.hibernate.criterion.Restrictions.eq('a.name', "CST"))
            eq('company', company)
        }
        if (dataCST) {
            dataCST.isDisplay = statutory.enableValueAddedTax
            dataCST.save()
        }
        // TCS update for show
        TaxSetting dataTCS = TaxSetting.createCriteria().get {
            createAlias('tax', 'a').add(org.hibernate.criterion.Restrictions.eq('a.name', "TCS"))
            eq('company', company)
        }
        if (dataTCS) {
            dataTCS.isDisplay = statutory.tcsEnableTaxCollectedAtSource
            dataTCS.save()
        }

        // ServiceTax update for show
        TaxSetting dataServiceTax = TaxSetting.createCriteria().get {
            createAlias('tax', 'a').add(org.hibernate.criterion.Restrictions.eq('a.name', "ServiceTax"))
            eq('company', company)
        }
        if (dataServiceTax) {
            dataServiceTax.isDisplay = statutory.enableServiceTax
            dataServiceTax.save()
        }
        // TDS update for show
        TaxSetting dataTDS = TaxSetting.createCriteria().get {
            createAlias('tax', 'a').add(org.hibernate.criterion.Restrictions.eq('a.name', "TDS"))
            eq('company', company)
        }
        if (dataTDS) {
            dataTDS.isDisplay = statutory.tdsEnableTaxDeductedAtSource
            dataTDS.save()
        }

    }

    def getAccountLedgerObjectById(Long id) {
        return AccountLedger.get(id)
    }


    ArrayList<PartyAccount> getPartyAccountByLedgerAndVoucherNull(AccountLedger ledger) {
        return PartyAccount.findAllByPartyNameAndVoucherIsNull(ledger);
    }


}
