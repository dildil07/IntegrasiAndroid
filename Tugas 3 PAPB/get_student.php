<?php

require_once __DIR__ . "/db_config.php";

//Koneksi ke mysql database

$mysqli = new mysqli(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE);

//Cek koneksi

if (mysqli_connect_errno()) {

echo "Tidak terkoneksi ke Database: " . mysqli_connect_error();

exit();

}

$result = $mysqli->query("SELECT * FROM Mahasiswa");

$myArray = array();

while($row = $result->fetch_assoc()) {

$myArray[] = $row;

}

echo json_encode($myArray);

?>