package com.payline.mobile.androidsdk.core.data

/**
 * États du widget possibles
 */
enum class WidgetState {
    PAYMENT_METHODS_LIST,
    PAYMENT_FAILURE_WITH_RETRY,
    PAYMENT_METHOD_NEEDS_MORE_INFOS,
    PAYMENT_REDIRECT_NO_RESPONSE,
    MANAGE_WEB_WALLET,
    ACTIVE_WAITING,
    PAYMENT_CANCELED_WITH_RETRY,

    // Final states
    PAYMENT_CANCELED,
    PAYMENT_SUCCESS,
    PAYMENT_FAILURE,
    TOKEN_EXPIRED,
    BROWSER_NOT_SUPPORTED,
    PAYMENT_ONHOLD_PARTNER,
    PAYMENT_SUCCESS_FORCE_TICKET_DISPLAY
}
