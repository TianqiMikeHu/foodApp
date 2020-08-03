<?php

	require_once '../includes/DBFunctions.php';
	$response = array();

	if ($_SERVER['REQUEST_METHOD']=='POST'){
		if (isset($_POST['User_ID']) and isset($_POST['Eatery_Name']) and isset($_POST['Visit_Date']) and isset($_POST['Feedback'])) {
			$db = new DBFunctions();
			if($db->add_review(
				$_POST['User_ID'],
				$_POST['Eatery_Name'],
				$_POST['Visit_Date'],
				$_POST['Feedback'])) {
				$response['error'] = false;
				$response['message'] = "Inserted review";
			} else {
				$response['error'] = true;
				$response['message'] = "ERROR inserting review";
			}
		} else {
			$response['error'] = true;
			$response['message'] = "ERROR (insert review) missing values";
		}

	} else {
		$response['error'] = true;
		$response['message'] = "ERROR (insert review)";
	}

	echo json_encode($response);