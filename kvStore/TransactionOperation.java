package kvStore;

public class TransactionOperation<K,V> {
    OperationType operationType;

    K key;

    V oldValue;

    public TransactionOperation(OperationType operationType, K key, V oldValue) {
        this.operationType = operationType;
        this.key = key;
        this.oldValue = oldValue;
    }
}
