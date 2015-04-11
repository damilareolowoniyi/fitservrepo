<?php

class DB_Functions {

    private $db;

    //put your code here
    // constructor
    function __construct() {
        require_once 'DB_Connect.php';
        // connecting to database
        $this->db = new DB_Connect();
        $this->db->connect();
    }

    // destructor
    function __destruct() {
        
    }

    /**
     * Storing new trainer
     * returns trainer details
     */
    public function storeUser($name, $email, $password, $username) {
        $uuid = uniqid('', true);
        $hash = $this->hashSSHA($password);
        $encrypted_password = $hash["encrypted"]; // encrypted password
        $salt = $hash["salt"]; // salt
        $result = mysql_query("INSERT INTO trainers(unique_id, name, email, password, salt, created_at, username) VALUES('$uuid', '$name', '$email', '$encrypted_password', '$salt', NOW(), '$username')");
        // check for successful store
        if ($result) {
            // get trainer details 
            $uid = mysql_insert_id(); // last inserted id
            $result = mysql_query("SELECT * FROM trainers WHERE uid = $uid");
            // return trainer details
            return mysql_fetch_array($result);
        } else {
            return false;
        }
    }

    /**
     * Get trainer by email and password
     */
    public function getUserByEmailAndPassword($email, $password) {
        $result = mysql_query("SELECT * FROM trainers WHERE email = '$email'") or die(mysql_error());
        // check for result 
        $no_of_rows = mysql_num_rows($result);
        if ($no_of_rows > 0) {
            $result = mysql_fetch_array($result);
            $salt = $result['salt'];
            $encrypted_password = $result['password'];
            $hash = $this->checkhashSSHA($salt, $password);
            // check for password equality
            if ($encrypted_password == $hash) {
                // trainer authentication details are correct
                return $result;
            }
        } else {
            // trainer not found
            return false;
        }
    }

    /**
     * Check trainer is existed or not
     */
    public function isUserExisted($email) {
        $result = mysql_query("SELECT email from trainers WHERE email = '$email'");
        $no_of_rows = mysql_num_rows($result);
        if ($no_of_rows > 0) {
            // trainer existed 
            return true;
        } else {
            // trainer not existed
            return false;
        }
    }

    /**
     * Encrypting password
     * @param password
     * returns salt and encrypted password
     */
    public function hashSSHA($password) {

        $salt = sha1(rand());
        $salt = substr($salt, 0, 10);
        $encrypted = base64_encode(sha1($password . $salt, true) . $salt);
        $hash = array("salt" => $salt, "encrypted" => $encrypted);
        return $hash;
    }

    /**
     * Decrypting password
     * @param salt, password
     * returns hash string
     */
    public function checkhashSSHA($salt, $password) {

        $hash = base64_encode(sha1($password . $salt, true) . $salt);

        return $hash;
    }

}

?>
