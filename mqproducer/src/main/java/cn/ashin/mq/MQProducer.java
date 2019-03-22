package cn.ashin.mq;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import cn.ashin.mq.params.MailParam;

import com.alibaba.fastjson.JSONObject;

/**
 * @author wbs
 * @version 2019年1月15日 下午2:45:37 类说明
 */
@Service("mqProducer")
public class MQProducer {

	@Autowired
	private JmsTemplate activeMqJmsTemplate;

	/**
	 * 发送消息
	 * 
	 * @param mail
	 */
	public void sendMessage(final MailParam mail) {
		//设置消息的过期时间
//		activeMqJmsTemplate.setTimeToLive(3000);
//		this.activeMqJmsTemplate.setExplicitQosEnabled(true);
//		//这段代码配置超时消息不缓存到DLQ
//		this.activeMqJmsTemplate.setDeliveryMode(DeliveryMode.NON_PERSISTENT);


		activeMqJmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(JSONObject.toJSONString(mail));
			}
		});

	}
}
