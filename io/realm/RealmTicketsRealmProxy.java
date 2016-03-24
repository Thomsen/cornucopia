package io.realm;


import android.util.JsonReader;
import android.util.JsonToken;
import com.cornucopia.storage.realm.RealmTickets;
import io.realm.RealmFieldType;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.ImplicitTransaction;
import io.realm.internal.LinkView;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Table;
import io.realm.internal.TableOrView;
import io.realm.internal.android.JsonUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RealmTicketsRealmProxy extends RealmTickets
    implements RealmObjectProxy, RealmTicketsRealmProxyInterface {

    static final class RealmTicketsColumnInfo extends ColumnInfo {

        public final long nameIndex;
        public final long completeIndex;
        public final long idIndex;

        RealmTicketsColumnInfo(String path, Table table) {
            final Map<String, Long> indicesMap = new HashMap<String, Long>(3);
            this.nameIndex = getValidColumnIndex(path, table, "RealmTickets", "name");
            indicesMap.put("name", this.nameIndex);

            this.completeIndex = getValidColumnIndex(path, table, "RealmTickets", "complete");
            indicesMap.put("complete", this.completeIndex);

            this.idIndex = getValidColumnIndex(path, table, "RealmTickets", "id");
            indicesMap.put("id", this.idIndex);

            setIndicesMap(indicesMap);
        }
    }

    private final RealmTicketsColumnInfo columnInfo;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("name");
        fieldNames.add("complete");
        fieldNames.add("id");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    RealmTicketsRealmProxy(ColumnInfo columnInfo) {
        this.columnInfo = (RealmTicketsColumnInfo) columnInfo;
    }

    @SuppressWarnings("cast")
    public String realmGet$name() {
        ((RealmObject) this).realm.checkIfValid();
        return (java.lang.String) ((RealmObject) this).row.getString(columnInfo.nameIndex);
    }

    public void realmSet$name(String value) {
        ((RealmObject) this).realm.checkIfValid();
        if (value == null) {
            throw new IllegalArgumentException("Trying to set non-nullable field name to null.");
        }
        ((RealmObject) this).row.setString(columnInfo.nameIndex, value);
    }

    @SuppressWarnings("cast")
    public boolean realmGet$complete() {
        ((RealmObject) this).realm.checkIfValid();
        return (boolean) ((RealmObject) this).row.getBoolean(columnInfo.completeIndex);
    }

    public void realmSet$complete(boolean value) {
        ((RealmObject) this).realm.checkIfValid();
        ((RealmObject) this).row.setBoolean(columnInfo.completeIndex, value);
    }

    @SuppressWarnings("cast")
    public long realmGet$id() {
        ((RealmObject) this).realm.checkIfValid();
        return (long) ((RealmObject) this).row.getLong(columnInfo.idIndex);
    }

    public void realmSet$id(long value) {
        ((RealmObject) this).realm.checkIfValid();
        ((RealmObject) this).row.setLong(columnInfo.idIndex, value);
    }

    public static Table initTable(ImplicitTransaction transaction) {
        if (!transaction.hasTable("class_RealmTickets")) {
            Table table = transaction.getTable("class_RealmTickets");
            table.addColumn(RealmFieldType.STRING, "name", Table.NOT_NULLABLE);
            table.addColumn(RealmFieldType.BOOLEAN, "complete", Table.NOT_NULLABLE);
            table.addColumn(RealmFieldType.INTEGER, "id", Table.NOT_NULLABLE);
            table.setPrimaryKey("");
            return table;
        }
        return transaction.getTable("class_RealmTickets");
    }

    public static RealmTicketsColumnInfo validateTable(ImplicitTransaction transaction) {
        if (transaction.hasTable("class_RealmTickets")) {
            Table table = transaction.getTable("class_RealmTickets");
            if (table.getColumnCount() != 3) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field count does not match - expected 3 but was " + table.getColumnCount());
            }
            Map<String, RealmFieldType> columnTypes = new HashMap<String, RealmFieldType>();
            for (long i = 0; i < 3; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }

            final RealmTicketsColumnInfo columnInfo = new RealmTicketsColumnInfo(transaction.getPath(), table);

            if (!columnTypes.containsKey("name")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'name' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("name") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'name' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.nameIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'name' does support null values in the existing Realm file. Remove @Required or @PrimaryKey from field 'name' or migrate using io.realm.internal.Table.convertColumnToNotNullable().");
            }
            if (!columnTypes.containsKey("complete")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'complete' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("complete") != RealmFieldType.BOOLEAN) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'boolean' for field 'complete' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.completeIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'complete' does support null values in the existing Realm file. Use corresponding boxed type for field 'complete' or migrate using io.realm.internal.Table.convertColumnToNotNullable().");
            }
            if (!columnTypes.containsKey("id")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'id' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("id") != RealmFieldType.INTEGER) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'long' for field 'id' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.idIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'id' does support null values in the existing Realm file. Use corresponding boxed type for field 'id' or migrate using io.realm.internal.Table.convertColumnToNotNullable().");
            }
            return columnInfo;
        } else {
            throw new RealmMigrationNeededException(transaction.getPath(), "The RealmTickets class is missing from the schema for this Realm.");
        }
    }

    public static String getTableName() {
        return "class_RealmTickets";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static RealmTickets createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        RealmTickets obj = realm.createObject(RealmTickets.class);
        if (json.has("name")) {
            if (json.isNull("name")) {
                ((RealmTicketsRealmProxyInterface) obj).realmSet$name(null);
            } else {
                ((RealmTicketsRealmProxyInterface) obj).realmSet$name((String) json.getString("name"));
            }
        }
        if (json.has("complete")) {
            if (json.isNull("complete")) {
                throw new IllegalArgumentException("Trying to set non-nullable field complete to null.");
            } else {
                ((RealmTicketsRealmProxyInterface) obj).realmSet$complete((boolean) json.getBoolean("complete"));
            }
        }
        if (json.has("id")) {
            if (json.isNull("id")) {
                throw new IllegalArgumentException("Trying to set non-nullable field id to null.");
            } else {
                ((RealmTicketsRealmProxyInterface) obj).realmSet$id((long) json.getLong("id"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    public static RealmTickets createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        RealmTickets obj = realm.createObject(RealmTickets.class);
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("name")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((RealmTicketsRealmProxyInterface) obj).realmSet$name(null);
                } else {
                    ((RealmTicketsRealmProxyInterface) obj).realmSet$name((String) reader.nextString());
                }
            } else if (name.equals("complete")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field complete to null.");
                } else {
                    ((RealmTicketsRealmProxyInterface) obj).realmSet$complete((boolean) reader.nextBoolean());
                }
            } else if (name.equals("id")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field id to null.");
                } else {
                    ((RealmTicketsRealmProxyInterface) obj).realmSet$id((long) reader.nextLong());
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return obj;
    }

    public static RealmTickets copyOrUpdate(Realm realm, RealmTickets object, boolean update, Map<RealmObject,RealmObjectProxy> cache) {
        if (((RealmObject) object).realm != null && ((RealmObject) object).realm.getPath().equals(realm.getPath())) {
            return object;
        }
        return copy(realm, object, update, cache);
    }

    public static RealmTickets copy(Realm realm, RealmTickets newObject, boolean update, Map<RealmObject,RealmObjectProxy> cache) {
        RealmTickets realmObject = realm.createObject(RealmTickets.class);
        cache.put(newObject, (RealmObjectProxy) realmObject);
        ((RealmTicketsRealmProxyInterface) realmObject).realmSet$name(((RealmTicketsRealmProxyInterface) newObject).realmGet$name());
        ((RealmTicketsRealmProxyInterface) realmObject).realmSet$complete(((RealmTicketsRealmProxyInterface) newObject).realmGet$complete());
        ((RealmTicketsRealmProxyInterface) realmObject).realmSet$id(((RealmTicketsRealmProxyInterface) newObject).realmGet$id());
        return realmObject;
    }

    public static RealmTickets createDetachedCopy(RealmTickets realmObject, int currentDepth, int maxDepth, Map<RealmObject, CacheData<RealmObject>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmTickets> cachedObject = (CacheData) cache.get(realmObject);
        RealmTickets standaloneObject;
        if (cachedObject != null) {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return cachedObject.object;
            } else {
                standaloneObject = cachedObject.object;
                cachedObject.minDepth = currentDepth;
            }
        } else {
            standaloneObject = new RealmTickets();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmObject>(currentDepth, standaloneObject));
        }
        ((RealmTicketsRealmProxyInterface) standaloneObject).realmSet$name(((RealmTicketsRealmProxyInterface) realmObject).realmGet$name());
        ((RealmTicketsRealmProxyInterface) standaloneObject).realmSet$complete(((RealmTicketsRealmProxyInterface) realmObject).realmGet$complete());
        ((RealmTicketsRealmProxyInterface) standaloneObject).realmSet$id(((RealmTicketsRealmProxyInterface) realmObject).realmGet$id());
        return standaloneObject;
    }

    @Override
    public String toString() {
        if (!isValid()) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmTickets = [");
        stringBuilder.append("{name:");
        stringBuilder.append(realmGet$name());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{complete:");
        stringBuilder.append(realmGet$complete());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{id:");
        stringBuilder.append(realmGet$id());
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        String realmName = ((RealmObject) this).realm.getPath();
        String tableName = ((RealmObject) this).row.getTable().getName();
        long rowIndex = ((RealmObject) this).row.getIndex();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (rowIndex ^ (rowIndex >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RealmTicketsRealmProxy aRealmTickets = (RealmTicketsRealmProxy)o;

        String path = ((RealmObject) this).realm.getPath();
        String otherPath = ((RealmObject) aRealmTickets).realm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;;

        String tableName = ((RealmObject) this).row.getTable().getName();
        String otherTableName = ((RealmObject) aRealmTickets).row.getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (((RealmObject) this).row.getIndex() != ((RealmObject) aRealmTickets).row.getIndex()) return false;

        return true;
    }

}
