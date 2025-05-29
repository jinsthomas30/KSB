package com.example.ksb.di

import com.example.ksb.BuildConfig
import com.example.ksb.datalist.data.remote.ApiService
import com.example.ksb.datalist.data.repository.DataRepositoryImpl
import com.example.ksb.datalist.domain.repository.DataRepository
import com.example.ksb.datalist.domain.usecase.DataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideApiService(): ApiService{
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    fun provideDataRepository(apiService: ApiService): DataRepository = DataRepositoryImpl(apiService)

    @Provides
    fun provideDataUsecase(dataRepository: DataRepository): DataUseCase = DataUseCase(dataRepository)
}