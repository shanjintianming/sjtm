package springDemo.core.ajax;

public class AjaxResult {
	private boolean result;
	
	private String message;
	
	private String url;
	
	private Object obj;

	public AjaxResult(){
		this(false, null, null, null);
	}
	
	public AjaxResult(boolean result,String message, String url, Object obj){
		this.result = result;
		this.message = message;
		this.url = url;
		this.obj = obj;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
}
