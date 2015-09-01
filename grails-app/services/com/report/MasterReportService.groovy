package com.report

import com.master.AccountGroup
import com.master.AccountLedger
import com.master.VoucherType
import com.system.Company
import com.system.SystemService
import com.transaction.PartyAccount
import com.transaction.Voucher
import grails.transaction.Transactional
import org.codehaus.groovy.grails.commons.GrailsApplication

@Transactional
class MasterReportService {

    GrailsApplication grailsApplication
    SystemService systemService

//    def dateFormat = grailsApplication.config.dateFormat;
    
    def serviceMethod() {

    }

    def companyListByCompanyIdArray(def companyArrayId) {
        return Company.findAllByIdInList(companyArrayId)
    }

    def getLedgerById(Long id) {
        return AccountLedger.findById(id);

    }

    def getLedgerByCompanyAndLedgerId(Long companyId, Long id, String from, String to) {
//       def data = Voucher.findAllByCompanyAndPartyName(companyListByCompanyIdArray(companyList.id),getLedgerById(id));
        def child = [];
        def parent = [];
        def crAmount = 0, drAmount = 0, totalCr = "", totalDr = "";
        def company = systemService.getCompanyObjectById(companyId);
        def ledger = getLedgerById(id);
//         def data = Voucher.findAllByCompanyAndPartyName(company,getLedgerById(id));
        def data = Voucher.createCriteria().list {
            eq("company", company)
            eq("partyName", ledger)
            if (from) {
                ge("date", Date.parse("yyyy-MM-dd", from))
            }
            if (to) {
                le("date", Date.parse("yyyy-MM-dd", to))
            }
        }

        if (data) {
            data?.sort{it?.date}?.each { d ->
                def credit = 0;
                def debit = 0;

                if (d?.amountStatus == "Dr") {
                    debit = d?.amount ?: 0;
                    drAmount = drAmount + debit
                } else if (d?.amountStatus == "Cr") {
                    credit = d?.amount ?: 0;
                    crAmount = crAmount + credit
                }

                child.push([
                        id          : d.id,
                        particulars : d?.partyName?.name ?: "",
                        vType       : d?.voucherType?.name ?: "",
                        vNo         : d?.voucherNo ?: "",
                        date        : d?.date?.format("dd-MM-yyyy") ?: "",
                        credit      : credit,
                        debit       : debit,
                        amountStatus: d?.amountStatus ?: ""
                ]);
            }

            if (ledger?.status == "Cr") {
                crAmount = crAmount + (ledger?.openingBalance ?: 0)
            } else {
                drAmount = drAmount + (ledger?.openingBalance ?: 0)
            }
            if (crAmount > drAmount) {
                totalCr = crAmount - drAmount;
            } else {
                totalDr = drAmount - crAmount;
            }
        }

        parent = [companyName: company.name,
                  fromDate: from ? from : company.booksBeginigFrom.format("yyyy-MM-dd"),
                  toDate  : to ? to : new Date().format("yyyy-MM-dd"),
                  partyName  : ledger?.name ?: "",
                  reportName : "Ledger Report",
                  openBalCr  : (ledger?.status == "Cr") ? ledger?.openingBalance : "",
                  openBalDr  : (ledger?.status == "Dr") ? ledger?.openingBalance : "",
                  crAmount   : crAmount,
                  drAmount   : drAmount,
                  totalCr    : totalCr,
                  totalDr    : totalDr,
                  child      : child]

        if (parent) {
            return parent;
        }
    }

