package com.example.submission2fx.core.dinjct

import android.util.Log
import androidx.room.Room
import com.example.submission2fx.core.data.GhibliAppRepository
import com.example.submission2fx.core.data.source.RemoteDataSource
import com.example.submission2fx.core.data.source.localdb.LocalDataSource
import com.example.submission2fx.core.data.source.localdb.roomDb.MovieDatabase
import com.example.submission2fx.core.data.source.remote.netzwork.ApiGhibli
import com.example.submission2fx.core.domain.repository.IGhibliAppRepositoryi
import com.example.submission2fx.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val databaseModule = module {
    factory { get<MovieDatabase>().movieDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("submission2fx".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            MovieDatabase::class.java, "ghibli.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        val hostname = "ghibliapi.herokuapp.com"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/9SMJwIglm/+G8ayiSmEVVVXrH5yzWx7Xkc33p+/0meo=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://ghibliapi.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiGhibli::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IGhibliAppRepositoryi> { GhibliAppRepository(get(), get(), get()) }
}