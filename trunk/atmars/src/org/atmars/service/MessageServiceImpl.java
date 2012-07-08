package org.atmars.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.atmars.dao.*;
import org.atmars.service.interfaces.MessageService;
import org.hibernate.classic.Session;
import org.hibernate.Query;

public class MessageServiceImpl implements MessageService {

	private MessageDAO messageDAO;
	private UserDAO userDAO;
	private CommentDAO commentDAO;
	private FavoriteDAO favoriteDAO;
	private TopicDAO topicDAO;

	public MessageServiceImpl(MessageDAO messageDAO, CommentDAO commentDAO,
			UserDAO userDAO, FavoriteDAO favoriteDAO, TopicDAO topicDAO) {
		this.messageDAO = messageDAO;
		this.commentDAO = commentDAO;
		this.userDAO = userDAO;
		this.favoriteDAO = favoriteDAO;
		this.topicDAO = topicDAO;
	}

	@Override
	public Message sendMessage(Integer userid, String text, String image,
			String position, int zhuanfaID) {
		Message message = new Message();
		message.setUser(userDAO.findById(userid));
		message.setImage(image);
		message.setPosition(position);
		message.setCommentCount(0);
		message.setTime(new Date());

		if (zhuanfaID == -1) {
			message.setText(text);
			message.setSourceId(-1);
		} else {
			Session s  = messageDAO.getHibernateTemplate().getSessionFactory().openSession();
			Query q = s.createQuery("from Message m where m.messageId="+String.valueOf(zhuanfaID));
			List l = q.list();
			Message m = (Message) l.get(0);
			if (m.getSourceId() == -1) {
				message.setText(text);
				message.setSourceId(zhuanfaID);
			} else {
				String text2 = text + "//" + m.getUser().getNickname() + ":"
						+ m.getText();
				message.setText(text2);
				message.setSourceId(m.getSourceId());
			}
			s.close();
		}
		messageDAO.save(message);

		// topic
		int a, b = 0;
		while (true) {
			a = text.indexOf('#', b);
			b = text.indexOf('#', a + 1);
			if (a != -1 && b != -1) {
				String title = text.substring(a + 1, b);
				List<Topic> topicList = topicDAO.findByTitle(title);
				if (topicList.size() == 0) {
					Topic topic = new Topic();
					topic.setTitle(title);
					topicDAO.save(topic);
				} else {
					Topic topic = (Topic) topicList.get(0);
					topic.setCount(topic.getCount() + 1);
					topicDAO.getHibernateTemplate().update(topic);
				}
				b++;
			} else {
				break;
			}
		}

		String queryString = "from Message m where m.user.userId="
				+ message.getUser().getUserId() + " order by m.time desc";
		List l = messageDAO.getHibernateTemplate().find(queryString);
		if (l == null || l.size() == 0) {
			return null;
		}

		Message m = (Message) l.get(0);
		m.setOriginal(null);
		return m;
	}

	@Override
	public MessageDAO getMessageDAO() {
		return this.messageDAO;
	}

	@Override
	public List getMessage(int userid) {
		String queryString = "select distinct m.messageId, m.user, m.text, m.image, m.time, m.position, m.sourceId, m.commentCount from Message m ,Follow f where m.user.userId = f.userByFollowingId or m.user.userId = "
				+ userid + " order by m.time desc";
		List l = messageDAO.getHibernateTemplate().find(queryString);
		return l;
	}

	@Override
	public List getMessage(int userid, int oldest_message_id) {
		// Session session = this.messageDAO.getSessionFactory().openSession();

		String queryString = "select distinct m.messageId, m.user, m.text, m.image, m.time, m.position, m.sourceId, m.commentCount from Message m ,Follow f where (m.user.userId = f.userByFollowingId or m.user.userId = "
				+ userid
				+ ")and( m.messageId < "
				+ oldest_message_id
				+ ") order by m.time desc";
		System.out.println(queryString);
		List l = messageDAO.getHibernateTemplate().find(queryString);
		// select m.messageId, m.user, m.text, m.image, m.time, m.position,
		// m.sourceId, m.commentCount
		// where m.user.userId = f.userByFollowedId or m.user.userId = " +
		// userid +
		// Query query = session.createQuery(queryString);
		// query.setFirstResult((page-1)*10);
		// query.setMaxResults(10);
		// String queryString =
		// "select m.message_id, m.user_id, m.text, m.image, m.time, m.position, m.source_id, m.comment_count from Message m, Follow f where m.user_id = f.followed_id or m.user_id = "
		// + userid + " limit " + page + ", 10";
		// System.out.println(query.list().size());
		return l;
	}

