package springdemo.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import springdemo.user.vo.OauthUser;
import springdemo.user.vo.OauthUserExample;

public interface OauthUserMapper {
    long countByExample(OauthUserExample example);

    int deleteByExample(OauthUserExample example);

    int insert(OauthUser record);

    int insertSelective(OauthUser record);

    List<OauthUser> selectByExample(OauthUserExample example);

    int updateByExampleSelective(@Param("record") OauthUser record, @Param("example") OauthUserExample example);

    int updateByExample(@Param("record") OauthUser record, @Param("example") OauthUserExample example);
}