package szu.model;

import java.util.ArrayList;
import java.util.List;

public class UserExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserExample() {
        oredCriteria = new ArrayList<>();
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
            criteria = new ArrayList<>();
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

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andGenderIsNull() {
            addCriterion("gender is null");
            return (Criteria) this;
        }

        public Criteria andGenderIsNotNull() {
            addCriterion("gender is not null");
            return (Criteria) this;
        }

        public Criteria andGenderEqualTo(Boolean value) {
            addCriterion("gender =", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotEqualTo(Boolean value) {
            addCriterion("gender <>", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThan(Boolean value) {
            addCriterion("gender >", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThanOrEqualTo(Boolean value) {
            addCriterion("gender >=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThan(Boolean value) {
            addCriterion("gender <", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThanOrEqualTo(Boolean value) {
            addCriterion("gender <=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderIn(List<Boolean> values) {
            addCriterion("gender in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotIn(List<Boolean> values) {
            addCriterion("gender not in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderBetween(Boolean value1, Boolean value2) {
            addCriterion("gender between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotBetween(Boolean value1, Boolean value2) {
            addCriterion("gender not between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andLevelIsNull() {
            addCriterion("level is null");
            return (Criteria) this;
        }

        public Criteria andLevelIsNotNull() {
            addCriterion("level is not null");
            return (Criteria) this;
        }

        public Criteria andLevelEqualTo(Integer value) {
            addCriterion("level =", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotEqualTo(Integer value) {
            addCriterion("level <>", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelGreaterThan(Integer value) {
            addCriterion("level >", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("level >=", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelLessThan(Integer value) {
            addCriterion("level <", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelLessThanOrEqualTo(Integer value) {
            addCriterion("level <=", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelIn(List<Integer> values) {
            addCriterion("level in", values, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotIn(List<Integer> values) {
            addCriterion("level not in", values, "level");
            return (Criteria) this;
        }

        public Criteria andLevelBetween(Integer value1, Integer value2) {
            addCriterion("level between", value1, value2, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("level not between", value1, value2, "level");
            return (Criteria) this;
        }

        public Criteria andExpIsNull() {
            addCriterion("exp is null");
            return (Criteria) this;
        }

        public Criteria andExpIsNotNull() {
            addCriterion("exp is not null");
            return (Criteria) this;
        }

        public Criteria andExpEqualTo(Integer value) {
            addCriterion("exp =", value, "exp");
            return (Criteria) this;
        }

        public Criteria andExpNotEqualTo(Integer value) {
            addCriterion("exp <>", value, "exp");
            return (Criteria) this;
        }

        public Criteria andExpGreaterThan(Integer value) {
            addCriterion("exp >", value, "exp");
            return (Criteria) this;
        }

        public Criteria andExpGreaterThanOrEqualTo(Integer value) {
            addCriterion("exp >=", value, "exp");
            return (Criteria) this;
        }

        public Criteria andExpLessThan(Integer value) {
            addCriterion("exp <", value, "exp");
            return (Criteria) this;
        }

        public Criteria andExpLessThanOrEqualTo(Integer value) {
            addCriterion("exp <=", value, "exp");
            return (Criteria) this;
        }

        public Criteria andExpIn(List<Integer> values) {
            addCriterion("exp in", values, "exp");
            return (Criteria) this;
        }

        public Criteria andExpNotIn(List<Integer> values) {
            addCriterion("exp not in", values, "exp");
            return (Criteria) this;
        }

        public Criteria andExpBetween(Integer value1, Integer value2) {
            addCriterion("exp between", value1, value2, "exp");
            return (Criteria) this;
        }

        public Criteria andExpNotBetween(Integer value1, Integer value2) {
            addCriterion("exp not between", value1, value2, "exp");
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

        public Criteria andStatusEqualTo(byte[] value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(byte[] value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(byte[] value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(byte[] value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(byte[] value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(byte[] value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<byte[]> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<byte[]> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(byte[] value1, byte[] value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(byte[] value1, byte[] value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andRoleIsNull() {
            addCriterion("role is null");
            return (Criteria) this;
        }

        public Criteria andRoleIsNotNull() {
            addCriterion("role is not null");
            return (Criteria) this;
        }

        public Criteria andRoleEqualTo(byte[] value) {
            addCriterion("role =", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleNotEqualTo(byte[] value) {
            addCriterion("role <>", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleGreaterThan(byte[] value) {
            addCriterion("role >", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleGreaterThanOrEqualTo(byte[] value) {
            addCriterion("role >=", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleLessThan(byte[] value) {
            addCriterion("role <", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleLessThanOrEqualTo(byte[] value) {
            addCriterion("role <=", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleIn(List<byte[]> values) {
            addCriterion("role in", values, "role");
            return (Criteria) this;
        }

        public Criteria andRoleNotIn(List<byte[]> values) {
            addCriterion("role not in", values, "role");
            return (Criteria) this;
        }

        public Criteria andRoleBetween(byte[] value1, byte[] value2) {
            addCriterion("role between", value1, value2, "role");
            return (Criteria) this;
        }

        public Criteria andRoleNotBetween(byte[] value1, byte[] value2) {
            addCriterion("role not between", value1, value2, "role");
            return (Criteria) this;
        }

        public Criteria andFanIsNull() {
            addCriterion("fan is null");
            return (Criteria) this;
        }

        public Criteria andFanIsNotNull() {
            addCriterion("fan is not null");
            return (Criteria) this;
        }

        public Criteria andFanEqualTo(Integer value) {
            addCriterion("fan =", value, "fan");
            return (Criteria) this;
        }

        public Criteria andFanNotEqualTo(Integer value) {
            addCriterion("fan <>", value, "fan");
            return (Criteria) this;
        }

        public Criteria andFanGreaterThan(Integer value) {
            addCriterion("fan >", value, "fan");
            return (Criteria) this;
        }

        public Criteria andFanGreaterThanOrEqualTo(Integer value) {
            addCriterion("fan >=", value, "fan");
            return (Criteria) this;
        }

        public Criteria andFanLessThan(Integer value) {
            addCriterion("fan <", value, "fan");
            return (Criteria) this;
        }

        public Criteria andFanLessThanOrEqualTo(Integer value) {
            addCriterion("fan <=", value, "fan");
            return (Criteria) this;
        }

        public Criteria andFanIn(List<Integer> values) {
            addCriterion("fan in", values, "fan");
            return (Criteria) this;
        }

        public Criteria andFanNotIn(List<Integer> values) {
            addCriterion("fan not in", values, "fan");
            return (Criteria) this;
        }

        public Criteria andFanBetween(Integer value1, Integer value2) {
            addCriterion("fan between", value1, value2, "fan");
            return (Criteria) this;
        }

        public Criteria andFanNotBetween(Integer value1, Integer value2) {
            addCriterion("fan not between", value1, value2, "fan");
            return (Criteria) this;
        }

        public Criteria andFollowIsNull() {
            addCriterion("follow is null");
            return (Criteria) this;
        }

        public Criteria andFollowIsNotNull() {
            addCriterion("follow is not null");
            return (Criteria) this;
        }

        public Criteria andFollowEqualTo(Integer value) {
            addCriterion("follow =", value, "follow");
            return (Criteria) this;
        }

        public Criteria andFollowNotEqualTo(Integer value) {
            addCriterion("follow <>", value, "follow");
            return (Criteria) this;
        }

        public Criteria andFollowGreaterThan(Integer value) {
            addCriterion("follow >", value, "follow");
            return (Criteria) this;
        }

        public Criteria andFollowGreaterThanOrEqualTo(Integer value) {
            addCriterion("follow >=", value, "follow");
            return (Criteria) this;
        }

        public Criteria andFollowLessThan(Integer value) {
            addCriterion("follow <", value, "follow");
            return (Criteria) this;
        }

        public Criteria andFollowLessThanOrEqualTo(Integer value) {
            addCriterion("follow <=", value, "follow");
            return (Criteria) this;
        }

        public Criteria andFollowIn(List<Integer> values) {
            addCriterion("follow in", values, "follow");
            return (Criteria) this;
        }

        public Criteria andFollowNotIn(List<Integer> values) {
            addCriterion("follow not in", values, "follow");
            return (Criteria) this;
        }

        public Criteria andFollowBetween(Integer value1, Integer value2) {
            addCriterion("follow between", value1, value2, "follow");
            return (Criteria) this;
        }

        public Criteria andFollowNotBetween(Integer value1, Integer value2) {
            addCriterion("follow not between", value1, value2, "follow");
            return (Criteria) this;
        }

        public Criteria andLikeIsNull() {
            addCriterion("like is null");
            return (Criteria) this;
        }

        public Criteria andLikeIsNotNull() {
            addCriterion("like is not null");
            return (Criteria) this;
        }

        public Criteria andLikeEqualTo(Integer value) {
            addCriterion("like =", value, "like");
            return (Criteria) this;
        }

        public Criteria andLikeNotEqualTo(Integer value) {
            addCriterion("like <>", value, "like");
            return (Criteria) this;
        }

        public Criteria andLikeGreaterThan(Integer value) {
            addCriterion("like >", value, "like");
            return (Criteria) this;
        }

        public Criteria andLikeGreaterThanOrEqualTo(Integer value) {
            addCriterion("like >=", value, "like");
            return (Criteria) this;
        }

        public Criteria andLikeLessThan(Integer value) {
            addCriterion("like <", value, "like");
            return (Criteria) this;
        }

        public Criteria andLikeLessThanOrEqualTo(Integer value) {
            addCriterion("like <=", value, "like");
            return (Criteria) this;
        }

        public Criteria andLikeIn(List<Integer> values) {
            addCriterion("like in", values, "like");
            return (Criteria) this;
        }

        public Criteria andLikeNotIn(List<Integer> values) {
            addCriterion("like not in", values, "like");
            return (Criteria) this;
        }

        public Criteria andLikeBetween(Integer value1, Integer value2) {
            addCriterion("like between", value1, value2, "like");
            return (Criteria) this;
        }

        public Criteria andLikeNotBetween(Integer value1, Integer value2) {
            addCriterion("like not between", value1, value2, "like");
            return (Criteria) this;
        }

        public Criteria andIpIsNull() {
            addCriterion("ip is null");
            return (Criteria) this;
        }

        public Criteria andIpIsNotNull() {
            addCriterion("ip is not null");
            return (Criteria) this;
        }

        public Criteria andIpEqualTo(String value) {
            addCriterion("ip =", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotEqualTo(String value) {
            addCriterion("ip <>", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThan(String value) {
            addCriterion("ip >", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThanOrEqualTo(String value) {
            addCriterion("ip >=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThan(String value) {
            addCriterion("ip <", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThanOrEqualTo(String value) {
            addCriterion("ip <=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLike(String value) {
            addCriterion("ip like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotLike(String value) {
            addCriterion("ip not like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpIn(List<String> values) {
            addCriterion("ip in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotIn(List<String> values) {
            addCriterion("ip not in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpBetween(String value1, String value2) {
            addCriterion("ip between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotBetween(String value1, String value2) {
            addCriterion("ip not between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNull() {
            addCriterion("phone is null");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNotNull() {
            addCriterion("phone is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneEqualTo(String value) {
            addCriterion("phone =", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotEqualTo(String value) {
            addCriterion("phone <>", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThan(String value) {
            addCriterion("phone >", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("phone >=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThan(String value) {
            addCriterion("phone <", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThanOrEqualTo(String value) {
            addCriterion("phone <=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLike(String value) {
            addCriterion("phone like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotLike(String value) {
            addCriterion("phone not like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneIn(List<String> values) {
            addCriterion("phone in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotIn(List<String> values) {
            addCriterion("phone not in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneBetween(String value1, String value2) {
            addCriterion("phone between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotBetween(String value1, String value2) {
            addCriterion("phone not between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNull() {
            addCriterion("password is null");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNotNull() {
            addCriterion("password is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordEqualTo(String value) {
            addCriterion("password =", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotEqualTo(String value) {
            addCriterion("password <>", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThan(String value) {
            addCriterion("password >", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("password >=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThan(String value) {
            addCriterion("password <", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThanOrEqualTo(String value) {
            addCriterion("password <=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLike(String value) {
            addCriterion("password like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotLike(String value) {
            addCriterion("password not like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordIn(List<String> values) {
            addCriterion("password in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotIn(List<String> values) {
            addCriterion("password not in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordBetween(String value1, String value2) {
            addCriterion("password between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotBetween(String value1, String value2) {
            addCriterion("password not between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andInfoIsNull() {
            addCriterion("info is null");
            return (Criteria) this;
        }

        public Criteria andInfoIsNotNull() {
            addCriterion("info is not null");
            return (Criteria) this;
        }

        public Criteria andInfoEqualTo(String value) {
            addCriterion("info =", value, "info");
            return (Criteria) this;
        }

        public Criteria andInfoNotEqualTo(String value) {
            addCriterion("info <>", value, "info");
            return (Criteria) this;
        }

        public Criteria andInfoGreaterThan(String value) {
            addCriterion("info >", value, "info");
            return (Criteria) this;
        }

        public Criteria andInfoGreaterThanOrEqualTo(String value) {
            addCriterion("info >=", value, "info");
            return (Criteria) this;
        }

        public Criteria andInfoLessThan(String value) {
            addCriterion("info <", value, "info");
            return (Criteria) this;
        }

        public Criteria andInfoLessThanOrEqualTo(String value) {
            addCriterion("info <=", value, "info");
            return (Criteria) this;
        }

        public Criteria andInfoLike(String value) {
            addCriterion("info like", value, "info");
            return (Criteria) this;
        }

        public Criteria andInfoNotLike(String value) {
            addCriterion("info not like", value, "info");
            return (Criteria) this;
        }

        public Criteria andInfoIn(List<String> values) {
            addCriterion("info in", values, "info");
            return (Criteria) this;
        }

        public Criteria andInfoNotIn(List<String> values) {
            addCriterion("info not in", values, "info");
            return (Criteria) this;
        }

        public Criteria andInfoBetween(String value1, String value2) {
            addCriterion("info between", value1, value2, "info");
            return (Criteria) this;
        }

        public Criteria andInfoNotBetween(String value1, String value2) {
            addCriterion("info not between", value1, value2, "info");
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