    def getGroupSummaryByCompanyAndgroupId(Long companyId, Long id, String from, String to) {
        def child = [];
        def parent = [];
        def voucherAmount = 0, total = 0, status = "";
        def company = systemService.getCompanyObjectById(companyId);
//        def group = masterService.getAccountGroupById(id);
        def group = AccountGroup.findById(id)

        //find all group which is under if group
        def groupChild = AccountGroup.findAllByUnderGroup(group);
        if (groupChild) {
            if (groupChild.size() > 0) {
                groupChild.each { c ->
                    def resultArray = [];
                    def openingBalCr = 0, openingBalDr = 0;
                    def voucherDr = 0, voucherCr = 0;

                    resultArray.add(c);
                    def arry = getAllUnderGroup([c], resultArray);

                    voucherDr = voucherDr + (Voucher.createCriteria().get {
                        eq("company", company)
                        if (from && to) {
                            between("date", Date.parse("yyyy-MM-dd", from), Date.parse("yyyy-MM-dd", to))
                        } else {
                            between("date", company.booksBeginigFrom, new Date())
                        }
                        createAlias('partyName', 'b').add(org.hibernate.criterion.Restrictions.in('b.underGroup', arry))
                        eq('amountStatus', "Dr")
                        projections {
                            sum('amount')
                        }
                        order("date", "asc")
                    } ?: 0)

                    voucherCr = voucherCr + (Voucher.createCriteria().get {
                        eq("company", company)
                        if (from && to) {
                            between("date", Date.parse("yyyy-MM-dd", from), Date.parse("yyyy-MM-dd", to))
                        } else {
                            between("date", company.booksBeginigFrom, new Date())
                        }
                        createAlias('partyName', 'b').add(org.hibernate.criterion.Restrictions.in('b.underGroup', arry))
                        eq('amountStatus', "Cr")
                        projections {
                            sum('amount')
                        }
                        order("date", "asc")
                    } ?: 0)

                    openingBalCr = openingBalCr + (AccountLedger.createCriteria().get {
                        eq("company", company)
                        'in'('underGroup', arry)
                        eq('status', "Cr")
                        projections {
                            sum('openingBalance')
                        }
                    } ?: 0)

                    openingBalDr = openingBalDr + (AccountLedger.createCriteria().get {
                        eq("company", company)
                        'in'('underGroup', arry)
                        eq('status', "Dr")
                        projections {
                            sum('openingBalance')
                        }
                    } ?: 0)

                    child.push(id: c.id,
                            isGroup: true,
                            template: "groupSummaryTemplate.html",
                            name: c.name,
                            amount: ((voucherCr + openingBalCr) > (voucherDr + openingBalDr)) ? ((voucherCr + openingBalCr) - (voucherDr + openingBalDr)) : ((voucherDr + openingBalDr) - (voucherCr + openingBalCr)),
                            amountStatus: ((voucherCr + openingBalCr) > (voucherDr + openingBalDr)) ? "Cr" : "Dr")
                }
            }
        } else {
            def accountLedger = AccountLedger.findAllByUnderGroup(group);
            if (accountLedger.size() > 0) {
                accountLedger.each { c ->
                    def amount = 0;
                    def voucher = Voucher.createCriteria().list {
                        eq("company", company)
                        if (from && to) {
                            between("date", Date.parse("yyyy-MM-dd", from), Date.parse("yyyy-MM-dd", to))
                        } else {
                            between("date", company.booksBeginigFrom, new Date())
                        }
                        eq('partyName', c)
                        order("date", "asc")
                    }

                    def voucherCr = 0, voucherDr = 0, openingBalCr = 0, openingBalDr = 0
                    if (c.status == "Cr") {
                        openingBalCr = openingBalCr + (c.openingBalance ?: 0)
                    } else {
                        openingBalDr = openingBalDr + (c.openingBalance ?: 0);
                    }

                    if (voucher.size() > 0) {
                        voucher.each { d ->
                            if (d.amountStatus == "Cr") {
                                voucherCr = voucherCr + d.amount;
                            } else {
                                voucherDr = voucherDr + d.amount;
                            }
                        }
                    }
                    child.push(id: c.id,
                            isGroup: "",
                            template: "ledgerReportTemplate.html",
                            name: c.name,
                            amount: ((openingBalCr + voucherCr) > (openingBalDr + voucherDr)) ? ((openingBalCr + voucherCr) - (openingBalDr + voucherDr)) : ((openingBalDr + voucherDr) - (openingBalCr + voucherCr)),
                            amountStatus: (openingBalCr + voucherCr) > (openingBalDr + voucherDr) ? "Cr" : "Dr")
                }
            }
        }

        def totalCrAmount = 0;
        def totalDrAmount = 0;
        if (child.size() > 0) {
            for (Map m : child) {
                if (m.amountStatus == "Cr") {
                    totalCrAmount = totalCrAmount + m.amount
                } else {
                    totalDrAmount = totalDrAmount + m.amount
                }
            }
        }

        parent = [companyName  : company.name,
                  fromDate: from ? from : company.booksBeginigFrom.format("yyyy-MM-dd"),
                  toDate  : to ? to : new Date().format("yyyy-MM-dd"),
                  groupName    : group?.name ?: "",
                  totalCrAmount: totalCrAmount ?: 0,
                  totalDrAmount: totalDrAmount ?: 0,
                  reportName   : "Group Summary Report",
                  child        : child]

        if (parent) {
            return parent;
        }
    }

    def getGroupVoucherByCompanyAndgroupId(Long companyId, Long id, String from, String to) {
        def child = [];
        def parent = [];
        def resultArray = [];
        def totalCrAmount = 0;
        def totalDrAmount = 0;
        def openingAmount = 0;
        def openingCr = 0;
        def openingDr = 0;
        def openingStatus = "";
        def crAmount = 0;
        def drAmount = 0;
        def totalCr = 0;
        def totalDr = 0;


        def company = systemService.getCompanyObjectById(companyId);
        def group = AccountGroup.findById(id);
        if (group) {
            resultArray.add(group);
            def arry = getAllUnderGroup([group], resultArray);

            def ledgerData = AccountLedger.createCriteria().list {
                eq("company", company)
                "in"("underGroup", arry)
            }

            if (ledgerData.size() > 0) {
                for (AccountLedger ac : ledgerData) {
                    if (ac.status == "Cr") {
                        openingAmount = openingAmount + (ac.openingBalance ?: 0)
                    } else {
                        openingAmount = openingAmount - (ac.openingBalance ?: 0)
                    }
                }
                if (openingAmount > 0) {
                    openingCr = openingAmount
                    openingStatus = "Cr"
                } else {
                    openingDr = openingAmount * (-1)
                    openingStatus = "Dr"
                }
            }
//            if (child.size() <= 0) {
                def voucherParent = Voucher.createCriteria().list {
                    eq("company", company)
                    if (from && to) {
                        between("date", Date.parse("yyyy-MM-dd", from), Date.parse("yyyy-MM-dd", to))
                    } else {
                        between("date", company.booksBeginigFrom, new Date())
                    }
                    createAlias('partyName', 'b').add(org.hibernate.criterion.Restrictions.in('b.underGroup', arry))
                }
                if (voucherParent) {
                    voucherParent.each { c ->
                        child.push([id         : c?.voucherNo ? c?.id : c?.voucher?.id,
                                    vNo        : c?.voucherNo ? c?.voucherNo : c?.voucher?.voucherNo,
                                    date        : c.date ? c.date.format("yyyy-MM-dd") : "",
//                                    particulars : c?.partyName?.name ?: "No Data Found",
                                    particulars: c?.voucherNo ? c?.partyName?.name : c?.voucher?.partyName?.name + grailsApplication.config.concat + c?.partyName?.name,
                                    vType      : c?.voucherNo ? c?.voucherType?.name : c?.voucher.voucherType.name,
                                    credit      : (c.amountStatus == "Cr") ? c.amount : 0,
                                    debit       : (c.amountStatus == "Dr") ? c.amount : 0,
                                    amountStatus: c?.amountStatus ?: ""])

                        if (c.amountStatus == "Cr") {
                            crAmount = crAmount + (c.amount ?: 0)
                        } else {
                            drAmount = drAmount + (c.amount ?: 0)
                        }
                        def total = (crAmount - drAmount + openingAmount)
                        if (total > 0) {
                            totalCr = total
                        } else {
                            totalDr = total * (-1);
                        }
                    }
                }
//            }
        }

        parent = [companyName: company.name,
                  fromDate: from ? from : company.booksBeginigFrom.format("yyyy-MM-dd"),
                  toDate  : to ? to : new Date().format("yyyy-MM-dd"),
                  isGroup: "",
                  partyName  : group?.name ?: "",
                  openBalCr  : openingCr ?: 0,
                  openBalDr  : openingDr ?: 0,
                  crAmount   : crAmount,
                  drAmount   : drAmount,
                  totalCr    : totalCr,
                  totalDr    : totalDr,
                  reportName: "Group Voucher Report",
                  child      : child]

        if (parent) {
            return parent;
        }
    }

