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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.InputStream;

/**
 *
 * @author Tim Vaughan
 */
public class Database {

	Node root;

	/**
	 * Constructor for empty credentials database.
	 */
	public Database() {

		root = new Group("root");
	}

	/**
	 * Constructor for credentials database read in JSON
	 * format from input stream.
	 * 
	 * @param iStream Input stream.
	 */
	public Database(InputStream iStream) {

	}

	/**
	 * Main method for testing and debugging only.
	 * 
	 * @param argv Command line parameters. (Unused.)
	 */
	public static void main (String[] argv) {

		Node myRoot = new Group("root");

		myRoot.addChild(new Group("Group 1"));
		myRoot.getChildren().get(0).addChild(new Credential("Title 1","User 1","Pass 1"));
		myRoot.getChildren().get(0).addChild(new Credential("Title 2","User 2","Pass 2"));

		myRoot.addChild(new Group("Group 2"));
		myRoot.getChildren().get(1).addChild(new Credential("Title 3","User 3","Pass 3"));
		myRoot.getChildren().get(1).addChild(new Credential("Title 4","User 4","Pass 4"));

		// Write test:
		Gson gsonWriter = new Gson();
		String jsonString = gsonWriter.toJson(myRoot);

		// Read test:
		Gson jsonReader = new GsonBuilder()
				.registerTypeAdapter(Node.class, new NodeDeserializer())
				.create();

		Group newRoot = jsonReader.fromJson(jsonString, Group.class);
		gsonWriter.toJson(newRoot, System.out);

	}
}
