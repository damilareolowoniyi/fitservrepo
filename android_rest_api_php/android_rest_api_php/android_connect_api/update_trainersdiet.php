<?php

/*
 * Following code will update a clients information
 * A client is identified by clients id (cid)
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['id']) && isset($_POST['diet_name']) && isset($_POST['diet_date']) && isset($_POST['food_name']) &&isset($_POST['size']) && isset($_POST['total_cal']) && isset($_POST['protein']) && isset($_POST['carbs']) && isset($_POST['fat']) && isset($_POST['meal_time']) && isset($_POST['notes'])) {
    
    $id = $_POST['id'];
    $diet_name = $_POST['diet_name'];
    $diet_date = $_POST['diet_date'];
    $food_name = $_POST['food_name'];
    $size = $_POST['size'];
    $total_cal = $_POST['total_cal'];
    $protein = $_POST['protein'];
    $carbs = $_POST['carbs'];
    $fat = $_POST['fat'];
    $meal_time = $_POST['meal_time'];
    $notes = $_POST['notes'];
    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    // mysql update row with matched pid
    $result = mysql_query("UPDATE trainers_dietplan SET diet_name = '$diet_name', diet_date = '$diet_date', food_name = '$food_name', size = '$size', total_cal = '$total_cal', protein = '$protein', carbs = '$carbs', fat = '$fat', meal_time = '$meal_time', notes = '$notes' WHERE id = $id");

    // check if row inserted or not
    if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Diet Plan successfully updated.";
        // echoing JSON response
        echo json_encode($response);
    } else {
        
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>
