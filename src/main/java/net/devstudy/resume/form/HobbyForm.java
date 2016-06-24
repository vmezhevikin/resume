package net.devstudy.resume.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.devstudy.resume.annotation.constraints.FirstFieldLessThanSecond;
import net.devstudy.resume.entity.Hobby;

@FirstFieldLessThanSecond(first = "currSize", second = "maxSize")
public class HobbyForm implements Serializable {
	
	private static final long serialVersionUID = 4518077336034434467L;

	private Integer maxSize;

	private Integer currSize;

	// @Size(max = 5, message = "Select no more than 5 items")
	private List<String> checkedItems = new ArrayList<>();

	public HobbyForm() {
		super();
	}

	public HobbyForm(List<Hobby> items, int maxSize) {
		super();
		for (Hobby hobby : items)
			checkedItems.add(hobby.getDescription());
		this.maxSize = maxSize;
	}

	public List<String> getCheckedItems() {
		return checkedItems;
	}

	public void setCheckedItems(List<String> checkedItems) {
		this.checkedItems = checkedItems;
		this.currSize = checkedItems.size();
	}

	public List<Hobby> getSelecetedHobbies() {
		List<Hobby> items = new ArrayList<>();
		for (String checked : checkedItems) {
			Hobby hobby = new Hobby();
			hobby.setDescription(checked);
			items.add(hobby);
		}

		return items;
	}

	public Integer getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(Integer maxSize) {
		this.maxSize = maxSize;
	}

	public Integer getCurrSize() {
		return currSize;
	}

	public void setCurrSize(Integer currSize) {
		this.currSize = currSize;
	}
}
