package com.myluckyday.test.paylinesdk.core.data

enum class ContextInfoKey(val value: String) {

    AMOUNT_SMALLEST_UNIT("PaylineAmountSmallestUnit"),
    CURRENCY_DIGITS("PaylineCurrencyDigits"),
    CURRENCY_CODE("PaylineCurrencyCode"),
    BUYER_FIRST_NAME("PaylineBuyerFirstName"),
    BUYER_LAST_NAME("PaylineBuyerLastName"),
    SHIPPING_ADDRESS_STREET2("PaylineBuyerShippingAddress.street2"),
    SHIPPING_ADDRESS_COUNTRY("PaylineBuyerShippingAddress.country"),
    SHIPPING_ADDRESS_NAME("PaylineBuyerShippingAddress.name"),
    SHIPPING_ADDRESS_STREET1("PaylineBuyerShippingAddress.street1"),
    SHIPPING_ADDRESS_CITYNAME("PaylineBuyerShippingAddress.cityName"),
    SHIPPING_ADDRESS_ZIPCODE("PaylineBuyerShippingAddress.zipCode"),
    BUYER_MOBILE_PHONE("PaylineBuyerMobilePhone"),
    SHIPPING_ADDRESS_PHONE("PaylineBuyerShippingAddress.phone"),
    BUYER_IP("PaylineBuyerIp"),
    FORMATTED_AMOUNT("PaylineFormattedAmount"),
    ORDER_DATE("PaylineOrderDate"),
    ORDER_REF("PaylineOrderRef"),
    ORDER_DELIVERY_MODE("PaylineOrderDeliveryMode"),
    ORDER_DELIVERY_TIME("PaylineOrderDeliveryTime"),
    ORDER_DETAILS("PaylineOrderDetails")
}
