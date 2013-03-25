<?php 
	include 'db_functions.php';

	//header('Content-type: application/json');
	$out = array();
	$token = $_GET['token'];
	$user = user_id($token);
	if($user == -1){// wrong token
		$out['status'] = 404;
		$out['error'] = 'Wrong token';
	}else{
		$out['status'] = 200;
		$type = $_GET["type"];
		if($type== "create"){
			$name = $_GET['name'];
			create_events($name, $user);
		}else if($type== "all"){
			$out['events'] = my_events($user);
		}else if($type== "edit"){
			$event_id = $_GET['event_id'];
			$name = $_GET['name'];
			edit_event($event_id, $user, $name);
			
		}else if($type== "delete"){
			$event_id = $_GET['event_id'];
			delete_event($event_id, $user);
			
		}else if($type== "search"){
			$name = $_GET['name'];
			$out['events'] = search_events($name);
		}else if($type== "join"){
			$event_id = $_GET['event_id'];
			join_event($event_id, $user);
		}

	}
	echo json_encode($out);
?>