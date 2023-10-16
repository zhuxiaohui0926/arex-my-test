//package com.example.arexmytest.service;
//
//import com.example.arexmytest.pojo.MockCategoryType;
//import com.example.arexmytest.pojo.Mocker;
//import com.example.arexmytest.pojo.TradeMocker;
//import com.example.arexmytest.utils.TimeUtils;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoDatabase;
//import com.mongodb.client.model.Filters;
//import com.mongodb.client.model.Sorts;
//import com.mongodb.client.model.Updates;
//import com.sun.istack.NotNull;
//import org.bson.conversions.Bson;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//
//import java.util.*;
//
//@EnableConfigurationProperties({StorageConfigurationProperties.class})
//public class TradeMockerMongoRepositoryProvider implements RepositoryProvider<TradeMocker>{
//    public static final String CREATE_TIME_COLUMN_NAME = "creationTime";
//    public static final String UPDATE_TIME_COLUMN_NAME = "updateTime";
//    public static final String EXPIRATION_TIME_COLUMN_NAME = "expirationTime";
//    public static final String PRIMARY_KEY_COLUMN_NAME = "_id";
//    public static final String RECORD_ID_COLUMN_NAME = "recordId";
//    private static final String APP_ID_COLUMN_NAME = "appId";
//    private static final String ENV_COLUMN_NAME = "recordEnvironment";
//    private static final String OPERATION_COLUMN_NAME = "operationName";
//    private static final String COLLECTION_PREFIX = "Mocker";
//    private static final String PLACE_HOLDER = "$";
//    private static final String VALUE_COLUMN = "value";
//
//    private static final String GROUP_OP = "$group";
//    private static final String SUM_OP = "$sum";
//    private static final String PROJECT_OP = "$project";
//    private static final String AGENT_RECORD_VERSION_COLUMN_NAME = "recordVersion";
//    private static final String TARGET_RESPONSE_COLUMN_NAME = "targetResponse";
//    private final static Bson CREATE_TIME_ASCENDING_SORT = Sorts.ascending(CREATE_TIME_COLUMN_NAME);
//    private final static Bson CREATE_TIME_DESCENDING_SORT = Sorts.descending(CREATE_TIME_COLUMN_NAME);
//
//    private final Class<TradeMocker> targetClassType;
//    private static final int DEFAULT_MIN_LIMIT_SIZE = 1;
//    private static final int DEFAULT_MAX_LIMIT_SIZE = 1000;
//    private static final int DEFAULT_BSON_WHERE_SIZE = 8;
//    protected final MongoDatabase mongoDatabase;
//    private final String providerName;
//    private final StorageConfigurationProperties properties;
//    private final Set<MockCategoryType> entryPointTypes;
//
//    public TradeMockerMongoRepositoryProvider(MongoDatabase mongoDatabase,
//                                             StorageConfigurationProperties properties,
//                                             Set<MockCategoryType> entryPointTypes) {
//        this(ProviderNames.DEFAULT, mongoDatabase, properties, entryPointTypes);
//    }
//
//    public TradeMockerMongoRepositoryProvider(String providerName,
//                                             MongoDatabase mongoDatabase,
//                                             StorageConfigurationProperties properties,
//                                             Set<MockCategoryType> entryPointTypes) {
//        this.properties = properties;
//        this.targetClassType = TradeMocker.class;
//        this.mongoDatabase = mongoDatabase;
//        this.providerName = providerName;
//        this.entryPointTypes = entryPointTypes;
//    }
//
//    MongoCollection<TradeMocker> createOrGetCollection(MockCategoryType category) {
//        String categoryName = this.getProviderName() + category.getName() + COLLECTION_PREFIX;
//        return mongoDatabase.getCollection(categoryName, this.targetClassType);
//    }
//
//    @Override
//    //todo
//    public Iterable<TradeMocker> queryRecordList(MockCategoryType category, String recordId) {
//        MongoCollection<TradeMocker> collectionSource = createOrGetCollection(category);
//        Bson recordIdFilter = buildRecordIdFilter(category, recordId);
//        if (Objects.equals(this.providerName, ProviderNames.DEFAULT)) {
//            updateExpirationTime(Collections.singletonList(recordIdFilter), collectionSource);
//        }
//        Iterable<TradeMocker> iterable = collectionSource
//                .find(recordIdFilter)
//                .sort(CREATE_TIME_ASCENDING_SORT);
//        return new AttachmentCategoryIterable(category, iterable);
//    }
//
//    @Override
//    public TradeMocker findEntryFromAllType(String recordId) {
//        return null;
//    }
//
//    @Override
//    public TradeMocker queryRecord(Mocker requestType) {
//        MockCategoryType categoryType = requestType.getCategoryType();
//        MongoCollection<TradeMocker> collectionSource = createOrGetCollection(categoryType);
//        TradeMocker item = collectionSource
//                .find(Filters.and(buildRecordFilters(categoryType, requestType)))
//                .sort(CREATE_TIME_DESCENDING_SORT)
//                .limit(DEFAULT_MIN_LIMIT_SIZE)
//                .first();
//        return AttachmentCategoryIterable.attach(categoryType, item);
//    }
//
//    @Override
//    public String getProviderName() {
//        return null;
//    }
//
//    @Override
//    public boolean save(TradeMocker value) {
//        return false;
//    }
//
//    @Override
//    public boolean saveList(List<TradeMocker> valueList) {
//        return false;
//    }
//
//    @Override
//    public long removeBy(MockCategoryType categoryType, String recordId) {
//        return 0;
//    }
//
//    @Override
//    public boolean update(TradeMocker value) {
//        return false;
//    }
//
//    @Override
//    public long removeByAppId(MockCategoryType categoryType, String appId) {
//        return 0;
//    }
//
//    @Override
//    public long removeByOperationNameAndAppId(MockCategoryType categoryType, String operationName, String appId) {
//        return 0;
//    }
//
//    private Bson buildRecordIdFilter(MockCategoryType categoryType, String value) {
//        if (categoryType.isEntryPoint()) {
//            return Filters.eq(PRIMARY_KEY_COLUMN_NAME, value);
//        }
//        return Filters.eq(RECORD_ID_COLUMN_NAME, value);
//    }
//
//    private void updateExpirationTime(List<Bson> bsons, MongoCollection<TradeMocker> collectionSource) {
//        long currentTimeMillis = System.currentTimeMillis();
//        long allowedLastMills = TimeUtils.getTodayFirstMills() + properties.getAllowReRunDays() * TimeUtils.ONE_DAY;
//        Bson filters = Filters.and(Filters.and(bsons),
//                Filters.or(Filters.lt(EXPIRATION_TIME_COLUMN_NAME, new Date(allowedLastMills)),
//                        Filters.exists(EXPIRATION_TIME_COLUMN_NAME, false)));
//        // Add different minutes to avoid the same expiration time
//        Bson update = Updates.combine(
//                Updates.set(EXPIRATION_TIME_COLUMN_NAME,
//                        new Date(allowedLastMills + currentTimeMillis % TimeUtils.ONE_HOUR)),
//                Updates.set(UPDATE_TIME_COLUMN_NAME, new Date(currentTimeMillis)));
//        collectionSource.updateMany(filters, update);
//    }
//
//    private List<Bson> buildRecordFilters(MockCategoryType categoryType, @NotNull Mocker mocker) {
//        List<Bson> filters = this.buildAppIdWithOperationFilters(mocker.getAppId(),
//                mocker.getOperationName());
//        Bson recordIdFilter = buildRecordIdFilter(categoryType, mocker.getRecordId());
//        filters.add(recordIdFilter);
//        Bson env = Filters.eq(ENV_COLUMN_NAME, mocker.getRecordEnvironment());
//        filters.add(env);
//        return filters;
//    }
//    private List<Bson> buildAppIdWithOperationFilters(String appId, String operationName) {
//        Bson app = Filters.eq(APP_ID_COLUMN_NAME, appId);
//        final List<Bson> bsonList = new ArrayList<>(DEFAULT_BSON_WHERE_SIZE);
//        bsonList.add(app);
//        if (operationName != null) {
//            bsonList.add(Filters.eq(OPERATION_COLUMN_NAME, operationName));
//        }
//        return bsonList;
//    }
//
//    private static final class AttachmentCategoryIterable implements Iterable<TradeMocker>, Iterator<TradeMocker> {
//        private final MockCategoryType categoryType;
//        private final Iterator<TradeMocker> source;
//
//        private AttachmentCategoryIterable(MockCategoryType categoryType, Iterable<TradeMocker> source) {
//            this.categoryType = categoryType;
//            this.source = source.iterator();
//        }
//
//        @Override
//        public Iterator<TradeMocker> iterator() {
//            return this;
//        }
//
//        @Override
//        public boolean hasNext() {
//            return source.hasNext();
//        }
//
//        @Override
//        public TradeMocker next() {
//            return attach(categoryType, source.next());
//        }
//
//        private static TradeMocker attach(MockCategoryType categoryType, TradeMocker item) {
//            if (item != null) {
//                item.setCategoryType(categoryType);
//                if (categoryType.isEntryPoint()) {
//                    item.setRecordId(item.getId());
//                }
//            }
//            return item;
//        }
//    }
//}
