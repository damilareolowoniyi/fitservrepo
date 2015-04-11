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

// get all bookings from bookings table
$result = mysql_query("SELECT * FROM bookings ORDER BY id DESC LIMIT 2 ") or die(mysql_error());

// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // bookings node
    $response["bookings"] = array();
    
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $bookings = array();
        $bookings["id"] = $row["id"];
        $bookings["Subject"] = $row["Subject"];
        $bookings["Name"] = $row["Name"];
        $bookings["Location"] = $row["Location"];
        $bookings["Description"] = $row["Description"];
        $bookings["StartTime"] = $row["StartTime"];
        $bookings["EndTime"] = $row["EndTime"];

        // push single bookings into final response array
        array_push($response["bookings"], $bookings);
    }
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
} else {
    // no bookings found
    $response["success"] = 0;
    $response["message"] = "No Bookings found";

    // echo no users JSON
    echo json_encode($response);
}
?>
