package org.atmars.dao;

import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Follow entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see org.atmars.dao.Follow
 * @author MyEclipse Persistence Tools
 */

public class FollowDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(FollowDAO.class);

	// property constants

	protected void initDao() {
		// do nothing
	}

	public void save(Follow transientInstance) {
		log.debug("saving Follow instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Follow persistentInstance) {
		log.debug("deleting Follow instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Follow findById(java.lang.Integer id) {
		log.debug("getting Follow instance with id: " + id);
		try {
			Follow instance = (Follow) getHibernateTemplate().get(
					"org.atmars.dao.Follow", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Follow instance) {
		log.debug("finding Follow instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Follow instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Follow as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all Follow instances");
		try {
			String queryString = "from Follow";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Follow merge(Follow detachedInstance) {
		log.debug("merging Follow instance");
		try {
			Follow result = (Follow) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Follow instance) {
		log.debug("attaching dirty Follow instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Follow instance) {
		log.debug("attaching clean Follow instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static FollowDAO getFromApplicationContext(ApplicationContext ctx) {
		return (FollowDAO) ctx.getBean("FollowDAO");
	}
}