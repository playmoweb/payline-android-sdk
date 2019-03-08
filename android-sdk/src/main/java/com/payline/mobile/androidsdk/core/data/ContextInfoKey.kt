package com.payline.mobile.androidsdk.core.data

/**
 * Cette classe d'enumération liste les clés des informations que l'on souhaite récupérer avec la méthode getContextInfo().
 *
 * @link <https://payline.atlassian.net/wiki/spaces/DT/pages/1329037328/PW+-+API+JavaScript>
 */
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
