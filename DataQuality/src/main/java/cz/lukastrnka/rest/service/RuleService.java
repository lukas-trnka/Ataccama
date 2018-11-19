package cz.lukastrnka.rest.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import cz.lukastrnka.rest.data.Rule;
import cz.lukastrnka.rest.data.Argument;
import cz.lukastrnka.rest.data.Column;
import cz.lukastrnka.rest.data.Value;

public class RuleService {

	private static long idCounter = 1;

	static HashMap<Long, Rule> mapOfRules = getMapOfRules();;

	public RuleService() {
		super();

		if (mapOfRules == null) {
			mapOfRules = new HashMap<Long, Rule>();
		}

	}

	/***
	 * Returns new ID for new Rule and incremtents counter. Each new rule has unique
	 * ID, no previous ID is reused.
	 * 
	 * @return new ID
	 */
	private long getNewId() {
		return idCounter++;
	}

	public List<Rule> getRulesFromMap() {
		List<Rule> rr = new ArrayList<Rule>();
		mapOfRules.forEach((k, v) -> {
			System.out.println("Item : " + k + " Count : " + v);
			rr.add(mapOfRules.get(k));
		});

		return rr;
	}

	public Rule getRule(long id) {
		if (mapOfRules.containsKey(id)) {
			Rule rule = mapOfRules.get(id);

			return rule;
		} else {
			return null;
		}
	}

	/***
	 * Creates and returns correct object of Argument. If there is a column-string
	 * in Json, then creates a Column type object and adds it in Argument object
	 * (superclass). If there is a value-integer, then returns Argument object with
	 * a Value type object in it.
	 * 
	 * @param n Json node with Argument Json object.
	 * @return Argument object, witch is a superclass of Column and Value objects.
	 * @throws UnknownArgumentException Json object is not a column or value
	 */
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

	/***
	 * Checks if such name already exists in map of all Rules.
	 * 
	 * @param name
	 * @return true if there is no such name in map of all Rules, otherwise returns
	 *         false
	 */
	private boolean uniqueName(String name) {

		for (Entry<Long, Rule> entry : mapOfRules.entrySet()) {
			if (entry.getValue().getName().equals(name)) {

				return false;
			}
		}

		return true;
	}

	public Rule addRule(String rule) throws UnknownArgumentException, NonUniqueNameException {

		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode mapOfMessage = mapper.readTree(rule);
			if (uniqueName(mapOfMessage.get("name").asText()) == false) {
				throw new NonUniqueNameException(mapOfMessage.get("name").asText());
			}

			Rule ruleToAdd = new Rule();
			ruleToAdd = createRule(mapOfMessage);
			ruleToAdd.setId(getNewId());

			mapOfRules.put(ruleToAdd.getId(), ruleToAdd);

			Rule newRule = mapOfRules.get(ruleToAdd.getId());
			return newRule;

		} catch (IOException Ex) {
			// ...
			System.out.println("TODO;  some addRule IOException Ex");
			return null;
		}

	}

	/***
	 * Parse user Json input into Rule object.
	 * 
	 * @param r User input with Json message mapped into JsonNode
	 * @return Returns object Rule without id.
	 * @throws UnknownArgumentException
	 */
	private Rule createRule(JsonNode r) throws UnknownArgumentException {

		Rule ruleToAdd = new Rule();

		Argument ar1 = addArg(r.get("arg1"));
		Argument ar2 = addArg(r.get("arg2"));

		JsonNode node = r.get("name");
		ruleToAdd.setName(node.asText());

		node = r.get("enabled");
		ruleToAdd.setEnabled(node.asBoolean());

		node = r.get("op");
		ruleToAdd.setOp(node.asText());

		ruleToAdd.setArg1(ar1);
		ruleToAdd.setArg2(ar2);

		return ruleToAdd;
	}

	/***
	 * Compares operators and arguments of two Rules.
	 * 
	 * @param ruleOne
	 * @param ruleTwo
	 * @return true if both Rules have the same operators and arguments, otherwise
	 *         false
	 */
	private boolean compareRules(Rule ruleOne, Rule ruleTwo) {

		if (!(ruleOne.getOp().equals(ruleTwo.getOp()))) {
			return false;
		}
		if (!(ruleOne.getArg1().giveArgument().equals(ruleTwo.getArg1().giveArgument()))) {
			return false;
		}
		if (!(ruleOne.getArg2().giveArgument().equals(ruleTwo.getArg2().giveArgument()))) {
			return false;
		}

		return true;
	}

	public Rule updateRule(long id, String rule) throws UnknownArgumentException {

		boolean ableToUpdate = false;
		long idToUpdate;

		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode mapOfMessage = mapper.readTree(rule);

			Rule ruleToUpdate = createRule(mapOfMessage);
			ruleToUpdate.setId(mapOfMessage.get("id").asLong());
			
			
			idToUpdate = ruleToUpdate.getId();
			if (!(idToUpdate == id)) {
				throw new IllegalArgumentException("Mishmash in ID of Json message: " + idToUpdate + " and URL: " + id);
			}
			ableToUpdate = compareRules(getRule(id), ruleToUpdate); 
			System.out.println("compare: " + compareRules(getRule(id), ruleToUpdate));

			if (ableToUpdate) {
				ruleToUpdate.setId(id);
				deleteRule(id);
				mapOfRules.put(id, ruleToUpdate);

				Rule updatedRule = mapOfRules.get(id);
				return updatedRule;
			} else {
				throw new IllegalArgumentException("Cannot change the value.");
			}

		} catch (IOException Ex) {
			// ...
			System.out.println("TODO;  some addRule IOException Ex");
			return null;
		}
	}

	public void deleteRule(long id) {
		mapOfRules.remove(id);
	}

	public static HashMap<Long, Rule> getMapOfRules() {
		return mapOfRules;
	}
}
