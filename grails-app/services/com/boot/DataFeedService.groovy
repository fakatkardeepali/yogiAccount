package com.boot

import com.common.AccountFeature
import com.common.AccountFlag
import com.common.StatutoryInfo
import com.common.VoucherStatus
import com.master.AccountGroup
import com.master.AccountLedger
import com.master.VoucherType
import com.system.Company
import com.system.Country
import com.system.Organization
import com.system.Role
import com.system.RoleChild
import com.system.Screen
import com.system.State
import com.system.User
import com.system.UserRole
import grails.transaction.Transactional

@Transactional
class DataFeedService {

    def settingEntry() {

        if (!Country.list().size()) {
            new Country(name: "India", dateCreated: new Date(), lastUpdated: new Date(), uniqueKey: 1).save();
        }

        if (!State.list().size()) {
            List<State> stateArray = new ArrayList<State>();

            def country = Country.first()
            stateArray.add(new State(name: "Andaman & Nicobar Island", country: country, uniqueKey: 1, dateCreated: new Date(), lastUpdated: new Date()));
            stateArray.add(new State(name: "Andhra Pardesh", country: country, uniqueKey: 2, dateCreated: new Date(), lastUpdated: new Date()));
            stateArray.add(new State(name: "Arunachal Pardesh", country: country, uniqueKey: 3, dateCreated: new Date(), lastUpdated: new Date()));
            stateArray.add(new State(name: "Assam", country: country, uniqueKey: 4, dateCreated: new Date(), lastUpdated: new Date()));
            stateArray.add(new State(name: "Bihar", country: country, uniqueKey: 5, dateCreated: new Date(), lastUpdated: new Date()));
            stateArray.add(new State(name: "Chandigarh", country: country, uniqueKey: 6, dateCreated: new Date(), lastUpdated: new Date()));
            stateArray.add(new State(name: "Chhattisgarh", country: country, uniqueKey: 7, dateCreated: new Date(), lastUpdated: new Date()));
            stateArray.add(new State(name: "Dadra & Nagar Haveli", country: country, uniqueKey: 8, dateCreated: new Date(), lastUpdated: new Date()));
            stateArray.add(new State(name: "Daman & Diu", country: country, uniqueKey: 9, dateCreated: new Date(), lastUpdated: new Date()));
            stateArray.add(new State(name: "Delhi", country: country, uniqueKey: 10, dateCreated: new Date(), lastUpdated: new Date()));
            stateArray.add(new State(name: "Goa", country: country, uniqueKey: 11, dateCreated: new Date(), lastUpdated: new Date()));
            stateArray.add(new State(name: "Gujrat", country: country, uniqueKey: 12, dateCreated: new Date(), lastUpdated: new Date()));
            stateArray.add(new State(name: "Haryana", country: country, uniqueKey: 13, dateCreated: new Date(), lastUpdated: new Date()));
            stateArray.add(new State(name: "Himachal Pardesh", country: country, uniqueKey: 14, dateCreated: new Date(), lastUpdated: new Date()));
            stateArray.add(new State(name: "Jammu & Kashmir", country: country, uniqueKey: 15, dateCreated: new Date(), lastUpdated: new Date()));
            stateArray.add(new State(name: "Jharkhand", country: country, uniqueKey: 16, dateCreated: new Date(), lastUpdated: new Date()));
            stateArray.add(new State(name: "Karanataka", country: country, uniqueKey: 17, dateCreated: new Date(), lastUpdated: new Date()));
            stateArray.add(new State(name: "Kerla", country: country, uniqueKey: 18, dateCreated: new Date(), lastUpdated: new Date()));
            stateArray.add(new State(name: "Lakshadweep", country: country, uniqueKey: 19, dateCreated: new Date(), lastUpdated: new Date()));
            stateArray.add(new State(name: "Madhya Pardesh", country: country, uniqueKey: 20, dateCreated: new Date(), lastUpdated: new Date()));
            stateArray.add(new State(name: "Maharashtra", country: country, uniqueKey: 21, dateCreated: new Date(), lastUpdated: new Date()));
            stateArray.add(new State(name: "Manipur", country: country, uniqueKey: 22, dateCreated: new Date(), lastUpdated: new Date()));
            stateArray.add(new State(name: "Meghalaya", country: country, uniqueKey: 23, dateCreated: new Date(), lastUpdated: new Date()));
            stateArray.add(new State(name: "Mizoram", country: country, uniqueKey: 24, dateCreated: new Date(), lastUpdated: new Date()));
            stateArray.add(new State(name: "Nagaland", country: country, uniqueKey: 25, dateCreated: new Date(), lastUpdated: new Date()));
            stateArray.add(new State(name: "Orissa", country: country, uniqueKey: 26, dateCreated: new Date(), lastUpdated: new Date()));
            stateArray.add(new State(name: "Paducherry", country: country, uniqueKey: 27, dateCreated: new Date(), lastUpdated: new Date()));
            stateArray.add(new State(name: "Punjab", country: country, uniqueKey: 28, dateCreated: new Date(), lastUpdated: new Date()));
            stateArray.add(new State(name: "Rajasthan", country: country, uniqueKey: 29, dateCreated: new Date(), lastUpdated: new Date()));
            stateArray.add(new State(name: "Sikkim", country: country, uniqueKey: 30, dateCreated: new Date(), lastUpdated: new Date()));
            stateArray.add(new State(name: "Tamilnadu", country: country, uniqueKey: 31, dateCreated: new Date(), lastUpdated: new Date()));
            stateArray.add(new State(name: "Tripura", country: country, uniqueKey: 32, dateCreated: new Date(), lastUpdated: new Date()));
            stateArray.add(new State(name: "Uttrakhand", country: country, uniqueKey: 33, dateCreated: new Date(), lastUpdated: new Date()));
            stateArray.add(new State(name: "Uttar Pardesh", country: country, uniqueKey: 34, dateCreated: new Date(), lastUpdated: new Date()));
            stateArray.add(new State(name: "West Bengal", country: country, uniqueKey: 35, dateCreated: new Date(), lastUpdated: new Date()));
            State.saveAll(stateArray);
        }

        if (!VoucherStatus.list().size()) {
            new VoucherStatus(voucherProperty: "Sale", parentLabel: "Amount(Dr)", parentStatus: "Dr", childLabel: "Credit", childStatus: "Cr").save();
            new VoucherStatus(voucherProperty: "Contra", parentLabel: "Amount(Cr)", parentStatus: "Cr", childLabel: "Debit", childStatus: "Dr").save();
            new VoucherStatus(voucherProperty: "Journal", parentLabel: "Amount(Dr)", parentStatus: "Dr", childLabel: "Credit", childStatus: "Cr").save();
            new VoucherStatus(voucherProperty: "Payment", parentLabel: "Amount(Dr)", parentStatus: "Dr", childLabel: "Credit", childStatus: "Cr").save();
            new VoucherStatus(voucherProperty: "Receipt", parentLabel: "Amount(Cr)", parentStatus: "Cr", childLabel: "Debit", childStatus: "Dr").save();
            new VoucherStatus(voucherProperty: "Purchase", parentLabel: "Amount(Cr)", parentStatus: "Cr", childLabel: "Debit", childStatus: "Dr").save();
            new VoucherStatus(voucherProperty: "Credit Note", parentLabel: "Amount(Cr)", parentStatus: "Cr", childLabel: "Debit", childStatus: "Dr").save();
            new VoucherStatus(voucherProperty: "Debit Note", parentLabel: "Amount(Dr)", parentStatus: "Dr", childLabel: "Credit", childStatus: "cr").save();
        }


        if (!Organization.list().size()) {
            new Organization(name: "Yogi Organization", uniqueKey: 1, dateCreated: new Date(), lastUpdated: new Date()).save();
        }
        if (!User.list().size()) new User(organization: Organization.first(), username: 'a@gmail.com', password: 'a', isAdmin: true, enable: true, uniqueKey: 1, dateCreated: new Date(), lastUpdated: new Date()).save();

        if (!Company.list().size()) new Company(name: "Yogi Company", organization: Organization.first(), uniqueKey: 1, dateCreated: new Date(), lastUpdated: new Date(), financialFrom: Date.parse("yyyy-MM-dd", "2015-04-01"), booksBeginigFrom: Date.parse("yyyy-MM-dd", "2015-04-01"), lastUpdatedBy: User.first()).save();

        if (User.first()) {
            def user = User.first();
            user.company = Company.first();
            user.save();
        }

        if (Screen.list().size()) {
            if (!Role.list().size()) {
                def roleInstance = new Role(name: "Admin_Authority", uniqueKey: 1, dateCreated: new Date(), lastUpdated: new Date(), lastUpdatedBy: User.first(), company: Company.first());
                def screenList = Screen.findAllByParentScreenIsNotNull();
                if (screenList) {
                    screenList.each { e ->
                        roleInstance.addToRoleChild(parentScreen: e?.parentScreen as Screen, screen: Screen.findById(e.id), canAdd: true, canDelete: true, canPrint: true, canUpdate: true, canView: true, company: Company.first());
                    }
                }
                if (roleInstance) roleInstance.save();
            }
        }

        if (!UserRole.list().size() && Role.list().size() && RoleChild.list().size()) {
            UserRole ur = new UserRole(user: User.first(), role: Role.first());
            if (ur) {
                ur.save();
            }
        }

        def company = Company.first();
        def lastUpdatedBy = User.first();
        if (!VoucherType.list().size()) {
            new VoucherType(name: "Purchase", property: "Purchase", methodOfVoucherNumbering: "1", useCommonNarration: true, defaultPrintTitle: "Purchase Invoice", company: company, lastUpdatedBy: lastUpdatedBy).save();
            new VoucherType(name: "Contra", property: "Contra", methodOfVoucherNumbering: "1", useCommonNarration: true, defaultPrintTitle: "Contra Invoice", company: company, lastUpdatedBy: lastUpdatedBy).save();
            new VoucherType(name: "Journal", property: "Journal", methodOfVoucherNumbering: "1", useCommonNarration: true, defaultPrintTitle: "Journal Invoice", company: company, lastUpdatedBy: lastUpdatedBy).save();
            new VoucherType(name: "Payment", property: "Payment", methodOfVoucherNumbering: "1", useCommonNarration: true, defaultPrintTitle: "Payment Voucher", company: company, lastUpdatedBy: lastUpdatedBy).save();
            new VoucherType(name: "Receipt", property: "Receipt", methodOfVoucherNumbering: "1", useCommonNarration: true, defaultPrintTitle: "Receipt Voucher", company: company, lastUpdatedBy: lastUpdatedBy).save();
            new VoucherType(name: "Sale", property: "Sale", methodOfVoucherNumbering: "1", narrationForEachEntry: true, defaultPrintTitle: "Sale Invoice", company: company, lastUpdatedBy: lastUpdatedBy).save();
            new VoucherType(name: "Credit Note", property: "Credit Note", methodOfVoucherNumbering: "1", narrationForEachEntry: true, defaultPrintTitle: "Credit Note", company: company, lastUpdatedBy: lastUpdatedBy).save();
            new VoucherType(name: "Debit Note", property: "Debit Note", methodOfVoucherNumbering: "1", narrationForEachEntry: true, defaultPrintTitle: "Debit Note", company: company, lastUpdatedBy: lastUpdatedBy).save();

        }
    }


