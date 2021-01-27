package com.example.faceeditor.database

data class FilterInput (

    var timesStart: Long? = null,
    var timesEnd: Long? = null,

    //--filter--
    var deviceID: String? = null,
    var cameraIndex: Int? = null,
    var memberName: String? = null,
    var groupID: String? = null,
    var note1: String? = null,
    var note2: String? = null,
    var wiegandCardNo: String? = null,

    //--page--
    var pageOffSet: Int? = null,
    var pageLimit: Int? = null,

    var faceSnapshot: Boolean = false, // return face snapshot
    var enrollPic : Boolean = false, // return enroll picture
    var sortBy : String? = null, // [time]
    var sortOrder : String? = null // [asc || desc]
)

data class FilterOutput(

    var eventId: String? = null,
    var uuid: String? = null,
    var name: String? = null,
    var age: Int? = null,
    var gender: Int? = null,
    //對應原本的adv_group
    var groupId: Int? = null,
    var photos: List<String>? = null,
    var note1: String? = null,
    var note2: String? = null,
    var wiegandNo: String? = null,
    var wiegand: String? = null,
    var wiegand_bits: Int? = null,
    var group_name: String? = null,
    var snapshot: String? = null,
    var snapshotByteArray: ByteArray? = null,
    var left: Int? = null,
    var top: Int? = null,
    var right: Int? = null,
    var bottom: Int? = null,
    var entry_count: Int? = null,
    var tag: Int? = null,
    var timestamp: Long? = null,
    var deviceID: String? = null,
    var deviceName: String? = null,
    var totalCount: Int? = null
)



interface DBInterface {

    fun getFaces(filter: FilterInput?): MutableList<FilterOutput>
    fun getLogs(filter: FilterInput?): MutableList<FilterOutput>
    fun getDBSize(): String
    fun removeLogs(ts: Long): Int
    fun compactDB(): Boolean

}