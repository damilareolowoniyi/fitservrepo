<?php

/*
 * Following code will list all the clients
 */

// array for JSON response
$response = array();


// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();

// get all clients from clients table
$result = mysql_query("SELECT * FROM trainers_dietplan") or die(mysql_error());

// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // clients node
    $response["diet"] = array();
    
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $diet = array();
        $diet["id"] = $row["id"];
        $diet["diet_name"] = $row["diet_name"];
        $diet["diet_date"] = $row["diet_date"];
        $diet["food_name"] = $row["food_name"];
        $diet["size"] = $row["size"];
        $diet["total_cal"] = $row["total_cal"];
        $diet["protein"] = $row["protein"];
        $diet["carbs"] = $row["carbs"];
        $diet["fat"] = $row["fat"];
        $diet["meal_time"] = $row["meal_time"];
        $diet["notes"] = $row["notes"];

        // push single client into final response array
        array_push($response["diet"], $diet);
    }
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
} else {
    // no bookings found
    $response["success"] = 0;
    $response["message"] = "No Diet found ";

    // echo no users JSON
    echo json_encode($response);
}
?>
