package springDemo.file.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springDemo.file.dao.OauthFileMapper;
import springDemo.file.service.FileService;
import springDemo.file.vo.OauthFile;

@Service
public class FileServiceImp implements FileService {

	@Autowired
	private OauthFileMapper oauthFileMapper;

	@Transactional
	@Override
	public int BatchInsetFileInfo(List<OauthFile> infoLst) {
		return oauthFileMapper.batchInsert(infoLst);
	}

	@Override
	public List<OauthFile> selectAll() {
		return oauthFileMapper.selectByExample(null);
	}
}
