package com.tritongames.shoppingwishlist.data.repository.checkout

import com.tritongames.shoppingwishlist.data.models.checkout.StripeCustomerApi
import com.tritongames.shoppingwishlist.data.models.checkout.StripeCustomerData
import com.tritongames.shoppingwishlist.util.Resource
import javax.inject.Inject

class DefaultCustomerCheckoutRepository @Inject constructor(private val apiStripeCustomer: StripeCustomerApi) : CustomerCheckoutRepository {
    override suspend fun getCustomerPurchaseInfo(): Resource<StripeCustomerData> {
        return try {
            val customerPurchaseResponse = apiStripeCustomer.getCustomerPurchaseInfo()
            val result = customerPurchaseResponse.body()

            if (customerPurchaseResponse.isSuccessful && result != null) {
                Resource.Success(result)
            }
            else {
                Resource.Error("Unknown error")
            }
        }catch (e : Exception) {
            Resource.Error(e.message ?: "Unknown Error")
        }
    }
}