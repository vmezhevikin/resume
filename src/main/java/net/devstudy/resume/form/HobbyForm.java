package net.devstudy.resume.form;

import java.io.Serializable;
import java.util.List;

import net.devstudy.resume.annotation.constraints.FirstFieldLessThanSecond;
import net.devstudy.resume.entity.Hobby;

@FirstFieldLessThanSecond(firstField = "currSize", secondField = "maxSize")
public class HobbyForm implements Serializable {
	
	private static final long serialVersionUID = 4518077336034434467L;

	private Integer maxSize;

	private Integer currSize;

	private List<Hobby> items;

	public HobbyForm() {
		super();
	}

	public HobbyForm(List<Hobby> items, int maxSize) {
		super();
		this.items = items;
		this.maxSize = maxSize;
		this.currSize = 0;
		for (Hobby hobby : items) {
			if ("checked".equals(hobby.getChecked())) {
				this.currSize++;
			}
		}
	}

	public List<Hobby> getItems() {
		return items;
	}

	public void setItems(List<Hobby> items) {
		this.items = items;
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
