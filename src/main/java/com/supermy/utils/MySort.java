package com.supermy.utils;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 * extjs 的order json 转换为 mongodb 的 order查询参数
 * @author jamesmo
 *
 */
public class MySort {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerFactory.getLogger(MySort.class);

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    private String sort;
	


	public MySort(String sortJson) throws JsonParseException, JsonMappingException, IOException {

        if ( null==sortJson || "".equalsIgnoreCase(sortJson) ){
            return;
        }

		logger.debug("sort value:{}",sortJson);

		ObjectMapper mapper = new ObjectMapper();
        ExtSortData[] userData = mapper.readValue(sortJson, ExtSortData[].class);

        StringBuffer o=new StringBuffer("");
        if (userData.length>0){

            for(ExtSortData obj : userData){
                if (o.length()>0){
                    o.append(",");
                }
                String property = obj.getProperty();
                String direction = obj.getDirection();
                o.append(property).append(" ");
                o.append(direction);
            }
            sort =" order by "+o.toString();
        }

	}

	/**
	 * @param args
	 * @throws java.io.IOException
	 * @throws org.codehaus.jackson.map.JsonMappingException
	 * @throws org.codehaus.jackson.JsonParseException
	 */
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {

		MySort mySort1 = new MySort("[{\"property\":\"content\",\"direction\":\"ASC\"},{\"property\":\"content1\",\"direction\":\"DESC\"}]");
        System.out.println("排序："+mySort1.getSort());

	}


}
