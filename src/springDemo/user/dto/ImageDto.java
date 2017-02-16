package springDemo.user.dto;

public class ImageDto {
	private String type;
	
	private String caption;
	
	private long size;
	
	private String width;
	
	private String url;
	
	private String key;

	private String fileName;
	
	private String previewAsData;
	
	private UrlDto extra;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPreviewAsData() {
		return previewAsData;
	}

	public void setPreviewAsData(String previewAsData) {
		this.previewAsData = previewAsData;
	}

	public UrlDto getExtra() {
		return extra;
	}

	public void setExtra(UrlDto extra) {
		this.extra = extra;
	}
}
