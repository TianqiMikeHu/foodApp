<?php

	require_once '../includes/DBFunctions.php';
	$response = array();

	if ($_SERVER['REQUEST_METHOD']=='POST'){
		if (isset($_POST['User_ID']) and isset($_POST['Display_ID']) and isset($_POST['Address']) and isset($_POST['Total_Outings'])) {
			$db = new DBFunctions();
			if($db->add_user(
				$_POST['User_ID'],
				$_POST['Display_ID'],
				$_POST['Address'],
				$_POST['Total_Outings'])) {
				$response['error'] = false;
				$response['message'] = "Inserted user";
			} else {
				$response['error'] = true;
				$response['message'] = "ERROR inserting user";
			}
		} else {
			$response['error'] = true;
			$response['message'] = "ERROR (add user) missing values";
		}

	} else {
		$response['error'] = true;
		$response['message'] = "ERROR (add user)";
	}

	echo json_encode($response);