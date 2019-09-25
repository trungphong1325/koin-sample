package com.treeforcom.koin_sample

import android.content.Context
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.treeforcom.koin_sample.api.CatApi
import com.treeforcom.koin_sample.api.bookingmanage.BookingManageService
import com.treeforcom.koin_sample.api.listuser.ListTrainerTraineeService
import com.treeforcom.koin_sample.api.login.LoginService
import com.treeforcom.koin_sample.cache.Database
import com.treeforcom.koin_sample.model.pref.UserManager
import com.treeforcom.koin_sample.repository.CatRepository
import com.treeforcom.koin_sample.repository.CatRepositoryImpl
import com.treeforcom.koin_sample.repository.bookingmanager.BookingManagerRepository
import com.treeforcom.koin_sample.repository.bookingmanager.BookingManagerRepositoryImpl
import com.treeforcom.koin_sample.repository.listuser.ListTrainerTraineeRepository
import com.treeforcom.koin_sample.repository.listuser.ListTrainerTraineeRepositoryImpl
import com.treeforcom.koin_sample.repository.login.LoginRepository
import com.treeforcom.koin_sample.repository.login.LoginRepositoryImpl
import com.treeforcom.koin_sample.viewmodel.MainViewModel
import com.treeforcom.koin_sample.viewmodel.bookingmanager.BookingManagerViewModel
import com.treeforcom.koin_sample.viewmodel.home.HomeViewModel
import com.treeforcom.koin_sample.viewmodel.login.LoginViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val CAT_API_BASE_URL = "https://api.thecatapi.com/v1/"
val appModules = module {
    // The Retrofit service using our custom HTTP client instance as a singleton
    single {
        createWebService<CatApi>(
            okHttpClient = createHttpClient(androidContext()),
            factory = RxJava2CallAdapterFactory.create(),
            baseUrl = CAT_API_BASE_URL
        )
    }
    single {
        createWebService<LoginService>(
            okHttpClient = createHttpClient(androidContext()),
            factory = RxJava2CallAdapterFactory.create(),
            baseUrl = "http://youpert.3forcom.biz/api/en/"
        )
    }
    single {
        createWebService<ListTrainerTraineeService>(
            okHttpClient = createHttpClient(androidContext()),
            factory = RxJava2CallAdapterFactory.create(),
            baseUrl = "http://youpert.3forcom.biz/api/en/"
        )
    }
    single {
        createWebService<BookingManageService>(
            okHttpClient = createHttpClient(androidContext()),
            factory = RxJava2CallAdapterFactory.create(),
            baseUrl = "http://youpert.3forcom.biz/api/en/"
        )
    }

    single { Room.databaseBuilder(androidContext(), Database::class.java, "database.db").build() }
    // Tells Koin how to create an instance of CatRepository
    factory { get<Database>().catDao() }
    factory { get<Database>().listTrainerTraineeDao() }
    factory<CatRepository> { CatRepositoryImpl(catApi = get(), catDao = get()) }
    factory<LoginRepository> { LoginRepositoryImpl(service = get()) }
    factory<ListTrainerTraineeRepository> { ListTrainerTraineeRepositoryImpl(service = get(), listUserDao = get ()) }
    factory<BookingManagerRepository> { BookingManagerRepositoryImpl(service = get()) }
    // Specific viewModel pattern to tell Koin how to build MainViewModel
    viewModel { MainViewModel(catRepository = get()) }
    viewModel { LoginViewModel(loginRepository = get()) }
    viewModel { HomeViewModel(repository = get()) }
    viewModel { BookingManagerViewModel(repository = get()) }
}

/* Returns a custom OkHttpClient instance with interceptor. Used for building Retrofit service */
fun createHttpClient(context: Context): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    val client = OkHttpClient.Builder()
    client.addInterceptor { chain ->
        val newRequest = chain.request().newBuilder()
            .addHeader("token", "${UserManager.getToken(context)}")
            .build()
        chain.proceed(newRequest)
    }

    client.addInterceptor(httpLoggingInterceptor)
    client.readTimeout(5 * 60, TimeUnit.SECONDS)

    return client.addInterceptor {
        val original = it.request()
        val requestBuilder = original.newBuilder()

        requestBuilder.header("Content-Type", "application/json")
        val request = requestBuilder.method(original.method(), original.body()).build()
        return@addInterceptor it.proceed(request)
    }.build()
}

/* function to build our Retrofit service */
inline fun <reified T> createWebService(
    okHttpClient: OkHttpClient,
    factory: CallAdapter.Factory, baseUrl: String
): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addCallAdapterFactory(factory)
        .client(okHttpClient)
        .build()
    return retrofit.create(T::class.java)
}
