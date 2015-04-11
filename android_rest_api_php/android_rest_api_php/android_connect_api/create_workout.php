<?php

/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['workout_name']) && isset($_POST['workout_date']) && isset($_POST['exercise_name']) && isset($_POST['sets']) && isset($_POST['weight_kg']) && isset($_POST['reps']) && isset($_POST['notes']) ) {
    
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

    // mysql inserting a new row
    $result = mysql_query("INSERT INTO workoutplan(workout_name, workout_date, exercise_name, sets, weight_kg, reps, notes) VALUES('$workout_name', '$workout_date', '$exercise_name', '$sets', '$weight_kg', '$reps', '$notes')");

    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Workout plan successfully created.";

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