<?php

/**
 * UserIdentity represents the data needed to identity a user.
 * It contains the authentication method that checks if the provided
 * data can identity the user.
 */
class UserIdentity extends CUserIdentity {
	/**
	 * Authenticates a user.
	 * The example implementation makes sure if the username and password
	 * are both 'demo'.
	 * In practical applications, this should be changed to authenticate
	 * against some persistent user identity storage (e.g. database).
	 * @return boolean whether authentication succeeds.
	 */
	public function authenticate() {
		$wiusers = WiUser::model()->findAll();

		$users = array(
			// username => password
			'demo' => 'demo',
			'admin' => 'admin',
		);

		foreach ($wiusers as $entry) {
			//print_r($entry);
			$users[$entry['username']] = $entry['password'];
		}

		//print_r($users);

		if (!isset($users[$this->username])) {
			$this->errorCode = self::ERROR_USERNAME_INVALID;
		} elseif ($users[$this->username] !== $this->password) {
			$this->errorCode = self::ERROR_PASSWORD_INVALID;
		} else {
			$this->errorCode = self::ERROR_NONE;
		}

		//return $users;
		return !$this->errorCode;
	}
}