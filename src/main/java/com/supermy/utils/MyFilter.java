package com.supermy.utils;


import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 * extjs 的order json 转换为 mongodb 的 filter 查询参数
 * @author jamesmo
 *
 */
public class MyFilter {
	private final Logger logger = LoggerFactory.getLogger(MyFilter.class);

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getFilter() {
        return filter;
    }

    private String filter;

	
	public MyFilter(String filterJson) throws IOException {
        if ( null==filterJson || "".equalsIgnoreCase(filterJson) ){
            return;
        }

		logger.debug("filter value:{}",filterJson);

		ObjectMapper mapper = new ObjectMapper();


        ExtFilterData[] filter = mapper.readValue(filterJson, ExtFilterData[].class);
//        for(ExtFilterData obj:filter){
//            System.out.println(obj.getComparison());
//            System.out.println(obj.getType());
//            System.out.println(obj.getValue());
//        }

//		List<LinkedHashMap<String,Object>> filterMap = mapper.readValue(filterJson, List.class);
		
        StringBuffer o=new StringBuffer();
        for(ExtFilterData obj:filter){
            if (o.length()>0){
                o.append(" and ");
            }
            
            String property=obj.getProperty();
            logger.debug("filter property:{$1}",property);

            

            String type = obj.getType();
            logger.debug("filter type:{$1}",type);
            
            if ("date".equalsIgnoreCase(type)){

                //o.append(obj.getField());
                o.append("to_char("+ obj.getField() +",'yyyy/mm/dd')");

                String comparison = obj.getComparison();
                if ("lt".equalsIgnoreCase(comparison)){
                    o.append(" < ");
                }
                if ("gt".equalsIgnoreCase(comparison)){
                    o.append(" > ");
                }
                if ("eq".equalsIgnoreCase(comparison)){
                    o.append(" = ");
                }

                o.append("'"+ obj.getValue() +"'");

                //to_date('2014/6/12','yyyy/mm/dd')
                //o.append("to_date('"+ obj.getValue() +"','yyyy/mm/dd')");
            }

            if ("string".equalsIgnoreCase(type)){
                o.append(obj.getField());

                o.append(" like ");

                o.append("'%"+ obj.getValue() +"%'");
            }

            if ("boolean".equalsIgnoreCase(type)){

                o.append(obj.getField());

                o.append(" = ");

                o.append(obj.getValue());
            }
            
            if (StringUtils.isNotEmpty(property)){
                o.append(property);

                o.append(" = ");

                o.append("'"+ obj.getValue() +"'");
            }


//            if ("list".equalsIgnoreCase(type)){  构造的时候有问题
//
//
//                o.append(obj.getField());
//
//                o.append(" is ");
//
//                o.append("'%"+ obj.getValue() +"%'");
//            }

		}
		this.filter =o.toString();

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 * @throws java.io.IOException
	 */
	public static void main(String[] args) throws IOException {
		String filter="[{\"type\":\"date\",\"comparison\":\"gt\",\"value\":\"2014-06-07\",\"field\":\"cdate\"},{\"type\":\"date\",\"comparison\":\"lt\",\"value\":\"2014-06-09\",\"field\":\"udate\"},{\"type\":\"string\",\"value\":\"快卡\",\"field\":\"name\"}]";

		MyFilter f = new MyFilter(filter);
		
		System.out.println(f.getFilter());
		

	}


}
