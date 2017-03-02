package springdemo.file.service;

import java.util.List;

import springdemo.file.vo.OauthFile;

public interface FileService {
	public int BatchInsetFileInfo(List<OauthFile> infoLst);
	
	public List<OauthFile> selectAll();
}
