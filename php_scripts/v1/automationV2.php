<?php

$dir    = '/Applications/XAMPP/xamppfiles/htdocs/GoogleApiReader/PlacesDetailJson';
//$dir    = 'C:\Users\ataka\Downloads\25-07-2020-100Places';
$files1 = scandir($dir);

require_once __DIR__ . '/db_config.php';

$link = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE);

/* check connection */
if (mysqli_connect_errno()) {
    printf("Connect failed: %s\n", mysqli_connect_error());
    exit();
}




//$result = mysqli_query($link, "SELECT * FROM Eatery");
// $inter = mysqli_query($link, "SELECT MAX(Eatery_ID) FROM `eatery`");
// echo json_encode($inter);





for($i = 3; $i < count($files1); $i++){
  $name = $dir . "/" . $files1[$i];
  $output = file_get_contents($name);
  $decoded_input = json_decode($output, true);
  //echo($files1[$i]);

  $id_result = mysqli_query($link, "SELECT max(Eatery_ID) + 1 FROM Eatery");
  $id_temp = mysqli_fetch_row($id_result)[0];
  echo json_encode($id_temp);
  $res = $decoded_input['result'];
  //echo json_encode($res, JSON_PRETTY_PRINT);

  if ($stmt = $link->prepare("INSERT INTO eatery VALUES(?, ?,?,?,?,?,?,?,?,?,?,?,? )")) {
    //echo json_encode($res['price_level'],JSON_PRETTY_PRINT);

  /* bind parameters for markers */
  $stmt->bind_param('issiissiissss', $id, $name, $website, $s_hour, $e_hour, $open_days, $address, $pricing, $coord, $phone, $reg_type, $type, $cuisine);
  $id = $id_temp;
  if (isset($res['name'])) {
    $name = $res['name'];
  } else {
  $name = NULL;
  }

  if (isset($res['website'])) {
    $website = $res['website'];
  } else {
  $website = NULL;
  }

  if (isset($res['opening_hours'])) {
    if (isset($res['opening_hours']['periods'][0]['open']['time'])) {
      $s_hour = $res['opening_hours']['periods'][0]['open']['time'];
    } else {
      $s_hour = NULL;
    }

    if (isset($res['opening_hours']['periods'][0]['close']['time'])) {
      $e_hour = $res['opening_hours']['periods'][0]['close']['time'];
    } else {
      $e_hour = NULL;
    }
  } else {
    $s_hour = NULL;
    $e_hour = NULL;
  }

  $days = "";
  if (isset($res['opening_hours'])) {
    for($j = 0; $j < count(array($res['opening_hours']['periods'])); $j++){
      $days .= $res['opening_hours']['periods'][$j]['close']['day'];
    }
  } else {
    $days = NULL;
  }
  $open_days = $days;
  if (isset($res['formatted_address'])) {
    $address = $res['formatted_address'];
  } else {
  $address = NULL;
  }
  if (isset($res['price_level'])) {
    $pricing = $res['price_level'];
  } else {
    $pricing = NULL;
  }
  //$pricing = $res['price_level'];
  $coord = NULL;
  if (isset($res['formatted_phone_number'])) {
    $phone = $res['formatted_phone_number'];
  } else {
  $phone = NULL;
  }
  $reg_type = NULL;
  if (isset($res['types'][0])) {
    $type = $res['types'][0];
  } else {
    $type = NULL;
  }
  $cuisine = NULL;

  // $id = $i-2;
  // $name = $res['name'];
  // $website = $res['website'];
  // $s_hour = $res['opening_hours']['periods'][0]['open']['time'];
  // $e_hour = $res['opening_hours']['periods'][0]['close']['time'];
  // $days = "";
  // for($j = 0; $j < count(array($res['opening_hours']['periods'])); $j++){
  //   $days .= $res['opening_hours']['periods'][$j]['close']['day'];
  // }
  // $open_days = $days;
  // $address = $res['formatted_address'];
  // if (isset($res['price_level'])) {
  //   $pricing = $res['price_level'];
  // } else {
  //   $pricing = NULL;
  // }
  // $coord = NULL;
  // $phone = $res['formatted_phone_number'];
  // $reg_type = NULL;
  // $type = $res['types'][0];
  // $cuisine = NULL;

/* execute query */
  $result = $stmt->execute();

  // /* bind result variables */
  // $stmt->bind_result($district);
  //
  // /* fetch value */
  // $stmt->fetch();
  //
  // printf("%s is in district %s\n", $city, $district);

  /* close statement */
  $stmt->close();
  }
}

//   $result = $stmt->execute();
// //echo json_encode($stmt, JSON_PRETTY_PRINT);
//     $stmt->close();
//     }
//      //check if row inserted or not
//     if ($result) {
//       // looping through all results
//       // products node
//       $response["products"] = array();
//       // success
//       $response["success"] = 1;

//       // echoing JSON response
//       echo json_encode($response, JSON_PRETTY_PRINT);
//     } else {
//         // failed to insert row
//         $response["success"] = 0;
//         $response["message"] = "Oops! An error occurred.";

//         // echoing JSON response
//         echo json_encode($response, JSON_PRETTY_PRINT);
//     }


//   }
//   //

//     mysqli_close($link);



?>