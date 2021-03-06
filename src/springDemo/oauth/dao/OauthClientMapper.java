package springDemo.oauth.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import springDemo.oauth.vo.OauthClient;
import springDemo.oauth.vo.OauthClientExample;

public interface OauthClientMapper {
    long countByExample(OauthClientExample example);

    int deleteByExample(OauthClientExample example);

    int insert(OauthClient record);

    int insertSelective(OauthClient record);

    List<OauthClient> selectByExample(OauthClientExample example);

    int updateByExampleSelective(@Param("record") OauthClient record, @Param("example") OauthClientExample example);

    int updateByExample(@Param("record") OauthClient record, @Param("example") OauthClientExample example);
}