	@Override
	public Comment sendComment(Integer userid, Integer messageid, String text) {
		
		Comment comment = new Comment();
		comment.setUser(userDAO.findById(userid));
		comment.setMessage(messageDAO.findById(messageid));
		comment.setText(text);
		comment.setTime(new Date());
		commentDAO.save(comment);
		
		Comment c = (Comment) commentDAO.getHibernateTemplate().findByExample(comment).get(0);
		
		c.setUser(userDAO.findById(c.getUser().getUserId()));
		c.getUser().setComments(null);
		c.getUser().setFavorites(null);
		c.getUser().setFollowsForFollowedId(null);
		c.getUser().setFollowsForFollowingId(null);
		c.getUser().setMessages(null);
		c.getUser().setPassword(null);
		
		c.setMessage(null);
		
		return c;
	}

	@Override 
	public List<Comment> getComment(int messageId,int cursor)
	{
		String queryString=null;
		List<Comment> comments = new ArrayList<Comment>();
		if(cursor==-1)
		{
			queryString = "from Comment c where c.message.messageId="+String.valueOf(messageId);
		}else
		{
			queryString= "from Comment c where c.message.messageId="+String.valueOf(messageId)+" and c.commentId<"+String.valueOf(cursor);
		}
		Session s = commentDAO.getHibernateTemplate().getSessionFactory().openSession();
		Query q = s.createQuery(queryString);
		q.setMaxResults(10);
		List l = q.list();
		for(Object o:l)
		{
			Comment c = (Comment) o;
			c.setMessage(null);
			c.setUser(userDAO.findById(c.getUser().getUserId()));
			c.setText(FaceServiceImpl.replaceFace(c.getText()));
			c.getUser().setComments(null);
			c.getUser().setFavorites(null);
			c.getUser().setFollowsForFollowedId(null);
			c.getUser().setFollowsForFollowingId(null);
			c.getUser().setMessages(null);
			comments.add(c);
		}
		s.close();
		
		return comments;
	}
	

	@Override
	public Message getSingleMessage(Integer id) {

		Message m = messageDAO.findById(id);
		if (m == null) {
			return null;
		}
		Message m2 = new Message();
		m2.setCommentCount(m.getCommentCount());
		m2.setImage(m.getImage());
		m2.setMessageId(m.getMessageId());
		m2.setPosition(m.getPosition());
		m2.setSourceId(m.getSourceId());
		m2.setText(m.getText());
		m2.setTime(m.getTime());
		User u2 = new User();
		User u = m.getUser();
		u2.setUserId(u.getUserId());
		u = userDAO.findById(u2.getUserId());
		u2.setEmail(u.getEmail());
		u2.setGender(u.getGender());
		u2.setNickname(u.getNickname());
		u2.setImage(u.getImage());
		u2.setPriority(u.getPriority());
		m2.setUser(u2);
		return m2;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public List<Message> searchMessage(Integer userid, String query) {
		String queryString = "select m.message_id, m.user_id, m.text, m.image, m.time, m.position, m.source_id, m.comment_count from Message m, Follow f where m.text like '%"
				+ query
				+ "%' and m.user_id = f.followed_id or m.user_id = "
				+ userid;
		return messageDAO.getHibernateTemplate().find(queryString);
	}

	@Override
	public void addFavoriteMessage(Integer userid, Integer messageid) {
		Favorite favorite = new Favorite();
		favorite.setUser(userDAO.findById(userid));
		favorite.setMessage(messageDAO.findById(messageid));
		favoriteDAO.save(favorite);
	}

	// @Override
	// public void removeFavoriteMessage(User u,Integer message_id) {
	// Favorite f = new Favorite();
	// f.setUser(u);
	// f.setMessage(message);
	// favoriteDAO.findByExample(new Favorite())
	// favoriteDAO.delete(favoriteDAO.findById(id));
	// }

	@Override
	public List<Topic> getHotTopic() {
		return topicDAO.getHibernateTemplate().find("from Topic t order by t.count desc");
	}

	@Override
	public List findNewestMessageS() {
		Session s = messageDAO.getSessionFactory().openSession();
		String hql = "from Message m where m.sourceId=-1 order by m.time desc";
		Query q = s.createQuery(hql);
		q.setMaxResults(6);
		return q.list();
	}
}
