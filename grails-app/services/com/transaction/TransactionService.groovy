package com.transaction

import com.common.AccountFlag
import com.master.AccountGroup
import com.master.AccountLedger
import com.master.MasterService
import com.system.Company
import com.system.SystemService
import grails.converters.JSON
import grails.transaction.Transactional

// Edited by Akshay Pingle
// Example    method name =  methodType + domainName + "By" + propertyName

@Transactional
class TransactionService {

    SystemService systemService
    MasterService masterService

    def serviceMethod() {

    }

    def getAllLedgerByComapnyAndProperty(Long companyId, def property) {
        def ledger = [];
        def data = AccountGroup.findAllByCompanyAndPropertyInList(systemService.getCompanyObjectById(companyId as Long), property)
        if (data) {
            ledger = AccountLedger.findAllByUnderGroupInList(data).sort { it.name }
        }
        return ledger;
    }

    def getAllLedgerByComapnyAndNotInProperty(Long companyId, def property) {
        def ledger = [];
        def data = AccountGroup.findAllByCompanyAndPropertyNotInList(systemService.getCompanyObjectById(companyId as Long), property)
        if (data) {
            ledger = AccountLedger.findAllByUnderGroupInList(data).sort { it.name }
        }
        return ledger;
    }

    def getBalanceSumByLedgerIdAndCompanyId(Long ledgerId, Long companyId) {
        def amount = 0;
        def status = "";
        def crAmt = PartyAccount.createCriteria().get {
            eq("company", systemService.getCompanyObjectById(companyId as Long))
            eq("partyName", masterService.getLedgerById(ledgerId as Long))
            eq("amountStatus", "Cr");
            projections {
                sum("amount");
            }
        } ?: 0

        def drAmt = PartyAccount.createCriteria().get {
            eq("company", systemService.getCompanyObjectById(companyId as Long))
            eq("partyName", masterService.getLedgerById(ledgerId as Long))
            eq("amountStatus", "Dr");
            projections {
                sum("amount");
            }
        } ?: 0
        if (crAmt > drAmt) {
            amount = crAmt - drAmt
            status = "Cr"
        } else {
            amount = drAmt - crAmt
            status = "Dr"
        }
        return [amount: amount, status: status];
    }

    def getBillRefByRemark(String remark) {
        return AccountFlag.findAllByRemark(remark);
    }

    def getAllBillByBillRef(Company company, Long id) {
        return PartyAccount.findAllByCompanyAndPartyNameAndRemainAmountGreaterThan(company, AccountLedger.findById(id), 0);
    }

    def findVoucherById(Long id) {
        def vType = [];
        def billChild = [];
        def saleChild = [];
        def data = Voucher.findById(id)
        if (data) {
            if (data.voucherBillDetails.size() > 0) {
                data.voucherBillDetails.each { c ->
                    billChild.push([typeRef     : c.typeOfRef.id,
                                    billNo      : c.billNo ?: "",
                                    crDays      : c.crDays ?: 0,
                                    amount      : c.amount ?: 0,
                                    amountStatus: c.amountStatus ?: ""])
                }
            }
            def voucherChild = Voucher.findAllByVoucher(data);
            if (voucherChild.size() > 0) {
                voucherChild.each { c ->
                    saleChild.push([status   : c.amountStatus ?: "",
                                    ledger: c.partyName.id,
                                    debit    : c.amount ?: 0,
                                    narration: c.narration ?: ""])
                }

            }

            vType = [id         : data.id,
                     voucherNo  : data?.voucherNo ?: "",
                     referenceNo: data?.referenceNo ?: "",
                     voucherType: data?.voucherType?.id ?: "",
                     date       : data.date.format("yyyy-MM-dd"),
                     partyName  : data.partyName.id ?: "",
                     narration  : data.narration ?: "",
                     amount     : data.amount ?: "",
                     saleChild  : saleChild,
                     billChild  : billChild];

            return vType;
        } else {
            return [];
        }
    }
}
