package com.clinbrain.mq.service;

import cn.hutool.core.util.StrUtil;
import com.clinbrain.mq.common.base.BaseService;
import com.clinbrain.mq.common.support.ConvertUtil;
import com.clinbrain.mq.mapper.auto.MsgTemplateMapper;
import com.clinbrain.mq.model.custom.Tablepar;
import com.clinbrain.mq.model.custom.UMsgTemplateExample;
import com.clinbrain.mq.model.custom.sms.UMsgTemplate;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 信息模板管理 MsgTemplateService
 * @Title: MsgTemplateService.java 
 * @Package com.clinbrain.mq.service 
 * @author clinbrain_自动生成
 * @email ${email}
 * @date 2021-12-09 14:28:10  
 **/
@Service
public class MsgTemplateService implements BaseService<UMsgTemplate, UMsgTemplateExample>{
	@Autowired
	private MsgTemplateMapper msgTemplateMapper;
	
      	   	      	      	      	      	
	/**
	 * 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	 public PageInfo<UMsgTemplate> list(Tablepar tablepar,UMsgTemplate UMsgTemplate){
	        UMsgTemplateExample testExample=new UMsgTemplateExample();
			//搜索
			if(StrUtil.isNotEmpty(tablepar.getSearchText())) {//小窗体
	        	testExample.createCriteria().andLikeQuery2(tablepar.getSearchText());
	        }else {//大搜索
	        	testExample.createCriteria().andLikeQuery(UMsgTemplate);
	        }
			//表格排序
			//if(StrUtil.isNotEmpty(tablepar.getOrderByColumn())) {
	        //	testExample.setOrderByClause(StringUtils.toUnderScoreCase(tablepar.getOrderByColumn()) +" "+tablepar.getIsAsc());
	        //}else{
	        //	testExample.setOrderByClause("id ASC");
	        //}
	        PageHelper.startPage(tablepar.getPage(), tablepar.getLimit());
	        List<UMsgTemplate> list= msgTemplateMapper.selectByExample(testExample);
	        PageInfo<UMsgTemplate> pageInfo = new PageInfo<UMsgTemplate>(list);
	        return  pageInfo;
	 }

	@Override
	public int deleteByPrimaryKey(String ids) {
					
			Integer[] integers = ConvertUtil.toIntArray(",", ids);
			List<Integer> stringB = Arrays.asList(integers);
			UMsgTemplateExample example=new UMsgTemplateExample();
			example.createCriteria().andIdIn(stringB);
			return msgTemplateMapper.deleteByExample(example);
			
				
	}
	
	
	@Override
	public UMsgTemplate selectByPrimaryKey(String id) {
				
			Integer id1 = Integer.valueOf(id);
			return msgTemplateMapper.selectByPrimaryKey(id1);
				
	}

	
	@Override
	public int updateByPrimaryKeySelective(UMsgTemplate record) {
		return msgTemplateMapper.updateByPrimaryKeySelective(record);
	}
	
	
	/**
	 * 添加
	 */
	@Override
	public int insertSelective(UMsgTemplate record) {
				
		record.setId(null);
		
				
		return msgTemplateMapper.insertSelective(record);
	}
	
	
	@Override
	public int updateByExampleSelective(UMsgTemplate record, UMsgTemplateExample example) {
		
		return msgTemplateMapper.updateByExampleSelective(record, example);
	}

	
	@Override
	public int updateByExample(UMsgTemplate record, UMsgTemplateExample example) {
		
		return msgTemplateMapper.updateByExample(record, example);
	}

	@Override
	public List<UMsgTemplate> selectByExample(UMsgTemplateExample example) {
		
		return msgTemplateMapper.selectByExample(example);
	}

	
	@Override
	public long countByExample(UMsgTemplateExample example) {
		
		return msgTemplateMapper.countByExample(example);
	}

	
	@Override
	public int deleteByExample(UMsgTemplateExample example) {
		
		return msgTemplateMapper.deleteByExample(example);
	}


}
