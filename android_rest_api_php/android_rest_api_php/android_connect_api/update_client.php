<?php

/*
 * Following code will update a clients information
 * A client is identified by clients id (cid)
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['cid']) && isset($_POST['email']) && isset($_POST['password']) && isset($_POST['current_client']) &&isset($_POST['fullname']) && isset($_POST['address']) && isset($_POST['age']) && isset($_POST['gender']) && isset($_POST['height']) && isset($_POST['weight']) && isset($_POST['goal'])&& isset($_POST['injuries'])) {
    
    $cid = $_POST['cid'];
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

    // mysql update row with matched pid
    $result = mysql_query("UPDATE trainers_client SET email = '$email', password = '$password', current_client = '$current_client', fullname = '$fullname', address = '$address', age = '$age', gender = '$gender', height = '$height', weight = '$weight', goal = '$goal', injuries = '$injuries' WHERE cid = $cid");

    // check if row inserted or not
    if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "client successfully updated.";
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
