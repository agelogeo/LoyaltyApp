<?php
header("Access-Control-Allow-Origin: *");
	$host = "localhost";
	$user = "id755156_agelo1995";
	$password = "Ney-8392";
	$db = $_GET['db'];

	$con = mysqli_connect($host,$user,$password,$db);
	//Error for database parameter
	if(!$con){
		$response["error"] = 100;
		$response["message"] = mysqli_connect_error();
	}else{
		$action = $_GET['action'];
		//GET COUPONS
		if($action=='get_coupons'){
			$sql = " SELECT * FROM coupons";
			$result = $con->query($sql);
			$stack = array();
			$d = array();
			if ($result->num_rows > 0) {
				while($row = $result->fetch_assoc()) {
					$d[] = $row;
				}
			} else {
				$response["error"] = 102;
				$response["message"] = "No rows available.";
			}
			$response["results"] = $d;
		}
		//COUPON CREATION
		else if($action=='coupon_creation'){
			if (empty($_GET['name']) || empty($_GET['required'])) {
			  $response["error"] = 103;
			  $response["message"] = "Required fields : name,required";
			}else{
				$name = $_GET['name'];
				$required = $_GET['required'];

				$sql2 = " INSERT INTO `coupons`(`name`, `required_stamps`) VALUES ('$name','$required')";
				$result = $con->query($sql2);
				$response["success"] = 1;
				$response["message"] = $result;
				$barcode = " SELECT * FROM coupons WHERE `name`='$name' ";
				$result = $con->query($barcode);
				$row = $result->fetch_assoc();
				$response["id"]=$row['id'];
				$response["name"] = $row['name'];
				$response["required_stamps"] = $row['required_stamps'];
			}
		}
		//COUPON DELETION
		else if($action=='coupon_deletion'){
			if (empty($_GET['id'])) {
			  $response["error"] = 103;
			  $response["message"] = "Required fields : id";
			}else{
				$id = $_GET['id'];
				$sql2 = " DELETE FROM `coupons` WHERE `id`='$id'";
				$response["success"] = 1;
				$result = $con->query($sql2);
				if(mysqli_affected_rows($con)!=0)
					$response["message"] = "true";
				else
					$response["message"] = "false";
			}
		}
		//COUPON SAVE
		else if($action=='coupon_save'){
			if (empty($_GET['id']) || empty($_GET['name']) || empty($_GET['required']) ) {
			  $response["error"] = 103;
			  $response["message"] = "Required fields : id,name,required";
			}else{
				$id = $_GET['id'];
				$name = $_GET['name'];
				$required = $_GET['required'];

				$sql2 = " UPDATE `coupons` SET `name`='$name',`required_stamps`='$required' WHERE `id`='$id'";
				$result = $con->query($sql2);
				$response["success"] = 1;
				$response["message"] = $result;
				$barcode = " SELECT * FROM coupons WHERE `id`='$id' ";
				$result = $con->query($barcode);
				$row = $result->fetch_assoc();
				$response["id"]=$row['id'];
				$response["name"] = $row['name'];
				$response["required_stamps"] = $row['required_stamps'];
			}
		}
		//STAMP CHANGE
		else if($action=='stamp_change'){
			if(empty($_GET['value']) || empty($_GET['id']) || empty($_GET['operation'])){
					$response["error"] = 105;
					$response["message"] = "value,id,operation parameter invalid.";
			}
			else{
				$operation = $_GET['operation'];
				if($operation=='add'){
					$value = $_GET['value'];
					$id = $_GET['id'];
					$sql = " UPDATE `customers` SET `last_visit`=(SELECT CURDATE()),`visits`=`visits`+1,`stamps`=`stamps`+'$value' WHERE `id`='$id'";
					$result = $con->query($sql);
					$response["success"] = 1;
					$response["message"] = $result;
					$barcode = " SELECT * FROM customers WHERE `id`='$id' ";
					$result = $con->query($barcode);
					$row = $result->fetch_assoc();
					$response["id"]=$row['id'];
					$response["name"] = $row['name'];
					$response["surname"] = $row['surname'];
					$response["phone"] = $row['phone'];
					$response["barcode"] = $row['barcode'];
					$response["stamps"]=$row['stamps'];
					$response["coupons_used"]=$row['coupons_used'];
					$response["visits"]=$row['visits'];
					$response["last_visit"]=$row['last_visit'];
				}
				else if($operation=='remove'){
					$value = $_GET['value'];
					$id = $_GET['id'];
					$sql = " UPDATE `customers` SET `stamps`=`stamps`-'$value' WHERE `id`='$id'";
					$result = $con->query($sql);
					$response["success"] = 1;
					$response["message"] = $result;
					$barcode = " SELECT * FROM customers WHERE `id`='$id' ";
					$result = $con->query($barcode);
					$row = $result->fetch_assoc();
					$response["id"]=$row['id'];
					$response["name"] = $row['name'];
					$response["surname"] = $row['surname'];
					$response["phone"] = $row['phone'];
					$response["barcode"] = $row['barcode'];
					$response["stamps"]=$row['stamps'];
					$response["coupons_used"]=$row['coupons_used'];
					$response["visits"]=$row['visits'];
					$response["last_visit"]=$row['last_visit'];
				}
				else{
					$response["error"] = 104;
					$response["message"] = 'operation parameter invalid.';
				}
			}
		}
		//GET CHARTS
		else if($action=='get_pie_chart'){
            if (empty($_GET['date']) || empty($_GET['interval']) || empty($_GET['hours'] )) {
			  $response["error"] = 103;
			  $response["message"] = "Required fields : date,interval,hours";
			}else{
				$date = $_GET['date'];
				$hours = $_GET['hours'];
				$interval = $_GET['interval'];
				if( $interval==-1 ){//TODAY
					$sql = "SELECT COUNT(*) AS count,coupons.name FROM `coupons_track` INNER JOIN `coupons` ON `coupons_track`.`coupons_id` = `coupons`.id WHERE DATE_SUB(DATE('{$date} {$hours}'), INTERVAL 0 DAY) = DATE(`datetime`) GROUP BY coupons_track.coupons_id";
					$result = $con->query($sql);
					$stack = array();
					$d = array();
					if ($result->num_rows > 0) {
						while($row = $result->fetch_assoc()) {
							$d[] = $row;
						}
					} else {
						$response["error"] = 102;
						$response["message"] = "No rows available.";
					}
					$response["results"] = $d;
				}else if($interval==1){//YESTERDAY
					$sql = "SELECT COUNT(*) AS count,coupons.name FROM `coupons_track` INNER JOIN `coupons` ON `coupons_track`.`coupons_id` = `coupons`.id WHERE DATE_SUB(DATE('{$date} {$hours}'), INTERVAL $interval DAY) = DATE(`datetime`) GROUP BY coupons_track.coupons_id";
					$result = $con->query($sql);
					$stack = array();
					$d = array();
					if ($result->num_rows > 0) {
						while($row = $result->fetch_assoc()) {
							$d[] = $row;
						}
					} else {
						$response["error"] = 102;
						$response["message"] = "No rows available.";
					}
					$response["results"] = $d;
				}else if($interval==7){//LAST 7 DAYS
					$sql = "SELECT COUNT(*) AS count,coupons.name FROM `coupons_track` INNER JOIN `coupons` ON `coupons_track`.`coupons_id` = `coupons`.id WHERE DATE_SUB(DATE('$date'), INTERVAL $interval DAY) <= DATE(`datetime`) GROUP BY coupons_track.coupons_id";
					$result = $con->query($sql);
					$stack = array();
					$d = array();
					if ($result->num_rows > 0) {
						while($row = $result->fetch_assoc()) {
							$d[] = $row;
						}
					} else {
						$response["error"] = 102;
						$response["message"] = "No rows available.";
					}
					$response["results"] = $d;
				}else if($interval==30){//LAST 30 DAYS
					$sql = "SELECT COUNT(*) AS count,coupons.name FROM `coupons_track` INNER JOIN `coupons` ON `coupons_track`.`coupons_id` = `coupons`.id WHERE DATE_SUB(DATE('$date'), INTERVAL 1 MONTH) <= DATE(`datetime`) GROUP BY coupons_track.coupons_id";
					$result = $con->query($sql);
					$stack = array();
					$d = array();
					if ($result->num_rows > 0) {
						while($row = $result->fetch_assoc()) {
							$d[] = $row;
						}
					} else {
						$response["error"] = 102;
						$response["message"] = "No rows available.";
					}
					$response["results"] = $d;
				}else if($interval==180){//LAST 6 MONTHS
					$sql = "SELECT COUNT(*) AS count,coupons.name FROM `coupons_track` INNER JOIN `coupons` ON `coupons_track`.`coupons_id` = `coupons`.id WHERE DATE_SUB(DATE('$date'), INTERVAL 6 MONTH) <= DATE(`datetime`) GROUP BY coupons_track.coupons_id";
					$result = $con->query($sql);
					$stack = array();
					$d = array();
					if ($result->num_rows > 0) {
						while($row = $result->fetch_assoc()) {
							$d[] = $row;
						}
					} else {
						$response["error"] = 102;
						$response["message"] = "No rows available.";
					}
					$response["results"] = $d;
				}else if($interval==365){//LAST YEAR
					$sql = "SELECT COUNT(*) AS count,coupons.name FROM `coupons_track` INNER JOIN `coupons` ON `coupons_track`.`coupons_id` = `coupons`.id WHERE DATE_SUB(DATE('$date'), INTERVAL 1 YEAR) <= DATE(`datetime`) GROUP BY coupons_track.coupons_id";
					$result = $con->query($sql);
					$stack = array();
					$d = array();
					if ($result->num_rows > 0) {
						while($row = $result->fetch_assoc()) {
							$d[] = $row;
						}
					} else {
						$response["error"] = 102;
						$response["message"] = "No rows available.";
					}
					$response["results"] = $d;
				}else if($interval==1000){//ALL TIME
					$sql = "SELECT COUNT(*) AS count,coupons.name FROM `coupons_track` INNER JOIN `coupons` ON `coupons_track`.`coupons_id` = `coupons`.id WHERE DATE_SUB(DATE('$date'), INTERVAL 10 YEAR) <= DATE(`datetime`) GROUP BY coupons_track.coupons_id";
					$result = $con->query($sql);
					$stack = array();
					$d = array();
					if ($result->num_rows > 0) {
						while($row = $result->fetch_assoc()) {
							$d[] = $row;
						}
					} else {
						$response["error"] = 102;
						$response["message"] = "No rows available.";
					}
					$response["results"] = $d;
				}				
			}
		}//TRACK VISIT
		else if($action=='track_visit'){
			if ( empty($_GET['date']) || empty($_GET['hours']) || empty($_GET['visits'])) {
			  $response["error"] = 103;
			  $response["message"] = "Required fields : date,hours,visits";
			}else{
				$date = $_GET['date'];
				$hours = $_GET['hours'];
				$visits = $_GET['visits'];
				$sql = " UPDATE `track_visits` SET `visits`= `visits`+'$visits' WHERE `datetime`='{$date} {$hours}'";
				$result = $con->query($sql);
				$stack = array();
				if (mysqli_affected_rows($con)) {
					$response["success"] = 1;
					$response["message"]= "UPDATED";					
				}else {
					$sql = "INSERT INTO `track_visits`( `datetime`, `visits`) VALUES ('{$date} {$hours}','$visits')";
					$result = $con->query($sql);
					if($result){
						$response["success"] = 1;
						$response["message"]= "CREATED";	
					}
				} 
			}
		}//GET VISITS BY DAY
		else if($action=='get_visits_by_day'){
			if (empty($_GET['date']) || empty($_GET['hours'])) {
			  $response["error"] = 103;
			  $response["message"] = "Required fields : date,hours";
			}else{
				$date = $_GET['date'];
				$hours = $_GET['hours'];
				$sql = "SELECT CAST(`track_visits`.datetime AS TIME) AS 'datetime',`track_visits`.`visits` FROM `track_visits` WHERE CAST(`datetime` AS DATE) = CAST('{$date} {$hours}' AS DATE)";
				$result = $con->query($sql);
				$stack = array();
				$d = array();
				if ($result->num_rows > 0) {
					while($row = $result->fetch_assoc()) {
						$d[] = $row;
					}
				} else {
					$response["error"] = 102;
					$response["message"] = "No rows available.";
				}
				$response["results"] = $d;
			}
		}
		//STAMP CREDIT
		else if($action=='coupon_credit'){

		}
		//CUSTOMER LOGIN
		else if($action=='customer_login'){
			if (empty($_GET['username'])) {
			  $response["error"] = 103;
			  $response["message"] = "Required fields : username";
			}else{
				$username = $_GET['username'];
				$sql = " SELECT * FROM customers WHERE `barcode`='$username' OR `phone`='$username'";
				$result = $con->query($sql);
				$stack = array();
				if ($result->num_rows == 1) {
					$row = $result->fetch_assoc();
					// output data of each row
					$response["success"] = 1;
					$response["id"]=$row['id'];
					$response["name"] = $row['name'];
					$response["surname"] = $row['surname'];
					$response["phone"] = $row['phone'];
					$response["barcode"] = $row['barcode'];
					$response["stamps"]=$row['stamps'];
					$response["coupons_used"]=$row['coupons_used'];
					$response["visits"]=$row['visits'];
					$response["last_visit"]=$row['last_visit'];
				} else {
					$response["error"] = 102;
					$response["message"] = "No rows available.";
				}
			}
		}
		//CUSTOMER CREATION
		else if($action=='customer_creation'){
			if (empty($_GET['name'])) {
			  $response["error"] = 103;
			  $response["message"] = "Required fields : name";
			}else{
				$name = $_GET['name'];
				$surname = $_GET['surname'];
				$phone = $_GET['phone'];
				$barcode = " INSERT INTO customers (`name`, `surname`, `phone`, `barcode`) VALUES ('$name','$surname','$phone',(SELECT FLOOR(rand() * 90000 + 10000)))";
				$check = $con->query($barcode);
				$i = 0;
				while(!$check && $i<50){
					$barcode = " INSERT INTO customers (`name`, `surname`, `phone`, `barcode`) VALUES ('$name','$surname','$phone',(SELECT FLOOR(rand() * 90000 + 10000)))";
					$check = $con->query($barcode);
					$i++;
				}
				if( $check ){
					$response["success"] = 1;
					$response["message"] = $check;
					$barcode = " SELECT * FROM customers WHERE `phone`='$phone' ";
					$result = $con->query($barcode);
					$row = $result->fetch_assoc();
					$response["id"]=$row['id'];
					$response["name"] = $row['name'];
					$response["surname"] = $row['surname'];
					$response["phone"] = $row['phone'];
					$response["barcode"] = $row['barcode'];
					$response["stamps"]=$row['stamps'];
					$response["coupons_used"]=$row['coupons_used'];
					$response["visits"]=$row['visits'];
					$response["last_visit"]=$row['last_visit'];
				}else{
					$response["success"] = 0;
					$response["message"] = $check;
				}
			}
		}
		//CUSTOMER DELETION
		else if($action=='customer_deletion'){
			if (empty($_GET['id'])) {
			  $response["error"] = 103;
			  $response["message"] = "Required fields : id";
			}else{
				$id = $_GET['id'];
				$sql2 = " DELETE FROM `customers` WHERE `id`='$id'";
				$response["success"] = 1;
				$result = $con->query($sql2);
				if(mysqli_affected_rows($con)!=0)
					$response["message"] = "true";
				else
					$response["message"] = "false";
			}
		}
		//CUSTOMER SEARCH
		else if($action=='search_customer'){
			if(empty($_GET['term'])){
				$response["message"] = "";
			}else{
				$term = $_GET['term'];
				if(empty($_GET['double'])){
					$sql = " SELECT * FROM `customers` WHERE `phone` LIKE '$term%' OR `barcode` LIKE '$term%' OR `name` LIKE '$term%' OR `surname` LIKE '$term%'";
				}else{
					$sql = " SELECT * FROM `customers` WHERE `phone` LIKE '%$term%' OR `barcode` LIKE '%$term%' OR `name` LIKE '$term%' OR `surname` LIKE '$term%'";
				}
				$result = $con->query($sql);
				$stack = array();
				$d = array();
				if ($result->num_rows > 0) {
					while($row = $result->fetch_assoc()) {
						$d[] = $row;
					}
				} else {
					$response["error"] = 102;
					$response["message"] = "No rows available.";
				}
				$response["results"] = $d;
			}
		}
		//CUSTOMER SAVE
		else if($action=='customer_save'){
			if (empty($_GET['id']) || empty($_GET['name']) || empty($_GET['surname']) || empty($_GET['phone'])) {
			  $response["error"] = 103;
			  $response["message"] = "Required fields : id,name,surname,phone";
			}else{
				$id = $_GET['id'];
				$name = $_GET['name'];
				$surname = $_GET['surname'];
				$barcode = $_GET['barcode'];
				$phone = $_GET['phone'];
				$stamps = $_GET['stamps'];
				$coupons_used = $_GET['coupons_used'];
				$visits = $_GET['visits'];

				$sql2 = " UPDATE `customers` SET `name`='$name',`surname`='$surname',`barcode`='$barcode',`phone`='$phone',`stamps`='$stamps',`coupons_used`='$coupons_used',`visits`='$visits' WHERE `id`='$id'";
				$result = $con->query($sql2);
				$response["success"] = 1;
				$response["message"] = $result;
				$barcode = " SELECT * FROM customers WHERE `id`='$id' ";
				$result = $con->query($barcode);
				$row = $result->fetch_assoc();
				$response["id"]=$row['id'];
				$response["name"] = $row['name'];
				$response["surname"] = $row['surname'];
				$response["phone"] = $row['phone'];
				$response["barcode"] = $row['barcode'];
				$response["stamps"]=$row['stamps'];
				$response["coupons_used"]=$row['coupons_used'];
				$response["visits"]=$row['visits'];
				$response["last_visit"]=$row['last_visit'];
			}
		}
		//OPERATOR LOGIN
		else if($action=='operator_login'){
			if (empty($_GET['username']) || empty($_GET['password'])) {
			  $response["error"] = 103;
			  $response["message"] = "Required fields : username,password";
			}else{
				$username = $_GET['username'];
				$password = $_GET['password'];
				$sql = " SELECT * FROM operators WHERE (`username`='$username' AND `password`='$password') OR (`phone`='$username' AND `password`='$password')";
				$result = $con->query($sql);
				$stack = array();
				if ($result->num_rows == 1) {
					$row = $result->fetch_assoc();
					// output data of each row
					$response["success"] = 1;
					$response["id"]=$row['id'];
					$response["username"]=$row['username'];
					$response["password"]=$row['password'];
					$response["access_level"]=$row['access_level'];
					$response["first_name"]=$row['first_name'];
					$response["last_name"]=$row['last_name'];
					$response["phone"]=$row['phone'];
				} else {
					$response["error"] = 102;
					$response["message"] = "No rows available.";
				}
			}
		}
		//OPERATOR CREATION
		else if($action=='operator_creation'){
			if (empty($_GET['username']) || empty($_GET['password'])) {
			  $response["error"] = 103;
			  $response["message"] = "Required fields : username,password";
			}else{
				$username = $_GET['username'];
				$password = $_GET['password'];
				$first_name = $_GET['first_name'];
				$last_name = $_GET['last_name'];
				$phone = $_GET['phone'];
				if( empty($_GET['access_level']) ){
					$sql2 = " INSERT INTO `operators`(`username`, `password`, `first_name`, `last_name`, `phone`) VALUES ('$username','$password','$first_name','$last_name','$phone')";
					$result = $con->query($sql2);
					$response["success"] = 1;
					$response["message"] = $result;
				}else{
					$access_level = $_GET['access_level'];
					$sql3 = " INSERT INTO `operators`(`username`, `password`, `access_level`, `first_name`, `last_name`, `phone`) VALUES ('$username','$password','$access_level','$first_name','$last_name','$phone')";
					$result = $con->query($sql3);
					$response["success"] = 1;
					$response["message"] = $result;
				}
			}
		}
		//OPERATOR DELETION
		else if($action=='operator_deletion'){
			if (empty($_GET['id'])) {
			  $response["error"] = 103;
			  $response["message"] = "Required fields : id";
			}else{
				$id= $_GET['id'];
				$sql2 = " DELETE FROM `operators` WHERE `id`='$id'";
				$response["success"] = 1;
				$result = $con->query($sql2);
				if(mysqli_affected_rows($con)!=0)
					$response["message"] = "true";
				else
					$response["message"] = "false";
			}
		}
		//OPERATOR SEARCH
		else if($action=='search_operator'){
			if(empty($_GET['term'])){
				$response["message"] = "";
			}else{
				$term = $_GET['term'];
				if(empty($_GET['double'])){
					$sql = " SELECT * FROM `operators` WHERE `phone` LIKE '$term%' OR `username` LIKE '$term%' OR `first_name` LIKE '$term%' OR `last_name` LIKE '$term%'";
				}else{
					$sql = " SELECT * FROM `operators` WHERE `phone` LIKE '%$term%' OR `username` LIKE '%$term%' OR `first_name` LIKE '$term%' OR `last_name` LIKE '$term%'";
				}
				$result = $con->query($sql);
				$stack = array();
				$d = array();
				if ($result->num_rows > 0) {
					while($row = $result->fetch_assoc()) {
						$d[] = $row;
					}
				} else {
					$response["error"] = 102;
					$response["message"] = "No rows available.";
				}
				$response["results"] = $d;
			}
		}
		//OPERATOR SAVE
		else if($action=='operator_save'){
			if (empty($_GET['id']) || empty($_GET['username']) || empty($_GET['password']) || empty($_GET['access_level']) || empty($_GET['first_name']) || empty($_GET['last_name']) || empty($_GET['phone']) ) {
			  $response["error"] = 103;
			  $response["message"] = "Required fields : id,username,password,access_level,first_name,last_name,phone";
			}else{
				$id = $_GET['id'];
				$username = $_GET['username'];
				$password = $_GET['password'];
				$access_level = $_GET['access_level'];
				$first_name = $_GET['first_name'];
				$last_name = $_GET['last_name'];
				$phone = $_GET['phone'];

				$sql2 = " UPDATE `operators` SET `username`='$username',`password`='$password',`access_level`='$access_level',`first_name`='$first_name',`last_name`='$last_name',`phone`='$phone' WHERE `id`='$id'";
				$result = $con->query($sql2);
				$response["success"] = 1;
				$response["message"] = $result;
				$barcode = " SELECT * FROM operators WHERE `id`='$id' ";
				$result = $con->query($barcode);
				$row = $result->fetch_assoc();
				$response["id"]=$row['id'];
				$response["username"]=$row['username'];
				$response["password"]=$row['password'];
				$response["access_level"]=$row['access_level'];
				$response["first_name"]=$row['first_name'];
				$response["last_name"]=$row['last_name'];
				$response["phone"]=$row['phone'];
			}
		}
		//DATABASE STATS
		else if($action=='get_db'){
			if(empty($_GET['filter'])){
				$response["error"] = 103;
				$response["message"] = "Required fields : filter";
			}else{
				$filter = $_GET['filter'];
				if($filter == 'default' ){
					$sql = " SELECT * FROM `customers` ";
				}else if($filter == 'name' ){
					$sql = " SELECT * FROM `customers` ORDER BY `name` ";
				}else if($filter == 'stamps' ){
					$sql = " SELECT * FROM `customers` ORDER BY `stamps` DESC ";
				}else if($filter == 'barcode' ){
					$sql = " SELECT * FROM `customers` ORDER BY `barcode` DESC ";
				}else if($filter == 'visits' ){
					$sql = " SELECT * FROM `customers` ORDER BY `visits` DESC";
				}
				$result = $con->query($sql);
				$stack = array();
				$d = array();
				if ($result->num_rows > 0) {
					while($row = $result->fetch_assoc()) {
						$d[] = $row;
					}
				} else {
					$response["error"] = 102;
					$response["message"] = "No rows available.";
				}
				$response["results"] = $d;
			}
		}
		//Error for Action parameter
		else{
			$response["error"] = 101;
			$response["message"] = 'Error on Action Paramenter';
		}
	}
	mysqli_close($con);
	die(json_encode($response));	

?>
