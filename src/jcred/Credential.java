/*
 * Copyright (C) 2012 Tim Vaughan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package jcred;

/**
 * Class of objects representing an individual set of credentials.
 *
 * @author Tim Vaughan
 */
public class Credential extends Node {

	// Identifying title of user/password pair:
	String title;

	// Username component of credential:
	String user;

	// Password component of credential:
	String password;

	/**
	 * Constructor to initialise new credential.
	 * 
	 * @param title
	 * @param user
	 * @param password 
	 */
	public Credential(String title, String user, String password) {
		this.title = title;
		this.user = user;
		this.password = password;
	}
	/*
	 * Getters and setters:
	 */

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUser() {
		return user;
	}

	public void setUsername(String user) {
		this.user = user;
	}

	/*
	 * Used to display name in JTree.
	 */
	
	@Override
	public String toString() {
		return getTitle();
	}
	
}
