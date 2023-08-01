package com.ayubu.qreader.network

import com.ayubu.qreader.model.Book
import com.ayubu.qreader.model.Item
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

//Step 2 Working on BookApi Get Request
@Singleton
interface BooksApi {
    @GET("volumes")
    suspend fun getAllBooks(@Query("q") query: String): Book

    @GET("volumes/{bookId}")
    suspend fun getBookInfo(@Path("bookId") bookid:String):Item


}