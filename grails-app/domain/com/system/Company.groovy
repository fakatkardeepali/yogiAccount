package com.system

import com.common.AccountFeature
import com.common.AccountFlag
import com.common.StatutoryInfo
import com.common.TaxSetting
import com.master.AccountGroup
import com.master.AccountLedger
import com.master.VoucherType
import grails.rest.Resource

@Resource(uri = "/company", formats = ['json', 'xml'])
class Company {

    String name, address
    String city
    Organization organization
    Country country
    State state
    String pincode, telephoneNo, email
    Date financialFrom, booksBeginigFrom
    String currencySymbol
    Integer numberForDecimalPlace = 0
    String registrationNo
    boolean isSymbolsSuffix = false
    boolean isCompanySplit = false
    boolean isManual = false
    Integer uniqueKey = 0
    Company company
    String taxNo,vatNo,cstNo,eccNo

    Date lastUpdated, dateCreated
    User lastUpdatedBy


    static constraints = {
        name nullable: false
        address nullable: true
        city nullable: true
        organization nullable: false
        country nullable: true
        state nullable: true
        pincode nullable: true
        telephoneNo nullable: true
        email nullable: true
        financialFrom nullable: false
        booksBeginigFrom nullable: false
        currencySymbol nullable: true
        registrationNo nullable: true
        company nullable: true
        taxNo nullable: true
        vatNo nullable: true
        cstNo nullable: true
        eccNo nullable: true

    }

    static mapping = {
        financialFrom type: 'date'
        booksBeginigFrom type: "date"

    }

    def beforeInsert() {
        uniqueKey = (last()?.uniqueKey ?: 0) + 1;
    }

