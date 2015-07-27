package com.mawujun.controller.spring.mvc;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
import com.mawujun.exception.BusinessException;
import com.mawujun.exception.DefaulExceptionCode;
import com.mawujun.repository.EntityTest;
import com.mawujun.repository.hibernate.validate.ValidatorUtils;
import com.mawujun.utils.page.PageParam;
import com.mawujun.utils.page.PageResult;

/**
 * 过滤，日期格式等设置http://hi.baidu.com/ien_leo/item/d1601c4d1a44b23dfa8960d5
 * @author mawujun
 * 这里测试   所有自动包装的可能
 */
@Controller
public class SpringMVCController_autoWarp {
	
	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
	Date date=null;
	{
		try {
			date=formatter.parse("2015-07-27");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private PageResult<Map<String,String>> getQueryResult(){
		PageResult<Map<String,String>> page=new PageResult<Map<String,String>>();
		page.setStart(0);
		page.setLimit(10);
		
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		for(int i=0;i<9;i++){
			Map<String,String> map=new HashMap<String,String>();
			map.put("name", "name"+i);
			map.put("age", "111"+i);
			map.put("weight", "100"+i);
			list.add(map);
		}
		page.setResult(list);
		page.setTotal(9);
		return page;
	}
	
	@RequestMapping("/autoWarp/queryPage.do")
	@ResponseBody
	public List<Map<String,String>> queryPage(){		
		PageResult<Map<String,String>> page=getQueryResult();
		
//		ModelMap map=new ModelMap();
//		map.put("root", page.getResult());
//		map.put("total", page.getTotalItems());	
//		return map;
		return page.getResult();
	}
	
	
	@RequestMapping("/autoWarp/queryPage1.do")
	@ResponseBody
	public PageResult queryPage1(){
		PageResult<Map<String,String>> page=new PageResult<Map<String,String>>();
		page.setStart(0);
		page.setLimit(10);
		
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		for(int i=0;i<9;i++){
			Map<String,String> map=new HashMap<String,String>();
			map.put("name", "name"+i);
			map.put("age", "111"+i);
			map.put("weight", "100"+i);
			list.add(map);
		}
		page.setResult(list);
		page.setTotal(100);	
		return page;
	}
	@RequestMapping("/autoWarp/queryMap.do")
	@ResponseBody
	public Map queryMap(){
		Map<String,String> map=new HashMap<String,String>();
		map.put("name", "name");
		map.put("age", "111");
		map.put("weight", "100");
		
		return map;
	}
	@RequestMapping("/autoWarp/queryModel.do")
	@ResponseBody
	public Model queryModel(){
		Model parent=new Model();
		parent.setId(1);
		parent.setAge(11);
		parent.setCreateDate(date);
		parent.setName("parent");
		
		return parent;
	}
	/**
	 * 死循环默认会解决掉
	 * @return
	 */
	@RequestMapping("/autoWarp/queryCycle.do")
	@ResponseBody
	public Model queryCycle(){
		Model parent=new Model();
		parent.setId(1);
		parent.setAge(11);
		parent.setCreateDate(date);
		parent.setName("parent");
		
		Model child=new Model();
		child.setId(1);
		child.setAge(11);
		child.setName("child");
		child.setParent(parent);
		parent.addChilden(child);
		
		Model child1=new Model();
		child1.setId(2);
		child1.setAge(22);
		child1.setName("child1");
		child1.setParent(parent);
		parent.addChilden(child1);
		JsonConfigHolder.setFilterPropertys("parent");
		return parent;
	}
	/**
	 * 死循环默认会解决掉
	 * @return
	 */
	@RequestMapping("/autoWarp/queryCycleList.do")
	@ResponseBody
	public List<Model> queryCycleList(){
		Model parent=new Model();
		parent.setId(1);
		parent.setAge(11);
		parent.setCreateDate(date);
		parent.setName("parent");
		
		Model child=new Model();
		child.setId(1);
		child.setAge(11);
		child.setName("child");
		child.setParent(parent);
		parent.addChilden(child);
		
		Model child1=new Model();
		child1.setId(2);
		child1.setAge(22);
		child1.setName("child1");
		child1.setParent(parent);
		parent.addChilden(child1);
		
		List<Model> list=new ArrayList<Model>();
		list.add(parent);
		JsonConfigHolder.setFilterPropertys("parent");
		return list;
	}
	
	/**
	 * 死循环默认会解决掉
	 * @return
	 */
	@RequestMapping("/autoWarp/queryCycleList_rootName.do")
	@ResponseBody
	public List<Model> queryCycleList_rootName(){
		Model parent=new Model();
		parent.setId(1);
		parent.setAge(11);
		parent.setCreateDate(date);
		parent.setName("parent");
		
		Model child=new Model();
		child.setId(1);
		child.setAge(11);
		child.setName("child");
		child.setParent(parent);
		parent.addChilden(child);
		
		Model child1=new Model();
		child1.setId(2);
		child1.setAge(22);
		child1.setName("child1");
		child1.setParent(parent);
		parent.addChilden(child1);
		
		List<Model> list=new ArrayList<Model>();
		list.add(parent);
		JsonConfigHolder.setFilterPropertys("parent");
		JsonConfigHolder.setRootName("children");
		return list;
	}
	
	@RequestMapping("/autoWarp/filterProperty.do")
	@ResponseBody
	public Model filterProperty(){
		Model parent=new Model();
		parent.setId(1);
		parent.setAge(11);
		parent.setCreateDate(date);
		parent.setName("parent");
		
//		ModelMap map=new ModelMap();
//		map.put("filterPropertys", "age,name");//过滤属性的设置
//		map.put("root", parent);
		JsonConfigHolder.setFilterPropertys("age,name");
		return parent;
	}
	
	@RequestMapping("/autoWarp/filterPropertyModelMap.do")
	@ResponseBody
	public ModelMap filterPropertyModelMap(){
//		Model parent=new Model();
//		parent.setId(1);
//		parent.setAge(11);
//		parent.setCreateDate(date);
//		parent.setName("parent");
//		
//		ResultMap map=new ResultMap();
//		map.setFilterPropertys("age,name");//过滤属性的设置
//		map.setRoot(parent);
		
		ModelMap map=new ModelMap();
		map.put("id", 1);
		map.put("age", 11);
		map.put("createDate", date);
		map.put("name", "parent");
		return map;
	}
	
	@RequestMapping("/autoWarp/filterPropertyList.do")
	@ResponseBody
	public List<Model> filterPropertyList(){
		//还没有测试root是List的情况
		Model parent=new Model();
		parent.setId(1);
		parent.setAge(11);
		parent.setCreateDate(date);
		parent.setName("parent");
		List<Model> list=new ArrayList<Model>();
		list.add(parent);
		
//		ModelMap map=new ModelMap();
//		map.put("filterPropertys", "age,name");//过滤属性的设置
//		map.put("root", list);
		
		JsonConfigHolder.setFilterPropertys("age,name");
		return list;
	}
	@RequestMapping("/autoWarp/filterOnlyId.do")
	@ResponseBody
	public Model filterOnlyId(){
		Model parent=new Model();
		parent.setId(2);
		parent.setAge(22);
		parent.setCreateDate(date);
		parent.setName("parent");
		
		Model child=new Model();
		child.setId(1);
		child.setAge(11);
		child.setCreateDate(date);
		child.setName("child");
		child.setParent(parent);
		
//		ModelMap map=new ModelMap();
//		map.put("onlyIds", "parent");//parent属性只获取id
//		map.put("root", child);
		return child;
	}
	
	@RequestMapping("/autoWarp/bindModel.do")
	@ResponseBody
	public Model bindModel(@RequestBody Model model){
		return model;
	}
	
	@RequestMapping("/autoWarp/bindPageRequestByJosn.do")
	@ResponseBody
	public PageResult bindPageRequestByJosn(@RequestBody PageParam pageParam){
		PageResult aa=new PageResult(pageParam);
		return aa;
	}
	@RequestMapping("/autoWarp/bindPageRequestByConverter.do")
	@ResponseBody
	public PageResult bindPageRequestByConverter(@RequestParam("pageRequest") PageParam pageParam){
		PageResult aa=new PageResult(pageParam);
		return aa;
	}
	
	/**
	 * 使用的是ServletRequestDataBinder进行绑定，
	 * @author mawujun email:mawujun1234@163.com qq:16064988
	 * @param pageRequest
	 * @return
	 */
	//http://www.iteye.com/topic/1122793?page=3#2385378
	@RequestMapping("/autoWarp/bindPageRequestNormal.do")
	@ResponseBody
	public PageResult bindPageRequestNormal(PageParam pageParam){
		//pageRequest.setWheres(wheres);
		PageResult aa=new PageResult(pageParam);
////		http://blog.csdn.net/idilent/article/details/1845227
////			http://blog.csdn.net/idilent/article/details/1800262
////		添加sortInfo的测试
//		for(WhereInfo where:aa.getWheres()){
//			where.getProp();
//		}
		return aa;
	}
	
	@RequestMapping("/autoWarp/testException.do")
	public void testException() throws Exception{
		throw new Exception("显示信息错误");
	}
	@RequestMapping("/autoWarp/testBussinessException.do")
	public void testBussinessException() throws Exception{
		throw new BusinessException(DefaulExceptionCode.SYSTEM_EXCEPTION);
	}
	@RequestMapping("/autoWarp/testConstraintViolationException.do")
	public void testConstraintViolationException() throws Exception{
		Model entity1=new Model();
		entity1.setName("1111");
		entity1.setEmail("11");
		ValidatorUtils.validate(entity1);
	}
	
	@RequestMapping("/autoWarp/testExtProperties.do")
	@ResponseBody
	public HashMap testExtProperties(){
		//JsonConfigHolder.setAutoWrap(false);
		JsonConfigHolder.addProperty("aa", "aaaa");
		HashMap bb= new HashMap();
		bb.put("11", 11);
		return bb;
	}
	
	@RequestMapping("/autoWarp/testExtProperties1.do")
	@ResponseBody
	public Model testExtProperties1(){
		//JsonConfigHolder.setAutoWrap(false);
		JsonConfigHolder.addProperty("aa", "aaaa");
		Model model=new Model();
		model.setName("1111");
		return model;
	}
	
	@RequestMapping("/autoWarp/testRtnStr.do")
	@ResponseBody
	public String testRtnStr(){
		String json="{name:'ma',age:16}";
		return json;
	}
	
	@RequestMapping("/autoWarp/testJSONP.do")
	@ResponseBody
	public String testJSONP(){
		//JsonConfigHolder.setJsonp(true);
		JsonConfigHolder.setJsonpCallback("jsonCllback");
		String json="{name:'ma',age:16}";
		return json;
	}

}
