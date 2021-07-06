package indi.rui.study.hibernate.service;

import indi.rui.study.hibernate.entity.StudyHibernateAccount;
import indi.rui.study.hibernate.entity.StudyHibernateAccountTableShard;
import indi.rui.study.hibernate.repository.StudyHibernateAccountTableShardRepository;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

/**
 * @author: yaowr
 * @create: 2021-07-05
 */
@Slf4j
@Service
@RestController
@RequestMapping("/study/hibernate/accountTableShard")
public class StudyHibernateAccountTableShardServiceImpl implements
        IStudyHibernateAccountTableShardService {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private StudyHibernateAccountTableShardRepository repository;

    @Transactional
    @Override
    public void createShard(String suffix) {
        if (!StringUtils.hasText(suffix)) {
            throw new RuntimeException("arguments 'suffix' must not be empty");
        }
        Session session = em.unwrap(Session.class);

        StandardServiceRegistry serviceRegistry = session.getSessionFactory().getSessionFactoryOptions().getServiceRegistry();
        MetadataSources metadataSources = new MetadataSources(serviceRegistry);

        metadataSources.addAnnotatedClass(StudyHibernateAccount.class);

        MetadataBuilderImpl metadataBuilder = new MetadataBuilderImpl(metadataSources);
        metadataBuilder.applyPhysicalNamingStrategy(new TableNamingStrategy(suffix));
        Metadata metadata = metadataBuilder.build();

        SchemaUpdate schemaUpdate = new SchemaUpdate();
        schemaUpdate.execute(EnumSet.of(TargetType.DATABASE), metadata, serviceRegistry);

        // 保存分表记录
        StudyHibernateAccountTableShard entity = new StudyHibernateAccountTableShard();
        entity.setSuffixNo(Integer.valueOf(suffix));
        entity.setIsCurrent(Boolean.FALSE);
        repository.save(entity);
        // 更新当前表
        refreshCurrent();
    }

    @Override
    public List<StudyHibernateAccountTableShard> findAll() {
        Iterator<StudyHibernateAccountTableShard> iterator = repository.findAll().iterator();
        List<StudyHibernateAccountTableShard> rtnList = new ArrayList<>();
        while (iterator.hasNext()) {
            rtnList.add(iterator.next());
        }
        return rtnList;
    }

    @Override
    public StudyHibernateAccountTableShard getCurrent() {
        List<StudyHibernateAccountTableShard> entities = repository.getCurrent();
        return entities.get(0);
    }

    @Transactional
    @Override
    public void refreshCurrent() {
        // 将所有记录的isCurrent置为false
        repository.updateCurrentStatusToFalse();
        // 将最大的后缀置为isCurrent
        Integer suffixNo = repository.getMaxSuffixNo();
        repository.updateCurrent(suffixNo);
    }

    @Override
    public void presetTable(Integer suffixNo) {
        ThreadLocalTableNameHolder.presetTable(suffixNo);
    }

    @Override
    public void presetDefaultTable() {
        presetTable(getCurrent().getSuffixNo());
    }

    /**
     * 建表命名策略
     */
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
