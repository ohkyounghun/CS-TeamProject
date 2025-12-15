package cache;

public interface Cache {
    boolean access(int address); // hit이면 true
    void insert(int address);    // miss 후 적재
}