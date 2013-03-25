<?php 
	include 'db_functions.php';

	header('Content-type: application/json');
	$out = array();
	$type = $_GET["type"];
	if($type == "login"){
		$user = login($_GET['email'], $_GET['password']);
		if(count($user) != 0){// found
			$token = generate_token($user[0]['id']);
			$out = array('status' => '200', 'token' => $token );
			
		}else{
			$out = array('status' => '404', 'error' => 'User not found' );
		}
	}else if ($type == "register") {
		$email = $_GET['email'];
		$password = $_GET['password'];
		
		if(email_exsits($email)){
			$out['status'] = '404';
			$out['error'] = 'User Already exsits';
		}else{
			$out['status'] = '200';
			insert_user($email, $password);
		}
	}
	echo json_encode($out);
?>