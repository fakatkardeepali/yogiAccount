package com.sync

class DomainSyncController {
    DomainSyncService domainSyncService
    def index() {}

    def save(def params){
        //make hash table here

        def partyJson=[]
        partyJson.push(
                className:"Party",
                name:"Yogi Systems",
                creditDays:"9",
                officeAddress:"Pune",
                telephoneNo1:"9890937123",
                faxNo:"1245-4567",
                company:1,
                lastUpdatedBy:1,
                status:"Dr",
                underGroup:1
        )

        domainSyncService.save(partyJson[0].className,partyJson)
    }
    def update(int id,def params){
        def partyJson=[]
        partyJson.push(
                className:"Party",
                name:"Yogi Systems",
                creditDays:"9",
                officeAddress:"Pune",
                telephoneNo1:"9890937123",
                faxNo:"1245-4567",
                company:1,
                lastUpdatedBy:1,
                status:"Dr",
                underGroup:1
        )
        domainSyncService.update(partyJson[0].className,partyJson)
    }
    def delete(int id){
        domainSyncService.delete(id)
    }
}
