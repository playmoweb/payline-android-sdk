package com.myluckyday.test.paylinesdk.app.data

enum class ContextInfoKey(val value: String) {

    AMOUNT_SMALLEST_UNIT("PaylineAmountSmallestUnit"),
    CURRENCY_DIGITS("PaylineCurrencyDigits"),
    CURRENCY_CODE("PaylineCurrencyCode"),

    ORDER_DETAILS("PaylineOrderDetails"),
}

//{  "PaylineAmountSmallestUnit": 100,
//    "PaylineCurrencyDigits": 2,
//    "PaylineCurrencyCode": "EUR",
//    "PaylineBuyerFirstName": "Jean",
//    "PaylineBuyerLastName": "DUPONT",
//    "PaylineBuyerShippingAddress.street2": "Batiment 2",
//    "PaylineBuyerShippingAddress.country": "FR",
//    "PaylineBuyerShippingAddress.name": "Perso",
//    "PaylineBuyerShippingAddress.street1": "5 rue de Marseille",
//    "PaylineBuyerShippingAddress.cityName": "Aix",
//    "PaylineBuyerShippingAddress.zipCode": "13390"
//    "PaylineBuyerMobilePhone": "0605040302",
//    "PaylineBuyerShippingAddress.phone": "0605040302",
//    "PaylineBuyerIp": "127.126.125.100",
//    "PaylineFormattedAmount": "EUR1.00",
//    "PaylineOrderDate": "05/26/2008 10:00",
//    "PaylineOrderRef": "170630111901",
//    "PaylineOrderDeliveryMode": "4",
//    "PaylineOrderDeliveryTime": "1",
//    "PaylineOrderDetails": [
//    { "ref": "1",
//        "price": 998,
//        "quantity": 1,
//        "comment": "commentaire1",
//        "category": "1",
//        "brand": "66999",
//        "subcategory1": "",
//        "subcategory2": "",
//        "additionalData": ""
//    }
//    ],
//}