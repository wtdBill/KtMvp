package ktmvp.yppcat.ktmvp.network.service

import io.reactivex.Observable
import ktmvp.yppcat.ktmvp.network.bean.DimenData
import ktmvp.yppcat.ktmvp.network.bean.JokeData
import ktmvp.yppcat.ktmvp.network.bean.NewsData
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
    fun getNews(@Field("type") type: String, @Field("key") key: String = Constants.JUHE_NEWS_KEY): Observable<NewsData>

    @POST(Constants.JUHE_JOKE)
    @FormUrlEncoded
    fun getJoke(@Field("sort") sort: String, @Field("page") page: Int, @Field("pagesize") pagesize: Int,
                @Field("time") time: String, @Field("key") key: String): Observable<JokeData>

    @POST(Constants.JUHE_DIMEN)
    @FormUrlEncoded
    fun getDimenData(@Field("text") text: String, @Field("w") w: Int, @Field("key") key: String = Constants.JUHE_DIMEN_KEY): Observable<DimenData>
}
