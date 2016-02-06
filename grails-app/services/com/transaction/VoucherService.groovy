package com.transaction

import com.common.AccountFlag
import com.system.Company
import com.system.SystemService
import com.system.User
import grails.converters.JSON
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap

@Transactional
class VoucherService {

    SystemService systemService

    def serviceMethod() {

    }

    def saveVoucherInstance( Voucher voucherInstance, Company company, User user, GrailsParameterMap params, Boolean fromAPI){
        //        def data=JSON.parse(params.tryChild)
//        data.each {d->
//            print(d.className)
//        }

//        def vNewInstance=this.class.classLoader.loadClass("Voucher",true,false)?.newInstance()

        ArrayList<Voucher> voucher = [];
//        bindData(voucherInstance, params, [exclude: ['lastUpdatedBy', 'company', 'date']])

//        voucherInstance.lastUpdatedBy = systemService.getUserById(session['activeUser'].user.id as Long);
//        voucherInstance.lastUpdatedBy = user;
////        voucherInstance.company = systemService.getCompanyObjectById(session['company'].id as Long);
//        voucherInstance.company = company;
//        voucherInstance.date = Date.parse("yyyy-MM-dd", params.date);
//        voucherInstance.amountStatus = VoucherDetails.parentStatus(voucherInstance);
//        voucherInstance.voucherNo = Voucher.getVoucherNumber(voucherInstance.voucherType.id, voucherInstance.date, params.voucherNo)
//
        if(fromAPI){

        } else {
            voucherInstance.lastUpdatedBy = user;
//        voucherInstance.company = systemService.getCompanyObjectById(session['company'].id as Long);
            voucherInstance.company = company;
            voucherInstance.date = Date.parse("yyyy-MM-dd", params.date);
            voucherInstance.amountStatus = VoucherDetails.parentStatus(voucherInstance);
            voucherInstance.voucherNo = Voucher.getVoucherNumber(voucherInstance.voucherType.id, voucherInstance.date, params.voucherNo)
        }
        if (voucherInstance == null) {
//            notFound()
//            TODO
            return null
        }

        if (params.child) {
            def child = null
            if(fromAPI){
                child = params.child
            } else {
                child = JSON.parse(params.child);
            }
            if (child) {
                child.each { c ->
//                    voucherInstance.addToVoucherDetails(VoucherDetails.buildFromJSON(c, session['company'], voucherInstance));
//                    voucher.add(Voucher.buildFromJSON(c, session['company'], voucherInstance, voucherInstance.lastUpdatedBy));
                    voucher.add(Voucher.buildFromJSON(c, company, voucherInstance, voucherInstance.lastUpdatedBy));
                }
            }

            if (voucherInstance.voucherType.property == "Contra") {
                new PartyAccount(billNo: voucherInstance?.voucherNo ?: 0, company: voucherInstance.company,
                        partyName: voucherInstance, crDays: 0,
                        voucher: voucherInstance ?: null,
                        amount: voucherInstance.amount as BigDecimal,
                        remainAmount: voucherInstance.amount as BigDecimal,
                        amountStatus: voucherInstance.amountStatus,
                        lastUpdatedBy: voucherInstance.lastUpdatedBy,
                        narration: voucherInstance?.narration ?: "",
                        billDate: voucherInstance?.lastUpdated ? voucherInstance?.lastUpdated : null,
                        typeOfRef: AccountFlag.findByName("On Account")).save();
            }
        }

        if (voucherInstance.partyName.maintainBill) {
            if (params.billChild) {
                def child = JSON.parse(params.billChild);
                if (child) {
                    child.each { c ->
                        println(c.typeRef)
                        def accountFlag = AccountFlag.get(c?.typeRef as Long);
                        if (accountFlag.name == "Agst Ref.") {

//                            PartyAccount.updateChildByBillSatus(c, session['company'], voucherInstance.partyName)
//                            def partyAccount = PartyAccount.updateChildByBillSatus(c, session['company'], voucherInstance.partyName)   //maintain remaining amount in Party Account
                            def partyAccount = PartyAccount.updateChildByBillSatus(c, company, voucherInstance.partyName)   //maintain remaining amount in Party Account
                            partyAccount.save();
                        } else {
//                            voucherInstance.addToPartyAccount(PartyAccount.buildFromJSON(c, session['company'], voucherInstance.partyName, voucherInstance.lastUpdatedBy));
                            voucherInstance.addToPartyAccount(PartyAccount.buildFromJSON(c, company, voucherInstance.partyName, voucherInstance.lastUpdatedBy));
                        }
//                        voucherInstance.addToVoucherBillDetails(VoucherBillDetails.buildFromJSON(c, session['company'], voucherInstance.partyName, voucherInstance.lastUpdatedBy));
                        voucherInstance.addToVoucherBillDetails(VoucherBillDetails.buildFromJSON(c, company, voucherInstance.partyName, voucherInstance.lastUpdatedBy));
                    }
                }
            }
        }

//        if (voucherInstance.hasErrors()) {
//            return voucherInstance
//        } else {
//            voucherInstance.save()
//            Voucher.saveAll(voucher);
//            Voucher.parametersInsert(voucherInstance.voucherType, voucherInstance.date);
//
//            return true;
//        }

        if(voucherInstance.save()) {
            Voucher.saveAll(voucher);
            Voucher.parametersInsert(voucherInstance.voucherType, voucherInstance.date);
        }
        return voucherInstance
    }
}
