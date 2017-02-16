package springDemo.file.service;

import java.util.List;

import springDemo.file.vo.OauthFile;

public interface FileService {
	public int BatchInsetFileInfo(List<OauthFile> infoLst);
	
	public List<OauthFile> selectAll();
}
