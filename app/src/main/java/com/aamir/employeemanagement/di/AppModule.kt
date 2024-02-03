package com.aamir.employeemanagement.di

import com.aamir.employeemanagement.networkUtils.EmployeeApi
import com.aamir.employeemanagement.networkUtils.EndPoints.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideEmployeeApi(okHttpClient: OkHttpClient): EmployeeApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL) // Replace with your API base URL
            .client(okHttpClient) // Use the OkHttpClient with the logging interceptor
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EmployeeApi::class.java)
    }
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Set the desired logging level
        }
    }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor) // Add the logging interceptor
            .build()
    }
}
