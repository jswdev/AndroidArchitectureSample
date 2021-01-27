package com.example.faceeditor.database

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by JessieChen on 2019/03/14.
 * adb pull /data/data/tw.com.geovision.gvface/files/ .
 */
@Suppress("unused")
open class FaceInfoRealm(
        @PrimaryKey
        var uuid: String = "",
        var name: String = "",
        //對應原本的adv_group
        var faceGroup: FaceGroupRealm? = null,
        var pictureOriginal: RealmList<ByteArray> = RealmList(),
        var picture: RealmList<ByteArray> = RealmList(),
        var note1: String = "",
        var note2: String = "",
        var wiegandNo: String = "",
        var wiegand: String = "",
        var faceLogs: RealmList<FaceRecognitionLogRealm> = RealmList(),
        var createdTime: Long = 0L,
        var lastUpdateTime: Long = 0L
) : RealmObject()
