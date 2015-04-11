<?php

/*
 * Following code will create a new product row
 * All bookings details are read from HTTP Post Request
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['Subject']) && isset($_POST['Name']) && isset($_POST['Location']) &&isset($_POST['StartTime']) && isset($_POST['EndTime']) && isset($_POST['Description'])) {
    
    $Subject = $_POST['Subject'];
    $Name = $_POST['Name'];
    $Location = $_POST['Location'];
     $StartTime = $_POST['StartTime'];
    $EndTime = $_POST['EndTime'];
    $Description = $_POST['Description'];

    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    // mysql inserting a new row
    $result = mysql_query("INSERT INTO bookings(Subject, Name, Location, StartTime, EndTime, Description) VALUES('$Subject', '$Name', '$Location', '$StartTime', '$EndTime', '$Description')");

    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = " Booking successfully created.";

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