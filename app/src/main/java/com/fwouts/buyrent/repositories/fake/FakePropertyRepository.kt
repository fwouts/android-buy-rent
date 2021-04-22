package com.fwouts.buyrent.repositories.fake

import androidx.paging.PagingData
import com.fwouts.buyrent.domain.Property
import com.fwouts.buyrent.repositories.ListType
import com.fwouts.buyrent.repositories.PropertyRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakePropertyRepository @Inject constructor(): PropertyRepository {
    companion object {
        private val PROPERTY_1 = Property(
            id = 1,
            price = "Contact Agent",
            address = "51 Imperial Avenue, Bondi",
            bed = 4.0f,
            bath = 2.0f,
            car = 4,
            imageUrls = listOf("cat"),
            agencyLogoUrl = ""
        )
        private val PROPERTY_2 = Property(
            id = 2,
            price = "Auction",
            address = "2/285-295 Bondi Road, Bondi",
            bed = 3.0f,
            bath = 2.0f,
            car = 2,
            imageUrls = listOf("cat"),
            agencyLogoUrl = ""
        )
        private val PROPERTY_3 = Property(
            id = 3,
            price = "AUCTION",
            address = "4/30 Penkivil Street, Bondi",
            bed = 3.0f,
            bath = 2.0f,
            car = 1,
            imageUrls = listOf("cat"),
            agencyLogoUrl = ""
        )

        val PROPERTIES = listOf(PROPERTY_1, PROPERTY_2, PROPERTY_3)
    }

    override fun getList(type: ListType): Flow<PagingData<Property>> {
        return flow {
            delay(3000)
            emit(PagingData.from(PROPERTIES))
        }
    }
}