    def getGroupVoucherByCompanyAndLedgerId(Long companyId, Long id, String from, String to) {
        def child = [];
        def parent = [];
        def resultArray = [];
        def totalCrAmount = 0;
        def totalDrAmount = 0;
        def openingAmount = 0;
        def openingCr = 0;
        def openingDr = 0;
        def crAmount = 0;
        def drAmount = 0;
        def totalCr = 0;
        def totalDr = 0;


        def company = systemService.getCompanyObjectById(companyId);
        def ledger = AccountLedger.findByUnderGroupAndCompany(AccountGroup.findById(id), company);

        if (ledger) {
            if (ledger.status == "Cr") {
                openingCr = openingCr + (ledger.openingBalance ?: 0)
            } else {
                openingDr = openingDr + (ledger.openingBalance ?: 0)
            }

            def voucherParent = Voucher.createCriteria().list {
                eq("company", company)
                if (from && to) {
                    between("date", Date.parse("yyyy-MM-dd", from), Date.parse("yyyy-MM-dd", to))
                } else {
                    between("date", company.booksBeginigFrom, new Date())
                }
                eq('partyName', ledger)
            }

            if (voucherParent) {
                voucherParent?.sort{it?.date}?.each { c ->
                    child.push([id          : c?.voucherNo ? c?.id : c?.voucher?.id,
                                vNo         : c?.voucherNo ? c?.voucherNo : c?.voucher?.voucherNo,
                                date        : c.date ? c.date.format("yyyy-MM-dd") : "",
//                                    particulars : c?.partyName?.name ?: "No Data Found",
                                particulars : c?.voucherNo ? c?.partyName?.name : c?.voucher?.partyName?.name + grailsApplication.config.concat + c?.partyName?.name,
                                vType       : c?.voucherNo ? c?.voucherType?.name : c?.voucher.voucherType.name,
                                credit      : (c.amountStatus == "Cr") ? c.amount : 0,
                                debit       : (c.amountStatus == "Dr") ? c.amount : 0,
                                amountStatus: c?.amountStatus ?: ""])

                    if (c.amountStatus == "Cr") {
                        crAmount = crAmount + (c.amount ?: 0)
                    } else {
                        drAmount = drAmount + (c.amount ?: 0)
                    }
                }
            }
        }

        parent = [companyName: company.name,
                  fromDate   : from ? from : company.booksBeginigFrom.format("yyyy-MM-dd"),
                  toDate     : to ? to : new Date().format("yyyy-MM-dd"),
                  isGroup    : "",
                  partyName  : ledger?.name ?: "",
                  openBalCr  : openingCr ?: 0,
                  openBalDr  : openingDr ?: 0,
                  crAmount   : ((crAmount) ?: 0),
                  drAmount   : ((drAmount) ?: 0),
                  totalCr    : ((crAmount + openingCr) > (drAmount + openingDr) ? ((crAmount + openingCr) - (drAmount + openingDr)) : 0),
                  totalDr    : ((crAmount + openingCr) < (drAmount + openingDr) ? ((drAmount + openingDr) - (crAmount + openingCr)) : 0),
                  reportName : "Group Voucher Report",
                  child      : child]

        if (parent) {
            return parent;
        }
    }

    //find all group related to under group...................
    def getAllUnderGroup(ArrayList<AccountGroup> arr, resultArray) {
        def data = AccountGroup.findAllByUnderGroupInList(arr);
        if (data.size() > 0) {
            data.each { c ->
                resultArray.add(c);
            }
            getAllUnderGroup(data, resultArray);
        } else {
            return resultArray;
        }
    }//End find all group related to under group...................

    //Find All Voucher Type Related to Under Voucher Type
    def findAllUnderVoucherType(ArrayList<VoucherType> arr, ArrayList resultArray) {
        def data = VoucherType.findAllByTypeOfVoucherInList(arr);

        if (data.size() > 0) {
            data.each { c ->
                resultArray.add(c);
            }
            findAllUnderVoucherType(data, resultArray);
        } else {
            return resultArray;
        }
    }

