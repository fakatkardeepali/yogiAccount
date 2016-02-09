package com.transaction

import com.common.AccountFlag
import com.system.Company
import com.system.SystemService
import com.system.User
import grails.converters.JSON
import grails.transaction.Transactional

@Transactional
class VoucherService {

    SystemService systemService

    def serviceMethod() {

    }

    def saveVoucherInstance( Voucher voucherInstance, Company company, User user, Map params, Boolean fromAPI){
        //        def data=JSON.parse(params.tryChild)
//        data.each {d->
//            print(d.className)
//        }

//        def vNewInstance=this.class.classLoader.loadClass("Voucher",true,false)?.newInstance()

        com.google.gson.Gson gson = new com.google.gson.Gson();
        ArrayList<Voucher> voucher = [];
        def child = null
        String jsonArray = ""
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
            if(fromAPI) {
                println("Child :" + params.child)
                jsonArray = gson.toJson(params.child);
            } else {
                jsonArray = params.child
            }
                child = JSON.parse(jsonArray);
            if (child) {
                child.each { c ->
//                    voucherInstance.addToVoucherDetails(VoucherDetails.buildFromJSON(c, session['company'], voucherInstance));
//                    voucher.add(Voucher.buildFromJSON(c, session['company'], voucherInstance, voucherInstance.lastUpdatedBy));
                    println("Child Passed...." + c)
                    if((fromAPI && c.debit > 0)) {
                        voucher.add(Voucher.buildFromJSON(c, company, voucherInstance, voucherInstance.lastUpdatedBy));
                    } else {
                        voucher.add(Voucher.buildFromJSON(c, company, voucherInstance, voucherInstance.lastUpdatedBy));
                    }
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
            child = null
            jsonArray = ""
            if (params.billChild) {
                if(fromAPI) {
                    println("Child :" + params.billChild)
                    jsonArray = gson.toJson(params.billChild);
                } else {
                    jsonArray = params.billChild
                }

                child = JSON.parse(jsonArray);

                if (child) {
                    child.each { c ->
                        println(c.typeRef)
                        def accountFlag = AccountFlag.get(c?.typeRef as Long);
                        if(fromAPI) {
                            c.billDate = Date.parse("MMM dd, yyyy hh:mm:ss a", c.billDate).format("yyyy-MM-dd")
                        }
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
            println("Voucher Before Save :" + voucher)
            Voucher.saveAll(voucher);
            Voucher.parametersInsert(voucherInstance.voucherType, voucherInstance.date);
        }
        return voucherInstance
    }
}
