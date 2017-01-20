<?php
	$host = "localhost";
	$user = "id99137_agelo1995";
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
				$response["id"]=$row[id];
				$response["name"] = $row[name];
				$response["required_stamps"] = $row[required_stamps];
			}
		}
		//COUPON DELETION
		else if($action=='coupon_deletion'){
			if (empty($_GET['name'])) {
			  $response["error"] = 103;
			  $response["message"] = "Required fields : name";	  
			}else{
				$name = $_GET['name'];
				$sql2 = " DELETE FROM `coupons` WHERE `name`='$name'";
				$response["success"] = 1;
				$result = $con->query($sql2);
				if(mysqli_affected_rows($con)!=0)
					$response["message"] = "true";
				else
					$response["message"] = "false";
			}
		}
		//COUPON CHANGE
		else if($action=='coupon_update'){
			
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
					$response["id"]=$row[id];
					$response["name"]=$row[name];
					$response["surname"]=$row[surname];
					$response["phone"]=$row[phone];
					$response["barcode"]=$row[barcode];
					$response["stamps"]=$row[stamps];
					$response["coupons_used"]=$row[coupons_used];
					$response["visits"]=$row[visits];
					$response["last_visit"]=$row[last_visit];
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
			  mysqli_close($con);
			  die(json_encode($response));
				  
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
					$response["id"]=$row[id];
					$response["name"] = $row[name];
					$response["surname"] = $row[surname];
					$response["phone"] = $row[phone];
					$response["barcode"] = $row[barcode];
					$response["stamps"]=$row[stamps];
					$response["coupons_used"]=$row[coupons_used];
					$response["visits"]=$row[visits];
					$response["last_visit"]=$row[last_visit];
				}else{
					$response["success"] = 0;
					$response["message"] = $check;
				}
			}
		}
		//CUSTOMER DELETION
		else if($action=='customer_deletion'){
			if (empty($_GET['username'])) {
			  $response["error"] = 103;
			  $response["message"] = "Required fields : username"; 
			}else{
				$username = $_GET['username'];
				$sql2 = " DELETE FROM `customers` WHERE `barcode`='$username' OR `phone`='$username'";
				$response["success"] = 1;
				$result = $con->query($sql2);
				if(mysqli_affected_rows($con)!=0)
					$response["message"] = "true";
				else
					$response["message"] = "false";
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
					$response["id"]=$row[id];
					$response["username"]=$row[username];
					$response["password"]=$row[password];
					$response["access_level"]=$row[access_level];
					$response["first_name"]=$row[first_name];
					$response["last_name"]=$row[last_name];
					$response["phone"]=$row[phone];	
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
			if (empty($_GET['username'])) {
			  $response["error"] = 103;
			  $response["message"] = "Required fields : username"; 
			}else{
				$username = $_GET['username'];
				$sql2 = " DELETE FROM `operators` WHERE `username`='$username' OR `phone`='$username'";
				$response["success"] = 1;
				$result = $con->query($sql2);
				if(mysqli_affected_rows($con)!=0)
					$response["message"] = "true";
				else
					$response["message"] = "false";
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