<?php
       $amount = $_POST['amount'];
		$currency = $_POST['currency'];
		$description = $_POST['description'];
		$email = $_POST['email'];

		$jString = "{
			"amount" => $amount,
			"currency" => $currency,
			"description" => $description,
			"email" => $email
		}";

		$jsonString = json_encode($jString);

		echo $jsonString;
?>