    def dataFeedGroup() {

        if (!AccountGroup.list().size()) {
            List<AccountGroup> groupArray = new ArrayList<AccountGroup>();

            def userObject = User.first()
            def companyObject = Company.first()
            groupArray.add(new AccountGroup(name: "Primary", uniqueKey: 1, lastUpdatedBy: userObject, company: companyObject, property: "Primary"));
            AccountGroup.saveAll(groupArray);
            List<AccountGroup> groupArray1 = new ArrayList<AccountGroup>();

            def accountObject = AccountGroup.first()
            groupArray1.add(new AccountGroup(name: "Assets", underGroup: accountObject, uniqueKey: 2, lastUpdatedBy: userObject, company: companyObject, property: "Assets"))
            groupArray1.add(new AccountGroup(name: "Liabilities", underGroup: accountObject, uniqueKey: 3, lastUpdatedBy: userObject, company: companyObject, property: "Liabilities"))
            groupArray1.add(new AccountGroup(name: "Expenses", underGroup: accountObject, uniqueKey: 4, lastUpdatedBy: userObject, company: companyObject, property: "Expenses"))
            groupArray1.add(new AccountGroup(name: "Income", underGroup: accountObject, uniqueKey: 5, lastUpdatedBy: userObject, company: companyObject, property: "Income"))
            AccountGroup.saveAll(groupArray1);
            List<AccountGroup> groupArray2 = new ArrayList<AccountGroup>();
            groupArray2.add(new AccountGroup(name: "Current Assets", underGroup: AccountGroup.findByName("Assets"), uniqueKey: 6, lastUpdatedBy: userObject, company: companyObject, isDisplay: true, property: "Current Assets"));
            groupArray2.add(new AccountGroup(name: "Fixed Assets", underGroup: AccountGroup.findByName("Assets"), uniqueKey: 7, lastUpdatedBy: userObject, company: companyObject, isDisplay: true, property: "Fixed Assets"));
            groupArray2.add(new AccountGroup(name: "Investments", underGroup: AccountGroup.findByName("Assets"), uniqueKey: 8, lastUpdatedBy: userObject, company: companyObject, isDisplay: true, property: "Investments"));
            groupArray2.add(new AccountGroup(name: "Misc. Expenses (Assets)", underGroup: AccountGroup.findByName("Assets"), uniqueKey: 9, lastUpdatedBy: userObject, company: companyObject, isDisplay: true, property: "Misc. Expenses (Assets)"));
            groupArray2.add(new AccountGroup(name: "Loans (liability)", underGroup: AccountGroup.findByName("Liabilities"), uniqueKey: 10, lastUpdatedBy: userObject, company: companyObject, isDisplay: true, property: "Loans (liability)"));
            groupArray2.add(new AccountGroup(name: "Branch/Divisions", underGroup: AccountGroup.findByName("Liabilities"), uniqueKey: 11, lastUpdatedBy: userObject, company: companyObject, isDisplay: true, property: "Branch/Divisions"));
            groupArray2.add(new AccountGroup(name: "Capital Account", underGroup: AccountGroup.findByName("Liabilities"), uniqueKey: 12, lastUpdatedBy: userObject, company: companyObject, isDisplay: true, property: "Capital Account"));
            groupArray2.add(new AccountGroup(name: "Current Liabilities", underGroup: AccountGroup.findByName("Liabilities"), uniqueKey: 13, lastUpdatedBy: userObject, company: companyObject, isDisplay: true, property: "Current Liabilities"));
            groupArray2.add(new AccountGroup(name: "Suspense A/c", underGroup: AccountGroup.findByName("Liabilities"), uniqueKey: 14, lastUpdatedBy: userObject, company: companyObject, isDisplay: true, property: "Suspense A/c"));
            groupArray2.add(new AccountGroup(name: "Direct Expenses", underGroup: AccountGroup.findByName("Expenses"), uniqueKey: 15, lastUpdatedBy: userObject, company: companyObject, isDisplay: true, property: "Direct Expenses"));
            groupArray2.add(new AccountGroup(name: "Indirect Expenses", underGroup: AccountGroup.findByName("Expenses"), uniqueKey: 16, lastUpdatedBy: userObject, company: companyObject, isDisplay: true, property: "Indirect Expenses"));
            groupArray2.add(new AccountGroup(name: "Purchase Accounts", underGroup: AccountGroup.findByName("Expenses"), uniqueKey: 17, lastUpdatedBy: userObject, company: companyObject, isDisplay: true, property: "Purchase Accounts"));
            groupArray2.add(new AccountGroup(name: "Direct Income", underGroup: AccountGroup.findByName("Income"), uniqueKey: 18, lastUpdatedBy: userObject, company: companyObject, isDisplay: true, property: "Direct Income"));
            groupArray2.add(new AccountGroup(name: "Indirect Income", underGroup: AccountGroup.findByName("Income"), uniqueKey: 19, lastUpdatedBy: userObject, company: companyObject, isDisplay: true, property: "Indirect Income"));
            groupArray2.add(new AccountGroup(name: "Sales Accounts", underGroup: AccountGroup.findByName("Income"), uniqueKey: 20, lastUpdatedBy: userObject, company: companyObject, isDisplay: true, property: "Sales Accounts"));
            AccountGroup.saveAll(groupArray2);
            List<AccountGroup> groupArray3 = new ArrayList<AccountGroup>();
            groupArray3.add(new AccountGroup(name: "Bank Accounts", underGroup: AccountGroup.findByName("Current Assets"), uniqueKey: 21, lastUpdatedBy: userObject, company: companyObject, isDisplay: true, property: "Bank Accounts"));
            groupArray3.add(new AccountGroup(name: "Cash-in-Hand", underGroup: AccountGroup.findByName("Current Assets"), uniqueKey: 22, lastUpdatedBy: userObject, company: companyObject, isDisplay: true, property: "Cash-in-Hand"));
            groupArray3.add(new AccountGroup(name: "Deposits (Assets)", underGroup: AccountGroup.findByName("Current Assets"), uniqueKey: 23, lastUpdatedBy: userObject, company: companyObject, isDisplay: true, property: "Deposits (Assets)"));
            groupArray3.add(new AccountGroup(name: "Stock-in-Hand", underGroup: AccountGroup.findByName("Current Assets"), uniqueKey: 24, lastUpdatedBy: userObject, company: companyObject, isDisplay: true, property: "Stock-in-Hand"));
            groupArray3.add(new AccountGroup(name: "Loan and Advance (Assets)", underGroup: AccountGroup.findByName("Current Assets"), uniqueKey: 25, lastUpdatedBy: userObject, company: companyObject, isDisplay: true, property: "Loan and Advance (Assets)"));
            groupArray3.add(new AccountGroup(name: "Sundry Debtors", underGroup: AccountGroup.findByName("Current Assets"), uniqueKey: 26, lastUpdatedBy: userObject, company: companyObject, isDisplay: true, property: "Sundry Debtors"));
            groupArray3.add(new AccountGroup(name: "Unsecured Loans", underGroup: AccountGroup.findByName("Loans (liability)"), uniqueKey: 27, lastUpdatedBy: userObject, company: companyObject, isDisplay: true, property: "Unsecured Loans"));
            groupArray3.add(new AccountGroup(name: "Bank OD A/c", underGroup: AccountGroup.findByName("Loans (liability)"), uniqueKey: 28, lastUpdatedBy: userObject, company: companyObject, isDisplay: true, property: "Bank OD A/c"));
            groupArray3.add(new AccountGroup(name: "Reserves & Surplus", alias: "Retained Earnings", underGroup: AccountGroup.findByName("Capital Account"), uniqueKey: 29, lastUpdatedBy: userObject, company: companyObject, isDisplay: true, property: "Reserves & Surplus"));
            groupArray3.add(new AccountGroup(name: "Secured Loans", underGroup: AccountGroup.findByName("Capital Account"), uniqueKey: 31, lastUpdatedBy: userObject, company: companyObject, isDisplay: true, property: "Secured Loans"));
            groupArray3.add(new AccountGroup(name: "Provisions", underGroup: AccountGroup.findByName("Current Liabilities"), uniqueKey: 32, lastUpdatedBy: userObject, company: companyObject, isDisplay: true, property: "Provisions"));
            groupArray3.add(new AccountGroup(name: "Sundry Creditors", underGroup: AccountGroup.findByName("Current Liabilities"), uniqueKey: 33, lastUpdatedBy: userObject, company: companyObject, isDisplay: true, property: "Sundry Creditors"));
            groupArray3.add(new AccountGroup(name: "Duties & Taxes", underGroup: AccountGroup.findByName("Current Liabilities"), uniqueKey: 34, lastUpdatedBy: userObject, company: companyObject, isDisplay: true, property: "Duties & Taxes"));
            AccountGroup.saveAll(groupArray3);
        }

    }


