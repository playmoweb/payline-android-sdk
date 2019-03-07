package com.payline.mobile.payment

import com.payline.mobile.core.data.ContextInfoResult

interface PaymentControllerListener {

    /**
     * Appelée lorsque la liste des moyens de paiement a été affichée
     */
    fun didShowPaymentForm()

    /**
     * Appelée lorsque la liste des moyens de paiement a été fermée sans avoir effectué ou sélectionné un moyen de paiement
     */
    fun didCancelPaymentForm()

    /**
     * Appelée lorsque le paiement a été terminé
     */
    fun didFinishPaymentForm()

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
