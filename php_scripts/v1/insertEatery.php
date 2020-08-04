<?php

	require_once '../includes/DBFunctions.php';
	$response = array();

	if ($_SERVER['REQUEST_METHOD']=='POST'){
		if (isset($_POST['Name']) and isset($_POST['Website']) and isset($_POST['Start_Hour']) and isset($_POST['End_Hour']) and isset($_POST['Open_Days']) and isset($_POST['Address']) and isset($_POST['Price_Level']) and isset($_POST['Phone']) and isset($_POST['Regional_Type']) and isset($_POST['Eatery_Type'])) {
			$db = new DBFunctions();
			if($db->createEatery(
				$_POST['Name'],
				$_POST['Website'],
				$_POST['Start_Hour'],
				$_POST['End_Hour'],
				$_POST['Open_Days'],
				$_POST['Address'],
				$_POST['Price_Level'],
				$_POST['Phone'],
				$_POST['Regional_Type'],
				$_POST['Eatery_Type'])) {
				$response['error'] = false;
				$response['message'] = "Inserted Eatery";
			} else {
				$response['error'] = true;
				$response['message'] = "ERROR inserting Eatery";
			}
		} else {
			$response['error'] = true;
			$response['message'] = "ERROR (insertEatery) missing values";
		}

	} else {
		$response['error'] = true;
		$response['message'] = "ERROR (insertEatery)";
	}

	echo json_encode($response);