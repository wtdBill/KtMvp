package ktmvp.yppcat.ktmvp.network

import ktmvp.yppcat.ktmvp.BuildConfig
import ktmvp.yppcat.ktmvp.utils.AppUtils
import ktmvp.yppcat.ktmvp.utils.NetworkUtil
import ktmvp.yppcat.ktmvp.application.MyApplication
import ktmvp.yppcat.ktmvp.network.service.JuheService
import ktmvp.yppcat.ktmvp.utils.Constants
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by ypp0623 on 19-2-18.
 */
object RetrofitManager {

    private var token: String = ""
    private val juheUrl = "http://v.juhe.cn/"


    private fun addQueryParamterInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originRequesr = chain.request()
            val request: Request
            val modifiledUrl = originRequesr.url().newBuilder()
                    .build()
            request = originRequesr.newBuilder().url(modifiledUrl).build()
            chain.proceed(request)
        }
    }

    private fun addHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
                    .method(originalRequest.method(), originalRequest.body())
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    private fun addCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            if (!NetworkUtil.isNetworkAvailable(MyApplication.context)) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build()
            }
            val response = chain.proceed(request)
            if (NetworkUtil.isNetworkAvailable(MyApplication.context)) {
                val maxAge = 0
                response.newBuilder()
                        .header("Cache-Control", "public,max-age=" + maxAge)
                        .removeHeader("Retrofit")
                        .build()
            } else {
                val maxStale = 60 * 60 * 24 * 28
                response.newBuilder()
                        .header("Cache-Control", "public,only-if-cached, max-stale=" + maxStale)
                        .removeHeader("nyn")
                        .build()
            }
            response
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(juheUrl)
                .client(getOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }


    private fun getOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        val cacheFile = File(MyApplication.context.cacheDir, "cache")
        val cache = Cache(cacheFile, 1024 * 1024 * 50)
        return OkHttpClient().newBuilder()
                .addInterceptor(addQueryParamterInterceptor())
                .addInterceptor(addHeaderInterceptor())
                .addInterceptor(addCacheInterceptor())
                .addInterceptor(httpLoggingInterceptor)
                .cache(cache)
                .connectTimeout(60L, TimeUnit.SECONDS)
                .readTimeout(60L, TimeUnit.SECONDS)
                .writeTimeout(60L, TimeUnit.SECONDS)
                .build()
    }

    val juheService: JuheService by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        getRetrofit().create(JuheService::class.java)
    }
}