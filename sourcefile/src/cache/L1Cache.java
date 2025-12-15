package cache;

public class L1Cache implements Cache {
    private static final int SIZE = 1;

    private final boolean[] valid = new boolean[SIZE];
    private final int[] tag = new int[SIZE];

    private int index(int addr) {
        return addr % SIZE; // 항상 0
    }

    private int getTag(int addr) {
        return addr / SIZE; // addr 그대로
    }

    @Override
    public boolean access(int address) {
        int i = index(address);
        int t = getTag(address);
        return valid[i] && tag[i] == t;
    }

    @Override
    public void insert(int address) {
        int i = index(address);
        tag[i] = getTag(address);
        valid[i] = true;
    }

    // (선택) 디버깅용
    public String dump() {
        return "L1{valid=" + valid[0] + ", tag=" + tag[0] + "}";
    }
}
