<?php

/*
 * Following code will list all the products
 */

// array for JSON response
$response = array();


// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();

// get all workout from workout table
$result = mysql_query("SELECT * FROM trainers_workoutplan") or die(mysql_error());

// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // workout node
    $response["workoutplan"] = array();
    
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $workoutplan = array();
        $workoutplan["wid"] = $row["wid"];
        $workoutplan["workout_name"] = $row["workout_name"];
        $workoutplan["workout_date"] = $row["workout_date"];
        $workoutplan["exercise_name"] = $row["exercise_name"];
        $workoutplan["sets"] = $row["sets"];
        $workoutplan["weight_kg"] = $row["weight_kg"];
        $workoutplan["reps"] = $row["reps"];
        $workoutplan["created_at"] = $row["created_at"];
        $workoutplan["updated_at"] = $row["updated_at"];


        // push single workout plan into final response array
        array_push($response["workoutplan"], $workoutplan);
    }
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No workout found";

    // echo no users JSON
    echo json_encode($response);
}
?>
