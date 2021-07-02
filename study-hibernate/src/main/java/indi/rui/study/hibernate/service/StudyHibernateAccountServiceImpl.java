package indi.rui.study.hibernate.service;

import indi.rui.study.hibernate.entity.StudyHibernateAccount;
import indi.rui.study.hibernate.repository.StudyHibernateAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.internal.MetadataBuilderImpl;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.hibernate.tool.schema.TargetType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

/**
 * @author: yaowr
 * @create: 2020-04-22
 */
@Slf4j
@Service
public class StudyHibernateAccountServiceImpl implements IStudyHibernateAccountService {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private StudyHibernateAccountRepository repository;

    @Override
    public void save(StudyHibernateAccount entity) {
        StudyHibernateAccount oldEntity = get(entity.getId());
        if (oldEntity == null) {
            oldEntity = new StudyHibernateAccount();
        }
        BeanUtils.copyProperties(entity, oldEntity);
        repository.save(oldEntity);
    }

    @Override
    public StudyHibernateAccount get(Long id) {
        if (id == null) {
            return null;
        }
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<StudyHibernateAccount> list() {
        Iterator<StudyHibernateAccount> iterator = repository.findAll().iterator();
        List<StudyHibernateAccount> rtnList = new ArrayList<>();
        while (iterator.hasNext()) {
            rtnList.add(iterator.next());
        }
        return rtnList;
    }

    @Override
    public void createShard(String suffix) {
        Session session = em.unwrap(Session.class);

        StandardServiceRegistry serviceRegistry = session.getSessionFactory().getSessionFactoryOptions().getServiceRegistry();
        MetadataSources metadataSources = new MetadataSources(serviceRegistry);

        metadataSources.addAnnotatedClass(StudyHibernateAccount.class);

        MetadataBuilderImpl metadataBuilder = new MetadataBuilderImpl(metadataSources);
        metadataBuilder.applyPhysicalNamingStrategy(new TableNamingStrategy(suffix));
        Metadata metadata = metadataBuilder.build();

        SchemaUpdate schemaUpdate = new SchemaUpdate();
        schemaUpdate.execute(EnumSet.of(TargetType.DATABASE), metadata, serviceRegistry);
    }

    class TableNamingStrategy extends SpringPhysicalNamingStrategy {

        private String suffix;

        public TableNamingStrategy(String suffix) {
            this.suffix = suffix;
        }

        @Override
        public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
            Identifier identifier = super.toPhysicalTableName(name, context);
            String tableName = identifier.getText() + "_" + suffix;
            log.info("tableName={}", tableName);
            return getIdentifier(tableName, identifier.isQuoted(), context);
        }
    }
}
