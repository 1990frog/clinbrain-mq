package com.clinbrain.mq.model.custom;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import cn.hutool.core.util.StrUtil;

/**
 *  MqMessageExample
 * @author clinbrain_自动生成
 * @date 2021-12-09 14:27:38
 */
public class UMqMessageExample {

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UMqMessageExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }
        
				
        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(Long value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(Long value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }
        
				
        public Criteria andMessageGenreIsNull() {
            addCriterion("message_genre is null");
            return (Criteria) this;
        }

        public Criteria andMessageGenreIsNotNull() {
            addCriterion("message_genre is not null");
            return (Criteria) this;
        }

        public Criteria andMessageGenreEqualTo(String value) {
            addCriterion("message_genre =", value, "messageGenre");
            return (Criteria) this;
        }

        public Criteria andMessageGenreNotEqualTo(String value) {
            addCriterion("message_genre <>", value, "messageGenre");
            return (Criteria) this;
        }

        public Criteria andMessageGenreGreaterThan(String value) {
            addCriterion("message_genre >", value, "messageGenre");
            return (Criteria) this;
        }

        public Criteria andMessageGenreGreaterThanOrEqualTo(String value) {
            addCriterion("message_genre >=", value, "messageGenre");
            return (Criteria) this;
        }

        public Criteria andMessageGenreLessThan(String value) {
            addCriterion("message_genre <", value, "messageGenre");
            return (Criteria) this;
        }

        public Criteria andMessageGenreLessThanOrEqualTo(String value) {
            addCriterion("message_genre <=", value, "messageGenre");
            return (Criteria) this;
        }

        public Criteria andMessageGenreLike(String value) {
            addCriterion("message_genre like", value, "messageGenre");
            return (Criteria) this;
        }

        public Criteria andMessageGenreNotLike(String value) {
            addCriterion("message_genre not like", value, "messageGenre");
            return (Criteria) this;
        }

        public Criteria andMessageGenreIn(List<String> values) {
            addCriterion("message_genre in", values, "messageGenre");
            return (Criteria) this;
        }

        public Criteria andMessageGenreNotIn(List<String> values) {
            addCriterion("message_genre not in", values, "messageGenre");
            return (Criteria) this;
        }

        public Criteria andMessageGenreBetween(String value1, String value2) {
            addCriterion("message_genre between", value1, value2, "messageGenre");
            return (Criteria) this;
        }

        public Criteria andMessageGenreNotBetween(String value1, String value2) {
            addCriterion("message_genre not between", value1, value2, "messageGenre");
            return (Criteria) this;
        }
        
				
        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }
        
				
        public Criteria andRetryIsNull() {
            addCriterion("retry is null");
            return (Criteria) this;
        }

        public Criteria andRetryIsNotNull() {
            addCriterion("retry is not null");
            return (Criteria) this;
        }

        public Criteria andRetryEqualTo(Integer value) {
            addCriterion("retry =", value, "retry");
            return (Criteria) this;
        }

        public Criteria andRetryNotEqualTo(Integer value) {
            addCriterion("retry <>", value, "retry");
            return (Criteria) this;
        }

        public Criteria andRetryGreaterThan(Integer value) {
            addCriterion("retry >", value, "retry");
            return (Criteria) this;
        }

        public Criteria andRetryGreaterThanOrEqualTo(Integer value) {
            addCriterion("retry >=", value, "retry");
            return (Criteria) this;
        }

        public Criteria andRetryLessThan(Integer value) {
            addCriterion("retry <", value, "retry");
            return (Criteria) this;
        }

        public Criteria andRetryLessThanOrEqualTo(Integer value) {
            addCriterion("retry <=", value, "retry");
            return (Criteria) this;
        }

        public Criteria andRetryLike(Integer value) {
            addCriterion("retry like", value, "retry");
            return (Criteria) this;
        }

        public Criteria andRetryNotLike(Integer value) {
            addCriterion("retry not like", value, "retry");
            return (Criteria) this;
        }

        public Criteria andRetryIn(List<Integer> values) {
            addCriterion("retry in", values, "retry");
            return (Criteria) this;
        }

        public Criteria andRetryNotIn(List<Integer> values) {
            addCriterion("retry not in", values, "retry");
            return (Criteria) this;
        }

        public Criteria andRetryBetween(Integer value1, Integer value2) {
            addCriterion("retry between", value1, value2, "retry");
            return (Criteria) this;
        }

        public Criteria andRetryNotBetween(Integer value1, Integer value2) {
            addCriterion("retry not between", value1, value2, "retry");
            return (Criteria) this;
        }
        
				
        public Criteria andDelayIsNull() {
            addCriterion("delay is null");
            return (Criteria) this;
        }

        public Criteria andDelayIsNotNull() {
            addCriterion("delay is not null");
            return (Criteria) this;
        }

        public Criteria andDelayEqualTo(String value) {
            addCriterion("delay =", value, "delay");
            return (Criteria) this;
        }

        public Criteria andDelayNotEqualTo(String value) {
            addCriterion("delay <>", value, "delay");
            return (Criteria) this;
        }

        public Criteria andDelayGreaterThan(String value) {
            addCriterion("delay >", value, "delay");
            return (Criteria) this;
        }

        public Criteria andDelayGreaterThanOrEqualTo(String value) {
            addCriterion("delay >=", value, "delay");
            return (Criteria) this;
        }

        public Criteria andDelayLessThan(String value) {
            addCriterion("delay <", value, "delay");
            return (Criteria) this;
        }

        public Criteria andDelayLessThanOrEqualTo(String value) {
            addCriterion("delay <=", value, "delay");
            return (Criteria) this;
        }

        public Criteria andDelayLike(String value) {
            addCriterion("delay like", value, "delay");
            return (Criteria) this;
        }

        public Criteria andDelayNotLike(String value) {
            addCriterion("delay not like", value, "delay");
            return (Criteria) this;
        }

        public Criteria andDelayIn(List<String> values) {
            addCriterion("delay in", values, "delay");
            return (Criteria) this;
        }

        public Criteria andDelayNotIn(List<String> values) {
            addCriterion("delay not in", values, "delay");
            return (Criteria) this;
        }

        public Criteria andDelayBetween(String value1, String value2) {
            addCriterion("delay between", value1, value2, "delay");
            return (Criteria) this;
        }

        public Criteria andDelayNotBetween(String value1, String value2) {
            addCriterion("delay not between", value1, value2, "delay");
            return (Criteria) this;
        }
        
				
        public Criteria andAssignToIsNull() {
            addCriterion("assign_to is null");
            return (Criteria) this;
        }

        public Criteria andAssignToIsNotNull() {
            addCriterion("assign_to is not null");
            return (Criteria) this;
        }

        public Criteria andAssignToEqualTo(String value) {
            addCriterion("assign_to =", value, "assignTo");
            return (Criteria) this;
        }

        public Criteria andAssignToNotEqualTo(String value) {
            addCriterion("assign_to <>", value, "assignTo");
            return (Criteria) this;
        }

        public Criteria andAssignToGreaterThan(String value) {
            addCriterion("assign_to >", value, "assignTo");
            return (Criteria) this;
        }

        public Criteria andAssignToGreaterThanOrEqualTo(String value) {
            addCriterion("assign_to >=", value, "assignTo");
            return (Criteria) this;
        }

        public Criteria andAssignToLessThan(String value) {
            addCriterion("assign_to <", value, "assignTo");
            return (Criteria) this;
        }

        public Criteria andAssignToLessThanOrEqualTo(String value) {
            addCriterion("assign_to <=", value, "assignTo");
            return (Criteria) this;
        }

        public Criteria andAssignToLike(String value) {
            addCriterion("assign_to like", value, "assignTo");
            return (Criteria) this;
        }

        public Criteria andAssignToNotLike(String value) {
            addCriterion("assign_to not like", value, "assignTo");
            return (Criteria) this;
        }

        public Criteria andAssignToIn(List<String> values) {
            addCriterion("assign_to in", values, "assignTo");
            return (Criteria) this;
        }

        public Criteria andAssignToNotIn(List<String> values) {
            addCriterion("assign_to not in", values, "assignTo");
            return (Criteria) this;
        }

        public Criteria andAssignToBetween(String value1, String value2) {
            addCriterion("assign_to between", value1, value2, "assignTo");
            return (Criteria) this;
        }

        public Criteria andAssignToNotBetween(String value1, String value2) {
            addCriterion("assign_to not between", value1, value2, "assignTo");
            return (Criteria) this;
        }
        
				
        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }
        
				
        public Criteria andContentIsNull() {
            addCriterion("content is null");
            return (Criteria) this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("content is not null");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("content =", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("content <>", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("content >", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("content >=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("content <", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("content <=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("content like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("content not like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentIn(List<String> values) {
            addCriterion("content in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotIn(List<String> values) {
            addCriterion("content not in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("content between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("content not between", value1, value2, "content");
            return (Criteria) this;
        }
        
				
        public Criteria andTemplateIdIsNull() {
            addCriterion("template_id is null");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIsNotNull() {
            addCriterion("template_id is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateIdEqualTo(Long value) {
            addCriterion("template_id =", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotEqualTo(Long value) {
            addCriterion("template_id <>", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdGreaterThan(Long value) {
            addCriterion("template_id >", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdGreaterThanOrEqualTo(Long value) {
            addCriterion("template_id >=", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLessThan(Long value) {
            addCriterion("template_id <", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLessThanOrEqualTo(Long value) {
            addCriterion("template_id <=", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLike(Long value) {
            addCriterion("template_id like", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotLike(Long value) {
            addCriterion("template_id not like", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIn(List<Long> values) {
            addCriterion("template_id in", values, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotIn(List<Long> values) {
            addCriterion("template_id not in", values, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdBetween(Long value1, Long value2) {
            addCriterion("template_id between", value1, value2, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotBetween(Long value1, Long value2) {
            addCriterion("template_id not between", value1, value2, "templateId");
            return (Criteria) this;
        }
        
				
        public Criteria andTraceIdIsNull() {
            addCriterion("trace_id is null");
            return (Criteria) this;
        }

        public Criteria andTraceIdIsNotNull() {
            addCriterion("trace_id is not null");
            return (Criteria) this;
        }

        public Criteria andTraceIdEqualTo(String value) {
            addCriterion("trace_id =", value, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdNotEqualTo(String value) {
            addCriterion("trace_id <>", value, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdGreaterThan(String value) {
            addCriterion("trace_id >", value, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdGreaterThanOrEqualTo(String value) {
            addCriterion("trace_id >=", value, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdLessThan(String value) {
            addCriterion("trace_id <", value, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdLessThanOrEqualTo(String value) {
            addCriterion("trace_id <=", value, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdLike(String value) {
            addCriterion("trace_id like", value, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdNotLike(String value) {
            addCriterion("trace_id not like", value, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdIn(List<String> values) {
            addCriterion("trace_id in", values, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdNotIn(List<String> values) {
            addCriterion("trace_id not in", values, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdBetween(String value1, String value2) {
            addCriterion("trace_id between", value1, value2, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdNotBetween(String value1, String value2) {
            addCriterion("trace_id not between", value1, value2, "traceId");
            return (Criteria) this;
        }
        
				
        public Criteria andOriginalDataIsNull() {
            addCriterion("original_data is null");
            return (Criteria) this;
        }

        public Criteria andOriginalDataIsNotNull() {
            addCriterion("original_data is not null");
            return (Criteria) this;
        }

        public Criteria andOriginalDataEqualTo(String value) {
            addCriterion("original_data =", value, "originalData");
            return (Criteria) this;
        }

        public Criteria andOriginalDataNotEqualTo(String value) {
            addCriterion("original_data <>", value, "originalData");
            return (Criteria) this;
        }

        public Criteria andOriginalDataGreaterThan(String value) {
            addCriterion("original_data >", value, "originalData");
            return (Criteria) this;
        }

        public Criteria andOriginalDataGreaterThanOrEqualTo(String value) {
            addCriterion("original_data >=", value, "originalData");
            return (Criteria) this;
        }

        public Criteria andOriginalDataLessThan(String value) {
            addCriterion("original_data <", value, "originalData");
            return (Criteria) this;
        }

        public Criteria andOriginalDataLessThanOrEqualTo(String value) {
            addCriterion("original_data <=", value, "originalData");
            return (Criteria) this;
        }

        public Criteria andOriginalDataLike(String value) {
            addCriterion("original_data like", value, "originalData");
            return (Criteria) this;
        }

        public Criteria andOriginalDataNotLike(String value) {
            addCriterion("original_data not like", value, "originalData");
            return (Criteria) this;
        }

        public Criteria andOriginalDataIn(List<String> values) {
            addCriterion("original_data in", values, "originalData");
            return (Criteria) this;
        }

        public Criteria andOriginalDataNotIn(List<String> values) {
            addCriterion("original_data not in", values, "originalData");
            return (Criteria) this;
        }

        public Criteria andOriginalDataBetween(String value1, String value2) {
            addCriterion("original_data between", value1, value2, "originalData");
            return (Criteria) this;
        }

        public Criteria andOriginalDataNotBetween(String value1, String value2) {
            addCriterion("original_data not between", value1, value2, "originalData");
            return (Criteria) this;
        }
        
				
        public Criteria andLogIsNull() {
            addCriterion("log is null");
            return (Criteria) this;
        }

        public Criteria andLogIsNotNull() {
            addCriterion("log is not null");
            return (Criteria) this;
        }

        public Criteria andLogEqualTo(String value) {
            addCriterion("log =", value, "log");
            return (Criteria) this;
        }

        public Criteria andLogNotEqualTo(String value) {
            addCriterion("log <>", value, "log");
            return (Criteria) this;
        }

        public Criteria andLogGreaterThan(String value) {
            addCriterion("log >", value, "log");
            return (Criteria) this;
        }

        public Criteria andLogGreaterThanOrEqualTo(String value) {
            addCriterion("log >=", value, "log");
            return (Criteria) this;
        }

        public Criteria andLogLessThan(String value) {
            addCriterion("log <", value, "log");
            return (Criteria) this;
        }

        public Criteria andLogLessThanOrEqualTo(String value) {
            addCriterion("log <=", value, "log");
            return (Criteria) this;
        }

        public Criteria andLogLike(String value) {
            addCriterion("log like", value, "log");
            return (Criteria) this;
        }

        public Criteria andLogNotLike(String value) {
            addCriterion("log not like", value, "log");
            return (Criteria) this;
        }

        public Criteria andLogIn(List<String> values) {
            addCriterion("log in", values, "log");
            return (Criteria) this;
        }

        public Criteria andLogNotIn(List<String> values) {
            addCriterion("log not in", values, "log");
            return (Criteria) this;
        }

        public Criteria andLogBetween(String value1, String value2) {
            addCriterion("log between", value1, value2, "log");
            return (Criteria) this;
        }

        public Criteria andLogNotBetween(String value1, String value2) {
            addCriterion("log not between", value1, value2, "log");
            return (Criteria) this;
        }
        
				
        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLike(Date value) {
            addCriterion("create_time like", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotLike(Date value) {
            addCriterion("create_time not like", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }
        
				
        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLike(Date value) {
            addCriterion("update_time like", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotLike(Date value) {
            addCriterion("update_time not like", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }
        
				
        public Criteria andExpiredIsNull() {
            addCriterion("expired is null");
            return (Criteria) this;
        }

        public Criteria andExpiredIsNotNull() {
            addCriterion("expired is not null");
            return (Criteria) this;
        }

        public Criteria andExpiredEqualTo(String value) {
            addCriterion("expired =", value, "expired");
            return (Criteria) this;
        }

        public Criteria andExpiredNotEqualTo(String value) {
            addCriterion("expired <>", value, "expired");
            return (Criteria) this;
        }

        public Criteria andExpiredGreaterThan(String value) {
            addCriterion("expired >", value, "expired");
            return (Criteria) this;
        }

        public Criteria andExpiredGreaterThanOrEqualTo(String value) {
            addCriterion("expired >=", value, "expired");
            return (Criteria) this;
        }

        public Criteria andExpiredLessThan(String value) {
            addCriterion("expired <", value, "expired");
            return (Criteria) this;
        }

        public Criteria andExpiredLessThanOrEqualTo(String value) {
            addCriterion("expired <=", value, "expired");
            return (Criteria) this;
        }

        public Criteria andExpiredLike(String value) {
            addCriterion("expired like", value, "expired");
            return (Criteria) this;
        }

        public Criteria andExpiredNotLike(String value) {
            addCriterion("expired not like", value, "expired");
            return (Criteria) this;
        }

        public Criteria andExpiredIn(List<String> values) {
            addCriterion("expired in", values, "expired");
            return (Criteria) this;
        }

        public Criteria andExpiredNotIn(List<String> values) {
            addCriterion("expired not in", values, "expired");
            return (Criteria) this;
        }

        public Criteria andExpiredBetween(String value1, String value2) {
            addCriterion("expired between", value1, value2, "expired");
            return (Criteria) this;
        }

        public Criteria andExpiredNotBetween(String value1, String value2) {
            addCriterion("expired not between", value1, value2, "expired");
            return (Criteria) this;
        }
        
				
        public Criteria andAttachPathsIsNull() {
            addCriterion("attach_paths is null");
            return (Criteria) this;
        }

        public Criteria andAttachPathsIsNotNull() {
            addCriterion("attach_paths is not null");
            return (Criteria) this;
        }

        public Criteria andAttachPathsEqualTo(String value) {
            addCriterion("attach_paths =", value, "attachPaths");
            return (Criteria) this;
        }

        public Criteria andAttachPathsNotEqualTo(String value) {
            addCriterion("attach_paths <>", value, "attachPaths");
            return (Criteria) this;
        }

        public Criteria andAttachPathsGreaterThan(String value) {
            addCriterion("attach_paths >", value, "attachPaths");
            return (Criteria) this;
        }

        public Criteria andAttachPathsGreaterThanOrEqualTo(String value) {
            addCriterion("attach_paths >=", value, "attachPaths");
            return (Criteria) this;
        }

        public Criteria andAttachPathsLessThan(String value) {
            addCriterion("attach_paths <", value, "attachPaths");
            return (Criteria) this;
        }

        public Criteria andAttachPathsLessThanOrEqualTo(String value) {
            addCriterion("attach_paths <=", value, "attachPaths");
            return (Criteria) this;
        }

        public Criteria andAttachPathsLike(String value) {
            addCriterion("attach_paths like", value, "attachPaths");
            return (Criteria) this;
        }

        public Criteria andAttachPathsNotLike(String value) {
            addCriterion("attach_paths not like", value, "attachPaths");
            return (Criteria) this;
        }

        public Criteria andAttachPathsIn(List<String> values) {
            addCriterion("attach_paths in", values, "attachPaths");
            return (Criteria) this;
        }

        public Criteria andAttachPathsNotIn(List<String> values) {
            addCriterion("attach_paths not in", values, "attachPaths");
            return (Criteria) this;
        }

        public Criteria andAttachPathsBetween(String value1, String value2) {
            addCriterion("attach_paths between", value1, value2, "attachPaths");
            return (Criteria) this;
        }

        public Criteria andAttachPathsNotBetween(String value1, String value2) {
            addCriterion("attach_paths not between", value1, value2, "attachPaths");
            return (Criteria) this;
        }
        
			
		 public Criteria andLikeQuery(UMqMessage record) {
		 	List<String> list= new ArrayList<String>();
		 	List<String> list2= new ArrayList<String>();
        	StringBuffer buffer=new StringBuffer();
			if(record.getId()!=null&&StrUtil.isNotEmpty(record.getId().toString())) {
    			 list.add("ifnull(id,'')");
    		}
			if(record.getMessageGenre()!=null&&StrUtil.isNotEmpty(record.getMessageGenre().toString())) {
    			 list.add("ifnull(message_genre,'')");
    		}
			if(record.getStatus()!=null&&StrUtil.isNotEmpty(record.getStatus().toString())) {
    			 list.add("ifnull(status,'')");
    		}
			if(record.getRetry()!=null&&StrUtil.isNotEmpty(record.getRetry().toString())) {
    			 list.add("ifnull(retry,'')");
    		}
			if(record.getDelay()!=null&&StrUtil.isNotEmpty(record.getDelay().toString())) {
    			 list.add("ifnull(delay,'')");
    		}
			if(record.getAssignTo()!=null&&StrUtil.isNotEmpty(record.getAssignTo().toString())) {
    			 list.add("ifnull(assign_to,'')");
    		}
			if(record.getTitle()!=null&&StrUtil.isNotEmpty(record.getTitle().toString())) {
    			 list.add("ifnull(title,'')");
    		}
			if(record.getContent()!=null&&StrUtil.isNotEmpty(record.getContent().toString())) {
    			 list.add("ifnull(content,'')");
    		}
			if(record.getTemplateId()!=null&&StrUtil.isNotEmpty(record.getTemplateId().toString())) {
    			 list.add("ifnull(template_id,'')");
    		}
			if(record.getTraceId()!=null&&StrUtil.isNotEmpty(record.getTraceId().toString())) {
    			 list.add("ifnull(trace_id,'')");
    		}
			if(record.getOriginalData()!=null&&StrUtil.isNotEmpty(record.getOriginalData().toString())) {
    			 list.add("ifnull(original_data,'')");
    		}
			if(record.getLog()!=null&&StrUtil.isNotEmpty(record.getLog().toString())) {
    			 list.add("ifnull(log,'')");
    		}
			if(record.getCreateTime()!=null&&StrUtil.isNotEmpty(record.getCreateTime().toString())) {
    			 list.add("ifnull(create_time,'')");
    		}
			if(record.getUpdateTime()!=null&&StrUtil.isNotEmpty(record.getUpdateTime().toString())) {
    			 list.add("ifnull(update_time,'')");
    		}
			if(record.getExpired()!=null&&StrUtil.isNotEmpty(record.getExpired().toString())) {
    			 list.add("ifnull(expired,'')");
    		}
			if(record.getAttachPaths()!=null&&StrUtil.isNotEmpty(record.getAttachPaths().toString())) {
    			 list.add("ifnull(attach_paths,'')");
    		}
			if(record.getId()!=null&&StrUtil.isNotEmpty(record.getId().toString())) {
    			list2.add("'%"+record.getId()+"%'");
    		}
			if(record.getMessageGenre()!=null&&StrUtil.isNotEmpty(record.getMessageGenre().toString())) {
    			list2.add("'%"+record.getMessageGenre()+"%'");
    		}
			if(record.getStatus()!=null&&StrUtil.isNotEmpty(record.getStatus().toString())) {
    			list2.add("'%"+record.getStatus()+"%'");
    		}
			if(record.getRetry()!=null&&StrUtil.isNotEmpty(record.getRetry().toString())) {
    			list2.add("'%"+record.getRetry()+"%'");
    		}
			if(record.getDelay()!=null&&StrUtil.isNotEmpty(record.getDelay().toString())) {
    			list2.add("'%"+record.getDelay()+"%'");
    		}
			if(record.getAssignTo()!=null&&StrUtil.isNotEmpty(record.getAssignTo().toString())) {
    			list2.add("'%"+record.getAssignTo()+"%'");
    		}
			if(record.getTitle()!=null&&StrUtil.isNotEmpty(record.getTitle().toString())) {
    			list2.add("'%"+record.getTitle()+"%'");
    		}
			if(record.getContent()!=null&&StrUtil.isNotEmpty(record.getContent().toString())) {
    			list2.add("'%"+record.getContent()+"%'");
    		}
			if(record.getTemplateId()!=null&&StrUtil.isNotEmpty(record.getTemplateId().toString())) {
    			list2.add("'%"+record.getTemplateId()+"%'");
    		}
			if(record.getTraceId()!=null&&StrUtil.isNotEmpty(record.getTraceId().toString())) {
    			list2.add("'%"+record.getTraceId()+"%'");
    		}
			if(record.getOriginalData()!=null&&StrUtil.isNotEmpty(record.getOriginalData().toString())) {
    			list2.add("'%"+record.getOriginalData()+"%'");
    		}
			if(record.getLog()!=null&&StrUtil.isNotEmpty(record.getLog().toString())) {
    			list2.add("'%"+record.getLog()+"%'");
    		}
			if(record.getCreateTime()!=null&&StrUtil.isNotEmpty(record.getCreateTime().toString())) {
    			list2.add("'%"+record.getCreateTime()+"%'");
    		}
			if(record.getUpdateTime()!=null&&StrUtil.isNotEmpty(record.getUpdateTime().toString())) {
    			list2.add("'%"+record.getUpdateTime()+"%'");
    		}
			if(record.getExpired()!=null&&StrUtil.isNotEmpty(record.getExpired().toString())) {
    			list2.add("'%"+record.getExpired()+"%'");
    		}
			if(record.getAttachPaths()!=null&&StrUtil.isNotEmpty(record.getAttachPaths().toString())) {
    			list2.add("'%"+record.getAttachPaths()+"%'");
    		}
        	buffer.append(" CONCAT(");
	        buffer.append(StrUtil.join(",",list));
        	buffer.append(")");
        	buffer.append(" like CONCAT(");
        	buffer.append(StrUtil.join(",",list2));
        	buffer.append(")");
        	if(!" CONCAT() like CONCAT()".equals(buffer.toString())) {
        		addCriterion(buffer.toString());
        	}
        	return (Criteria) this;
        }
        
        public Criteria andLikeQuery2(String searchText) {
		 	List<String> list= new ArrayList<String>();
        	StringBuffer buffer=new StringBuffer();
    		list.add("ifnull(id,'')");
    		list.add("ifnull(message_genre,'')");
    		list.add("ifnull(status,'')");
    		list.add("ifnull(retry,'')");
    		list.add("ifnull(delay,'')");
    		list.add("ifnull(assign_to,'')");
    		list.add("ifnull(title,'')");
    		list.add("ifnull(content,'')");
    		list.add("ifnull(template_id,'')");
    		list.add("ifnull(trace_id,'')");
    		list.add("ifnull(original_data,'')");
    		list.add("ifnull(log,'')");
    		list.add("ifnull(create_time,'')");
    		list.add("ifnull(update_time,'')");
    		list.add("ifnull(expired,'')");
    		list.add("ifnull(attach_paths,'')");
        	buffer.append(" CONCAT(");
	        buffer.append(StrUtil.join(",",list));
        	buffer.append(")");
        	buffer.append("like '%");
        	buffer.append(searchText);
        	buffer.append("%'");
        	addCriterion(buffer.toString());
        	return (Criteria) this;
        }
        
}
	
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}