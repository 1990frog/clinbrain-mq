package com.clinbrain.mq.service;

import cn.hutool.core.util.StrUtil;
import com.clinbrain.mq.common.base.BaseService;
import com.clinbrain.mq.common.support.ConvertUtil;
import com.clinbrain.mq.mapper.auto.MqMessageMapper;
import com.clinbrain.mq.model.custom.Tablepar;
import com.clinbrain.mq.model.custom.UMqMessage;
import com.clinbrain.mq.model.custom.UMqMessageExample;
import com.clinbrain.mq.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 *  MqMessageService
 * @Title: MqMessageService.java 
 * @Package com.clinbrain.mq.service 
 * @author clinbrain_自动生成
 * @email ${email}
 * @date 2021-12-09 14:27:38  
 **/
@Service
public class MqMessageService implements BaseService<UMqMessage, UMqMessageExample>{
	@Autowired
	private MqMessageMapper mqMessageMapper;
	
      	   	      	      	      	      	      	      	      	      	      	      	      	      	      	      	      	      	
	/**
	 * 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	 public PageInfo<UMqMessage> list(Tablepar tablepar,UMqMessage UMqMessage){
	        UMqMessageExample testExample=new UMqMessageExample();
			//搜索
			if(StrUtil.isNotEmpty(tablepar.getSearchText())) {//小窗体
	        	testExample.createCriteria().andLikeQuery2(tablepar.getSearchText());
	        }else {//大搜索
	        	testExample.createCriteria().andLikeQuery(UMqMessage);
	        }
			//表格排序
			if(StrUtil.isNotEmpty(tablepar.getOrderByColumn())) {
	        	testExample.setOrderByClause(StringUtils.toUnderScoreCase(tablepar.getOrderByColumn()) +" "+tablepar.getIsAsc());
	        }else{
	        	testExample.setOrderByClause("create_time DESC");
	        }
	        PageHelper.startPage(tablepar.getPage(), tablepar.getLimit());
	        List<UMqMessage> list= mqMessageMapper.selectByExample(testExample);
	        PageInfo<UMqMessage> pageInfo = new PageInfo<UMqMessage>(list);
	        return  pageInfo;
	 }

	@Override
	public int deleteByPrimaryKey(String ids) {
				
			Long[] integers = ConvertUtil.toLongArray(",", ids);
			List<Long> stringB = Arrays.asList(integers);
			UMqMessageExample example=new UMqMessageExample();
			example.createCriteria().andIdIn(stringB);
			return mqMessageMapper.deleteByExample(example);
		
				
	}
	
	
	@Override
	public UMqMessage selectByPrimaryKey(String id) {
				
			Long id1 = Long.valueOf(id);
			return mqMessageMapper.selectByPrimaryKey(id1);
			
				
	}

	
	@Override
	public int updateByPrimaryKeySelective(UMqMessage record) {
		return mqMessageMapper.updateByPrimaryKeySelective(record);
	}
	
	
	/**
	 * 添加
	 */
	@Override
	public int insertSelective(UMqMessage record) {
				
		record.setId(null);
		
				
		return mqMessageMapper.insertSelective(record);
	}
	
	
	@Override
	public int updateByExampleSelective(UMqMessage record, UMqMessageExample example) {
		
		return mqMessageMapper.updateByExampleSelective(record, example);
	}

	
	@Override
	public int updateByExample(UMqMessage record, UMqMessageExample example) {
		
		return mqMessageMapper.updateByExample(record, example);
	}

	@Override
	public List<UMqMessage> selectByExample(UMqMessageExample example) {
		
		return mqMessageMapper.selectByExample(example);
	}

	
	@Override
	public long countByExample(UMqMessageExample example) {
		
		return mqMessageMapper.countByExample(example);
	}

	
	@Override
	public int deleteByExample(UMqMessageExample example) {
		
		return mqMessageMapper.deleteByExample(example);
	}


}
