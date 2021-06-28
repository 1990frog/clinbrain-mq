package com.clinbrain.mq.admin.service;

import com.clinbrain.mq.admin.common.base.BaseService;
import com.clinbrain.mq.admin.common.base.PageInfo;
import com.clinbrain.mq.admin.common.support.ConvertUtil;
import com.clinbrain.mq.admin.mapper.auto.TSysDictDataMapper;
import com.clinbrain.mq.admin.mapper.auto.TSysDictTypeMapper;
import com.clinbrain.mq.admin.model.auto.TSysDictData;
import com.clinbrain.mq.admin.model.auto.TSysDictDataExample;
import com.clinbrain.mq.admin.model.auto.TSysDictType;
import com.clinbrain.mq.admin.model.custom.Tablepar;
import com.clinbrain.mq.admin.shiro.util.ShiroUtils;
import com.clinbrain.mq.admin.util.SnowflakeIdWorker;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 字典数据表Service
* @Title: TSysDictDataService.java
* @Package com.clinbrain.mq.service
* @author 一休
* @email 438081243@qq.com
* @date 2019-09-08 00:10:43
 */
@Service
public class SysDictDataService implements BaseService<TSysDictData, TSysDictDataExample> {
	@Autowired
	private TSysDictDataMapper tSysDictDataMapper;
	@Autowired
	private TSysDictTypeMapper tSysDictTypeMapper;
	/**
	 * 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	 public PageInfo<TSysDictData> list(Tablepar tablepar, String name, String dictId){
	        TSysDictDataExample testExample=new TSysDictDataExample();
		    testExample.setOrderByClause("dict_sort ASC");
		    if(dictId!=null&&!"".equals(dictId)){
		    	TSysDictType dictType= tSysDictTypeMapper.selectByPrimaryKey(dictId);
		    	if(dictType!=null) {
		    		 testExample.createCriteria().andDictTypeEqualTo(dictType.getDictType());
		    	}
			}
	        if(name!=null&&!"".equals(name)){
	        	testExample.createCriteria().andDictValueLike("%"+name+"%");
	        }

	        PageHelper.startPage(tablepar.getPage(), tablepar.getLimit());
	        List<TSysDictData> list= tSysDictDataMapper.selectByExample(testExample);
	        PageInfo<TSysDictData> pageInfo = new PageInfo<TSysDictData>(list);
	        return  pageInfo;
	 }


	@Override
	public int deleteByPrimaryKey(String ids) {
		List<String> lista= ConvertUtil.toListStrArray(ids);
		TSysDictDataExample example=new TSysDictDataExample();
		example.createCriteria().andIdIn(lista);
		return tSysDictDataMapper.deleteByExample(example);
	}

	/**
	 * 添加
	 */
	@Override
	public int insertSelective(TSysDictData record) {
		//添加雪花主键id
		record.setId(SnowflakeIdWorker.getUUID());
		record.setCreateTime(new Date());
		record.setUpdateTime(new Date());
		record.setCreateBy(ShiroUtils.getUser().getUsername());
		return tSysDictDataMapper.insertSelective(record);
	}

	@Override
	public TSysDictData selectByPrimaryKey(String id) {
		return tSysDictDataMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(TSysDictData record) {
		record.setUpdateTime(new Date());
		record.setUpdateBy(ShiroUtils.getUser().getUsername());
		return tSysDictDataMapper.updateByPrimaryKeySelective(record);
	}


	@Override
	public int updateByExampleSelective(TSysDictData record, TSysDictDataExample example) {
		
		return tSysDictDataMapper.updateByExampleSelective(record, example);
	}

	
	@Override
	public int updateByExample(TSysDictData record, TSysDictDataExample example) {
		
		return tSysDictDataMapper.updateByExample(record, example);
	}

	@Override
	public List<TSysDictData> selectByExample(TSysDictDataExample example) {
		
		return tSysDictDataMapper.selectByExample(example);
	}

	
	@Override
	public long countByExample(TSysDictDataExample example) {
		
		return tSysDictDataMapper.countByExample(example);
	}

	
	@Override
	public int deleteByExample(TSysDictDataExample example) {
		
		return tSysDictDataMapper.deleteByExample(example);
	}
	
	/**
	 * 检查name
	 * @param TSysDictData
	 * @return
	 */
	public int checkNameUnique(TSysDictData tSysDictData){
		TSysDictDataExample example=new TSysDictDataExample();
		example.createCriteria().andDictValueEqualTo(tSysDictData.getDictValue());
		List<TSysDictData> list=tSysDictDataMapper.selectByExample(example);
		return list.size();
	}

	/**
	 * 批量删除
	 * @param dictIds
	 * @author fuce
	 * @Date 2019年9月9日 上午12:40:52
	 */
	public void deleteByPrimaryDictIds(List<String> dictIds) {
		TSysDictDataExample example=new TSysDictDataExample();
		example.createCriteria().andIdIn(dictIds);
		tSysDictDataMapper.deleteByExample(example);
	}
}
