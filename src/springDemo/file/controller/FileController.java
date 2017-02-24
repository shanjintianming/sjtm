package springDemo.file.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import springDemo.core.shiro.Principal;
import springDemo.file.service.FileService;
import springDemo.file.vo.OauthFile;
import springDemo.user.dto.UploadDto;
import springDemo.util.FileUtil;

@Controller
public class FileController {
	
	@Autowired
	private FileService fileService;
	
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String toUpload() {
		return "upload";
	}
	
	@RequestMapping(value = "/uploadView", method = RequestMethod.GET)
	public String toUploadView() {
		return "uploadView";
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public UploadDto toLogin(@RequestParam("fileLst") CommonsMultipartFile[] fileLst) {
		
		UploadDto uploadDto = FileUtil.image.upload(fileLst[0], 1);
		return uploadDto;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String deleteFile(@RequestParam(value="deleteUrl",required=false) String deleteUrl) {
		// request.get
		FileUtil.image.delete(deleteUrl);
		return "success";
	}
	
	@RequestMapping(value = "/submitFile", method = RequestMethod.POST)
	public String submitFile(@RequestParam(value="fileLstUrl",required=false) String[] fileLstUrl) {
		
		if (fileLstUrl != null) {
			Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
			
			List<OauthFile> infoLst = new ArrayList<OauthFile>();
			List<String> urlList = new ArrayList<>();
					
			for(String fileUrl : fileLstUrl){
				if (!urlList.contains(fileUrl)) {
					OauthFile file = new OauthFile();
					file.setFileUrl(fileUrl);
					file.setFileType(1);
					file.setInsertUser(principal.getUserId());
					file.setUpdateUser(principal.getUserId());
					infoLst.add(file);
				}
				
				urlList.add(fileUrl);
			}
			
			fileService.BatchInsetFileInfo(infoLst);			
		}

		return "uploadView";
	}
	
	@RequestMapping(value = "/getFile")
	@ResponseBody
	public List<OauthFile> getFile() {
		List<OauthFile> infoLst = fileService.selectAll();
		return infoLst;
	}
}
