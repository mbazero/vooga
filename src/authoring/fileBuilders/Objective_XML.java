package authoring.fileBuilders;

import game_engine.behaviors.IActor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.w3c.dom.Element;

public class Objective_XML {

	private String description;
	private List<String> prereqs = new ArrayList<>();
	private List<Behaviours_XML> onComplete = new ArrayList<>();
	private List<Behaviours_XML> onFailed = new ArrayList<>();

	public Objective_XML(String description) {
		this.description = description;
	}

	private Objective_XML(String description, List<String> prereqs,
			List<Behaviours_XML> onComplete, List<Behaviours_XML> onFailed) {
		this.description = description;
		this.prereqs = prereqs;
		this.onComplete = onComplete;
		this.onFailed = onFailed;
	}

	public void addPrereqs(List<String> prereqs) {
		this.prereqs.addAll(prereqs);
	}
	
	public void addOnComplete(List<String> actions, IActor targetType) {
		addToBehaviour(onComplete, actions, targetType);
	}
	
	public void addOnFailed(List<String> actions, IActor targetType) {
		addToBehaviour(onFailed, actions, targetType);
	}

	private void addToBehaviour(List<Behaviours_XML> l, List<String> actions,
			IActor target) {
		l.addAll(actions.stream().map(s -> {
			String[] par = (s+" ").split(":");
			System.out.println(s);
			return new Behaviours_XML("sprite", "0", par[1], par[2]);
		}).collect(Collectors.toList()));
	}

	public void writeToXML(Element parent, int index, XMLBuilder xml) {
		Element thisObjective = xml.add(parent, "objective_" + index);
		xml.addChildWithValue(thisObjective, "description", description);
		StringBuilder sb = new StringBuilder();
		prereqs.forEach(s -> sb.append(s + " "));
		xml.addChildWithValue(thisObjective, "prereqs", sb.toString());
		addBehaviours(thisObjective, "onComplete", onComplete, xml);
		addBehaviours(thisObjective, "onFailed", onFailed, xml);
	}

	private void addBehaviours(Element currentObjective, String tagname,
			List<Behaviours_XML> behaviours, XMLBuilder xml) {
		Element el = xml.add(currentObjective, tagname);
		Element b = xml.add(el, "behaviors");
		int i = 0;
		for (Behaviours_XML be : behaviours)
			be.writeToXML(b, i++, xml);
	}

}