    def getSalesRegisterByCompanyAndDateBetween(Long companyId, Long id, String from, String to, String property) {
        def parent;
        def child = [];
        def resultArray = [];
        def company = systemService.getCompanyObjectById(companyId);
        def voucherType = VoucherType.findById(id);
        def debitTotal = 0;
        def creditTotal = 0;

        if (voucherType) {
            resultArray.add(voucherType);
            def data = findAllUnderVoucherType([voucherType], resultArray);

            def salesData = [];

            if (from && to) {
                salesData = Voucher.findAllByCompanyAndVoucherTypeInListAndDateBetween(company, data, Date.parse("yyyy-MM-dd", from), Date.parse("yyyy-MM-dd", to));
            } else {
                salesData = Voucher.findAllByCompanyAndVoucherTypeInList(company, data);
            }

            if (salesData) {
                salesData?.sort{it?.date}?.each { s ->
                    def debit = 0;
                    def credit = 0;

                    if (s?.amountStatus == "Dr") {
                        debit = s?.amount ?: 0;
                        debitTotal = debitTotal + debit;
                    } else if (s?.amountStatus == "Cr") {
                        credit = s?.amount ?: 0;
                        creditTotal = creditTotal + credit;
                    }

                    child.push([
                            id         : s.id,
                            date       : s?.date?.format("dd-MM-yyyy") ?: "",
                            particulars: s?.partyName?.name ?: "",
                            vType      : s?.voucherType?.name ?: "",
                            vNo        : s?.voucherNo ?: "",
                            debit      : debit,
                            credit     : credit
                    ])
                }
            }
        }

        parent = [
                companyName: company.name,
                reportName : "List of All " + property + " Vouchers",
                fromDate   : from ?: "",
                toDate     : to ?: "",
                child      : child,
                debitTotal : debitTotal,
                creditTotal: creditTotal
        ];

        if (parent) {
            return parent
        }
    }

    def getPayablesOutstandingReportByCompanyAndProperty(Long companyId, String property, String fromDate, String toDate, String reportName) {
        def parent;
        def child = [];
        def resultArray = [];
        def company = systemService.getCompanyObjectById(companyId);
        def totalPendingAmount = 0;
        def partyAccountData = [];
        def group = AccountGroup.findAllByNameAndCompany(property, company);

        if (group) {
            resultArray.add(group);
            def arry = getAllUnderGroup([group], resultArray);

            def ledgerData = AccountLedger.findAllByCompanyAndUnderGroupInList(company, arry)

            if (fromDate && toDate) {
                partyAccountData = PartyAccount.findAllByCompanyAndPartyNameInListAndBillDateBetween(company, ledgerData, Date.parse("yyyy-MM-dd", fromDate), Date.parse("yyyy-MM-dd", toDate))
            } else if (fromDate) {
                partyAccountData = PartyAccount.findAllByCompanyAndPartyNameInListAndBillDateGreaterThanEquals(company, ledgerData, Date.parse("yyyy-MM-dd", fromDate))
            } else if (toDate) {
                partyAccountData = PartyAccount.findAllByCompanyAndPartyNameInListAndBillDateLessThanEquals(company, ledgerData, Date.parse("yyyy-MM-dd", toDate))
            } else {
                partyAccountData = PartyAccount.findAllByCompanyAndPartyNameInList(company, ledgerData)
            }

            if (partyAccountData) {
                partyAccountData?.sort{it?.billDate}?.each { s ->
                    def dueDate = s?.billDate ? (s?.billDate) + (s?.crDays ? s.crDays.intValue() : 0) : ""
                    totalPendingAmount = totalPendingAmount + (s?.remainAmount ?: 0)

                    child.push([
                            id           : s?.voucher?.id ?: "",
                            date         : s?.billDate?.format("dd-MM-yyyy") ?: "",
                            particulars  : s?.partyName?.name ?: "",
                            vType        : s?.voucher?.voucherType?.name ?: "",
                            vNo          : s?.voucher?.voucherNo ?: "",
                            refNo        : s?.billNo ?: "",
                            openingAmount: s?.amount ?: 0,
                            pendingAmount: s?.remainAmount ?: 0,
                            dueOn        : dueDate ? dueDate.format("dd-MM-yyyy") : "",
                            overDue      : dueDate ? ((new Date() - dueDate) > 0) ? (new Date() - dueDate) : "" : ""
                    ]);
                }
            }
        }

        parent = [
                companyName       : company.name,
                reportName        : reportName,
                fromDate          : fromDate ? Date.parse("yyyy-MM-dd", fromDate).format("dd-MM-yyyy") : "",
                toDate            : toDate ? Date.parse("yyyy-MM-dd", toDate).format("dd-MM-yyyy") : "",
                child             : child,
                totalPendingAmount: totalPendingAmount
        ];

        if (parent) {
            return parent
        }
    }

    def getStatisticsReport(Long companyId) {
        def parent;
        def child = [];
        def company = systemService.getCompanyObjectById(companyId);
        def totalCount = 0;
        int i = 0;

        def voucherTypeData = VoucherType.findAllByCompany(company);

        if (voucherTypeData) {
            voucherTypeData.each { v ->
                i++;
                def count = Voucher.findAllByCompanyAndVoucherTypeAndDateBetween(company, v as VoucherType, company?.booksBeginigFrom, new Date()).count {
                    it.voucherType
                };
                def groupCount = AccountGroup.findAllByCompanyAndIsDisplayAndDateCreatedBetween(company, true, company?.booksBeginigFrom, new Date()).count {
                    it.id
                };
                def ledgerCount = AccountLedger.findAllByCompanyAndDateCreatedBetween(company, company?.booksBeginigFrom, new Date()).count {
                    it.id
                };
                def voucherTypeCount = VoucherType.findAllByCompanyAndDateCreatedBetween(company, company?.booksBeginigFrom, new Date()).count {
                    it.id
                };

                totalCount = totalCount + count;

                if (i == 1) {
                    child.push([
                            voucherType: v?.name ?: "",
                            count      : count ?: 0,
                            accountType: "Groups",
                            count1     : groupCount
                    ]);
                } else if (i == 2) {
                    child.push([
                            voucherType: v?.name ?: "",
                            count      : count ?: 0,
                            accountType: "Ledgers",
                            count1     : ledgerCount
                    ]);
                } else if (i == 3) {
                    child.push([
                            voucherType: v?.name ?: "",
                            count      : count ?: 0,
                            accountType: "Voucher Types",
                            count1     : voucherTypeCount
                    ]);
                } else {
                    child.push([
                            voucherType: v?.name ?: "",
                            count      : count ?: 0,
                            accountType: "",
                            count1     : ""
                    ]);
                }
            }
        }

        parent = [
                companyName: company.name,
                reportName : "Statistics Report",
                fromDate   : company?.booksBeginigFrom?.format("dd-MM-yyyy") ?: "",
                toDate     : new Date().format("dd-MM-yyyy"),
                child      : child,
                totalCount : totalCount
        ];

        if (parent) {
            return parent
        }
    }

