package com.example.hungerspot

class orders{
    var notes:String?=null;
//    var imgurl:String?=null;
    var timefrom:String?=null;
    var timetill:String?=null;


    constructor(){

    }
    constructor(notes: String?, timefrom: String?, timetill: String?) {
        this.notes = notes
//        this.imgurl = imgurl
        this.timefrom = timefrom
        this.timetill = timetill
    }
}