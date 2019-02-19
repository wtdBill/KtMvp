package ktmvp.yppcat.ktmvp.network.service

import com.example.ypp.life.network.entity.JokeEntity
import com.example.ypp.life.network.entity.NewsEntity
import io.reactivex.Observable
import ktmvp.yppcat.ktmvp.utils.Constants
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by ypp0623 on 19-2-19.
 */
interface JuheService {

    @POST(Constants.JUHE_NEWS)
    @FormUrlEncoded
    fun getNews(@Field("type") type: String, @Field("key") key: String = Constants.JUHE_NEWS_KEY): Observable<NewsEntity>

    @POST(Constants.JUHE_JOKE)
    @FormUrlEncoded
    fun getJoke(@Field("sort") sort: String, @Field("page") page: Int, @Field("pagesize") pagesize: Int,
                @Field("time") time: String, @Field("key") key: String): Observable<JokeEntity>
}
