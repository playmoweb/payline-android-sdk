# PaylineSDK

# Description

Le SDK Payline est un kit de développement qui va permettre d'intéragir avec le service Payline afin d'effectuer un paiement ou de voir le porte-monnaie.

# Installation

????

# Utilisation

## Initialisation

Pour l'initialisation du SDK, il faut tout d'abord instancier un  `PaymentController()` et un  `WalletController()` et ensuite les associer à leur listener qui seront décrits par la suite. Habituellement, cela est fait dans le  `onCreate()` de l'activité:

```kotlin
private var paymentController = PaymentController()
paymentController.registerListener(listener, context)

private var walletController = WalletController()
walletController.registerListener(listener, context)
```
La méthode d'initialisation du paiement requiert deux paramètres: un "paymentControllerListener" et le context
La méthode d'initialisation du porte-monnaie requiert deux paramètres: un "walletControllerListener" et le context

Cependant, il faut aussi dissocier le listener lorsque vous avez fini d'utiliser le SDK. Habituellement, cela est fait dans le  `onDestroy()` de l'activité:

```kotlin
paymentController.unregisterListener()
walletController.unregisterListener()
```

Pour que votre activité agisse comme un listener, vous devez implémenter les interfaces `PaymentControllerListener` et `WalletControllerListener`:

```kotlin
class MainActivity : AppCompatActivity(), PaymentControllerListener, WalletControllerListener
```

## Configuration

La méthode `showPaymentForm` est utilisée pour afficher la page des moyens de paiement.

```kotlin
private val paymentController = PaymentController()
paymentController.showPaymentForm(uri)
```

OR

La méthode `showManageWallet` est utilisée pour afficher la page du porte-monnaie.

```kotlin
private val walletController = WalletController()
walletController.showManageWallet(uri)
```
Ces deux méthodes requièrent l'uri de la page vers laquelle nous devons être redirigés.

# Dalenys Documentation

La documentation de Payline peut être trouvé [here](https://payline.atlassian.net/wiki/spaces/DT/overview). Elle offre une vue d'ensemble du sytème, des détails et des explications sur certains sujets.

---
