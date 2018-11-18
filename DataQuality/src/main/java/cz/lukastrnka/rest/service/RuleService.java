package cz.lukastrnka.rest.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonNode;

//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;

//import org.codehaus.jackson.JsonNode;
//import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonProcessingException;
//import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import cz.lukastrnka.rest.data.Rule;
import cz.lukastrnka.rest.data.Argument;
import cz.lukastrnka.rest.data.Column;
import cz.lukastrnka.rest.data.Value;

public class RuleService {

	private static long idCounter = 2;

	static HashMap<Long, Rule> mapOfRules = getMapOfRules();;

	public RuleService() {
		super();

		if (mapOfRules == null) {
			mapOfRules = new HashMap<Long, Rule>();
		}

		// Rule r1 = new Rule(1, "AlessB", true, "<", new Argument("A"), new
		// Argument("B"));
		// Rule r1 = new Rule(1, "AlessB", true, "<");
		Argument a = new Column("A");
		Argument b = new Column("B");

		Argument c = new Column("C");
		Argument d = new Value(5);

		Rule si = new Rule();
		si.setId(1L);
		si.setName("AlessB");
		si.setEnabled(true);
		si.setOp("<");
		si.setArg1(a);
		si.setArg2(b);
		mapOfRules.put(1L, si);
		/*
		 * Rule si2 = new Rule(); si2.setId(2); si2.setName("dis5");
		 * si2.setEnabled(false); si2.setOp("!="); si2.setArg1(c); si2.setArg2(d);
		 * mapOfRules.put(2L, si2);
		 * 
		 * System.out.println("-- before serialization --"); System.out.println(si);
		 * System.out.println(si2);
		 */
		ObjectMapper om = new ObjectMapper();
		try {
			String jsonString = om.writeValueAsString(si);

			System.out.println("-- after serialization --");
			System.out.println(jsonString);
			/*
			 * jsonString = om.writeValueAsString(si2); System.out.println(jsonString);
			 * mapOfRules.remove(2); System.out.println("2 pryc");
			 */
		} catch (Exception e) {
		}

	}

	private long getNewId() {
		return idCounter++;
	}

	public Rule getRule(long id) {
		if (mapOfRules.containsKey(id)) {
			Rule rule = mapOfRules.get(id);

			System.out.println("id: " + id + " " + mapOfRules.get(id));
			return rule;
		} else {
			return null;
		}
	}

	private Argument addArg(JsonNode n) throws UnknownArgumentException {

		if (n.has("column") && n.get("column").isTextual()) {
			Argument arg = new Column(n.get("column").asText());
			return arg;
		} else if (n.has("value") && n.get("value").isInt()) {
			Argument arg = new Value(n.get("value").asInt());
			return arg;
		} else {
			// System.out.println(" wrong input json !!!" + n.toString());
			throw new UnknownArgumentException(n.toString());
		}
	}

	private boolean uniqueName(String name) {

		for (Entry<Long, Rule> entry : mapOfRules.entrySet()) {
			//System.out.println("*** name in map : " + entry.getValue().getName() + " ==? " + name);
			//System.out.println("*** name equals : " + (entry.getValue().getName().equals(name)));
			if (entry.getValue().getName().equals(name)) {

				return false;
			}
		}

		return true;
	}

	public Rule addRule(String rule) throws UnknownArgumentException, NonUniqueNameException { 
		// throws JsonProcessingException, IOException

		JsonNode node;

		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode mapOfMessage = mapper.readTree(rule);
			System.out.println("uniqueName(mapOfMessage.get(\"name\").asText())   "
					+ uniqueName(mapOfMessage.get("name").asText()));
			if (uniqueName(mapOfMessage.get("name").asText()) == false) {
				System.out.println("//////// not unique name");
				throw new NonUniqueNameException(mapOfMessage.get("name").asText());
			}

			Argument ar1 = addArg(mapOfMessage.get("arg1"));
			Argument ar2 = addArg(mapOfMessage.get("arg2"));

			Rule ruleToAdd = new Rule();

			ruleToAdd.setId(getNewId());

			node = mapOfMessage.get("name");
			ruleToAdd.setName(node.asText());

			node = mapOfMessage.get("enabled");
			ruleToAdd.setEnabled(node.asBoolean());

			node = mapOfMessage.get("op");
			ruleToAdd.setOp(node.asText());

			ruleToAdd.setArg1(ar1);
			ruleToAdd.setArg2(ar2);
			mapOfRules.put(ruleToAdd.getId(), ruleToAdd);

			Rule newRule = mapOfRules.get(ruleToAdd.getId());

			return newRule;

		} catch (IOException Ex) {
			// ...
			System.out.println("TODO;  some addRule IOException Ex");
			return null;
		}

	}

//	public Rule updateRule(long id, String rule) {
//		getRule(id);
//		
//		
//		
//		addRule(rule);
//		
//		return;
//	}
	
	
	public void deleteRule(long id) {
		mapOfRules.remove(id);
	}

	public static HashMap<Long, Rule> getMapOfRules() {
		return mapOfRules;
	}
}
