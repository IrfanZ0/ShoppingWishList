<?php

$amount = $_POST['amount'];
$currency = $_POST{'currency'};
$description = $POST{'description'};
$email = $POST{'email'};

$jString = "{
	"amount": $amount,
	"currency": $currency,
	"description": $description,
	"email": $email
}";

$jsonString = json_encode($jString);

echo $jsonString;

?>