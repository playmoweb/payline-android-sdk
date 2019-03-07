# PaylineSDK

# Description

Le SDK Payline est un kit de développement qui va permettre d'intéragir avec le service Payline afin d'effectuer un paiement ou de voir le porte-monnaie.

# Installation

????

# Utilisation

## Initialisation

Pour l'initialisation du SDK, il faut tout d'abord instancier un  `PaymentController()` et un  `WalletController` et ensuite les associer à leur listener respectif qui seront décrits par la suite. Habituellement, cela est fait dans le  `onCreate()` de l'activité:

```kotlin
private var paymentController = PaymentController()
paymentController.registerListener(listener, context)

private var walletController = WalletController()
paymentController.registerListener(listener, context)
```
La méthode d'initialisation du paiement requiert deux paramètres: un "paymentControllerListener" et le context
La méthode d'initialisation du paiement requiert deux paramètres: un "walletControllerListener" et le context

Cependant, il faut aussi dissocier le listener lorsque vous avez fini avec l'utilisatation du SDK. Habituellement, cela est fait dans le  `onDestroy()` de l'activité:

```kotlin
paymentController.unregisterListener()
walletController.unregisterListener()
```

Pour que votre activité agisse comme un listener, vous devez implémenter les interfaces `PaymentControllerListener` et `WalletControllerListener`:

```kotlin
class MainActivity : AppCompatActivity(), PaymentControllerListener, WalletControllerListener
```

## Configuration

Les méthodes `showPaymentForm` and `showWalletForm` sont utilisées pour afficher la page des moyens de paiement ou la page du porte-monnaie.

```kotlin
private val paymentController = PaymentController()
paymentController.showPaymentForm(uri)
```

OR

```kotlin
private val walletController = WalletController()
walletController.showManageWallet(uri)
```
Ces deux méthodes requièrent l'uri de la page vers laquelle nous devons être redirigés.

### PaymentController

Une fois que la page des moyens de paiement a été affichée, plusieurs méthodes sont accessibles:

```kotlin
fun updateWebPaymentData(data: JSONObject)
```
`updateWebPaymentData` permet de mettre à jour les informations de session de paiement après l'initialisation du widget et avant la finalisation du paiement.


```kotlin
fun getIsSandbox()
```
`getIsSandbox` permet de connaitre l'environnement, savoir si c'est une production ou une homologation.


```kotlin
fun endToken(handledByMerchant: Boolean, additionalData: JSONObject?)
```
`endToken` permet de mettre fin à la vie du jeton de session web.


```kotlin
fun getLanguage()
```
`getLanguage` permet de connaitre la clé du language du widget.


```kotlin
fun getContextInfo(key: ContextInfoKey)
```
`getContextInfo` permet de connaitre l'information dont la clé est passée en paramètre.
Les différentes clés possible pour cette fonction sont les suivantes:

    - AMOUNT_SMALLEST_UNIT("PaylineAmountSmallestUnit")
    - CURRENCY_DIGITS("PaylineCurrencyDigits")
    - CURRENCY_CODE("PaylineCurrencyCode")
    - BUYER_FIRST_NAME("PaylineBuyerFirstName")
    - BUYER_LAST_NAME("PaylineBuyerLastName")
    - SHIPPING_ADDRESS_STREET2("PaylineBuyerShippingAddress.street2")
    - SHIPPING_ADDRESS_COUNTRY("PaylineBuyerShippingAddress.country")
    - SHIPPING_ADDRESS_NAME("PaylineBuyerShippingAddress.name")
    - SHIPPING_ADDRESS_STREET1("PaylineBuyerShippingAddress.street1")
    - SHIPPING_ADDRESS_CITYNAME("PaylineBuyerShippingAddress.cityName")
    - SHIPPING_ADDRESS_ZIPCODE("PaylineBuyerShippingAddress.zipCode")
    - BUYER_MOBILE_PHONE("PaylineBuyerMobilePhone")
    - SHIPPING_ADDRESS_PHONE("PaylineBuyerShippingAddress.phone")
    - BUYER_IP("PaylineBuyerIp")
    - FORMATTED_AMOUNT("PaylineFormattedAmount")
    - ORDER_DATE("PaylineOrderDate")
    - ORDER_REF("PaylineOrderRef")
    - ORDER_DELIVERY_MODE("PaylineOrderDeliveryMode")
    - ORDER_DELIVERY_TIME("PaylineOrderDeliveryTime")
    - ORDER_DETAILS("PaylineOrderDetails")


