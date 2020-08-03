<?php
	class DBFunctions {
		private $conn;

		function __construct() {
			require_once dirname(__FILE__).'/DBConnect.php';
			$db = new DBConnect();
			$this->conn = $db->connect();
		}

		public function getRestaurants() {
			$stmt = $this->conn->prepare("SELECT * FROM `Restaurant`;");
			$stmt->bind_result($name, $address);
			$stmt->execute();
			$results = array();
			while($stmt->fetch()) {
				$temp = array();
				$temp['name'] = $name;
				$temp['address'] = $address;
				array_push($results, $temp);
			}
			echo json_encode($results);
		}

		public function getEatery() {
			$stmt = $this->conn->prepare("SELECT `Eatery_ID`, `Eatery_Name` FROM `eatery`;");
			$stmt->bind_result($name, $address);
			$stmt->execute();
			$results = array();
			while($stmt->fetch()) {
				$temp = array();
				$temp['name'] = $name;
				$temp['address'] = $address;
				array_push($results, $temp);
			}
			echo json_encode($results);
		}

		public function createRestaurant($Name, $Address) {
			//if ($this->isDuplicate($Name, $Address)) {
			//	return false;
			//} else {
			$insert = $this->conn->prepare("INSERT INTO `Restaurant` (`Name`, `Address`) VALUES (?, ?);");
			$insert->bind_param("ss",$Name,$Address);
			if ($insert->execute()) {
				return true;
			} else {
			return false;
			}
			//}
		}

		public function createEatery($Eatery_ID, $Eatery_Name, $Email, $Start_Hour, $End_Hour, $Open_Days, $Address, $Pricing, $Coordinates, $Phone_Num, $Regional_Type, $Eatery_Type, $Cuisine) {
			$insert = $this->conn->prepare("INSERT INTO `eatery` (`Eatery_ID`, `Eatery_Name`, `Email`, `Start_Hour`, `End_Hour`, `Open_Days`, `Address`, `Pricing`, `Coordinates`, `Phone_Num`, `Regional_Type`, `Eatery_Type`, `Cuisine`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
			$insert->bind_param("sssssssssssss",$Eatery_ID,$Eatery_Name,$Email,$Start_Hour,$End_Hour,$Open_Days,$Address,$Pricing,$Coordinates,$Phone_Num,$Regional_Type,$Eatery_Type,$Cuisine);
			if ($insert->execute()) {
				return true;
			} else {
			return false;
			}
		}

		public function add_user($User_ID, $Display_ID, $Address, $Total_Outings) {
			$insert = $this->conn->prepare("INSERT INTO usertable VALUES(?,?,?,? );");
			$insert->bind_param("sssi",$User_ID, $Display_ID, $Address, $Total_Outings);
			if ($insert->execute()) {
				return true;
			} else {
			return false;
			}
		}

		public function add_review($User_ID, $Eatery_Name, $Visit_Date, $Feedback) {
			$stmt = $this->conn->prepare("SELECT Eatery_ID FROM eatery WHERE Eatery_Name = ?;");
			$stmt->bind_param('s', $Eatery_Name);
			$stmt->execute();
			$result = $stmt->get_result();
			$row = $result->fetch_assoc();
			$eateryID = $row['Eatery_ID'];
			//echo json_encode($eateryID);
			$insert = $this->conn->prepare("INSERT INTO eateryvisit VALUES(?, ?,?,? );");
			$insert->bind_param("siss",$User_ID, $eateryID, $Visit_Date, $Feedback);
			if ($insert->execute()) {
				return true;
			} else {
			return false;
			}
		}

		public function getReviews($Eatery_Name) {
			$stmt = $this->conn->prepare("SELECT Visit_Date, Feedback FROM eatery NATURAL JOIN eateryvisit WHERE Eatery_Name = ?;");
			$stmt->bind_param('s', $Eatery_Name);
			$stmt->execute();
			$result = $stmt->get_result()->fetch_all();
			echo json_encode($result,JSON_PRETTY_PRINT);
		}


		//private function isDuplicate() {
		//	$insert = $this->conn->prepare("SELECT Name FROM Restaurant WHERE Name = ? OR Address = ?;");
		//	$insert->bind_param("ss", $Name, $Address);
		//	$insert->execute();
		//	$insert->store_result();
		//	return $insert->num_rows > 0;
		//}
	}