    def getLedgerOutstandingByCompanyAndgroupId(Long companyId, Long id, String from, String to) {
        def data = [], parent = [], parentChild = [];
        def totalOpeningAmountCr = 0, totalRemainAmountCr = 0;
        def totalOpeningAmountDr = 0, totalRemainAmountDr = 0;
        def company = systemService.getCompanyObjectById(companyId);
        def accountLedger = AccountLedger.findById(id);


        def child = PartyAccount.findAllByPartyName(accountLedger);
        if (child) {
            child?.sort{it?.billDate}?.each { c ->
                def dueDate = (c?.billDate?:0) + (c?.crDays ? c.crDays.intValue() : 0)
                parentChild.push(date: c?.billDate?.format("yyyy-MM-dd") ?: "",
                        refNo: c?.billNo ?: "", vType: c?.voucher?.voucherType?.name ?: "",
                        vNo: c?.voucher?.voucherNo ?: "",
                        id: c?.voucher?.id ?: "",
                        amount: c?.amount ?: 0,
                        status: c?.amountStatus ?: 0,
                        remainAmount: c?.remainAmount ?: 0,
                        dueOn: dueDate?dueDate.format("yyyy-MM-dd"):"",
                        overDue: dueDate?(((new Date() - dueDate) > 0) ? (new Date() - dueDate) : ""):"")
                if (c?.amountStatus == "Cr") {
                    totalOpeningAmountCr = totalOpeningAmountCr + (c?.amount ?: 0)
                    totalRemainAmountCr = totalRemainAmountCr + (c?.remainAmount ?: 0)
                } else {
                    totalOpeningAmountDr = totalOpeningAmountDr + (c?.amount ?: 0)
                    totalRemainAmountDr = totalRemainAmountDr + (c?.remainAmount ?: 0)
                }
            }
        }

        parent = [companyName        : company.name,
                  reportName         : "Ledger Outstanding",
                  partyName          : accountLedger?.name ?: "",
                  fromDate: from ? from : company.booksBeginigFrom.format("yyyy-MM-dd"),
                  toDate  : to ? to : new Date().format("yyyy-MM-dd"),
                  totalOpeningAmount : ((totalOpeningAmountDr > totalOpeningAmountCr) ? (totalOpeningAmountDr - totalOpeningAmountCr) : (totalOpeningAmountCr - totalOpeningAmountDr)),
                  totalRemainAmount  : ((totalRemainAmountDr > totalRemainAmountCr) ? (totalRemainAmountDr - totalRemainAmountCr) : (totalRemainAmountCr - totalRemainAmountDr)),
                  openingAmountStatus: ((totalOpeningAmountDr > totalOpeningAmountCr) ? "Dr" : "Cr"),
                  remianAmountStatus : ((totalRemainAmountDr > totalRemainAmountCr) ? "Dr" : "Cr"),
                  child              : parentChild];

        if (parent) {
            return parent
        }
    }

    def getGroupOutstandingByCompanyAndgroupId(Long companyId, Long id, String from, String to) {
        def parentChild = [], parent = [], amountCr = 0, amountDr = 0;
        def company = systemService.getCompanyObjectById(companyId);
        def accountGroup = AccountGroup.findById(id);
        def accountLedger = AccountLedger.findAllByUnderGroup(accountGroup);
//       def party = PartyAccount.findAllByPartyName(accountLedger);
        if (accountLedger.size() > 0) {
            accountLedger.each { d ->
                def child = PartyAccount.findAllByPartyName(d)?.sort{it?.billDate};
                if (child) {
                    amountCr = 0;
                    amountDr = 0;
                    child.each { c ->
                        if (c.amountStatus == "Cr") {
                            amountCr = amountCr + (c?.remainAmount ?: 0)
                        } else {
                            amountDr = amountDr + (c?.remainAmount ?: 0)
                        }
                    }
                }

                parentChild.push(id: d?.id ?: "",
                        isGroup: "",
                        template: "ledgerOutstanding.html",
                        name: d?.name ?: "",
                        amount: ((amountCr) > (amountDr)) ? (amountCr - amountDr) : (amountDr - amountCr),
                        amountStatus: ((amountCr) > (amountDr)) ? "Cr" : "Dr")
            }
        }

        parent = [companyName  : company.name,
                  fromDate: from ? from : company.booksBeginigFrom.format("yyyy-MM-dd"),
                  toDate  : to ? to : new Date().format("yyyy-MM-dd"),
                  groupName    : accountGroup?.name ?: "",
                  totalCrAmount: ((amountCr) > (amountDr)) ? (amountCr - amountDr) : 0,
                  totalDrAmount: ((amountCr) > (amountDr)) ? 0 : (amountDr - amountCr),
                  reportName   : "Group Outstanding",
                  child        : parentChild]

        if (parent) {
            return parent;
        }
    }

