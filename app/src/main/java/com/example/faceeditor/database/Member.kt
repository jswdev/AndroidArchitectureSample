package com.example.faceeditor.database

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

@Suppress("unused")
open class Member(
    @PrimaryKey var id: Int? = 0,
    var created: String? = null,
    var gender: String? = null,
    var image: String? = null,
    var name: String? = null,
    var species: String? = null,
    var status: String? = null,
    var type: String? = null,
    var url: String? = null
): RealmObject()

@Suppress("unused")
open class MemberList(
    val info: Info,
    val results: MutableList<Member>
)

open class Info(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: String
)