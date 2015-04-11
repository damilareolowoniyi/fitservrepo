<?php

/*
 * Following code will get single product details
 * A product is identified by product id (pid)
 */

// array for JSON response
$response = array();


// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();

// check for post data
if (isset($_GET["wid"])) {
    $wid = $_GET['wid'];

    // get a product from products table
    $result = mysql_query("SELECT * FROM workoutplan WHERE wid = $wid");

    if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {

            $result = mysql_fetch_array($result);

            $workoutplan = array();
            $workoutplan["wid"] = $result["wid"];
            $workoutplan["workout_name"] = $result["workout_name"];
            $workoutplan["workout_date"] = $result["workout_date"];
            $workoutplan["exercise_name"] = $result["exercise_name"];
            $workoutplan["sets"] = $result["sets"];
            $workoutplan["weight_kg"] = $result["weight_kg"];
            $workoutplan["reps"] = $result["reps"];
            $workoutplan["notes"] = $result["notes"];
            $workoutplan["created_at"] = $result["created_at"];
            $workoutplan["updated_at"] = $result["updated_at"];
            // success
            $response["success"] = 1;

            // user node
            $response["workoutplan"] = array();

            array_push($response["workoutplan"], $workoutplan);

            // echoing JSON response
            echo json_encode($response);
        } else {
            // no workout Plan found
            $response["success"] = 0;
            $response["message"] = "No workout found";

            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no workout found
        $response["success"] = 0;
        $response["message"] = "No workout found";

        // echo no users JSON
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