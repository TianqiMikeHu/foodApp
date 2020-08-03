<?php

	require_once '../includes/DBFunctions.php';
	if (isset($_POST['Eatery_Name'])) {
		$db = new DBFunctions();
		$db->getReviews($_POST['Eatery_Name']);
	}