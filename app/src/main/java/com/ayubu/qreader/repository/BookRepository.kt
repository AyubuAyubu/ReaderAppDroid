package com.ayubu.qreader.repository

import com.ayubu.qreader.data.DataOrException
import com.ayubu.qreader.model.Item
import com.ayubu.qreader.network.BooksApi
import javax.inject.Inject

//Step 4 Dependancy Injection of BooksApi and create custom DataOrException class
class BookRepository @Inject constructor(private val api:BooksApi) {
    private val dataOrException=DataOrException<List<Item>,Boolean,Exception>()
    private val bookInfoDataOrException=DataOrException<Item,Boolean,Exception>()

    suspend fun getBooks(searchQuery:String):
            DataOrException<List<Item>,Boolean,Exception>{
            try{
                dataOrException.loading=true
                dataOrException.data=api.getAllBooks(searchQuery).items
                if (dataOrException.data!!.isNotEmpty()) dataOrException.loading=false

            }catch (e:Exception){
                dataOrException.e=e
            }
        return dataOrException
    }
    suspend fun getBookInfo(bookId:String):
            DataOrException<Item,Boolean,Exception>{
        val response=try{
            bookInfoDataOrException.loading=true
            bookInfoDataOrException.data= api.getBookInfo(bookId)
            if (bookInfoDataOrException.data.toString().isNotEmpty()) bookInfoDataOrException.loading=false
            else{}
        }catch (e:Exception){
            bookInfoDataOrException.e=e
        }
        return bookInfoDataOrException
    }
}