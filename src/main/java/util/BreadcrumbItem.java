package util;

public class BreadcrumbItem {
	private String text;
	private String url;

	public BreadcrumbItem(String text, String url) {
		this.text = text;
		this.url = url;
	}

	public String getText() {
		return text;
	}

	public String getUrl() {
		return url;
	}
}