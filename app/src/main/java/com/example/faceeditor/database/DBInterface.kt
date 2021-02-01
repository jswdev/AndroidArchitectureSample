package com.example.faceeditor.database

data class FilterInput (
    //--filter--
    var name: String? = null,
    var sortOrder : String? = null // [asc || desc]
)

interface DBInterface {

    fun getFaces(filter: FilterInput?): MutableList<Member>
    fun insertFaces(faces: List<Member>)
    fun deleteFace(face: Member)
}