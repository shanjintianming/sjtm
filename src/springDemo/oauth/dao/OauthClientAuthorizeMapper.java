package springDemo.oauth.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import springDemo.oauth.vo.OauthClientAuthorize;
import springDemo.oauth.vo.OauthClientAuthorizeExample;

public interface OauthClientAuthorizeMapper {
    long countByExample(OauthClientAuthorizeExample example);

    int deleteByExample(OauthClientAuthorizeExample example);

    int insert(OauthClientAuthorize record);

    int insertSelective(OauthClientAuthorize record);

    List<OauthClientAuthorize> selectByExample(OauthClientAuthorizeExample example);

    int updateByExampleSelective(@Param("record") OauthClientAuthorize record, @Param("example") OauthClientAuthorizeExample example);

    int updateByExample(@Param("record") OauthClientAuthorize record, @Param("example") OauthClientAuthorizeExample example);
    
    int batchInsert(List<OauthClientAuthorize> list);
}