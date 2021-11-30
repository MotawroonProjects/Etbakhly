package com.etbakhly.services;


import com.etbakhly.models.BuffetDataModel;
import com.etbakhly.models.CategorDish;
import com.etbakhly.models.CategoryDataModel;
import com.etbakhly.models.CategoryDishDataModel;
import com.etbakhly.models.KitchenDataModel;
import com.etbakhly.models.PlaceGeocodeData;
import com.etbakhly.models.PlaceMapDetailsData;
import com.etbakhly.models.SliderDataModel;
import com.etbakhly.models.UserModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public interface Service {
    @GET("place/findplacefromtext/json")
    Call<PlaceMapDetailsData> searchOnMap(@Query(value = "inputtype") String inputtype,
                                          @Query(value = "input") String input,
                                          @Query(value = "fields") String fields,
                                          @Query(value = "language") String language,
                                          @Query(value = "key") String key
    );

    @GET("geocode/json")
    Call<PlaceGeocodeData> getGeoData(@Query(value = "latlng") String latlng,
                                      @Query(value = "language") String language,
                                      @Query(value = "key") String key);

    @GET("api/Catering/indexCategory")
    Call<CategoryDataModel> getCategory();


    @GET("api/Catering/most_famous")
    Call<KitchenDataModel> getFamous(@Query(value = "latitude") String latitude,
                                        @Query(value = "longitude") String longitude);

    @GET("api/Catering/Caterer_is_special")
    Call<KitchenDataModel> getSpecialKitchen();

    @GET("api/Catering/Caterer_free_delivery")
    Call<KitchenDataModel> getFreeDelivery();

    @GET("api/Catering/indexSlider")
    Call<SliderDataModel> getSlider();

    @GET("api/Catering/indexCaterer")
    Call<KitchenDataModel> getKitchenCategory(@Query(value = "category_id") String category_id);

    @GET("api/Catering/indexBuffets")
    Call<BuffetDataModel> getBuffetList();

    @GET("api/Catering/indexCategoryDishes")
    Call<CategoryDishDataModel> getDishes(@Query(value = "category_dishes_id")String category_dishes_id);

    @FormUrlEncoded
    @POST("api/Catering/login")
    Call<UserModel> login(@Field(value = "phone_code") String phone_code,
                          @Field(value = "phone") String phone,
                          @Field(value = "yes_i_read_it") String yes_i_read_it
    );

    @FormUrlEncoded
    @POST("api/Catering/signup")
    Call<UserModel> signUpWithoutImage(@Field("name") String name,
                                       @Field("phone") String phone,
                                       @Field("phone_code") String phone_code,
                                       @Field("email") String email,
                                       @Field("longitude") String longitude,
                                       @Field("latitude") String latitude );

    @Multipart
    @POST("api/Catering/signup")
    Call<UserModel> signUpWithImage(@Part("name")RequestBody name,
                                    @Part("phone")RequestBody phone,
                                    @Part("phone_code")RequestBody phone_code,
                                    @Part("email")RequestBody email,
                                    @Part("longitude")RequestBody longitude,
                                    @Part("latitude")RequestBody latitude,
                                    @Part MultipartBody.Part image
    );
}