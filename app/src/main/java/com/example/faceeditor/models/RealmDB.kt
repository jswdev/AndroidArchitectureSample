package com.example.faceeditor.models

import android.content.Context
import android.util.Base64
import android.util.Log
import com.example.faceeditor.database.*
import io.realm.CompactOnLaunchCallback
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.Sort
import java.io.File

class RealmDB(context: Context): DBInterface, CompactOnLaunchCallback{

    private val schemaVersion: Long = 0
    private var realmConfig: RealmConfiguration

    private fun testQueryRecognitionEvent(it: Realm) {

        val faceCount = 50
        val faceLogCount = 10000
        val name = "geovision"
        val wiggandNo = 0
        val uuid = "1111-2222-3333-4444-"
        val note = arrayOf("sw1", "sw2", "sw3", "sw4", "sw5", "sw6")
        val note2 = arrayOf("teamA", "teamB", "teamC", "teamD", "teamE", "teamF")
        val faceInfoArray = mutableListOf<FaceInfoRealm>()
        val createTime = 1592120630000
        val image = "/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxMRERUSEREVFhUWFRUSFRUYEBITExUYGBUYFxUWFhUaHCggGRolGxMWITEhJikrLi4uFx" +
                "8zODMsNygtLjcBCgoKDg0OGxAQGy0lHyYvLS8tLS8tLS0tLS0vLS0uLS0vLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tNS0tLS0tLf/AABEI" +
                "AOEA4QMBEQACEQEDEQH/xAAbAAEAAQUBAAAAAAAAAAAAAAAABAIDBQYHAf/EAD8QAAIBAgMECAMFBQgDAAAAAAABAgMRBBIhBQYxQRMiUW" +
                "FxgZGxUqHRFDJCYsEHI3KTsjNDU4KSosLhFRbw/8QAGgEBAAIDAQAAAAAAAAAAAAAAAAIDAQQFBv/EADMRAQACAQIDBAgGAwEBAAAAAAAB" +
                "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"
        val image2 = "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"

        val byteArray = Base64.decode(image, Base64.NO_WRAP)
        val fakeSnapshotPic = Base64.decode(image2, Base64.NO_WRAP)

        val faceGroupArray = mutableListOf<FaceGroupRealm>()
        val groupName = arrayOf("VIP_A", "VIP_B", "VIP_C")
        for (i in 0..2){
            val faceGroup = it.copyToRealm(
                FaceGroupRealm(
                    id = (i + 1000),
                    name = groupName[i],
                    dispName = groupName[i]
                )
            )
            faceGroupArray.add(faceGroup)
        }

        for (i in 0..faceCount) {

            val faceInfo = FaceInfoRealm(
                uuid = uuid + i.toString(),
                name = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"[(0..25).random()] + (0..1000).random().toString(),
                faceGroup = faceGroupArray[(0..2).random()],
                note1 = note[(0..5).random()],
                note2 = note2[(0..5).random()],
                wiegandNo = (wiggandNo + i).toString(),
                wiegand = name,
                createdTime = createTime + (i * 1000),
                lastUpdateTime = createTime + (i * 1000)
            )
            //if ((0..5).random() == 1) {
            faceInfo.picture.add(byteArray)
            //}
//            if ((0..5).random() == 2) {
            faceInfo.picture.add(byteArray)
//            }
            //if ((0..5).random() == 3) {
            faceInfo.pictureOriginal.add(byteArray)
            //}

            faceInfoArray.add(faceInfo)
        }

        for (i in 0..faceLogCount) {

            val faceLog =
                FaceRecognitionLogRealm(
                    id = "log$i",
                    snapshotPic = fakeSnapshotPic,
                    age = (0..80).random(),
                    gender = (0..1).random(),
                    left = (0..999).random(),
                    top = (0..999).random(),
                    bottom = (0..999).random(),
                    right = (0..999).random(),
                    createdTime = createTime + (i * 1000)
                )

            faceInfoArray[(0..faceCount).random()].faceLogs.add(faceLog)
        }

        val startTime = System.currentTimeMillis()
        for (faceInfo in faceInfoArray) {
            it.insert(faceInfo)
        }
        val endTime = System.currentTimeMillis()
        val dif = endTime - startTime
        Log.d("QueryRecognitionEvent", "create log time = $dif")
    }

    init {

        Realm.init(context)


        realmConfig = RealmConfiguration
            .Builder()
            .name("faceEditorRealm.realm")
            .schemaVersion(schemaVersion)
            .deleteRealmIfMigrationNeeded()
            .compactOnLaunch(this)
            .initialData {

                testQueryRecognitionEvent(it)
            }.build()
    }

