package dh.mygrades.database.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "UNIVERSITY".
*/
public class UniversityDao extends AbstractDao<University, Long> {

    public static final String TABLENAME = "UNIVERSITY";

    /**
     * Properties of entity University.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property UniversityId = new Property(0, Long.class, "universityId", true, "UNIVERSITY_ID");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Published = new Property(2, Boolean.class, "published", false, "PUBLISHED");
        public final static Property UpdatedAtServer = new Property(3, String.class, "updatedAtServer", false, "UPDATED_AT_SERVER");
    };

    private DaoSession daoSession;


    public UniversityDao(DaoConfig config) {
        super(config);
    }
    
    public UniversityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"UNIVERSITY\" (" + //
                "\"UNIVERSITY_ID\" INTEGER PRIMARY KEY ," + // 0: universityId
                "\"NAME\" TEXT NOT NULL ," + // 1: name
                "\"PUBLISHED\" INTEGER," + // 2: published
                "\"UPDATED_AT_SERVER\" TEXT);"); // 3: updatedAtServer
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"UNIVERSITY\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, University entity) {
        stmt.clearBindings();
 
        Long universityId = entity.getUniversityId();
        if (universityId != null) {
            stmt.bindLong(1, universityId);
        }
        stmt.bindString(2, entity.getName());
 
        Boolean published = entity.getPublished();
        if (published != null) {
            stmt.bindLong(3, published ? 1L: 0L);
        }
 
        String updatedAtServer = entity.getUpdatedAtServer();
        if (updatedAtServer != null) {
            stmt.bindString(4, updatedAtServer);
        }
    }

    @Override
    protected void attachEntity(University entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public University readEntity(Cursor cursor, int offset) {
        University entity = new University( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // universityId
            cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getShort(offset + 2) != 0, // published
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // updatedAtServer
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, University entity, int offset) {
        entity.setUniversityId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.getString(offset + 1));
        entity.setPublished(cursor.isNull(offset + 2) ? null : cursor.getShort(offset + 2) != 0);
        entity.setUpdatedAtServer(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(University entity, long rowId) {
        entity.setUniversityId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(University entity) {
        if(entity != null) {
            return entity.getUniversityId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}