package core.scene;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RenderList {

	private LinkedHashMap<String, GameObject> objectList;
	private boolean changed;

	public RenderList() {

		changed = false;
		objectList = new LinkedHashMap<String, GameObject>();
	}

	public LinkedHashMap<String, GameObject> getObjectList() {
		return objectList;
	}

	public void setObjectList(LinkedHashMap<String, GameObject> objectList) {
		this.objectList = objectList;
	}

	public boolean contains(String id) {

		return objectList.containsKey(id);
	}

	public GameObject get(String key) {

		return objectList.get(key);
	}

	public void add(GameObject object) {

		objectList.put(object.getId(), object);
	}

	public void remove(GameObject object) {

		objectList.remove(object.getId());
	}

	public void remove(String key) {

		objectList.remove(key);
	}

	public Set<String> getKeySet() {

		return objectList.keySet();
	}

	public Set<Map.Entry<String, GameObject>> getEntrySet() {

		return objectList.entrySet();
	}

	public Collection<GameObject> getValues() {

		return objectList.values();
	}

	public List<GameObject> sortFrontToBack() {
		return null;
	}

	public List<GameObject> sortBackToFront() {
		return null;
	}

	public boolean hasChanged() {
		return changed;
	}

	public boolean isEmpty() {
		return objectList.isEmpty();
	}
}