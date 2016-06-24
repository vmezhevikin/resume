package net.devstudy.resume.model;

public class UploadImageResult {
	
	private String largeImageLink;
	private String smallImageLink;

	public UploadImageResult(String largeImageLink, String smallImageLink) {
		super();
		this.largeImageLink = largeImageLink;
		this.smallImageLink = smallImageLink;
	}

	public String getLargeImageLink() {
		return largeImageLink;
	}

	public String getSmallImageLink() {
		return smallImageLink;
	}
}