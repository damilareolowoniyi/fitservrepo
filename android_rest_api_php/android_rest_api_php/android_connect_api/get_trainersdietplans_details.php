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
if (isset($_GET["id"])) {
    $id = $_GET['id'];

    // get a product from products table
    $result = mysql_query("SELECT * FROM trainers_dietplan WHERE id = $id");

    if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {

            $result = mysql_fetch_array($result);

        $diet = array();
        $diet["id"] = $result["id"];
        $diet["diet_name"] = $result["diet_name"];
        $diet["diet_date"] = $result["diet_date"];
        $diet["food_name"] = $result["food_name"];
        $diet["size"] = $result["size"];
        $diet["total_cal"] = $result["total_cal"];
        $diet["protein"] = $result["protein"];
        $diet["carbs"] = $result["carbs"];
        $diet["fat"] = $result["fat"];
        $diet["meal_time"] = $result["meal_time"];
        $diet["notes"] = $result["notes"];
             // success
            $response["success"] = 1;

            // user node
            $response["diet"] = array();

            array_push($response["diet"], $diet);

            // echoing JSON response
            echo json_encode($response);
        } else {
            // no workout Plan found
            $response["success"] = 0;
            $response["message"] = "No Diet Plans found";

            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no workout found
        $response["success"] = 0;
        $response["message"] = "No No Diet Plans found";

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