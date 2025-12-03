package com.example.currencyconverter.core.di

import com.example.currencyconverter.BuildConfig
import com.example.currencyconverter.data.network.client.KtorClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModules {
    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient = HttpClient {
        expectSuccess = true
        defaultRequest {
            url("${BuildConfig.BASE_URL}${BuildConfig.API_KEY}/")
        }
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                    isLenient = true
                }
            )
        }
        install(Logging) {
            logger = Logger.SIMPLE
        }
    }

    @Provides
    @Singleton
    fun provideKtorClient(
        client: HttpClient
    ) = KtorClient(client)
}