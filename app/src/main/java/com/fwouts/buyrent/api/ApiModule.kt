package com.fwouts.buyrent.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Provides fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(DwellingTypeAdapter())
            .add(SearchModeAdapter())
            .add(PolymorphicJsonAdapterFactory.of(SearchListing::class.java, "listing_type")
                .withSubtype(PropertyListing::class.java, "property")
                .withSubtype(TopSpotListing::class.java, "topspot")
                .withSubtype(ProjectListing::class.java, "project"))
            .build()
    }

    @Provides fun provideRetrofit(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://domain-adapter-api.domain.com.au/v1/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides fun provideBuyRentApi(retrofit: Retrofit): BuyRentApi {
        return retrofit.create(BuyRentApi::class.java)
    }
}