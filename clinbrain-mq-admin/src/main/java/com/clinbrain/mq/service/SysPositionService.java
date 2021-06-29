package com.clinbrain.mq.service;

import cn.hutool.core.util.StrUtil;
import com.clinbrain.mq.common.base.BaseService;
import com.clinbrain.mq.common.support.ConvertUtil;
import com.clinbrain.mq.mapper.auto.SysPositionMapper;
import com.clinbrain.mq.model.auto.SysPosition;
import com.clinbrain.mq.model.auto.SysPositionExample;
import com.clinbrain.mq.model.custom.Tablepar;
import com.clinbrain.mq.util.SnowflakeIdWorker;
import com.clinbrain.mq.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 岗位表 SysPositionService
 * @Title: SysPositionService.java
 * @Package com.clinbrain.mq.service
 * @author fuce_自动生成
 * @email 115889198@qq.com
 * @date 2020-04-16 23:46:24
 **/
@Service
public class SysPositionService implements BaseService<SysPosition, SysPositionExample>{
	@Autowired
	private SysPositionMapper sysPositionMapper;
	
      	   	      	      	      	      	
	/**
	 * 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	 public PageInfo<SysPosition> list(Tablepar tablepar,String name){
	        SysPositionExample testExample=new SysPositionExample();
	        testExample.setOrderByClause("id ASC");
	        if(name!=null&&!"".equals(name)){
	        	testExample.createCriteria().andPostNameLike("%"+name+"%");
	        }
	        
	        if(StrUtil.isNotEmpty(tablepar.getOrderByColumn())) {
	        	testExample.setOrderByClause(StringUtils.toUnderScoreCase(tablepar.getOrderByColumn()) +" "+tablepar.getIsAsc());
	        }
	        
	        
	        PageHelper.startPage(tablepar.getPage(), tablepar.getLimit());
	        List<SysPosition> list= sysPositionMapper.selectByExample(testExample);
	        PageInfo<SysPosition> pageInfo = new PageInfo<SysPosition>(list);
	        return  pageInfo;
	 }

	@Override
	public int deleteByPrimaryKey(String ids) {
				
			List<String> lista=ConvertUtil.toListStrArray(ids);
			SysPositionExample example=new SysPositionExample();
			example.createCriteria().andIdIn(lista);
			return sysPositionMapper.deleteByExample(example);
			
				
	}
	
	
	@Override
	public SysPosition selectByPrimaryKey(String id) {
				
			return sysPositionMapper.selectByPrimaryKey(id);
				
	}

	
	@Override
	public int updateByPrimaryKeySelective(SysPosition record) {
		return sysPositionMapper.updateByPrimaryKeySelective(record);
	}
	
	
	/**
	 * 添加
	 */
	@Override
	public int insertSelective(SysPosition record) {
				
		//添加雪花主键id
		record.setId(SnowflakeIdWorker.getUUID());
			
				
		return sysPositionMapper.insertSelective(record);
	}
	
	
	@Override
	public int updateByExampleSelective(SysPosition record, SysPositionExample example) {
		
		return sysPositionMapper.updateByExampleSelective(record, example);
	}

	
	@Override
	public int updateByExample(SysPosition record, SysPositionExample example) {
		
		return sysPositionMapper.updateByExample(record, example);
	}

	@Override
	public List<SysPosition> selectByExample(SysPositionExample example) {
		
		return sysPositionMapper.selectByExample(example);
	}

	
	@Override
	public long countByExample(SysPositionExample example) {
		
		return sysPositionMapper.countByExample(example);
	}

	
	@Override
	public int deleteByExample(SysPositionExample example) {
		
		return sysPositionMapper.deleteByExample(example);
	}
	
	/**
	 * 检查name
	 * @param sysPosition
	 * @return
	 */
	public int checkNameUnique(SysPosition sysPosition){
		SysPositionExample example=new SysPositionExample();
		example.createCriteria().andPostNameEqualTo(sysPosition.getPostName());
		List<SysPosition> list=sysPositionMapper.selectByExample(example);
		return list.size();
	}


	/**
	 * 修改权限状态展示或者不展示
	 * @param record
	 * @return
	 */
	public int updateVisible(SysPosition record) {
		return sysPositionMapper.updateByPrimaryKeySelective(record);
	}

}
