package com.payline.mobile.androidsdk.payment

import com.payline.mobile.androidsdk.core.data.ContextInfoResult
import com.payline.mobile.androidsdk.core.data.WidgetState

interface PaymentControllerListener {

    /**
     * Appelée lorsque la liste des moyens de paiement a été affichée
     */
    fun didShowPaymentForm()

    /**
     * Appelée lorsque le paiement a été terminé
     */
    fun didFinishPaymentForm(state: WidgetState)

    /**
     * Appelée lorsque l'environnement est connu
     *
     * @param isSandbox isSandbox est à true si l'environnement une production et à false lorsque c'est une homologation
     */
    fun didGetIsSandbox(isSandbox: Boolean)

    /**
     * Appelée lorsque la clé du language du widget est connue
     *
     * @param language language correspond à la langue du widget
     */
    fun didGetLanguage(language: String)

    /**
     * Appelée lorsque l'information du contexte est connue
     */
    fun didGetContextInfo(info: ContextInfoResult)

}
