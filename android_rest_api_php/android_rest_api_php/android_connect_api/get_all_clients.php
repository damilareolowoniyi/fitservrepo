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
$result = mysql_query("SELECT * FROM trainers_client") or die(mysql_error());

// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // clients node
    $response["client"] = array();
    
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $client = array();
        $client["cid"] = $row["cid"];
        $client["email"] = $row["email"];
        $client["password"] = $row["password"];
        $client["current_client"] = $row["current_client"];
        $client["fullname"] = $row["fullname"];
        $client["address"] = $row["address"];
        $client["age"] = $row["age"];
        $client["gender"] = $row["gender"];
        $client["height"] = $row["height"];
        $client["weight"] = $row["weight"];
        $client["goal"] = $row["goal"];
        $client["injuries"] = $row["injuries"];

        // push single client into final response array
        array_push($response["client"], $client);
    }
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
} else {
    // no bookings found
    $response["success"] = 0;
    $response["message"] = "No clients found";

    // echo no users JSON
    echo json_encode($response);
}
?>
