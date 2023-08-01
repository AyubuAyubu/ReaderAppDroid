package com.ayubu.qreader.di

import com.ayubu.qreader.network.BooksApi
import com.ayubu.qreader.repository.BookRepository
import com.ayubu.qreader.utilis.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//step 3 working on retrofit builder
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //Expose our BookRepository so that everyone can get it
    @Singleton
    @Provides
    fun provideBookRepository(api:BooksApi)=BookRepository(api)

    @Singleton
    @Provides
    fun provideBookApi():BooksApi{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BooksApi::class.java)
    }
}