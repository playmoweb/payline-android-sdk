package com.payline.mobile.tokenfetcher

import org.json.JSONObject
import java.util.*

data class FetchTokenParams(
    val type: Type,
    val data: JSONObject
) {
    enum class Type {
        PAYMENT,
        WALLET
    }

    companion object {

        @JvmStatic
        fun testPaymentParams(): FetchTokenParams {
            return FetchTokenParams(
                type = Type.PAYMENT,
                data = JSONObject().apply {
                    put("orderRef", UUID.randomUUID().toString())
                    put("amount", 5)
                    put("currencyCode", "EUR")
                    put("languageCode", "FR")
                    put("buyer", JSONObject().apply {
                        put("email", "John.Doe@gmail.com")
                        put("firstname", "John")
                        put("lastname", "Doe")
                        put("mobilePhone", "0123456789")
                        put("shippingAddress", JSONObject().apply {
                            put("city", "Aix-en-Provence")
                            put("country", "FR")
                            put("firstname", "John")
                            put("lastname", "Doe")
                            put("phone", "0123456789")
                            put("street1", "15 rue de Rue")
                            put("zipCode", "69002")
                        })
                    })
                }
            )
        }

        @JvmStatic
        fun testWalletParams(): FetchTokenParams {
            return FetchTokenParams(
                type = Type.WALLET,
                data = JSONObject().apply {
                    put("buyer", JSONObject().apply {
                        put("email", "John.Doe@gmail.com")
                        put("firstname", "John")
                        put("lastname", "Doe")
                        put("mobilePhone", "0123456789")
                        put("shippingAddress", JSONObject().apply {
                            put("city", "Aix-en-Provence")
                            put("country", "FR")
                            put("firstname", "John")
                            put("lastname", "Doe")
                            put("phone", "0123456789")
                            put("street1", "15 rue de Rue")
                            put("zipCode", "69002")
                        })
                        put("walletId", "12342414-DFD-13434141")
                    })
                    put("updatePersonalDetails", false)
                    put("languageCode", "EN")
                }
            )
        }
    }
}