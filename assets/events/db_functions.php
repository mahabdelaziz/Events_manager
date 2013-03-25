<?php
include 'config.php';

function get_conn(){
	$db = new PDO('mysql:host=' . db_host . ';dbname=' . db_name, db_user_name, db_password);
	//$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	return $db;
}

function get_all($table, $from_page, $order){
	$conn = get_conn();
	$start_value = $from_page * 10;
	$str = $start_value . ' ,' . 10;
	if (!isset($order))
		$order = 'id';
	$stmt = $conn->prepare('SELECT * FROM '.$table.' ORDER BY '.$order.' ASC LIMIT ' . $str);
	$stmt->execute();
	$result = $stmt->fetchAll();
	return $result;
}


function get($table, $id) {
	$conn = get_conn();
	$stmt = $conn->prepare('SELECT * FROM '.$table.' WHERE id = :id LIMIT 1');
	$stmt->execute(array('id' => $id));
	$result = $stmt->fetchAll();
	return $result[0];
}

function update($table, $row, $attributes) {
	$conn = get_conn();
	$data = ' ';
	foreach($attributes as $attr){
		if( strcmp($attr['db_name'], 'id') != 0 && strcmp($attr['db_name'], 'created_at') != 0){
			$data .= $attr['db_name'] . ' = ' . "'" .$row[$attr['db_name']] ."'"  . ',';
		}
	}
	$data = substr($data, 0, -1);
	$stmt = $conn->prepare('UPDATE ' . $table . ' SET '. $data .' WHERE id = :id');
	$stmt->execute(array('id' => $row['id']));
	$result = $stmt->fetchAll();
}

function delete($table, $id){
	$conn = get_conn();
	$stmt = $conn->prepare('DELETE FROM ' . $table . ' WHERE id = :id');
	$stmt->execute(array('id' => $id));
	$result = $stmt->fetchAll();
}

function login($email, $password){
	$conn = get_conn();
	$stmt = $conn->prepare('SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1');
	$stmt->execute(array('email' => $email, 'password' => $password));
	$result = $stmt->fetchAll();
	return $result;
}

function generate_token($id){
	$token = md5(microtime());
	$conn = get_conn();
	$stmt = $conn->prepare('UPDATE users SET token = :token WHERE id = :id');
	$stmt->execute(array('token' => $token, 'id' => $id));
	$result = $stmt->fetchAll();	
	return $token;
}

function email_exsits($email){
	$conn = get_conn();
	$stmt = $conn->prepare('SELECT * FROM users WHERE email = :email LIMIT 1');
	$stmt->execute(array('email' => $email));
	$result = $stmt->fetchAll();
	return count($result) != 0;
}

function insert_user($email, $password){
	$conn = get_conn();
	$stmt = $conn->prepare('INSERT INTO users ( email, password ) VALUES ( :email , :password)');
	$stmt->execute(array('email' => $email, 'password' => $password));
	$result = $stmt->fetchAll();
	
}

function user_id($token){
	$conn = get_conn();
	$stmt = $conn->prepare('SELECT * FROM users WHERE token = :token LIMIT 1');
	$stmt->execute(array('token' => $token));
	$result = $stmt->fetchAll();
	if(count($result) ==0)
		return -1;
	else
		return $result[0]['id'];
}

function create_events($name, $user_id){
	$conn = get_conn();
	$stmt = $conn->prepare('INSERT INTO events ( name, user_id ) VALUES ( :name , :user_id )');
	$stmt->execute(array('name' => $name, 'user_id' => $user_id));
	$result = $stmt->fetchAll();
}

function my_events($user_id){
	$conn = get_conn();
	$stmt = $conn->prepare('SELECT * FROM events WHERE user_id = :id');
	$stmt->execute(array('id' => $user_id));
	$result = $stmt->fetchAll();
	return $result;
}
function edit_event($event_id, $user_id, $params){
	$conn = get_conn();
	$stmt = $conn->prepare('UPDATE events SET name = :name WHERE user_id = :user_id AND id = :id');
	$stmt->execute(array('user_id' => $user_id, 'id' => $event_id, 'name' => $params['name']));
	$result = $stmt->fetchAll();
}

function delete_event($event_id, $user_id){
	$conn = get_conn();
	$stmt = $conn->prepare('DELETE events WHERE user_id = :user_id AND id = :id');
	$stmt->execute(array('user_id' => $user_id, 'id' => $event_id));
	$result = $stmt->fetchAll();
}

function search_events($name){
	$conn = get_conn();
	$stmt = $conn->prepare("SELECT * FROM events WHERE name LIKE :name" );
	$stmt->execute(array('name' => '%'.$name.'%'));
	$result = $stmt->fetchAll();
	return $result;
}

function join_event($event_id, $user_id){
	$conn = get_conn();
	$stmt = $conn->prepare('INSERT INTO events_join ( event_id, user_id ) VALUES ( :event_id , :user_id )');
	$stmt->execute(array('event_id' => $event_id, 'user_id' => $user_id));
	$result = $stmt->fetchAll();
}
?>
