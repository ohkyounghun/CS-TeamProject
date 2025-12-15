package cache;

public class L3Cache2Way implements Cache {

    private static final int TOTAL_SIZE = 256;
    private static final int WAYS = 2;
    private static final int SETS = TOTAL_SIZE / WAYS;

    private boolean[][] valid = new boolean[SETS][WAYS];
    private int[][] tag = new int[SETS][WAYS];
    private int[] lruVictim = new int[SETS];

    private int getSetIndex(int address) {
        return address % SETS;
    }

    private int getTag(int address) {
        return address / SETS;
    }

    @Override
    public boolean access(int address) {
        int set = getSetIndex(address);
        int t = getTag(address);

        if (valid[set][0] && tag[set][0] == t) {
            lruVictim[set] = 1;
            return true;
        }

        if (valid[set][1] && tag[set][1] == t) {
            lruVictim[set] = 0;
            return true;
        }

        return false;
    }

    @Override
    public void insert(int address) {
        int set = getSetIndex(address);
        int t = getTag(address);

        // 이미 있으면 LRU만 갱신
        if (valid[set][0] && tag[set][0] == t) {
            lruVictim[set] = 1;
            return;
        }
        if (valid[set][1] && tag[set][1] == t) {
            lruVictim[set] = 0;
            return;
        }

        if (!valid[set][0]) {
            valid[set][0] = true;
            tag[set][0] = t;
            lruVictim[set] = 1;
            return;
        }

        if (!valid[set][1]) {
            valid[set][1] = true;
            tag[set][1] = t;
            lruVictim[set] = 0;
            return;
        }

        int victim = lruVictim[set];
        valid[set][victim] = true;
        tag[set][victim] = t;
        lruVictim[set] = 1 - victim;
    }
}