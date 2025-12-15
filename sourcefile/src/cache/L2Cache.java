package cache;

public class L2Cache implements Cache {
    private static final int SIZE = 16;

    private final boolean[] valid = new boolean[SIZE];
    private final int[] tag = new int[SIZE];

    private int index(int addr) {
        return addr % SIZE;
    }

    private int getTag(int addr) {
        return addr / SIZE;
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

    // (선택) 디버깅용: 특정 index 상태 확인
    public String dumpLine(int index) {
        return "L2[" + index + "]{valid=" + valid[index] + ", tag=" + tag[index] + "}";
    }
}
