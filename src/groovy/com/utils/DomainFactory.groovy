package com.utils

/**
 * Created by pc-2 on 17/8/15.
 */
class DomainFactory {

    static def createDomain(String className,def map){
        //here we will create instance of class and will save it

    }
}



/*
Our steps to save any map of class
 1.Catch the parameters from API in controller of perticular class
 2.Parse it to get map of key-value pairs
 3.Get className from the map
 4.Get map for perticular class in Account project using switch case
 5.Create instance of that class using CreateDomain method in DomainUtil class
 6.Save the perticular Instance of class
 */