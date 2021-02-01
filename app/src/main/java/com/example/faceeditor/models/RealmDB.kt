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

    init {

        Realm.init(context)
        realmConfig = RealmConfiguration
            .Builder()
            .name("faceEditorRealm.realm")
            .schemaVersion(schemaVersion)
            .deleteRealmIfMigrationNeeded()
            .compactOnLaunch(this)
            .initialData {}.build()
    }

    override fun getFaces(filter: FilterInput?): MutableList<Member> {

        val realm = Realm.getInstance(realmConfig)
        val outputs = mutableListOf<Member>()
        realm.executeTransaction { realmIn ->

            val faces = realmIn.where(Member::class.java)

            filter?.name?.let {
                //faceRecognitionLog.equalTo("faceInfoResult.name", it)
                faces.contains("name", it)
            }

            faces.sort("name",
                //Sort = desc ? asc
                filter?.sortOrder.let {
                    var resSort = Sort.DESCENDING
                    if (it.equals("asc")) {
                        resSort = Sort.ASCENDING
                    }
                    resSort
                }
            )

            faces.findAll().takeIf { it.isNotEmpty() }?.run {

                val copy = realmIn.copyFromRealm(this)
                outputs.addAll(copy)
            }
        }

        realm.close()
        return outputs
    }

    override fun insertFaces(faces: List<Member>) {

        val realm = Realm.getInstance(realmConfig)
        realm.executeTransaction { realmIn ->
            faces.forEach{
                realmIn.insertOrUpdate(it)
            }
        }
        realm.close()
    }

    override fun deleteFace(face: Member) {

        val realm = Realm.getInstance(realmConfig)
        realm.executeTransaction { realmIn ->

            val faces = realmIn.where(Member::class.java)
            val condition = faces.equalTo("id", face.id).findAll()
            condition.deleteAllFromRealm()
        }
        realm.close()
    }

    override fun shouldCompact(totalBytes: Long, usedBytes: Long): Boolean {

            return (totalBytes > usedBytes)
    }
}