    def dataFeedLedger() {
        if (!AccountLedger.list().size()) {

            def user = User.first()

            def company = Company.first()
            AccountLedger acc = new AccountLedger(name: "Cash", underGroup: AccountGroup.findByName("Cash-in-Hand"), lastUpdatedBy: user, company: company, openingBalance: 0, status: "Dr");
            acc.save();
            AccountLedger acc1 = new AccountLedger(name: "Profit & Loss A/c", underGroup: AccountGroup.findByName("Primary"), lastUpdatedBy: user, company: company, openingBalance: 0, status: "Dr");
            acc1.save();
        }
    }


    def dataFeedTaxType() {

        if (!AccountFlag.list().size()) {
            //Basic Tax Type
            Integer uniqueCounter = 0;

            List<AccountFlag> billRef = new ArrayList<AccountFlag>();
            billRef.add(new AccountFlag(name: "Advance", description: "", remark: "billRef", uniqueKey: ++uniqueCounter, parent: null));
            billRef.add(new AccountFlag(name: "Agst Ref.", description: "", remark: "billRef", uniqueKey: ++uniqueCounter, parent: null));
            billRef.add(new AccountFlag(name: "New Ref.", description: "", remark: "billRef", uniqueKey: ++uniqueCounter, parent: null));
            billRef.add(new AccountFlag(name: "On Account", description: "", remark: "billRef", uniqueKey: ++uniqueCounter, parent: null));
            AccountFlag.saveAll(billRef);

            List<AccountFlag> accountFlagArray = new ArrayList<AccountFlag>();
            accountFlagArray.add(new AccountFlag(name: "Excise", description: "", remark: "TAX", uniqueKey: ++uniqueCounter, parent: null));
            accountFlagArray.add(new AccountFlag(name: "Others", description: "", remark: "TAX", uniqueKey: ++uniqueCounter, parent: null));
            accountFlagArray.add(new AccountFlag(name: "VAT", description: "", remark: "TAX", uniqueKey: ++uniqueCounter, parent: null));
            accountFlagArray.add(new AccountFlag(name: "CST", description: "", remark: "TAX", uniqueKey: ++uniqueCounter, parent: null));
            accountFlagArray.add(new AccountFlag(name: "TCS", description: "", remark: "TAX", uniqueKey: ++uniqueCounter, parent: null));
            accountFlagArray.add(new AccountFlag(name: "ServiceTax", description: "", remark: "TAX", uniqueKey: ++uniqueCounter, parent: null));
            accountFlagArray.add(new AccountFlag(name: "TDS", description: "", remark: "TAX", uniqueKey: ++uniqueCounter, parent: null));
            accountFlagArray.add(new AccountFlag(name: "Not Applicable", description: "", remark: "ROUND", uniqueKey: ++uniqueCounter, parent: null));
            accountFlagArray.add(new AccountFlag(name: "Downward Rounding", description: "", remark: "ROUND", uniqueKey: ++uniqueCounter, parent: null));
            accountFlagArray.add(new AccountFlag(name: "Normal Rounding", description: "", remark: "ROUND", uniqueKey: ++uniqueCounter, parent: null));
            accountFlagArray.add(new AccountFlag(name: "Upward Rounding", description: "", remark: "ROUND", uniqueKey: ++uniqueCounter, parent: null));
            accountFlagArray.add(new AccountFlag(name: "Co-Op Society", description: "Type of Organization", remark: "TOO", uniqueKey: ++uniqueCounter, parent: null));
            accountFlagArray.add(new AccountFlag(name: "Individual/Proprietary", description: "Type of Organization", remark: "TOO", uniqueKey: ++uniqueCounter, parent: null));
            accountFlagArray.add(new AccountFlag(name: "Others", description: "Type of Organization", remark: "TOO", uniqueKey: ++uniqueCounter, parent: null));
            accountFlagArray.add(new AccountFlag(name: "partnership", description: "Type of Organization", remark: "TOO", uniqueKey: ++uniqueCounter, parent: null));
            accountFlagArray.add(new AccountFlag(name: "Registered Private Ltd Company", description: "Type of Organization", remark: "TOO", uniqueKey: ++uniqueCounter, parent: null));
            accountFlagArray.add(new AccountFlag(name: "Registered Public Ltd Company", description: "Type of Organization", remark: "TOO", uniqueKey: ++uniqueCounter, parent: null));
            accountFlagArray.add(new AccountFlag(name: "Register Trust", description: "Type of Organization", remark: "TOO", uniqueKey: ++uniqueCounter, parent: null));
            accountFlagArray.add(new AccountFlag(name: "Society", description: "Type of Organization", remark: "TOO", uniqueKey: ++uniqueCounter, parent: null));
            accountFlagArray.add(new AccountFlag(name: "Composition", description: "Type of Dealer", remark: "TOD", uniqueKey: ++uniqueCounter, parent: null));
            accountFlagArray.add(new AccountFlag(name: "Composition to Regular", description: "Type of Dealer", remark: "TOD", uniqueKey: ++uniqueCounter, parent: null));
            accountFlagArray.add(new AccountFlag(name: "Regular", description: "Type of Dealer", remark: "TOD", uniqueKey: ++uniqueCounter, parent: null));
            accountFlagArray.add(new AccountFlag(name: "Government", description: "List of Company Type", remark: "LOC", uniqueKey: ++uniqueCounter, parent: null));
            accountFlagArray.add(new AccountFlag(name: "Others", description: "List of Company Type", remark: "LOC", uniqueKey: ++uniqueCounter, parent: null));
            AccountFlag.saveAll(accountFlagArray);
            // Child of Tax Type
            List<AccountFlag> accountFlagArray1 = new ArrayList<AccountFlag>();
            accountFlagArray1.add(new AccountFlag(name: "Additional Duty", description: "DH (Duty Head)", remark: "DH", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("Excise")));
            accountFlagArray1.add(new AccountFlag(name: "Cess on Custom Duty", description: "DH (Duty Head)", remark: "DH", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("Excise")));
            accountFlagArray1.add(new AccountFlag(name: "Cess on Duty", description: "DH (Duty Head)", remark: "DH", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("Excise")));
            accountFlagArray1.add(new AccountFlag(name: "Customs Duty", description: "DH (Duty Head)", remark: "DH", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("Excise")));
            accountFlagArray1.add(new AccountFlag(name: "Excise Duty", description: "DH (Duty Head)", remark: "DH", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("Excise")));
            accountFlagArray1.add(new AccountFlag(name: "Special Duty", description: "DH (Duty Head)", remark: "DH", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("Excise")));
            AccountFlag.saveAll(accountFlagArray1);

            List<AccountFlag> accountFlagArray2 = new ArrayList<AccountFlag>();
            accountFlagArray2.add(new AccountFlag(name: "Additional Duty", description: "", remark: "", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("Additional Duty")));
            accountFlagArray2.add(new AccountFlag(name: "Additional Duty", description: "", remark: "", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("Cess on Custom Duty")));
            accountFlagArray2.add(new AccountFlag(name: "Surcharge on Tax", description: "", remark: "", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("Cess on Custom Duty")));
            accountFlagArray2.add(new AccountFlag(name: "Additional Duty", description: "", remark: "", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("Cess on Duty")));
            accountFlagArray2.add(new AccountFlag(name: "Surcharge on Tax", description: "", remark: "", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("Cess on Duty")));
            accountFlagArray2.add(new AccountFlag(name: "On Total Sales", description: "", remark: "", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("Customs Duty")));
            accountFlagArray2.add(new AccountFlag(name: "Tax based on Item rate", description: "", remark: "", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("Customs Duty")));
            accountFlagArray2.add(new AccountFlag(name: "On Total Sales", description: "", remark: "", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("Excise Duty")));
            accountFlagArray2.add(new AccountFlag(name: "Tax based on Item rate", description: "", remark: "", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("Excise Duty")));
            accountFlagArray2.add(new AccountFlag(name: "On Total Sales", description: "", remark: "", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("Special Duty")));
            accountFlagArray2.add(new AccountFlag(name: "Tax based on Item rate", description: "", remark: "", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("Special Duty")));
            AccountFlag.saveAll(accountFlagArray2);

            List<AccountFlag> accountFlagArray3 = new ArrayList<AccountFlag>();
            accountFlagArray3.add(new AccountFlag(name: "Additional Duty", description: "", remark: "", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("CST")));
            accountFlagArray3.add(new AccountFlag(name: "On Total Sales", description: "", remark: "", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("CST")));
            accountFlagArray3.add(new AccountFlag(name: "Surcharge on Tax", description: "", remark: "", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("CST")));
            accountFlagArray3.add(new AccountFlag(name: "Tax based on Item rate", description: "", remark: "", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("CST")));
            accountFlagArray3.add(new AccountFlag(name: "Additional Duty", description: "", remark: "", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("Others")));
            accountFlagArray3.add(new AccountFlag(name: "On Total Sales", description: "", remark: "", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("Others")));
            accountFlagArray3.add(new AccountFlag(name: "Surcharge on Tax", description: "", remark: "", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("Others")));
            accountFlagArray3.add(new AccountFlag(name: "Tax based on Item rate", description: "", remark: "", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("Others")));
            AccountFlag.saveAll(accountFlagArray3);

            List<AccountFlag> accountFlagArray4 = new ArrayList<AccountFlag>();
            //++uniqueCounter10 of SC
            accountFlagArray4.add(new AccountFlag(name: "Advertising Agency", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Advertising Space Or Time", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Airport Services", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Air Transport of Passenger", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Air Travel Agency", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Architects Services", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Asset Management", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "ATM Operations", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Auctioneer's Services", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Banking and Financial", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Beauty Parlours", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Broadcasting Services", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Business and Exhibition Services", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Business Auxiliary Services", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Business Support Services", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Cable Operators", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Cargo Handling Services", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Chartered Accountants", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Cleaning Services", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Clearing and Forwarding Agency", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Commercial Training & Coaching", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Company Secretaries", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Construction of Res.Complex", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Construction Services in Commercial/Industrial/Civil", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Consulting Engineering", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Container By Rail", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Convention Service", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Cost Accountant", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Courier Agency", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Credit Card Related Services", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Credit Rating Agencies", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Custom House Agent", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Design Services", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Development and Supply of Content", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Dredging Services", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Dry Cleaning Services", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Erection,Commissioning and Installation", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Event Management Services", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Facsimile Services", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Fashion Designer Services", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Forward Contract Services", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Franchise Services", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "General Insurance Business", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Goods Transport Operator", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Health Club and Fitness Centre", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Information Technology Software Centre", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Insurance Auxiliary", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Intellectual Property Services Other than Copyright", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Interior Decorators", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Internet Cafe", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Internet Telecommunication Services", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Leased Circuits", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Life Insurance Services", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Mailing Lite Compilation", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Maintenance and Repair Service", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Managements Consultants", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Management Of Investment in ULIP", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Mandap Keeper", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Manpower Recruiter Agency", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Market Research Agency", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Mechanized Slaughter House", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "MemberShip Of Clubs", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Mining of Mineral,oil and Gas", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Online Information and Data", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Opinion Poll Services", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Outdoor Catering", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Packaging Services", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Pager Services", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Pandal Or Shamiana Services", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Photography Service", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Port Services", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Processing and Clearing House Agent Service", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Public Relations Service", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Rail Travel Agent", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Real Estate Agent", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Recognized Or Registered Association Service", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Recognized Stock Exchange Service", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Recovery Agent", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Registrar to an Issue", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Rent-A-Cab Service", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Renting of Immovable Property", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Scientific and Technical Consultancy", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Security Agency", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Servicing of Motor Vehicle", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Share Transfer Agent", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Ship Management Service", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Site Preparation And Clearance", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Sound Recording And Service", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Sponsorship Service", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Steamer Agent", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Stock Broker", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Storage And Warehouse Service", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Supply of Tangible Goods Service", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Survey and Map Making", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Telecommunication Service", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Telegraph Service", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Telephone Services", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Telex Services", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Test,Inspection,Certification", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Tour Operator", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Transport By Cruise Ships", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Transport of Goods By Air", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Transport of Goods By Pipeline", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Transport of Goods By Road", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Travel Agents(other then Air/rail Agent)", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "TV or Radio Programme Production", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Under Writers", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Video Tap Production", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Works Contract Services", description: "SC ( Service Category )", remark: "SC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            // 42 of NOP
            accountFlagArray4.add(new AccountFlag(name: "Any Other Income", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Any Other Interest on Securities as per Section++uniqueCounter93 ", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Commission on Sale of Lottery Tickets", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Commission Or Brokerage", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Deemed Dividend U/s 2(22)E ", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Fees for Professional And Technical Services", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Fees for Tech. Services Agreement is Made After Feb 29,1964 before April++uniqueCounter,1976 ", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Fees for Tech. Services Agreement is Made After March 31,1976 before June++uniqueCounter,1997 ", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Fees for Tech. Services Agreement is Made After May 31,1997 before June++uniqueCounter,2005 ", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Fees for Tech. Services Agreement is Made on or After June++uniqueCounter,2005 ", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Income By Way  of Long-Term Gains Referred in Section in++uniqueCounter15E", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Income From Foreign Currency Bonds Or Shares of", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Income From Foreign Exchange Assets payable to an indian Citizen", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Income in Respect In Units of Non-Residents", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Income of Foreign Institutional Investor's From", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Insurance Commission", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Interest on 8% Savings Taxable (Bond),2003", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Interest on nSecurities", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Interest Other Then interest On Securities", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Interest Payable By Government and Indian Concern In foreign Currency", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Long Term Capital Gains[Not Being Covered in Sec++uniqueCounter0(33)(36)(38)]", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Other Sums Parable's To A Non-Resident", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Payment Of Compensation on Acquisition Of Immovable Property", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Payments In Respect  of Deposit Under NSS", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Payments In respect of Units to an offShore Find", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Payments On AccountsOf Re-purchase of Units By..", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Payments To Contractors (Other Then Advertisement)", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Payments  to Non-Resident Sportsmen/Sports Association", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Payments to Contractors(Advertisement Contractors)", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Payments to Sub-Contractors", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Rent on Land ,Building or Furniture", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Rent on Plant,Machinery or Equipment", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Royalty(F) Agreement Is Made After May 31,1997 Before June++uniqueCounter,2005", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Royalty(F) Agreement Is Made  Before June++uniqueCounter,1997", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Royalty(F) Agreement Is Made on or After  June++uniqueCounter,2005", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Royalty(G) Agreement Is Made After March 31,1961 Before April++uniqueCounter,1976", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Royalty(G) Agreement Is Made After March 31,1976 Before June++uniqueCounter,1997", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Royalty(G) Agreement Is Made After March 31,1997 Before June++uniqueCounter,2005", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Royalty(G) Agreement Is Made on Or  After June++uniqueCounter,2005", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Short-Term Capital Gains U/s++uniqueCounter11A", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Winning From Horse Race", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Winning From Horse Lotteries and CrossWord Puzzles", description: "NOP (Nature Of Payment)", remark: "NOP", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            // 9 of TCS
            accountFlagArray4.add(new AccountFlag(name: "Alcoholic Liquor For Human Consumption", description: "LTT (List of TCS Types)", remark: "LTT", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TCS")));
            accountFlagArray4.add(new AccountFlag(name: "Any Other Forest Produce (Not Being Tendu Leaves) ", description: "LTT (List of TCS Types)", remark: "LTT", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TCS")));
            accountFlagArray4.add(new AccountFlag(name: "Contractors/Licensee/Leave Relating to Mine/Quarry", description: "LTT (List of TCS Types)", remark: "LTT", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TCS")));
            accountFlagArray4.add(new AccountFlag(name: "Contractors/Licensee/Leave Relating to Parking Lots", description: "LTT (List of TCS Types)", remark: "LTT", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TCS")));
            accountFlagArray4.add(new AccountFlag(name: "Contractors/Licensee/Leave Relating to Toll Plaza", description: "LTT (List of TCS Types)", remark: "LTT", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TCS")));
            accountFlagArray4.add(new AccountFlag(name: "Scrap", description: "LTT (List of TCS Types)", remark: "LTT", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TCS")));
            accountFlagArray4.add(new AccountFlag(name: "Tendu Leaves", description: "LTT (List of TCS Types)", remark: "LTT", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TCS")));
            accountFlagArray4.add(new AccountFlag(name: "Timber Obtained  by Any Mode Other Than Forest Lease", description: "LTT (List of TCS Types)", remark: "LTT", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TCS")));
            accountFlagArray4.add(new AccountFlag(name: "Timber Obtained  Under Forest Lease", description: "LTT (List of TCS Types)", remark: "LTT", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TCS")));

            accountFlagArray4.add(new AccountFlag(name: "Artificial Juridical Person", description: "DT (Deductee Type)", remark: "DT", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Association Of Persons", description: "DT (Deductee Type)", remark: "DT", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Body Of Individuals", description: "DT (Deductee Type)", remark: "DT", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Company – Non Resident", description: "DT (Deductee Type)", remark: "DT", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Company –  Resident", description: "DT (Deductee Type)", remark: "DT", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Co- Operative Society", description: "DT (Deductee Type)", remark: "DT", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Individual/HUF – Non Resident", description: "DT (Deductee Type)", remark: "DT", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Individual/HUF – Resident", description: "DT (Deductee Type)", remark: "DT", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Local Authority", description: "DT (Deductee Type)", remark: "DT", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));
            accountFlagArray4.add(new AccountFlag(name: "Partnership Firm", description: "DT (Deductee Type)", remark: "DT", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TDS")));

            accountFlagArray4.add(new AccountFlag(name: "Exempt", description: "Classification", remark: "Classification", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));
            accountFlagArray4.add(new AccountFlag(name: "Export", description: "Classification", remark: "Classification", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("ServiceTax")));

            accountFlagArray4.add(new AccountFlag(name: "Artificial Juridical Person", description: "CT (Collectee Type)", remark: "CT", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TCS")));
            accountFlagArray4.add(new AccountFlag(name: "Association Of Persons", description: "CT (Collectee Type)", remark: "CT", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TCS")));
            accountFlagArray4.add(new AccountFlag(name: "Body Of Individuals", description: "CT (Collectee Type)", remark: "CT", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TCS")));
            accountFlagArray4.add(new AccountFlag(name: "Company – Non Resident", description: "CT (Collectee Type)", remark: "CT", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TCS")));
            accountFlagArray4.add(new AccountFlag(name: "Company –  Resident", description: "CT (Collectee Type)", remark: "CT", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TCS")));
            accountFlagArray4.add(new AccountFlag(name: "Co- Operative Society", description: "CT (Collectee Type)", remark: "CT", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TCS")));
            accountFlagArray4.add(new AccountFlag(name: "Individual/HUF – Non Resident", description: "CT (Collectee Type)", remark: "CT", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TCS")));
            accountFlagArray4.add(new AccountFlag(name: "Individual/HUF – Resident", description: "CT (Collectee Type)", remark: "CT", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TCS")));
            accountFlagArray4.add(new AccountFlag(name: "Local Authority", description: "CT (Collectee Type)", remark: "CT", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TCS")));
            accountFlagArray4.add(new AccountFlag(name: "Partnership Firm", description: "CT (Collectee Type)", remark: "CT", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("TCS")));
            //  child of CST
            accountFlagArray4.add(new AccountFlag(name: "Inter-State Sales", description: "Vat/Tax class", remark: "VTC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("CST")));
            accountFlagArray4.add(new AccountFlag(name: "Inter State Sales Against Form-E1", description: "Vat/Tax class", remark: "VTC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("CST")));
            accountFlagArray4.add(new AccountFlag(name: "Inter State Sales Against Form-E2", description: "Vat/Tax class", remark: "VTC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("CST")));
            accountFlagArray4.add(new AccountFlag(name: "Inter State Sales at Lower Rate", description: "Vat/Tax class", remark: "VTC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("CST")));
            accountFlagArray4.add(new AccountFlag(name: "Inter State Sales – Exempted", description: "Vat/Tax class", remark: "VTC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("CST")));
            accountFlagArray4.add(new AccountFlag(name: "Inter State Sales – Tax Free", description: "Vat/Tax class", remark: "VTC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("CST")));
            //  child of VAT
            accountFlagArray4.add(new AccountFlag(name: "Input VAT @ 1%", description: "Vat/Tax class", remark: "VTC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("VAT")));
            accountFlagArray4.add(new AccountFlag(name: "Input VAT @ 12.5%", description: "Vat/Tax class", remark: "VTC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("VAT")));
            accountFlagArray4.add(new AccountFlag(name: "Input VAT @ 20%", description: "Vat/Tax class", remark: "VTC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("VAT")));
            accountFlagArray4.add(new AccountFlag(name: "Input VAT @ 4%", description: "Vat/Tax class", remark: "VTC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("VAT")));
            accountFlagArray4.add(new AccountFlag(name: "Input VAT @ 8% on Works Contract", description: "Vat/Tax class", remark: "VTC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("VAT")));
            accountFlagArray4.add(new AccountFlag(name: "Output VAT @ 1%", description: "Vat/Tax class", remark: "VTC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("VAT")));
            accountFlagArray4.add(new AccountFlag(name: "Output VAT @ 12.5%", description: "Vat/Tax class", remark: "VTC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("VAT")));
            accountFlagArray4.add(new AccountFlag(name: "Output VAT @ 20%", description: "Vat/Tax class", remark: "VTC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("VAT")));
            accountFlagArray4.add(new AccountFlag(name: "Output VAT @ 4%", description: "Vat/Tax class", remark: "VTC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("VAT")));
            accountFlagArray4.add(new AccountFlag(name: "Output VAT @ 8% on Works Contract", description: "Vat/Tax class", remark: "VTC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("VAT")));
            accountFlagArray4.add(new AccountFlag(name: "Purchase – Capital Goods @ 12.5%", description: "Vat/Tax class", remark: "VTC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("VAT")));
            accountFlagArray4.add(new AccountFlag(name: "Purchase – Capital Goods @ 4%", description: "Vat/Tax class", remark: "VTC", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("VAT")));
            accountFlagArray4.add(new AccountFlag(name: "On VAT Rate", description: "", remark: "", uniqueKey: ++uniqueCounter, parent: AccountFlag.findByName("VAT")));
            AccountFlag.saveAll(accountFlagArray4);
        }
    }

    def accountSetting() {
        def company = Company.first()
        if (!AccountFeature.findByCompany(company)) new AccountFeature(company: company).save();
        if (!StatutoryInfo.findByCompany(company)) new StatutoryInfo(company: company).save();
    }
}
