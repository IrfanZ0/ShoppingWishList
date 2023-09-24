<?php
require 'vendor/autoload.php';
if(isset($_POST['authKey']) && ($_POST['authKey'] == "abc"){

    $stripe = new \Stripe\StripeClient('sk_test_51JeixFF1WVVH8ZyJSlj2xwp3f2fjGaab9ut1V7E4KrrXu7K3SBxzbYFSJ4kgwYCy9hhCyzNhqU3FLRSuYdoQ6La000RhjXTi24');

    // Use an existing Customer ID if this is a returning customer.
    $customer = $stripe->customers->create(
    [
        'name' => 'Irfan Ziaulla'
        'address' => [
        'line1' => '456 Example Street',
        'postal_code' => '45871',
        'city' => 'Anywhere',
        'state' => 'CA',
        'country' => 'US',
    ]

    );
    $ephemeralKey = $stripe->ephemeralKeys->create([
      'customer' => $customer->id,
    ], [
      'stripe_version' => '2022-08-01',
    ]);
    $paymentIntent = $stripe->paymentIntents->create([
      'amount' => 1099,
      'currency' => 'usd',
      'customer' => $customer->id,
      // In the latest version of the API, specifying the `automatic_payment_methods` parameter is optional because Stripe enables its functionality by default.
      'automatic_payment_methods' => [
        'enabled' => 'true',
      ],
    ]);

    echo json_encode(
      [
        'paymentIntent' => $paymentIntent->client_secret,
        'ephemeralKey' => $ephemeralKey->secret,
        'customer' => $customer->id,
        'publishableKey' => 'pk_test_51JeixFF1WVVH8ZyJdQGLKm40w9ufobfuyrHo9mjT0YUWHix5tqKMRmf74oCJId2LDyGMbUokc9cA46PLTJtU90fd00wo9q4Pim'
      ]
    );
    http_response_code(200);
}
else echo "Not Authorized";

?>
