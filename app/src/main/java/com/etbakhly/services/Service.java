package com.etbakhly.services;


import com.etbakhly.models.CategoryDataModel;
import com.etbakhly.models.MostFamousDataModel;
import com.etbakhly.models.PlaceGeocodeData;
import com.etbakhly.models.PlaceMapDetailsData;
import com.etbakhly.models.SliderDataModel;
import com.etbakhly.models.SpecialKitchenDataModel;

import retrofit2.Call;
import retrofit2.http.GET;
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
    Call<MostFamousDataModel> getFamous(@Query(value = "latitude") String latitude,
                                        @Query(value = "longitude") String longitude);

    @GET("api/Catering/Caterer_is_special")
    Call<SpecialKitchenDataModel> getSpecialKitchen();

    @GET("api/Catering/Caterer_free_delivery")
    Call<SpecialKitchenDataModel> getFreeDelivery();

    @GET("api/Catering/indexSlider")
    Call<SliderDataModel> getSlider();

}