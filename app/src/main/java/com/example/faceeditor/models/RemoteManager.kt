package com.example.faceeditor.models

import com.example.faceeditor.extension.RemoteDataSource
import com.example.faceeditor.extension.Resource
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.*

data class Member(
    val created: String,
    val gender: String,
    val id: Int,
    val image: String,
    val name: String,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)

data class MemberList(
    val info: Info,
    val results: List<Member>
)

data class Info(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: Any
)

interface RemoteService {
    @GET("character")
    suspend fun getAllMembers() : Response<MemberList>

    @GET("character/{id}")
    suspend fun getMember(@Path("id") id: Int): Response<Member>
}

class RemoteManager(retrofit: Retrofit): RemoteDataSource() {

    private var myAPI : RemoteService = retrofit.create(RemoteService::class.java)

    fun getAPI(): RemoteService { return myAPI }

    //suspend fun getAllMembers() = getResult { myAPI.getAllMembers() }

    //suspend fun getMember(id: Int) = getResult { myAPI.getMember(id) }
}