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

import com.google.gson.*;
import java.lang.reflect.Type;

/**
 *
 * @author Tim Vaughan
 */
public class NodeDeserializer implements JsonDeserializer<Node> {

	@Override
	public Node deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {

		JsonObject jsonObject = (JsonObject)json;
		Node result;

		if (jsonObject.get("groupName") != null) {
			result = new Group(jsonObject.get("groupName").getAsString());
		} else {
			result = new Credential(jsonObject.get("title").getAsString(),
					jsonObject.get("user").getAsString(),
					jsonObject.get("password").getAsString());
		}

		JsonArray jsonArray = jsonObject.getAsJsonArray("children");
		for (JsonElement childJson : jsonArray) {
			result.addChild(deserialize(childJson, Node.class, context));
		}

		return result;
	}
	
}
