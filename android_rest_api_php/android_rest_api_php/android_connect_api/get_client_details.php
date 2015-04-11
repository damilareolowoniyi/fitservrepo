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
if (isset($_GET["cid"])) {
    $cid = $_GET['cid'];

    // get a product from products table
    $result = mysql_query("SELECT * FROM trainers_client WHERE cid = $cid");

    if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {

            $result = mysql_fetch_array($result);

        $client = array();
        $client["cid"] = $result["cid"];
        $client["email"] = $result["email"];
        $client["password"] = $result["password"];
        $client["current_client"] = $result["current_client"];
        $client["fullname"] = $result["fullname"];
        $client["address"] = $result["address"];
        $client["age"] = $result["age"];
        $client["gender"] = $result["gender"];
        $client["height"] = $result["height"];
        $client["weight"] = $result["weight"];
        $client["goal"] = $result["goal"];
        $client["injuries"] = $result["injuries"];
            // success
            $response["success"] = 1;

            // user node
            $response["client"] = array();

            array_push($response["client"], $client);

            // echoing JSON response
            echo json_encode($response);
        } else {
            // no workout Plan found
            $response["success"] = 0;
            $response["message"] = "No clients found";

            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no workout found
        $response["success"] = 0;
        $response["message"] = "No clients found";

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