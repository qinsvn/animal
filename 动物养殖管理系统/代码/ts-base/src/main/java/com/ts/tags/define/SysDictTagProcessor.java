package com.ts.tags.define;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.context.ApplicationContext;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.spring5.context.SpringContextUtils;
import org.thymeleaf.templatemode.TemplateMode;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ts.common.config.TsConfig;
import com.ts.common.domain.DictDO;
import com.ts.common.service.DictService;
import com.ts.common.service.impl.DictServiceImpl;
import com.ts.common.utils.StringUtils;
import com.ts.common.utils.UtilNetwork;
/**
 * 自定义字典标签
 */
public class SysDictTagProcessor extends AbstractElementTagProcessor {
    // 标签名
    private static final String TAG_NAME = "dict";
    // 优先级
    private static final int PRECEDENCE = 10000;
    public SysDictTagProcessor(String dialectPrefix) {
        super(
                // 此处理器将仅应用于HTML模式
                TemplateMode.HTML,
                // 要应用于名称的匹配前缀
                dialectPrefix,
                // 标签名称：匹配此名称的特定标签
                TAG_NAME,
                // 将标签前缀应用于标签名称
                true,
                // 无属性名称：将通过标签名称匹配
                null,
                // 没有要应用于属性名称的前缀
                false,
                // 优先(内部方言自己的优先)
                PRECEDENCE
        );
    }
    /**
     * 处理自定义标签 DOM 结构
     *
     * @param iTemplateContext            模板页上下文
     * @param iProcessableElementTag      待处理标签
     * @param iElementTagStructureHandler 元素标签结构处理器
     */
    @Override
    protected void doProcess(ITemplateContext iTemplateContext, IProcessableElementTag iProcessableElementTag, IElementTagStructureHandler iElementTagStructureHandler) {
    	  //获取标签的属性值
        String dictType = iProcessableElementTag.getAttributeValue("type");
        //获取url
        String onload = iProcessableElementTag.getAttributeValue("onload");
        
        //静态数据字典
        if (StringUtils.isNotEmpty(dictType)) {
        	staticDict(iTemplateContext, iProcessableElementTag, iElementTagStructureHandler);
		}else if(StringUtils.isNotEmpty(onload)){//动态数据字典
			dynamicDict(iTemplateContext, iProcessableElementTag, iElementTagStructureHandler);
		}else{
		}
        
    }
    