    override fun getFaces(filter: FilterInput?): MutableList<FilterOutput> {

        val realm = Realm.getInstance(realmConfig)

        val outputs = mutableListOf<FilterOutput>()

//        var deviceName: String? = null
//
//        var deviceID: String? = null

        realm.executeTransaction { realmIn ->

            val faceInfos = realmIn.where(FaceInfoRealm::class.java)

            filter?.memberName?.let {
                //faceRecognitionLog.equalTo("faceInfoResult.name", it)
                faceInfos.contains("name", it)
            }

            filter?.wiegandCardNo?.let {
                faceInfos.contains("wiegandNo", it)
            }

            filter?.note1?.let {
                faceInfos.contains("note1", it)
            }

            filter?.note2?.let {
                faceInfos.contains("note2", it)
            }

            filter?.groupID?.let {

                faceInfos.equalTo("faceGroup.id", it.toInt())
            }

//            realm.where(DeviceInfoRealm::class.java).findFirst()?.let {it ->
//
//                deviceID = it.deviceId
//                deviceName = it.deviceName
//            }

            filter?.timesStart?.let { start ->
                filter.timesEnd?.let { end ->
                    faceInfos
                        .greaterThanOrEqualTo("createdTime", start)
                        .lessThanOrEqualTo("createdTime", end)
                }
            }

            filter?.sortBy?.let { sortBy ->

                val resStr = when (sortBy) {
                    "time" -> "createdTime"
                    //"name" -> "name"
                    else -> "name"
                }

                faceInfos.sort(resStr,
                    //Sort = desc ? asc
                    filter.sortOrder.let {
                        var resSort = Sort.DESCENDING
                        if (it.equals("asc")) {
                            resSort = Sort.ASCENDING
                        }
                        resSort
                    }
                )
            }

            val faceLogsResult = faceInfos.findAll()

            if (faceLogsResult.size > 0) {

                val pageFacesResult: MutableList<FaceInfoRealm> = mutableListOf()
                val offset = filter?.pageOffSet ?: 0
                val limit = filter?.pageLimit ?: 100

                if (faceLogsResult.size < offset + limit) {

                    pageFacesResult.addAll(faceLogsResult.subList(offset, faceLogsResult.size))
                } else {

                    pageFacesResult.addAll(faceLogsResult.subList(offset, offset + limit))
                }

                for (data in pageFacesResult) {

                    val picFaceByteArrayList: MutableList<String> = mutableListOf()
                    data.let {
                        it.picture.let { pics ->
                            for (pic: ByteArray in pics) {
                                val encodePic =
                                    Base64.encodeToString(pic, 0, pic.size, Base64.NO_WRAP)
                                picFaceByteArrayList.add(encodePic)
                            }
                        }
                    }

                    outputs.add(
                        FilterOutput(
                            uuid = data.uuid,
                            name = data.name,
                            groupId = data.faceGroup?.id ?: -1,
                            photos = picFaceByteArrayList,
                            note1 = data.note1,
                            note2 = data.note2,
                            wiegandNo = data.wiegandNo,
                            wiegand = data.wiegand,
                            group_name = data.faceGroup?.name ?: "unknown group name",
                            //snapshot = BitmapAndBase64StringToolUtil.convertBytesToB64String(data.snapshotPic),
                            snapshotByteArray = data.pictureOriginal.first(),
//                        left = data.left,
//                        top = data.top,
//                        right = data.right,
//                        bottom = data.bottom,
//                        entry_count = 1,
//                        tag = 0,
                            timestamp = data.createdTime,
//                        deviceName = data.,
                            deviceID = data.uuid,
                            totalCount = faceLogsResult.size
                        )
                    )
                }
            }
        }

        realm.close()

        return outputs
    }

    override fun getLogs(filter: FilterInput?): MutableList<FilterOutput> {

        TODO("Not yet implemented")
    }

    override fun removeLogs(ts: Long): Int {

        val realm = Realm.getInstance(realmConfig)
        var count: Int = 0
        realm.executeTransaction { realmIn ->

            val faceInfos = realmIn.where(FaceRecognitionLogRealm::class.java).lessThan("createdTime", ts).findAll()
            count = faceInfos.size
            faceInfos.deleteAllFromRealm()
        }

        realm.close()
        return count
    }

    override fun getDBSize(): String{

        val realm = Realm.getInstance(realmConfig)
        val path = realm.path
        val file = File(path)
        val kb = file.length() / (1024.0)

        realm.close()
        return "%.2f".format(kb)
    }

    override fun compactDB(): Boolean{

        return Realm.compactRealm(realmConfig)
    }

    override fun shouldCompact(totalBytes: Long, usedBytes: Long): Boolean {

            return (totalBytes > usedBytes)
    }
}