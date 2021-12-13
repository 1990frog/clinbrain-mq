package com.clinbrain.mq.service;

import cn.hutool.core.util.StrUtil;
import com.clinbrain.mq.common.base.BaseService;
import com.clinbrain.mq.common.support.ConvertUtil;
import com.clinbrain.mq.mapper.custom.UContactDetailsMapper;
import com.clinbrain.mq.mapper.custom.UContactMapper;
import com.clinbrain.mq.model.custom.Tablepar;
import com.clinbrain.mq.model.custom.UContact;
import com.clinbrain.mq.model.custom.UContactDetails;
import com.clinbrain.mq.model.custom.UContactExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 *  UContactService
 * @Title: UContactService.java 
 * @Package com.clinbrain.v2.service 
 * @author fuce_自动生成
 * @email ${email}
 * @date 2021-12-09 13:37:41  
 **/
@Service
public class UContactService implements BaseService<UContact, UContactExample> {
	@Autowired
	private UContactMapper uContactMapper;

    @Autowired
    private UContactDetailsMapper uContactDetailsMapper;
	
      	   	      	      	      	      	      	      	      	
	/**
	 * 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	 public PageInfo<UContact> list(Tablepar tablepar, UContact uContact){
	        UContactExample testExample=new UContactExample();
			//搜索
			if(StrUtil.isNotEmpty(tablepar.getSearchText())) {//小窗体
	        	testExample.createCriteria().andLikeQuery2(tablepar.getSearchText());
	        }else {//大搜索
	        	testExample.createCriteria().andLikeQuery(uContact);
	        }
			//表格排序
			//if(StrUtil.isNotEmpty(tablepar.getOrderByColumn())) {
	        //	testExample.setOrderByClause(StringUtils.toUnderScoreCase(tablepar.getOrderByColumn()) +" "+tablepar.getIsAsc());
	        //}else{
	        //	testExample.setOrderByClause("id ASC");
	        //}
	        PageHelper.startPage(tablepar.getPage(), tablepar.getLimit());
	        List<UContact> list= uContactMapper.selectByExample(testExample);
	        PageInfo<UContact> pageInfo = new PageInfo<UContact>(list);
	        return  pageInfo;
	 }

	@Override
	public int deleteByPrimaryKey(String ids) {
					
			Integer[] integers = ConvertUtil.toIntArray(",", ids);
			List<Integer> stringB = Arrays.asList(integers);
			UContactExample example=new UContactExample();
			example.createCriteria().andIdIn(stringB);
			return uContactMapper.deleteByExample(example);
			
				
	}
	
	
	@Override
	public UContact selectByPrimaryKey(String id) {
				
			Integer id1 = Integer.valueOf(id);
			return uContactMapper.selectByPrimaryKey(id1);
				
	}

	
	@Override
	public int updateByPrimaryKeySelective(UContact record) {
        record.setCreateTime(null);
        record.setUpdateTime(new Date());
		return uContactMapper.updateByPrimaryKeySelective(record);
	}
	
	
	/**
	 * 添加
	 */
	@Override
	public int insertSelective(UContact record) {
				
		record.setId(null);
		record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
				
		return uContactMapper.insertSelective(record);
	}
	
	
	@Override
	public int updateByExampleSelective(UContact record, UContactExample example) {
		
		return uContactMapper.updateByExampleSelective(record, example);
	}

	
	@Override
	public int updateByExample(UContact record, UContactExample example) {
		
		return uContactMapper.updateByExample(record, example);
	}

	@Override
	public List<UContact> selectByExample(UContactExample example) {
		
		return uContactMapper.selectByExample(example);
	}

	
	@Override
	public long countByExample(UContactExample example) {
		
		return uContactMapper.countByExample(example);
	}

	
	@Override
	public int deleteByExample(UContactExample example) {
		
		return uContactMapper.deleteByExample(example);
	}


    public List<UContactDetails> selectContactDetailByConcatId(Integer contactId) {
        return uContactDetailsMapper.selectListByContactId(contactId);
    }

    public long saveContactDetails(List<UContactDetails> details) {
        return uContactDetailsMapper.insertBatch(details);
    }

    public long deleteContactDetailById(Integer id) {
        return uContactDetailsMapper.deleteById(id);
    }
}
