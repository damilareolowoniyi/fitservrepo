<?php

/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['diet_name']) && isset($_POST['diet_date']) && isset($_POST['food_name']) &&isset($_POST['size']) && isset($_POST['total_cal']) && isset($_POST['protein']) && isset($_POST['carbs']) && isset($_POST['fat']) && isset($_POST['meal_time']) && isset($_POST['notes']) ) {
    
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

    // mysql inserting a new row
    $result = mysql_query("INSERT INTO dietplan(diet_name, diet_date, food_name, size, total_cal, protein, carbs, fat, meal_time, notes) VALUES('$diet_name', '$diet_date', '$food_name', '$size', '$total_cal', '$protein', '$carbs', '$fat', '$meal_time', '$notes')");

    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Diet plan successfully created.";

        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
        
        // echoing JSON response
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>