    def findAllAccountGroupByCompanyAndName(ArrayList<String> array, Company company) {
        def account = findAccountGroupByNameInList(array, company)
        def accountGroup = AccountGroup.findAllByUnderGroupInListAndCompany(account, company)
        if (account) {
            account.each { c ->
                accountGroup.add(c);
            }
        }
        return accountGroup;
    }

    def findAccountGroupByNameInList(ArrayList<String> array, Company company) {
        return AccountGroup.findAllByNameInListAndCompany(array, company);
    }

    def findCashBankOrTrailBalance(ArrayList<String> array, Company company, String from, String to, String templatePath, String reportName) {
        def child = [], parent = [];
        for (int i = 0; i < array.size(); i++) {
            child.push(findAllLedgerUnderGroup(array[i], company, from, to, true, templatePath))
        }
        def totalCrAmount = 0;
        def totalDrAmount = 0;
        if (child.size() > 0) {
            for (Map m : child) {
                if (m.amountStatus == "Cr") {
                    totalCrAmount = totalCrAmount + m.amount
                } else {
                    totalDrAmount = totalDrAmount + m.amount
                }
            }
        }

        parent = [companyName  : company.name,
                  fromDate     : from ? from : company.booksBeginigFrom.format("yyyy-MM-dd"),
                  toDate       : to ? to : new Date().format("yyyy-MM-dd"),
                  groupName    : "",
                  totalCrAmount: totalCrAmount ?: 0,
                  totalDrAmount: totalDrAmount ?: 0,
                  reportName   : reportName,
                  child        : child]

        if (parent) {
            return parent;
        }
    }

    def findAllCashBankSummaryReport(ArrayList<String> array, Company company, String from, String to) {
        return findCashBankOrTrailBalance(array, company, from, to, "cashBankSummaryTemplate.html", "Cash/Bank Summary")
    }

    def findAllTrailBalanceReport(ArrayList<String> array, Company company, String from, String to) {
        return findCashBankOrTrailBalance(array, company, from, to, "cashBankSummaryTemplate.html", "Trail Balance")
    }

    def findAllCashBankSummaryReportByGroupId(Long id, Company company, String from, String to) {
        def group = AccountGroup.findById(id);
        def underGroup = AccountGroup.findAllByUnderGroup(group);
        if (underGroup.size() > 0) {
            return getGroupSummaryByCompanyAndgroupId((company.id), id, from, to)
        } else {
            return getGroupVoucherByCompanyAndgroupId((company.id), id, from, to);
        }
    }

    def findAllLedgerUnderGroup(String groupName, Company company, String from, String to, Boolean isGroup, String template) {
        def voucherCr = 0, voucherDr = 0, child = [];
//        def account = findAllAccountGroupByCompanyAndName([groupName], company)
        def acc = AccountGroup.findByPropertyAndCompany(groupName, company);
        if (acc) {
            def resultArray = [];
            resultArray.add(acc);
            def arry = getAllUnderGroup([acc], resultArray);

            def accountLedger = AccountLedger.createCriteria().list {
                eq("company", company)
                'in'('underGroup', arry)
            }

            voucherCr = voucherCr + (AccountLedger.createCriteria().get {
                eq("company", company)
                'in'('underGroup', arry)
                eq("status", "Cr")
                projections {
                    sum('openingBalance')
                }
            } ?: 0)

            voucherDr = voucherDr + (AccountLedger.createCriteria().get {
                eq("company", company)
                'in'('underGroup', arry)
                eq("status", "Dr")
                projections {
                    sum('openingBalance')
                }
            } ?: 0)

            if (accountLedger.size() > 0) {
                voucherCr = voucherCr + (Voucher.createCriteria().get {
                    eq("company", company)
                    'in'('partyName', accountLedger)
                    eq("amountStatus", "Cr")
                    if (from && to) {
                        between("date", Date.parse("yyyy-MM-dd", from), Date.parse("yyyy-MM-dd", to))
                    } else {
                        between("date", company.booksBeginigFrom, new Date())
                    }
                    projections {
                        sum('amount')
                    }
                    order("date","asc")
                } ?: 0)

                voucherDr = voucherDr + (Voucher.createCriteria().get {
                    eq("company", company)
                    'in'('partyName', accountLedger)
                    eq("amountStatus", "Dr")
                    if (from && to) {
                        between("date", Date.parse("yyyy-MM-dd", from), Date.parse("yyyy-MM-dd", to))
                    } else {
                        between("date", company.booksBeginigFrom, new Date())
                    }
                    projections {
                        sum('amount')
                    }
                    order("date","asc")
                } ?: 0)
            }
            child = ([id          : acc?.id ?: "",
                      uniqueKey   : acc?.uniqueKey ?: "",
                      isGroup     : isGroup,
                      template    : template,
                      name        : acc?.name ?: "",
                      amount      : (voucherCr > voucherDr) ? (voucherCr - voucherDr) : (voucherDr - voucherCr),
                      amountStatus: (voucherCr > voucherDr) ? ("Cr") : ("Dr")])
        }
        return child;
    }

