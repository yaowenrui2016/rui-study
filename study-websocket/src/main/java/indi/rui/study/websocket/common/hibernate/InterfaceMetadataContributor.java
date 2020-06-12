package indi.rui.study.websocket.common.hibernate;

import org.hibernate.annotations.common.reflection.XClass;
import org.hibernate.annotations.common.reflection.XProperty;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.boot.model.relational.Database;
import org.hibernate.boot.spi.InFlightMetadataCollector;
import org.hibernate.boot.spi.MetadataContributor;
import org.hibernate.mapping.*;
import org.jboss.jandex.IndexView;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author: yaowr
 * @create: 2020-06-11
 */
public class InterfaceMetadataContributor implements MetadataContributor {
    @Override
    public void contribute(InFlightMetadataCollector metadataCollector, IndexView jandexIndex) {
        Map<String, PersistentClass> bingdingMap = metadataCollector.getEntityBindingMap();
        Iterator<Map.Entry<String, PersistentClass>> iterator = bingdingMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, PersistentClass> entry = iterator.next();
            PersistentClass persistentClass = entry.getValue();
            Class mappedClazz = persistentClass.getRootClass().getMappedClass();
            Class[] interfaces = mappedClazz.getInterfaces();
            for (Class interfs : interfaces) {
                List<XProperty> xProps = metadataCollector.getBootstrapContext().getReflectionManager().toXClass(interfs).getDeclaredProperties(XClass.ACCESS_PROPERTY);
                for (XProperty xProperty : xProps) {
                    persistentClass.addProperty(buildProperty(xProperty, metadataCollector, persistentClass.getTable()));
                }
            }
        }
    }

//    private void buildPropertyBinder(PersistentClass persistentClass, XProperty property) {
//        RootClass rootClass  = (RootClass) persistentClass;
//        rootClass.g
//
//        //prepare PropertyBinder
//        PropertyBinder propertyBinder = new PropertyBinder();
//        propertyBinder.setName( inferredData.getPropertyName() );
//        propertyBinder.setReturnedClassName( inferredData.getTypeName() );
//        propertyBinder.setAccessType( inferredData.getDefaultAccess() );
//        propertyBinder.setHolder( propertyHolder );
//        propertyBinder.setProperty( property );
//        propertyBinder.setReturnedClass( inferredData.getPropertyClass() );
//        propertyBinder.setBuildingContext( context );
//        if ( isIdentifierMapper ) {
//            propertyBinder.setInsertable( false );
//            propertyBinder.setUpdatable( false );
//        }
//        propertyBinder.setDeclaringClass( inferredData.getDeclaringClass() );
//        propertyBinder.setEntityBinder( entityBinder );
//        propertyBinder.setInheritanceStatePerClass( inheritanceStatePerClass );
//
//        boolean isId = !entityBinder.isIgnoreIdAnnotations() &&
//                ( property.isAnnotationPresent( Id.class )
//                        || property.isAnnotationPresent( EmbeddedId.class ) );
//        propertyBinder.setId( isId );
//
//        final LazyGroup lazyGroupAnnotation = property.getAnnotation( LazyGroup.class );
//        if ( lazyGroupAnnotation != null ) {
//            propertyBinder.setLazyGroup( lazyGroupAnnotation.value() );
//        }
//    }

    private Property buildProperty(XProperty xProperty, InFlightMetadataCollector metadataCollector, Table table) {
        Property prop = new Property();
        prop.setName(xProperty.getName());
        prop.setLazy(true);
        prop.setLob(false);
        prop.setPropertyAccessorName(DynamicPropertyAccessorStrategy.class.getName());
        prop.setValue(buildSimpleValue(metadataCollector, xProperty, table));
        return prop;
    }

    private SimpleValue buildSimpleValue(InFlightMetadataCollector metadataCollector, XProperty xProperty, Table table) {
        SimpleValue value = new SimpleValue(metadataCollector, table);
        Column column = new Column();
        column.setName(defineColumnName(metadataCollector, xProperty.getName()));
        table.addColumn(column);
        value.addColumn(column);
        value.setTypeName(xProperty.getElementClass().getName());
        return value;
    }

    private String defineColumnName(InFlightMetadataCollector metadataCollector, String propertyName) {
        Database database = metadataCollector.getDatabase();
        PhysicalNamingStrategy physicalNamingStrategy = metadataCollector.getMetadataBuildingOptions().getPhysicalNamingStrategy();
        final Identifier explicitName = database.toIdentifier( propertyName );
        final Identifier physicalName =  physicalNamingStrategy.toPhysicalColumnName( explicitName, database.getJdbcEnvironment() );
        return physicalName.render( database.getDialect() );
    }
}
