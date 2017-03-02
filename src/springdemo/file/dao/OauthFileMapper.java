package springdemo.file.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import springdemo.file.vo.OauthFile;
import springdemo.file.vo.OauthFileExample;

public interface OauthFileMapper {
    long countByExample(OauthFileExample example);

    int deleteByExample(OauthFileExample example);

    int insert(OauthFile record);

    int insertSelective(OauthFile record);

    List<OauthFile> selectByExample(OauthFileExample example);

    int updateByExampleSelective(@Param("record") OauthFile record, @Param("example") OauthFileExample example);

    int updateByExample(@Param("record") OauthFile record, @Param("example") OauthFileExample example);
    
    int batchInsert(List<OauthFile> list);
}