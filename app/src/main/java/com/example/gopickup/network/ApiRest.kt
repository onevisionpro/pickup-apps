package com.example.gopickup.network

import com.example.gopickup.base.BaseRequest
import com.example.gopickup.base.BaseResponse
import com.example.gopickup.model.request.*
import com.example.gopickup.model.response.*
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiRest {

    @POST("api/login")
    fun postLogin(@Body login: Login): Observable<BaseResponse<User>>

    @POST("api/login")
    fun postOTP(@Body login: Login): Observable<BaseResponse<User>>

    @POST("api/login/resendOTP")
    fun postResendOTP(@Body baseRequest: BaseRequest<ResendOTPRequest>): Observable<BaseResponse<Any>>

    @POST("api/version/checker")
    fun postVersionChecker(@Body baseRequest: BaseRequest<String>): Observable<BaseResponse<VersionChecker>>

    /**
     * Home Page
     */
    @POST("api/home/information")
    fun getHomeInformation(@Body baseRequest: BaseRequest<String>): Observable<BaseResponse<HomeInformation>>

    @POST("api/home/recentOrder")
    fun getRecentOrderItems(@Body recentOrderRequest: BaseRequest<RecentOrder>): Observable<BaseResponse<List<RecentOrderItem>>>

    /**
     * Profile
     */
    @POST("api/profile/information")
    fun getProfile(@Body profileRequest: BaseRequest<String>): Observable<BaseResponse<Profile>>

    @POST("api/profile/changeProfile")
    fun postEditProfile(@Body profile: BaseRequest<EditProfile>): Observable<BaseResponse<Any>>

    @POST("api/profile/changePassword")
    fun postResetPassword(@Body resetPassword: BaseRequest<ResetPassword>): Observable<BaseResponse<Any>>

    /**
     * History Order
     */
    @POST("api/order/historyOrder")
    fun getHistoryOrderList(@Body historyOrderRequest: BaseRequest<TrackId>): Observable<BaseResponse<List<HistoryOrder>>>

    @POST("api/order/historyOrder")
    fun getHistoryOrderDetails(@Body historyOrderRequest: BaseRequest<TrackId>): Observable<BaseResponse<List<OrderDetails>>>

    /**
     * Order
     */
    @POST("utility/getData/warehouses")
    fun getWarehouses(@Body baseRequest: BaseRequest<String>): Observable<BaseResponse<List<Warehouse>>>

    @POST("utility/getData/items")
    fun getItemsWarehouse(@Body baseRequest: BaseRequest<String>): Observable<BaseResponse<List<ItemWarehouse>>>

    @POST("api/order/createOrder")
    fun postCreateOrder(@Body createOrder: BaseRequest<CreateOrder>): Observable<BaseResponse<TrackId>>

    @POST("api/order/myOrder")
    fun getMyOrderList(@Body trackId: BaseRequest<TrackId>): Observable<BaseResponse<List<Order>>>

    @POST("api/order/myOrder")
    fun getMyOrderDetails(@Body trackId: BaseRequest<TrackId>): Observable<BaseResponse<List<OrderDetails>>>

    @POST("api/order/editOrder")
    fun postEditOrder(@Body editOrder: BaseRequest<EditOrder>): Observable<BaseResponse<OrderId>>

    @POST("api/order/cancelOrder")
    fun postCancelOrder(@Body trackId: BaseRequest<TrackId>): Observable<BaseResponse<Any>>

    @POST("api/order/openOrder")
    fun getOpenOrderList(@Body trackId: BaseRequest<TrackId>): Observable<BaseResponse<List<Order>>>

    @POST("api/order/openOrder")
    fun getOpenOrderDetails(@Body trackId: BaseRequest<TrackId>): Observable<BaseResponse<List<OrderDetails>>>

    @POST("api/order/bookOrder")
    fun postBookOrder(@Body trackId: BaseRequest<TrackId>): Observable<BaseResponse<Any>>

    @POST("api/order/takeOrder")
    fun postTakeOrder(@Body takeOrder: BaseRequest<TakeOrder>): Observable<BaseResponse<Any>>

    @POST("api/order/previewBA")
    fun getPreviewBA(@Body trackId: BaseRequest<TrackId>): Observable<BaseResponse<PreviewBA>>

    @POST("api/order/sendOrder")
    fun postSendOrder(@Body sendOrder: BaseRequest<SendOrder>): Observable<BaseResponse<Any>>

    /**
     * More
     */
    @POST("utility/getData/extentionWording")
    fun getWordings(@Body moreType: BaseRequest<Type>): Observable<BaseResponse<List<AboutApps>>>
}
