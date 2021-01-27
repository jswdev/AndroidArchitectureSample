package com.example.faceeditor.database

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by JessieChen on 2019/03/14.
 * adb pull /data/data/tw.com.geovision.gvface/files/ .
 */
@Suppress("unused")
open class FaceGroupRealm(
        @PrimaryKey var id: Int = 0,
        var name: String = "",
        var dispName: String = "",
        var dispColor: String = "#000000"
) : RealmObject()