### PaymentControllerListener

Le `PaymentControllerListener` va permettre d'avertir les classes qui l'implement lorsque des données ou des actions sont reçues. Il contient différentes méthodes: 

```kotlin
fun didShowPaymentForm()
```
`didShowPaymentForm` est la méthode appelée lorsque la liste des moyens de paiement a été affichée.


```kotlin
fun didCancelPaymentForm()
```
`didCancelPaymentForm` est la méthode appelée lorsque la liste des moyens de paiement a été fermée sans avoir effectué ou sélectionné un moyen de paiement.


```kotlin
fun didFinishPaymentForm()
```
`didFinishPaymentForm` est la méthode appelée lorsque le paiement a été terminé.


```kotlin
fun didGetIsSandbox(isSandbox: Boolean)
```
`didGetIsSandbox` est la méthode appelée lorsque l'environnement a été reçu. `isSandbox`  vaudra true si l'environnement est une production et false si c'est une homologation.


```kotlin
fun didGetLanguage(language: String)
```
`didGetLanguage` est la méthode appelée lorsque la clé du language du widget est connue. `language` peut avoir des valeurs comme "fr", "en" ...


```kotlin
fun didGetContextInfo(key: ContextInfoKey)
```
`didGetContextInfo` est la méthode appelée lorsque l'information du contexte est connue

### WalletControllerListener

Le `WalletControllerListener` va permettre d'avertir les classes qui l'implement lorsque des données ou des actions sont reçues. Il contient une méthode: 

```kotlin
fun didShowManageWebWallet()
```
`didShowManageWebWallet` est la méthode appelée lorsque le porte-monnaie a été affiché.

## Example Usage

### Initialisation du moyen de paiement

```kotlin
class TestApp: AppCompatActivity(), PaymentControllerListener {

    private lateinit var paymentController: PaymentController

    private var token: String? = null
    private var uri: Uri? = null
    
    private val fetchTokenCallback: (FetchTokenResult?)->Unit = { result ->
        token = result?.token
        result?.redirectUrl?.let {
            uri = Uri.parse(it)
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        paymentController = PaymentController()
        paymentController.registerListener(this, this)
        
        fetchTokenForPayment()
        
        paymentButton.setOnClickListener {
            token ?: return@setOnClickListener
            uri ?: return@setOnClickListener
            paymentController.showPaymentForm(token!!, uri!!)
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        paymentController.unregisterListener()
    }
    
    private fun fetchTokenForPayment() {
        TokenFetcher(fetchTokenCallback).execute(
            FetchTokenParams(
                type = FetchTokenParams.Type.PAYMENT,
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
        )
    }
    
    override fun didShowPaymentForm() {

    }
    
    override fun didCancelPaymentForm() {

    }
    
    override fun didFinishPaymentForm() {

    }
    
    override fun didGetIsSandbox(isSandbox: Boolean) {

    }
    
    override fun didGetLanguage(language: String) {

    }
    
    override fun didGetContextInfo(info: ContextInfoResult) {

    }

}
```

### Initialisation du porte-monnaie

```kotlin
class TestApp: AppCompatActivity(), WalletControllerListener {

    private lateinit var walletController: WalletController

    private var token: String? = null
    private var uri: Uri? = null

    private val fetchTokenCallback: (FetchTokenResult?)->Unit = { result ->
        token = result?.token
        result?.redirectUrl?.let {
            uri = Uri.parse(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        walletController = WalletController()
        walletController.registerListener(this, this)

        walletTokenButton.setOnClickListener { fetchTokenForWallet() }

        walletButton.setOnClickListener {
            token ?: return@setOnClickListener
            uri ?: return@setOnClickListener
            walletController.showManageWallet(token!!, uri!!)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        walletController.unregisterListener()
    }

    private fun fetchTokenForWallet() {
        TokenFetcher(fetchTokenCallback).execute(FetchTokenParams(...))
    }

    override fun didShowManageWebWallet() {
        
    }

}
```

# Payline Documentation

La documentation de Payline peut être trouvé [here](https://support.payline.com/hc/fr/categories/360000068528-API-Reference). Elle offre une vue d'ensemble du sytème, des détails et des explications sur certains sujets.

---

