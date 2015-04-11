<?php

/*
 * Following code will create a new clients row
 * All create clients details are read from HTTP Post Request
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['email']) && isset($_POST['password']) && isset($_POST['current_client']) &&isset($_POST['fullname']) && isset($_POST['address']) && isset($_POST['age']) && isset($_POST['gender']) && isset($_POST['height']) && isset($_POST['weight']) && isset($_POST['goal'])&& isset($_POST['injuries'])) {
    
    $email = $_POST['email'];
    $password = $_POST['password'];
    $current_client = $_POST['current_client'];
    $fullname = $_POST['fullname'];
    $address = $_POST['address'];
    $age = $_POST['age'];
    $gender = $_POST['gender'];
    $height = $_POST['height'];
    $weight = $_POST['weight'];
    $goal = $_POST['goal'];
    $injuries = $_POST['injuries'];

    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    // mysql inserting a new row
    $result = mysql_query("INSERT INTO trainers_client(email, password, current_client, fullname, address, age, gender, height, weight, goal, injuries) VALUES('$email', '$password', '$current_client', '$fullname', '$address', '$age', '$gender', '$height', '$weight', '$goal', '$injuries')");

    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = " clients successfully created.";

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