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
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author Tim Vaughan
 */
public class Database implements TreeModel {

	Node root;

	Set<TreeModelListener> treeModelListeners;

	/**
	 * Constructor for empty credentials database.
	 */
	public Database() {

		// Create manditory root group:
		root = new Group("root");

		// Initialise TreeModel interface:
		treeModelListeners = new HashSet<TreeModelListener>();
	}

	/**
	 * Constructor for credentials database read in JSON
	 * format from input stream.
	 * 
	 * @param iStream Input stream.
	 */
	public void loadFromStream(InputStream iStream) {

		Gson jsonReader = new GsonBuilder()
				.registerTypeAdapter(Node.class, new NodeDeserializer())
				.create();

		root = jsonReader.fromJson(new InputStreamReader(iStream), Node.class);

		// Inform any listeners that tree structure has changed.
		fireTreeModelListeners();
	}

	/**
	 * Let any registered listeners know that the tree structure
	 * has changed.
	 */
	public void fireTreeModelListeners() {

		TreePath tp = new TreePath(new Node[] {root});
		TreeModelEvent evt = new TreeModelEvent(this, tp);

		for (TreeModelListener l : treeModelListeners)
			l.treeStructureChanged(evt);
	}

	/**
	 * Main method for testing and debugging only.
	 * 
	 * @param argv Command line parameters. (Unused.)
	 */
	public static void main (String[] argv) throws Exception {

		Database dbase = new Database();

		dbase.root.addChild(new Group("Group 1"));
		dbase.root.getChildren()
				.get(0)
				.addChild(new Credential("Title 1","User 1","Pass 1"));
		dbase.root.getChildren()
				.get(0)
				.addChild(new Credential("Title 2","User 2","Pass 2"));

		dbase.root.addChild(new Group("Group 2"));
		dbase.root.getChildren()
				.get(1)
				.addChild(new Credential("Title 3","User 3","Pass 3"));
		dbase.root.getChildren()
				.get(1)
				.addChild(new Credential("Title 4","User 4","Pass 4"));

		// Write test:
		Gson gsonWriter = new Gson();
		String jsonString = gsonWriter.toJson(dbase.root);

		// Read test:
		Gson jsonReader = new GsonBuilder()
				.registerTypeAdapter(Node.class, new NodeDeserializer())
				.create();

		//Node newRoot = jsonReader.fromJson(jsonString, Node.class);
		Node newRoot = jsonReader.fromJson(jsonString, Node.class);
		gsonWriter.toJson(newRoot, System.out);

	}

	/*
	 * TreeModel interface methods.
	 */

	@Override
	public Object getRoot() {
		return root;
	}

	@Override
	public Object getChild(Object parent, int index) {
		return ((Node)parent).getChildren().get(index);
	}

	@Override
	public int getChildCount(Object parent) {
		return ((Node)parent).children.size();
	}

	@Override
	public boolean isLeaf(Object node) {
		return ((Node)node).children.isEmpty();
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		return ((Node)parent).children.indexOf(child);
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {
		treeModelListeners.add(l);
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		treeModelListeners.remove(l);
	}
}