    private void staticDict(ITemplateContext iTemplateContext, IProcessableElementTag iProcessableElementTag, IElementTagStructureHandler iElementTagStructureHandler){
    	// 获取 Spring上下文
        ApplicationContext applicationContext = SpringContextUtils.getApplicationContext(iTemplateContext);
        //获取字典service的bean
        DictService dictService = applicationContext.getBean(DictServiceImpl.class);
        
        //获取标签的属性值
        String dictType = iProcessableElementTag.getAttributeValue("type");
        //选中的值
        String selected = iProcessableElementTag.getAttributeValue("value");
        //附件的选项
        String firstValue = iProcessableElementTag.getAttributeValue("firstValue");
        //附件的选项
        String firstText = iProcessableElementTag.getAttributeValue("firstText"); 
        //获取 匹配标签的属性值(父级)
        String superValueText = iProcessableElementTag.getAttributeValue("superValueText");
        
        
        
        String tagId = iProcessableElementTag.getAttributeValue("id");
        String tageName = iProcessableElementTag.getAttributeValue("name");
        String tagClass = iProcessableElementTag.getAttributeValue("class");
        String tagStyle = iProcessableElementTag.getAttributeValue("style");
        String onchange = iProcessableElementTag.getAttributeValue("onchange");
        String hidden = iProcessableElementTag.getAttributeValue("hidden");
       
       
        //查询数据库
        // 根据类型查询出字典列表
        Map<String, Object> param = new HashMap<String, Object>(16);
        param.put("type", dictType);
        List<DictDO> dictList = dictService.list(param); 
       
        // 创建将替换自定义标签的 DOM 结构
        IModelFactory modelFactory = iTemplateContext.getModelFactory();
        IModel model = modelFactory.createModel();
        // 这里是将字典的内容拼装成一个下拉框
        model.add(modelFactory.createOpenElementTag(String.format("select  id='%s' name='%s' class='%s' style='%s' onchange=\"%s\"",tagId, tageName, tagClass,tagStyle,onchange)));
     
        if (StringUtils.isNotEmpty(firstText)) {
        	  model.add(modelFactory.createOpenElementTag(String.format("option value='%s'", firstValue)));
        	  model.add(modelFactory.createText(firstText));
              model.add(modelFactory.createCloseElementTag("option"));
		}
        
        for (DictDO dict : dictList) {
        	
        	String optionSelectStr = "";
        	if (Objects.equals(dict.getValue(), selected)){
				optionSelectStr = "selected='selected'";
			}	
        	 String optionDisplay = "";
             if (hidden!=null) {
             	optionDisplay = "style='display:none'";
         	}

			String superValueTextStr = "";
			if (superValueTextStr !=null) {
				JSONObject  jsonObject = (JSONObject) JSON.toJSON(dict);;
				String superValue = jsonObject.getString(superValueText);
				superValueTextStr = "superValue='"+superValue+"'";
			}
			
             model.add(modelFactory.createOpenElementTag(String.format("option value='%s' %s %s %s", dict.getValue(), optionSelectStr,optionDisplay,superValueTextStr)));
            model.add(modelFactory.createText(dict.getName()));
            model.add(modelFactory.createCloseElementTag("option"));
        }
        model.add(modelFactory.createCloseElementTag("select"));
        // 利用引擎替换整合标签
        iElementTagStructureHandler.replaceWith(model, false);
    }

private void dynamicDict(ITemplateContext iTemplateContext, IProcessableElementTag iProcessableElementTag, IElementTagStructureHandler iElementTagStructureHandler){

//	<sys:dict url="sys/menu/list" valueText="menuId" nameText="name"  id="animalId" name="animalName" class="form-control"  style=""  firstValue="-1" firstText="请选择动物菜单" selected="pig" />

    //获取请求的url
    String onload = iProcessableElementTag.getAttributeValue("onload");
    //获取 匹配标签的属性值
    String valueText = iProcessableElementTag.getAttributeValue("valueText");
    //获取 匹配标签的文本
    String nameText = iProcessableElementTag.getAttributeValue("nameText");

    //获取 匹配标签的属性值(父级)
    String superValueText = iProcessableElementTag.getAttributeValue("superValueText");
    
    
    //选中的值
    String selected = iProcessableElementTag.getAttributeValue("value");
    //附件的选项
    String firstValue = iProcessableElementTag.getAttributeValue("firstValue");
    //附件的选项
    String firstText = iProcessableElementTag.getAttributeValue("firstText");
    
    
    String tagId = iProcessableElementTag.getAttributeValue("id");
    String tageName = iProcessableElementTag.getAttributeValue("name");
    String tagClass = iProcessableElementTag.getAttributeValue("class");
    String tagStyle = iProcessableElementTag.getAttributeValue("style");
    String onchange = iProcessableElementTag.getAttributeValue("onchange");
    String hidden = iProcessableElementTag.getAttributeValue("hidden");
   
    		
    
    String retString = null;
    String[] urlArray = onload.split(":");
    String baseUri = TsConfig.getProperty("ts.baseUri");
    if (!onload.startsWith("post:")) {//get请求
		if(urlArray.length==2){
			onload = urlArray[1];
		}
		onload =baseUri+ onload;
	    retString = UtilNetwork.doGet(onload);
	}else{
		int index = onload.indexOf("?");
		Map<String,String> map = new HashMap<>();
		if (index>0) {
			String param = onload.substring(index+1);
			String[] params = param.split("&");
			for (String item:params) {
				String[] kv = item.split("=");
				map.put(kv[0],kv[1]);
			}
		}
		onload =baseUri+ urlArray[1];
	     retString = UtilNetwork.doPost(onload, JSON.toJSONString(map));
	}
  
    // 创建将替换自定义标签的 DOM 结构
    IModelFactory modelFactory = iTemplateContext.getModelFactory();
    IModel model = modelFactory.createModel();
    // 这里是将字典的内容拼装成一个下拉框
    model.add(modelFactory.createOpenElementTag(String.format("select  id='%s' name='%s' class='%s' style='%s' onchange=\"%s\"",tagId, tageName, tagClass,tagStyle,onchange)));

   if (StringUtils.isNotEmpty(firstText)) {
  	  model.add(modelFactory.createOpenElementTag(String.format("option value='%s'", firstValue)));
  	  model.add(modelFactory.createText(firstText));
      model.add(modelFactory.createCloseElementTag("option"));
	}
  
    JSONArray retJsonArray =  JSON.parseArray(retString);
    for (Object object : retJsonArray) {
		JSONObject  jsonObject = (JSONObject) object;
		String objectVal = jsonObject.getString(valueText);
		String objectName =jsonObject.getString(nameText);
		if (StringUtils.isNotEmpty(objectVal)) {
			
				String optionDisplayStr = "";
				if (hidden != null) {
					optionDisplayStr = "style='display:none'";
				}

				String optionSelectStr = "";
				if (Objects.equals(objectVal, selected)) {
					optionSelectStr = "selected='selected'";
				}	
				
				String superValueTextStr = "";
				if (superValueTextStr !=null) {
					String superValue = jsonObject.getString(superValueText);
					superValueTextStr = "superValue='"+superValue+"'";
				}
			
			model.add(modelFactory.createOpenElementTag(String.format("option value='%s' %s %s %s", objectVal, optionSelectStr,optionDisplayStr,superValueTextStr)));
			model.add(modelFactory.createText(objectName));
			model.add(modelFactory.createCloseElementTag("option"));
		}
	}
    
    model.add(modelFactory.createCloseElementTag("select"));
    // 利用引擎替换整合标签
    iElementTagStructureHandler.replaceWith(model, false);
}
}
