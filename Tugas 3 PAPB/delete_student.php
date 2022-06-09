<?php

require_once __DIR__ . "/db_config.php";

$response = array();

$response["success"] = 0;

if(isset($_POST['nim']) ){

$nim = $_POST['nim'];

//Koneksi ke mysql database

$mysqli = new mysqli(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE);

//Cek koneksi

if (mysqli_connect_errno()) {

echo "Tidak terkoneksi ke Database: " . mysqli_connect_error();

exit();

}

//Delete data

$response = array();

$result = $mysqli -> query("DELETE FROM Mahasiswa WHERE nim = $nim");

if($mysqli -> affected_rows > 0){

$response["success"] = 1;

}

}

echo json_encode($response);

?>