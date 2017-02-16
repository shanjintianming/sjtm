package springDemo.util;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public enum JsonUtil {
	Json;
	
	private ObjectMapper mapper;
	
	private JsonUtil(){
		mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);  
	}
	
	@JsonCreator
	public <T, E, F> Map<E, F> jsonToMap(String jsonStr, Class<T> mapClass, Class<E> keyClass, Class<F> valClass){
		
		Map<E, F> resultMap = null;
		
		try {
			JavaType javaType = mapper.getTypeFactory().constructMapLikeType(mapClass, keyClass, valClass);
			
			resultMap = mapper.readValue(jsonStr, javaType);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultMap;	
	}
}
