package com.common

import com.system.Company
import grails.rest.Resource

@Resource(uri = "/accountFeature", formats = ['json', 'xml'])
class AccountFeature {

    Integer uniqueKey = 0
    Company company
    boolean incomeExpenseStatementInsteadOfPL = false
    boolean allowMultipleCurrency = false
    boolean useDebitCreditNotes = false
    boolean maintainBillWiseDetails = false
    boolean enableChequePrinting = false
    boolean forNontradingAccountAlso = false
    boolean usedAdvanceParameters = false
    boolean activeIntersetCalculation = false
    boolean useNameInReport = false
    boolean useAliasNameInReport = false
    boolean useAliasNameAlongWithNameinForms = false
    boolean dateFormat = false
    boolean decimalCharacterToUseInAmount = false
    boolean usePerfixSymbolForDebitAmount = false
    boolean usePostfixSymbolForDebitAmount = false
    boolean usePerfixSymbolFoCreditAmount = false
    boolean usePostfixSymbolForCreditAmount = false
    boolean useAddressForLedgerAccounts = false
    boolean useContactsDetailsForLedgerAccounts = false
    boolean useSingleEntryModeForPurchase = false
    boolean useSingleEntryModeForSale = false
    boolean useSingleEntryModeForPayment = false
    boolean useSingleEntryModeForRecepit = false
    boolean useSingleEntryModeForContra = false
    boolean useSingleEntryModeForDebit = false
    boolean useSingleEntryModeForCredit = false
    boolean usePaymentRecepitAsContra = false
    boolean useCrDrInsteadOfToBy = false
    boolean warnOnNegativeCashBalance = false
    boolean allowCashAccountsInJournals = false
    boolean showbillwiseDetails = false
    boolean allowIncomeAccountsInSaleVouchers = false
    boolean showLedgerBalance = false
    boolean showBalanceAsOnVoucherDate = false
    boolean allowOthersDetails = false
    boolean allowExpensesFixedAssetsInPurchaseAccounts = false


    static constraints = {
    }

    def beforeInsert() {
        uniqueKey = (last()?.uniqueKey ?: 0) + 1;
    }
}
