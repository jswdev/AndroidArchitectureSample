package com.example.faceeditor.models

import com.example.faceeditor.database.MemberList
import com.example.faceeditor.extension.RemoteDataSource
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.*

interface RemoteService {
    @GET("character")
    suspend fun getAllMembers() : Response<MemberList>

//    @GET("character/{id}")
//    suspend fun getMember(@Path("id") id: Int): Response<Member>
}

class RemoteManager(retrofit: Retrofit): RemoteDataSource() {

    private var myAPI : RemoteService = retrofit.create(RemoteService::class.java)

    suspend fun getAllMembers() =  getResult { myAPI.getAllMembers() }

    //suspend fun getMember(id: Int) = getResult { myAPI.getMember(id) }
}