package ru.ifmo.cis.mrp.back.ejb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.jms.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * User: Igor
 * Date: 16.11.11
 * Time: 16:12
 */
@Startup
@Singleton
public class BackTimer {

    private static final Logger LOGGER = LoggerFactory.getLogger(BackTimer.class);

    private static final String BACK_TIMER = "Back_timer";

    @PersistenceContext(unitName = "MRPPersistenceUnit")
    private EntityManager em;


    @Resource
    private SessionContext sessionContext;

    @Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName = "/topic/tickTopic")
    private Topic tickTopic;

    private TopicConnection con = null;
    private TopicSession ses = null;
    private TopicPublisher sender = null;
    public int supplyTimer = 0;

    @EJB
    private SequenceOptimizer sequenceOptimizer;

    @PostConstruct
    public void scheduleTimer() {
        LOGGER.info("[Timer] Scheduling timer after 20 seconds...");
        for (Timer t : sessionContext.getTimerService().getTimers()) {
            t.cancel();
        }
        TopicConnectionFactory qcf = (TopicConnectionFactory) connectionFactory;
        try {
            con = qcf.createTopicConnection();
            ses = con.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            sender = ses.createPublisher(tickTopic);
        } catch (JMSException e) {
            LOGGER.error("Error while creating topic connection", e);
        }
        sessionContext.getTimerService().createIntervalTimer(30000, 1000, new TimerConfig(BACK_TIMER, true));
    }

    @PreDestroy
    public void destroy() {
        try {
            if (sender != null)
                sender.close();
            if (ses != null)
                ses.close();
            if (con != null)
                con.close();
        } catch (JMSException e) {
            LOGGER.error("Exception while closing BackTimer JMS", e);
        }
    }


    @Timeout
    public void timeout(final Timer timer) {
        if ((timer.getInfo() != null) && (timer.getInfo().equals(BACK_TIMER))) {
            LOGGER.info("[Back] Tick (Ticks to Supply request: " + (Production.supplyRequestTime - (supplyTimer % Production.supplyRequestTime) - 1) + ")");
            try {
                if (ses != null) {
                    ObjectMessage msg = ses.createObjectMessage(sequenceOptimizer.getSequence());
                    sender.publish(msg);
                    if (Production.supplyRequestTime - 1 - (supplyTimer % Production.supplyRequestTime) == 0) {
                        LOGGER.info("[Back] It's supply request time!");
                        msg = ses.createObjectMessage(sequenceOptimizer.countSupplyRequest(Production.supplyTime)); //TODO:??? supplyTime
                        sender.publish(msg);
                        //supplyRequest=em.merge(supplyRequest);

                        /*List supplies = em.createQuery("from SupplyRequest order by id desc").setMaxResults(1).getResultList();
                        if (supplies.size() == 1) {
                            msg.setObjectProperty("supplyRequest", supplies.get(0));
                        }*/
                    }
                }
                ++supplyTimer;
            } catch (JMSException e) {
                LOGGER.error("Exception while sending order from front to back", e);
            }
        }
    }
}
