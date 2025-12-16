package cache;

public class L4Cache implements Cache {
    private static final int SIZE = 4096;

    private final boolean[] valid = new boolean[SIZE];
    private final int[] tag = new int[SIZE];

    // (권장) 실험/보고서용 카운터
    private long hits = 0;
    private long misses = 0;

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

        boolean hit = valid[i] && tag[i] == t;
        if (hit) hits++;
        else misses++;

        return hit;
    }

    @Override
    public void insert(int address) {
        int i = index(address);
        tag[i] = getTag(address);
        valid[i] = true;
    }

    // (권장) 결과 출력용
    public long getHits() { return hits; }
    public long getMisses() { return misses; }

    // (선택) 디버깅용
    public String dumpAt(int addr) {
        int i = index(addr);
        return "L4{index=" + i + ", valid=" + valid[i] + ", tag=" + tag[i] + "}";
    }
}
