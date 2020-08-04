<?php

	require_once '../includes/DBFunctions.php';
	$response = array();

	if ($_SERVER['REQUEST_METHOD']=='POST'){
		if (isset($_POST['new_value']) and isset($_POST['item_id']) and isset($_POST['menu_id']) and isset($_POST['eatery_id'])) {
			$db = new DBFunctions();
			if($db->updatePricing(
				$_POST['new_value'],
				$_POST['item_id'],
				$_POST['menu_id'],
				$_POST['eatery_id'])) {
				$response['error'] = false;
				$response['message'] = "Updated Price";
			} else {
				$response['error'] = true;
				$response['message'] = "ERROR updating price";
			}
		} else {
			$response['error'] = true;
			$response['message'] = "ERROR missing values";
		}

	} else {
		$response['error'] = true;
		$response['message'] = "ERROR (update price)";
	}

	echo json_encode($response);