    def findAllProfitAndLossReport(ArrayList<String> array, Company company, String from, String to) {
        def parent = [], saleChilds = [], purchaseChilds = [], purchaseChild = [], saleChild = [];
        for (int i = 0; i < array.size(); i++) {
            def primaryAccount = AccountGroup.findAllByUnderGroup(AccountGroup.findByName(array[i]));
            if (primaryAccount.size() > 0) {
                primaryAccount.each { c ->
                    if (c.underGroup.name == array[1]) {
                        saleChild.push(findAllLedgerUnderGroup(c.name, company, from, to, true, "profitAndLossAcTemplate.html"))
                    } else {
                        purchaseChild.push(findAllLedgerUnderGroup(c.name, company, from, to, true, "profitAndLossAcTemplate.html"))
                    }
                }
            }
        }
        def totalPurchaseCr = 0, totalPurchaseDr = 0;
        def totalSaleDr = 0, totalSaleCr = 0;
        if (saleChild.size() > 0) {
            for (Map m : saleChild) {
                if (m.amountStatus == "Cr") {
                    totalSaleCr = totalSaleCr + m.amount
                } else {
                    totalSaleDr = totalSaleDr + m.amount
                }
            }
        }
        if (purchaseChild.size() > 0) {
            for (Map m : purchaseChild) {
                if (m.amountStatus == "Cr") {
                    totalPurchaseCr = totalPurchaseCr + m.amount
                } else {
                    totalPurchaseDr = totalPurchaseDr + m.amount
                }
            }
        }

        def totalSale = ((totalSaleCr - totalSaleDr) > 0) ? (totalSaleCr - totalSaleDr) : (totalSaleDr - totalSaleCr)
        def totalPurchase = ((totalPurchaseCr - totalPurchaseDr) > 0) ? (totalPurchaseCr - totalPurchaseDr) : (totalPurchaseDr - totalPurchaseCr)

        parent = [companyName        : company.name,
                  fromDate: from ? from : company.booksBeginigFrom.format("yyyy-MM-dd"),
                  toDate  : to ? to : new Date().format("yyyy-MM-dd"),
                  groupName          : "",
                  totalSale          : totalSale,
                  totalPurchase      : totalPurchase,
                  totalCrAmount      : ((totalPurchase - totalSale) > 0) ? (totalPurchase - totalSale) : "",
                  totalCrAmountStatus: ((totalPurchase - totalSale) > 0) ? "Net Loss" : "",
                  totalDrAmount      : ((totalSale - totalPurchase) > 0) ? (totalSale - totalPurchaseDr) : "",
                  totalDrAmountStauts: ((totalSale - totalPurchase) > 0) ? "Net Profit" : "",
                  reportName         :  "Purchase Account",
                  reportName1         : "Sale Account",
                  purchaseChild      : purchaseChild.sort{it.uniqueKey},
                  saleChild          : saleChild.sort{it.uniqueKey}]

        if (parent) {
            return parent;
        }
    }

    //find total profit and loss for balance sheet
    def findTotalProfitAndLossBalance(ArrayList<String> array, Company company, String from, String to) {
        def child = [], purchaseChild = [], saleChild = [];
        for (int i = 0; i < array.size(); i++) {
            def primaryAccount = AccountGroup.findAllByUnderGroup(AccountGroup.findByName(array[i]));
            if (primaryAccount.size() > 0) {
                primaryAccount.each { c ->
                    if (c.underGroup.name == array[1]) {
                        saleChild.push(findAllLedgerUnderGroup(c.name, company, from, to, true, "profitAndLossAcTemplate.html"))
                    } else {
                        purchaseChild.push(findAllLedgerUnderGroup(c.name, company, from, to, true, "profitAndLossAcTemplate.html"))
                    }
                }
            }
        }
        def totalPurchaseCr = 0, totalPurchaseDr = 0;
        def totalSaleDr = 0, totalSaleCr = 0;
        if (saleChild.size() > 0) {
            for (Map m : saleChild) {
                if (m.amountStatus == "Cr") {
                    totalSaleCr = totalSaleCr + m.amount
                } else {
                    totalSaleDr = totalSaleDr + m.amount
                }
            }
        }
        if (purchaseChild.size() > 0) {
            for (Map m : purchaseChild) {
                if (m.amountStatus == "Cr") {
                    totalPurchaseCr = totalPurchaseCr + m.amount
                } else {
                    totalPurchaseDr = totalPurchaseDr + m.amount
                }
            }
        }

        def totalSale = ((totalSaleCr - totalSaleDr) > 0) ? (totalSaleCr - totalSaleDr) : (totalSaleDr - totalSaleCr)
        def totalPurchase = ((totalPurchaseCr - totalPurchaseDr) > 0) ? (totalPurchaseCr - totalPurchaseDr) : (totalPurchaseDr - totalPurchaseCr)

        child = ([id          : "",
                  isGroup     : "",
                  template    : "",
                  name        : "Profit And Loss A/C",
                  amount      : (totalPurchase > totalSale) ? (totalPurchase - totalSale) : (totalSale - totalPurchase),
                  amountStatus: (totalPurchase > totalSale) ? ("Dr") : ("Cr")])

        if (child) {
            return child;
        } else {
            return [];
        }
    }

