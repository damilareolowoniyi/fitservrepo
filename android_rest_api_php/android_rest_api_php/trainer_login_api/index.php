<?php

/**
 * File to handle all API requests
 * Accepts GET and POST
 * 
 * Each request will be identified by TAG
 * Response will be JSON data
**/
  /**
 * check for POST request 
 */
if (isset($_POST['tag']) && $_POST['tag'] != '') {
    // get tag
    $tag = $_POST['tag'];

    // include db handler
    require_once 'include/DB_Functions.php';
    $db = new DB_Functions();

    // response Array
    $response = array("tag" => $tag, "error" => FALSE);

    // check for tag type
    if ($tag == 'login') {
        // Request type is check Login
        $email = $_POST['email'];
        $password = $_POST['password'];

        // check for trainer
        $trainer = $db->getUserByEmailAndPassword($email, $password);
        if ($trainer != false) {
            // trainer found
            $response["error"] = FALSE;
            $response["uid"] = $trainer["unique_id"];
            $response["trainer"]["name"] = $trainer["name"];
            $response["trainer"]["email"] = $trainer["email"];
            $response["trainer"]["created_at"] = $trainer["created_at"];
            $response["trainer"]["username"] = $trainer["username"];
            echo json_encode($response);
        } else {
            // trainer not found
            // echo json with error = 1
            $response["error"] = TRUE;
            $response["error_msg"] = "Incorrect email or password!";
            echo json_encode($response);
        }
    } else if ($tag == 'register') {
        // Request type is Register new trainer
        $name = $_POST['name'];
        $email = $_POST['email'];
        $password = $_POST['password'];
        $username = $_POST['username'];
        // check if trainer is already existed
        if ($db->isUserExisted($email)) {
            // user is already existed - error response
            $response["error"] = TRUE;
            $response["error_msg"] = "User already existed";
            echo json_encode($response);
        } else {
            // store trainer 
            $trainer = $db->storeUser($name, $email, $password, $username);
            if ($trainer) {
                // trainer stored successfully
                $response["error"] = FALSE;
                $response["uid"] = $trainer["unique_id"];
                $response["trainer"]["name"] = $trainer["name"];
                $response["trainer"]["email"] = $trainer["email"];
                $response["trainer"]["created_at"] = $trainer["created_at"];
                $response["trainer"]["username"] = $trainer["username"];
                echo json_encode($response);
            } else {
                // trainer failed to store
                $response["error"] = TRUE;
                $response["error_msg"] = "Error occured in Registartion";
                echo json_encode($response);
            }
        }
    } else {
        // trainer failed to store
        $response["error"] = TRUE;
        $response["error_msg"] = "Unknow 'tag' value. It should be either 'login' or 'register'";
        echo json_encode($response);
    }
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameter 'tag' is missing!";
    echo json_encode($response);
}
?>
