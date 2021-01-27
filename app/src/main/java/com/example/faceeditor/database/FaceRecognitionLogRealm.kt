package com.example.faceeditor.database

import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.LinkingObjects

/**
 * Created by JessieChen on 2019/03/14.
 * adb pull /data/data/tw.com.geovision.gvface/files/ .
 */

@Suppress("unused")
open class FaceRecognitionLogRealm(
        var id: String = System.currentTimeMillis().toString(),
        var snapshotPic: ByteArray = byteArrayOf(),
        var age: Int = 0,
        var confidence: Float = 0f,
        var gender: Int = 0,
        var left: Int = 0,
        var top: Int = 0,
        var right: Int = 0,
        var bottom: Int = 0,
        var createdTime: Long = System.currentTimeMillis()
) : RealmObject() {
        @LinkingObjects("faceLogs")
        val faceInfoResult: RealmResults<FaceInfoRealm>? = null
}
