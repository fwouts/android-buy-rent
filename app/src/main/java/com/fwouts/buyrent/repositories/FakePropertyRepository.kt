package com.fwouts.buyrent.repositories

import com.fwouts.buyrent.domain.Property
import com.fwouts.buyrent.testing.fixtures.PropertyFixtures
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakePropertyRepository @Inject constructor(): PropertyRepository {
    override fun getList(): Flow<List<Property>> {
        return flow {
            delay(3000)
            emit(PropertyFixtures.PROPERTIES)
        }
    }
}