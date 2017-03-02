package springdemo.user.dto;

public class UploadDto {
	private String[] initialPreview;
	
	private ImageDto[] initialPreviewConfig;
	
	public String[] getInitialPreview() {
		return initialPreview;
	}

	public void setInitialPreview(String[] initialPreview) {
		this.initialPreview = initialPreview;
	}

	public ImageDto[] getInitialPreviewConfig() {
		return initialPreviewConfig;
	}

	public void setInitialPreviewConfig(ImageDto[] initialPreviewConfig) {
		this.initialPreviewConfig = initialPreviewConfig;
	}
}
