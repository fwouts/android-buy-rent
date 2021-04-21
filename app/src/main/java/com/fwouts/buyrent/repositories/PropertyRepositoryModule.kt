package com.fwouts.buyrent.repositories

import com.fwouts.buyrent.repositories.remote.RemotePropertyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PropertyRepositoryModule {

    @Binds
    abstract fun bindPropertyRepository(
        impl: RemotePropertyRepository
    ): PropertyRepository
}