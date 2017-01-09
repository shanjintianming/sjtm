package springDemo.oauth.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import springDemo.oauth.vo.OauthClick;
import springDemo.oauth.vo.OauthClickExample;

public interface OauthClickMapper {
    long countByExample(OauthClickExample example);

    int deleteByExample(OauthClickExample example);

    int insert(OauthClick record);

    int insertSelective(OauthClick record);

    List<OauthClick> selectByExample(OauthClickExample example);

    int updateByExampleSelective(@Param("record") OauthClick record, @Param("example") OauthClickExample example);

    int updateByExample(@Param("record") OauthClick record, @Param("example") OauthClickExample example);
}