    def afterInsert() {
        def company = Company.findById(id);
        if (company.isManual) {

            List<AccountGroup> groupArray = new ArrayList<AccountGroup>();
            groupArray.add(new AccountGroup(name: "Primary", lastUpdatedBy: lastUpdatedBy, company: company, property: "Primary"));
            AccountGroup.saveAll(groupArray);

            def acctGrp = AccountGroup.findByCompanyAndName(company, "Primary");

            List<AccountGroup> groupArray1 = new ArrayList<AccountGroup>();
            groupArray1.add(new AccountGroup(name: "Assets", underGroup: acctGrp, lastUpdatedBy: lastUpdatedBy, company: company, property: "Assets"))
            groupArray1.add(new AccountGroup(name: "Liabilities", underGroup: acctGrp, lastUpdatedBy: lastUpdatedBy, company: company, property: "Liabilities"))
            groupArray1.add(new AccountGroup(name: "Expenses", underGroup: acctGrp, lastUpdatedBy: lastUpdatedBy, company: company, property: "Expenses"))
            groupArray1.add(new AccountGroup(name: "Income", underGroup: acctGrp, lastUpdatedBy: lastUpdatedBy, company: company, property: "Income"))
            AccountGroup.saveAll(groupArray1);

            def assets = AccountGroup.findByCompanyAndName(company, "Assets");
            def liabilities = AccountGroup.findByCompanyAndName(company, "Liabilities");
            def expenses = AccountGroup.findByCompanyAndName(company, "Expenses");
            def income = AccountGroup.findByCompanyAndName(company, "Income");


            List<AccountGroup> groupArray2 = new ArrayList<AccountGroup>();
            groupArray2.add(new AccountGroup(name: "Current Assets", underGroup: assets, lastUpdatedBy: lastUpdatedBy, company: company, isDisplay: true, property: "Current Assets"));
            groupArray2.add(new AccountGroup(name: "Fixed Assets", underGroup: assets, lastUpdatedBy: lastUpdatedBy, company: company, isDisplay: true, property: "Fixed Assets"));
            groupArray2.add(new AccountGroup(name: "Investments", underGroup: assets, lastUpdatedBy: lastUpdatedBy, company: company, isDisplay: true, property: "Investments"));
            groupArray2.add(new AccountGroup(name: "Misc. Expenses (Assets)", underGroup: assets, lastUpdatedBy: lastUpdatedBy, company: company, isDisplay: true, property: "Misc. Expenses (Assets)"));
            groupArray2.add(new AccountGroup(name: "Loans (liability)", underGroup: liabilities, lastUpdatedBy: lastUpdatedBy, company: company, isDisplay: true, property: "Loans (liability)"));
            groupArray2.add(new AccountGroup(name: "Branch/Divisions", underGroup: liabilities, lastUpdatedBy: lastUpdatedBy, company: company, isDisplay: true, property: "Branch/Divisions"));
            groupArray2.add(new AccountGroup(name: "Capital Account", underGroup: liabilities, lastUpdatedBy: lastUpdatedBy, company: company, isDisplay: true, property: "Capital Account"));
            groupArray2.add(new AccountGroup(name: "Current Liabilities", underGroup: liabilities, lastUpdatedBy: lastUpdatedBy, company: company, isDisplay: true, property: "Current Liabilities"));
            groupArray2.add(new AccountGroup(name: "Suspense A/c", underGroup: liabilities, lastUpdatedBy: lastUpdatedBy, company: company, isDisplay: true, property: "Suspense A/c"));
            groupArray2.add(new AccountGroup(name: "Direct Expenses", underGroup: expenses, lastUpdatedBy: lastUpdatedBy, company: company, isDisplay: true, property: "Direct Expenses"));
            groupArray2.add(new AccountGroup(name: "Indirect Expenses", underGroup: expenses, lastUpdatedBy: lastUpdatedBy, company: company, isDisplay: true, property: "Indirect Expenses"));
            groupArray2.add(new AccountGroup(name: "Purchase Accounts", underGroup: expenses, lastUpdatedBy: lastUpdatedBy, company: company, isDisplay: true, property: "Purchase Accounts"));
            groupArray2.add(new AccountGroup(name: "Direct Income", underGroup: income, lastUpdatedBy: lastUpdatedBy, company: company, isDisplay: true, property: "Direct Income"));
            groupArray2.add(new AccountGroup(name: "Indirect Income", underGroup: income, lastUpdatedBy: lastUpdatedBy, company: company, isDisplay: true, property: "Indirect Income"));
            groupArray2.add(new AccountGroup(name: "Sales Accounts", underGroup: income, lastUpdatedBy: lastUpdatedBy, company: company, isDisplay: true, property: "Sales Accounts"));
            AccountGroup.saveAll(groupArray2);

            def currtAssets = AccountGroup.findByCompanyAndName(company, "Current Assets");
            def loanLaibly = AccountGroup.findByCompanyAndName(company, "Loans (liability)");
//        def retainEarn =  AccountGroup.findByCompanyAndName(company,"Retained Earnings");
            def capAcct = AccountGroup.findByCompanyAndName(company, "Capital Account");
            def currtLiab = AccountGroup.findByCompanyAndName(company, "Current Liabilities");

            List<AccountGroup> groupArray3 = new ArrayList<AccountGroup>();
            groupArray3.add(new AccountGroup(name: "Bank Accounts", underGroup: currtAssets, lastUpdatedBy: lastUpdatedBy, company: company, isDisplay: true, property: "Bank Accounts"));
            groupArray3.add(new AccountGroup(name: "Cash-in-Hand", underGroup: currtAssets, lastUpdatedBy: lastUpdatedBy, company: company, isDisplay: true, property: "Cash-in-Hand"));
            groupArray3.add(new AccountGroup(name: "Deposits (Assets)", underGroup: currtAssets, lastUpdatedBy: lastUpdatedBy, company: company, isDisplay: true, property: "Deposits (Assets)"));
            groupArray3.add(new AccountGroup(name: "Stock-in-Hand", underGroup: currtAssets, lastUpdatedBy: lastUpdatedBy, company: company, isDisplay: true, property: "Stock-in-Hand"));
            groupArray3.add(new AccountGroup(name: "Loan and Advance (Assets)", underGroup: currtAssets, lastUpdatedBy: lastUpdatedBy, company: company, isDisplay: true, property: "Loan and Advance (Assets)"));
            groupArray3.add(new AccountGroup(name: "Sundry Debtors", underGroup: currtAssets, lastUpdatedBy: lastUpdatedBy, company: company, isDisplay: true, property: "Sundry Debtors"));
            groupArray3.add(new AccountGroup(name: "Unsecured Loans", underGroup: loanLaibly, lastUpdatedBy: lastUpdatedBy, company: company, isDisplay: true, property: "Unsecured Loans"));
            groupArray3.add(new AccountGroup(name: "Bank OD A/c", underGroup: loanLaibly, lastUpdatedBy: lastUpdatedBy, company: company, isDisplay: true, property: "Bank OD A/c"));
            groupArray3.add(new AccountGroup(name: "Reserves & Surplus", alias: "Retained Earnings", underGroup: capAcct, lastUpdatedBy: lastUpdatedBy, company: company, isDisplay: true, property: "Reserves & Surplus"));
            groupArray3.add(new AccountGroup(name: "Secured Loans", underGroup: capAcct, lastUpdatedBy: lastUpdatedBy, company: company, isDisplay: true, property: "Secured Loans"));
            groupArray3.add(new AccountGroup(name: "Provisions", underGroup: currtLiab, lastUpdatedBy: lastUpdatedBy, company: company, isDisplay: true, property: "Provisions"));
            groupArray3.add(new AccountGroup(name: "Sundry Creditors", underGroup: currtLiab, lastUpdatedBy: lastUpdatedBy, company: company, isDisplay: true, property: "Sundry Creditors"));
            groupArray3.add(new AccountGroup(name: "Duties & Taxes", underGroup: currtLiab, lastUpdatedBy: lastUpdatedBy, company: company, isDisplay: true, property: "Duties & Taxes"));
            AccountGroup.saveAll(groupArray3);


            new VoucherType(name: "Purchase", property: "Purchase", methodOfVoucherNumbering: "1", useCommonNarration: true, defaultPrintTitle: "Purchase Invoice", company: company, lastUpdatedBy: lastUpdatedBy).save();
            new VoucherType(name: "Contra", property: "Contra", methodOfVoucherNumbering: "1", useCommonNarration: true, defaultPrintTitle: "Contra Invoice", company: company, lastUpdatedBy: lastUpdatedBy).save();
            new VoucherType(name: "Journal", property: "Journal", methodOfVoucherNumbering: "1", useCommonNarration: true, defaultPrintTitle: "Journal Invoice", company: company, lastUpdatedBy: lastUpdatedBy).save();
            new VoucherType(name: "Payment", property: "Payment", methodOfVoucherNumbering: "1", useCommonNarration: true, defaultPrintTitle: "Payment Voucher", company: company, lastUpdatedBy: lastUpdatedBy).save();
            new VoucherType(name: "Receipt", property: "Receipt", methodOfVoucherNumbering: "1", useCommonNarration: true, defaultPrintTitle: "Receipt Voucher", company: company, lastUpdatedBy: lastUpdatedBy).save();
            new VoucherType(name: "Sale", property: "Sale", methodOfVoucherNumbering: "1", narrationForEachEntry: true, defaultPrintTitle: "Sale Invoice", company: company, lastUpdatedBy: lastUpdatedBy).save();
            new VoucherType(name: "Credit Note", property: "Credit Note", methodOfVoucherNumbering: "1", narrationForEachEntry: true, defaultPrintTitle: "Credit Note", company: company, lastUpdatedBy: lastUpdatedBy).save();
            new VoucherType(name: "Debit Note", property: "Debit Note", methodOfVoucherNumbering: "1", narrationForEachEntry: true, defaultPrintTitle: "Debit Note", company: company, lastUpdatedBy: lastUpdatedBy).save();

            // Account Feature and Statutory Setting

            AccountLedger acc = new AccountLedger(name: "Cash", underGroup: AccountGroup.findByName("Cash-in-Hand"), lastUpdatedBy: lastUpdatedBy, company: company, openingBalance: 0, status: "Dr");
            acc.save();
            AccountLedger acc1 = new AccountLedger(name: "Profit & Loss A/c", underGroup: AccountGroup.findByName("Primary"), lastUpdatedBy: lastUpdatedBy, company: company, openingBalance: 0, status: "Dr");
            acc1.save();


            new AccountFeature(company: company).save()
            new StatutoryInfo(company: company).save()

            // tax setting
            List<TaxSetting> taxList = new ArrayList<TaxSetting>()
            def accountFlag = AccountFlag.findAllByRemark("TAX")
            if (accountFlag.size()) {
                for (ac in accountFlag) {
                    boolean display = (ac.name == "Excise" || ac.name == "Others")
                    taxList.add(new TaxSetting(company: company, isDisplay: display, tax: ac))
                }
            }
            TaxSetting.saveAll(taxList)
        }
    }
}
