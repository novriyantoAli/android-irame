package co.id.digital.insinyur.irame.data.network

import android.util.Log
import co.id.digital.insinyur.irame.data.db.DB
import co.id.digital.insinyur.irame.data.models.*
import co.id.digital.insinyur.irame.util.Constant

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface API {

    @GET("api/package")
    suspend fun fetchPackage(
        @Header(Constant.AUTHORIZATION) auth: String
    ): Response<List<PackageResponse>>

    @POST("api/package")
    suspend fun storePackage(
        @Header(Constant.AUTHORIZATION) auth: String,
        @Body packages: PackageResponse
    ): Response<PackageResponse>

    @PUT("api/package/{id}")
    suspend fun updatePackage(
        @Header(Constant.AUTHORIZATION) auth: String,
        @Path("id") id: Int,
        @Body packages: PackageResponse
    ): Response<PackageResponse>

    @DELETE("api/package/{id}")
    suspend fun deletePackage(
        @Header(Constant.AUTHORIZATION) auth: String,
        @Path("id") id: Int
    ): Response<MessageResponse>

    @GET("api/rad/nas")
    suspend fun getNAS(
        @Header(Constant.AUTHORIZATION) auth: String
    ): Response<NASResponse>

    @POST("api/rad/nas")
    @FormUrlEncoded
    suspend fun upsertNAS(
        @Header(Constant.AUTHORIZATION) auth: String,
        @Field("nasname") nasname: String,
        @Field("secret") secret: String
    ): Response<NASResponse>

    @GET("api/rad/usergroup")
    suspend fun fetchRadusergroup(
        @Header(Constant.AUTHORIZATION) auth: String
    ): Response<List<RadusergroupResponse>>

    @GET("api/rad/usergroup/load/{username}")
    suspend fun loadProfile(
        @Header(Constant.AUTHORIZATION) auth: String,
        @Path("username") username: String
    ): Response<ProfileResponse>

    @PUT("api/rad/usergroup")
    suspend fun saveProfile(
        @Header(Constant.AUTHORIZATION) auth: String,
        @Body profile: ProfileResponse
    ): Response<RadusergroupResponse>

    @DELETE("api/rad/usergroup/{username}")
    suspend fun deleteRadusergroup(
        @Header(Constant.AUTHORIZATION) auth: String,
        @Path("username") username: String
    ): Response<RadusergroupResponse>

    @GET("api/rad/postauth")
    suspend fun fetchRadpostauth(
        @Header(Constant.AUTHORIZATION) auth: String,
        @Query("username") username: String,
        @Query("id") id: Int,
        @Query("limit") limit: Int
    ): Response<PageRadpostauthResponse>

    @GET("api/users")
    suspend fun fetchUsers(
        @Header(Constant.AUTHORIZATION) auth: String,
        @Query("id") id: Int,
        @Query("limit") limit: Int
    ): Response<PageUsersResponse>

    @GET("api/users")
    suspend fun fetchPagedUsers(
        @Header(Constant.AUTHORIZATION) bearer: String,
        @Query("id") id: Int,
        @Query("limit") limit: Int
    ): PageUsersResponse

    // ======================================
    // TESTING PAGINATION
    // ======================================


    @POST("api/users")
    suspend fun saveUsers(
        @Header(Constant.AUTHORIZATION) auth: String,
        @Body usersResponse: UsersResponse
    ): Response<UsersResponse>


    @GET("api/reseller")
    suspend fun fetchReseller(
            @Header(Constant.AUTHORIZATION) auth: String
    ): Response<List<ResellerResponse>>

    @DELETE("api/reseller/{id}")
    suspend fun deleteReseller(
        @Header(Constant.AUTHORIZATION) auth: String,
        @Path("id") id: Int
    ): Response<ResellerResponse>

    @POST("api/reseller/activated")
    @FormUrlEncoded
    suspend fun activatedReseller(
        @Header(Constant.AUTHORIZATION) auth: String,
        @Field("id") id: Int
    ): Response<ResellerResponse>

    @POST("api/transaction/report")
    @FormUrlEncoded
    suspend fun transactionReport(
        @Header(Constant.AUTHORIZATION) auth: String,
        @Field("date_start") dateStart: String,
        @Field("date_end") dateEnd: String
    ): Response<List<TransactionResponse>>

    @POST("api/transaction/refill")
    @FormUrlEncoded
    suspend fun balanceRefill(
        @Header(Constant.AUTHORIZATION) auth: String,
        @Field("id_reseller") id: Int,
        @Field("balance") balance: Int
    ): Response<MessageResponse>

    @GET("api/transaction/balance/{id}")
    suspend fun balance(
        @Header(Constant.AUTHORIZATION) auth: String,
        @Path("id") id: Int
    ): Response<BalanceResponse>

    @GET("api/menu")
    suspend fun fetchMenu(
        @Header(Constant.AUTHORIZATION) auth: String
    ): Response<List<MenuResponse>>

    @POST("api/menu")
    suspend fun storeMenu(
        @Header(Constant.AUTHORIZATION) auth: String,
        @Body menuResponse: MenuResponse
    ): Response<MenuResponse>

    @PUT("api/menu/{id}")
    suspend fun updateMenu(
        @Header(Constant.AUTHORIZATION) auth: String,
        @Path("id") id: Int,
        @Body menuResponse: MenuResponse
    ): Response<MenuResponse>

    @DELETE("api/menu/{id}")
    suspend fun deleteMenu(
        @Header(Constant.AUTHORIZATION) auth: String,
        @Path("id") id: Int
    ): Response<MenuResponse>

    @POST("api/login")
    @FormUrlEncoded
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<LoginResponse>


    @GET("api/invoice")
    suspend fun fetchInvoice(
        @Header(Constant.AUTHORIZATION) auth: String,
        @Query("id") id: Int,
        @Query("limit") limit: Int
    ): Response<PageInvoiceResponse>

    @POST("api/invoice")
    suspend fun saveInvoice(
        @Header(Constant.AUTHORIZATION) auth: String,
        @Body body: InvoiceResponse
    ): Response<InvoiceResponse>

    @PUT("api/invoice")
    suspend fun updateInvoice(
        @Header(Constant.AUTHORIZATION) auth: String,
        @Body body: InvoiceResponse
    ): Response<InvoiceResponse>

    @DELETE("api/invoice/{id}")
    suspend fun deleteInvoice(
        @Header(Constant.AUTHORIZATION) auth: String,
        @Path("id") id: Int
    ): Response<InvoiceResponse>

    @POST("api/payment/find")
    suspend fun findPayment(
        @Header(Constant.AUTHORIZATION) auth: String,
        @Body body: PaymentResponse
    ): Response<List<PaymentResponse>>

    @POST("api/payment")
    suspend fun savePayment(
        @Header(Constant.AUTHORIZATION) auth: String,
        @Body body: PaymentResponse
    ): Response<PaymentResponse>

    @DELETE("api/payment/{id}")
    suspend fun deletePayment(
        @Header(Constant.AUTHORIZATION) auth: String,
        @Path("id") id: Int
    ): Response<PaymentResponse>

    @GET("api/report/finance/day")
    suspend fun reportFinanceDay(
        @Header(Constant.AUTHORIZATION) bearer: String
    ): Response<List<FinanceReportResponse>>

    @GET("api/report/finance/month")
    suspend fun reportFinanceMonth(
        @Header(Constant.AUTHORIZATION) bearer: String
    ): Response<List<FinanceReportResponse>>

    @GET("api/report/finance/year")
    suspend fun reportFinanceYear(
        @Header(Constant.AUTHORIZATION) bearer: String
    ): Response<List<FinanceReportResponse>>

    @GET("api/report/expiration/users/day")
    suspend fun reportExpirationToday(
        @Header(Constant.AUTHORIZATION) bearer: String
    ): Response<List<UsersResponse>>

    companion object {
        operator fun invoke(networkConnectionInterceptor: Interceptor, db: DB): API {
            val app = db.getAppState().getAppSync()

            // default value
            var host = "172.0.0.1"
            var port = "3031"

            if (app != null){
                host = app.link
                port = app.port
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(10, TimeUnit.MINUTES)
                .build()

            return Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://${host}:${port}/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(API::class.java)
        }
    }
}