    def findAllBalanceSheetReport(ArrayList<String> array, Company company, String from, String to) {
        def parent = [], purchaseChild = [], saleChild = [], profitLossChild = [];

        for (int i = 0; i < array.size(); i++) {
            def primaryAccount = AccountGroup.findAllByUnderGroup(AccountGroup.findByName(array[i]));
            if (primaryAccount.size() > 0) {
                primaryAccount.each { c ->
                    if (c.underGroup.name == array[1]) {
                        saleChild.push(findAllLedgerUnderGroup(c.name, company, from, to, true, "balanceSheetTemplate.html"))
                    } else {
                        purchaseChild.push(findAllLedgerUnderGroup(c.name, company, from, to, true, "balanceSheetTemplate.html"))
                    }
                }
            }
        }

        def profitLoss = findTotalProfitAndLossBalance(["Expenses", "Income"], company, from, to);
        if (profitLoss) {
            profitLossChild.push(profitLoss);
        }

        def totalPurchaseCr = 0, totalPurchaseDr = 0;
        def totalSaleDr = 0, totalSaleCr = 0;
        if (saleChild.size() > 0) {
            for (Map m : saleChild) {
                if (m.amountStatus == "Cr") {
                    totalSaleCr = totalSaleCr + m.amount
                } else {
                    totalSaleDr = totalSaleDr + m.amount
                }
            }
        }
        if (purchaseChild.size() > 0) {
            for (Map m : purchaseChild) {
                if (m.amountStatus == "Cr") {
                    totalPurchaseCr = totalPurchaseCr + m.amount
                } else {
                    totalPurchaseDr = totalPurchaseDr + m.amount
                }
            }
        }

        def totalSale = ((totalSaleCr - totalSaleDr) > 0) ? (totalSaleCr - totalSaleDr) : (totalSaleDr - totalSaleCr)
        def totalPurchase = ((totalPurchaseCr - totalPurchaseDr) > 0) ? (totalPurchaseCr - totalPurchaseDr) : (totalPurchaseDr - totalPurchaseCr)

        if (profitLossChild.size() > 0) {
            for (Map m : profitLossChild) {
                if (m.amountStatus == "Dr") {
                    totalSale = totalSale + m.amount
                } else {
                    totalPurchase = totalPurchase + m.amount
                }
            }
        }

        parent = [companyName    : company.name,
                  fromDate       : from ? from : company.booksBeginigFrom.format("yyyy-MM-dd"),
                  toDate         : to ? to : new Date().format("yyyy-MM-dd"),
                  groupName      : "",
                  totalSale      : totalSale ?: 0,
                  totalPurchase  : totalPurchase ?: 0,
                  totalCrAmount  : totalSale ?: "",
                  totalDrAmount  : totalPurchase ?: 0,
                  openingCrAmount: ((totalPurchase - totalSale) > 0) ? (totalPurchase - totalSale) : 0,
                  openingDrAmount: ((totalSale - totalPurchase) > 0) ? (totalSale - totalPurchase) : 0,
                  reportName1    : array[0],
                  reportName2    : array[1],
                  purchaseChild  : purchaseChild,
                  saleChild      : saleChild,
                  profitLossChild: profitLossChild]

        if (parent) {
            return parent;
        }
    }

    //find all Group And Ledger
    def getAllGroupsAndLedger(ArrayList<AccountGroup> arr, resultArray) {
        def data = AccountGroup.findAllByUnderGroupInList(arr);
        if (data.size() > 0) {
            data.each { c ->
                resultArray.push([
                        id  : c.id,
                        name: "----------------->" + c.name
                ])
            }
            getAllUnderGroup(data, resultArray);
        } else {
            return resultArray;
        }
    }

    def getListOfAccounts(Long companyId) {
        def company = systemService.getCompanyObjectById(companyId);
        def child = [];
        def parent;
        def resultArray = [];

        def groupData = AccountGroup.findAllByCompanyAndDateCreatedBetweenAndIsDisplayAndUnderGroupIsNotNull(company, company.booksBeginigFrom, new Date(), false);

        if (groupData) {
            groupData.each { g ->
                def group = AccountGroup.findById(g.id as Long);
                resultArray = [];
//                resultArray.add(group);

                resultArray.push([
                        id  : g.id,
                        name: "* " + g.name
                ])

                def arry = getAllGroupsAndLedger([group], resultArray);

                if (arry) {
                    arry.each { a ->
                        child.push([
                                id  : a.id,
                                name: a.name
                        ])
                    }
                }
            }
        }

        parent = [companyName: company.name,
                  fromDate   : company?.booksBeginigFrom?.format("dd-MM-yyyy"),
                  toDate     : new Date().format("dd-MM-yyyy"),
                  reportName : "List of Accounts",
                  child      : child]

        if (parent) {
            return parent;
        }
    }

    def getDayBookReport(Long companyId, String from, String to) {
        def company = systemService.getCompanyObjectById(companyId);
        def child = [];
        def parent;
        def Data = []

        if (from && to) {
            Data = Voucher.findAllByCompanyAndDateBetween(company, Date.parse("yyyy-MM-dd", from), Date.parse("yyyy-MM-dd", to));
        } else if (from) {
            Data = Voucher.findAllByCompanyAndDateGreaterThanEquals(company, Date.parse("yyyy-MM-dd", from));
        } else if (to) {
            Data = Voucher.findAllByCompanyAndDateLessThanEquals(company, Date.parse("yyyy-MM-dd", to));
        } else {
            Data = Voucher.findAllByCompanyAndDateBetweenAndVoucherTypeIsNotNull(company, company.booksBeginigFrom, new Date());
        }

        if (Data) {
            Data.each { d ->
                def debit = 0;
                def credit = 0;

                if (d?.amountStatus == "Cr") {
                    credit = d?.amount ?: 0;
                } else if (d?.amountStatus == "Dr") {
                    debit = d?.amount ?: 0;
                }

                child.push([
                        id          : d?.id,
                        date        : d?.date?.format("dd-MM-yyyy") ?: "",
                        particulars : d?.partyName?.name ?: "",
                        vType       : d?.voucherType?.name ?: "",
                        vNo         : d?.voucherNo ?: "",
                        debitAmount : debit,
                        creditAmount: credit
                ]);
            }
        }

        parent = [companyName: company.name,
                  fromDate   : from ? Date.parse("dd-MM-yyyy", from)?.format("dd-MM-yyyy") : "",
                  toDate     : to ? Date.parse("dd-MM-yyyy", to)?.format("dd-MM-yyyy") : "",
                  reportName : "Day Book",
                  child      : child]

        if (parent) {
            return parent;
        }
    }
}
