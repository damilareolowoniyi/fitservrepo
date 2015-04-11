<?php

/*
 * Following code will update a workout information
 * A workout is identified by product wid (wid)
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['wid']) && isset($_POST['workout_name']) && isset($_POST['workout_date']) && isset($_POST['exercise_name']) && isset($_POST['sets']) && isset($_POST['weight_kg']) && isset($_POST['reps']) && isset($_POST['notes']) ) {
    
    $wid = $_POST['wid'];
    $workout_name = $_POST['workout_name'];
    $workout_date = $_POST['workout_date'];
    $exercise_name = $_POST['exercise_name'];
    $sets = $_POST['sets'];
    $weight_kg = $_POST['weight_kg'];
    $reps = $_POST['reps'];
    $notes = $_POST['notes'];

    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    // mysql update row with matched pid
    $result = mysql_query("UPDATE workoutplan SET workout_name = '$workout_name', workout_date = '$workout_date', exercise_name = '$exercise_name', sets = '$sets', weight_kg = '$weight_kg', reps = '$reps', notes = '$notes'  WHERE wid = $wid");

    // check if row inserted or not
    if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "workout successfully updated.";
        
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
