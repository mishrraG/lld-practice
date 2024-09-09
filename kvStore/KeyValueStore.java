package kvStore;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class KeyValueStore<K, V> {
    private Map<K, V> store; //Main in-memory key-value store

    private Stack<TransactionOperation<K,V>> transactionLog;

    private boolean inTransaction = false; // Flag

    public KeyValueStore() {
        store = new HashMap<K, V>();
        transactionLog = new Stack<>();
    }

    public static void main(String[] args) {

        KeyValueStore<String, String> kvStore = new KeyValueStore<>();

        kvStore.put("key1", "value1");

        kvStore.beginTransaction();


        kvStore.put("key2", "value2");
        kvStore.put("key3", "value3");
        kvStore.put("key4", "value4");


        kvStore.commit();

        kvStore.get("key4");
        kvStore.get("key2");
        kvStore.get("key3");

        kvStore.beginTransaction();

        kvStore.put("key5", "old");
        kvStore.put("key6", "value2");
        kvStore.put("key7", "value2");
        kvStore.delete("key5");
        kvStore.put("key5", "new");


        kvStore.commit();


        kvStore.get("key5");
        kvStore.get("key6");
        kvStore.get("key7");

    }

    //Put method to add or update a key-value pair
    public void put(K key, V value) {
        if(inTransaction) {
            //Log the currentState of key before updating it

            V oldValue = store.get(key);

            transactionLog.push(new TransactionOperation<K,V>(OperationType.PUT, key, oldValue));
        }
        store.put(key, value);
        System.out.println("Added key: " + key + " value: " + value);

    }

    //Get method to fetch value given a key
    public V get(K key) {
        V value = store.get(key);
        if (value == null) {
            System.out.println("Key not found: " + key);
        } else {
            System.out.println("Key: " + key + " value: " + value);
        }
        return value;
    }

    //Delete given value
    public void delete(K key) {
        if(inTransaction) {
            V oldValue = store.get(key);

            if(oldValue == null) {
                System.out.println("Key not found: " + key);
                return;
            }

            transactionLog.push(new TransactionOperation<>(OperationType.DELETE, key, oldValue));
        }
            V removedValue = store.remove(key);
            if (removedValue != null) {
                System.out.println("Deleted key: " + key + " value: " + removedValue);
            } else {
                System.out.println("Key not found: " + key);
            }
    }


    public void beginTransaction() {
        if(inTransaction) {
            System.out.println("Transaction is already beginning");
        } else
        {
            inTransaction = true;

            transactionLog.clear();

            System.out.println("Begin transaction");
        }
    }

    public void commit(){
        if(!inTransaction)
        {
            System.out.println("No transaction found");
        }
        else{
            transactionLog.clear();
            inTransaction = false;
            System.out.println("Transaction commited");
        }
    }

    public void rollback(){
        if(!inTransaction)
        {
            System.out.println("No transaction found");
        }
        else{
            while(!transactionLog.empty()){
                TransactionOperation<K,V> operation = transactionLog.pop();

                if(operation.operationType == OperationType.PUT){
                    if(operation.oldValue == null){
                        store.remove(operation.key);
                        System.out.println("Removed key: " + operation.key);
                    }
                    else{
                        store.put(operation.key, operation.oldValue); //Restores the key to oldvalue
                    }
                } else if (operation.operationType == OperationType.DELETE) {
                    store.put(operation.key, operation.oldValue); // Restoring by adding the deleted key
                }
            }

            inTransaction = false;

            System.out.println("Transaction rolled back");
        }
    }
}