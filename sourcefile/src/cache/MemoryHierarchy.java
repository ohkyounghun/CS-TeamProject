package cache;

public class MemoryHierarchy {
    private final L1Cache l1 = new L1Cache();
    private final L2Cache l2 = new L2Cache();
    private final L3Cache2Way l3 = new L3Cache2Way();
    private final L4Cache l4 = new L4Cache();

    public long l1Hit=0, l1Miss=0;
    public long l2Hit=0, l2Miss=0;
    public long l3Hit=0, l3Miss=0;
    public long l4Hit=0, l4Miss=0;

    // 합의안 §4 흐름 그대로
    public void access(int address) {
        // L1
        if (l1.access(address)) { l1Hit++; return; }
        l1Miss++;

        // L2
        if (l2.access(address)) {
            l2Hit++;
            l1.insert(address);
            return;
        }
        l2Miss++;

        // L3
        if (l3.access(address)) {
            l3Hit++;
            l2.insert(address);
            l1.insert(address);
            return;
        }
        l3Miss++;

        // L4
        if (l4.access(address)) {
            l4Hit++;
        } else {
            l4Miss++;
            l4.insert(address); // L4 miss면 로드했다고 가정하고 적재
        }

        // inclusive promotion
        l3.insert(address);
        l2.insert(address);
        l1.insert(address);
    }

    public L4Cache getL4() {
        return l4;
    }
}
