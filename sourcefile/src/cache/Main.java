package cache;
import java.io.BufferedReader;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage: java cache.Main <trace_file_path>");
            return;
        }

        String tracePath = args[0];
        MemoryHierarchy mh = new MemoryHierarchy();

        long total = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(tracePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                int addr = Integer.parseInt(line);
                mh.access(addr);
                total++;
            }
        }

        System.out.println("=== Results ===");
        System.out.println("Trace: " + tracePath);
        System.out.println("Total access: " + total);

        System.out.printf("L1 hit=%d miss=%d hitRatio=%.4f%n",
                mh.l1Hit, mh.l1Miss, (double) mh.l1Hit / total);
        System.out.printf("L2 hit=%d miss=%d hitRatio=%.4f%n",
                mh.l2Hit, mh.l2Miss, (double) mh.l2Hit / total);
        System.out.printf("L3 hit=%d miss=%d hitRatio=%.4f%n",
                mh.l3Hit, mh.l3Miss, (double) mh.l3Hit / total);
        System.out.printf("L4 hit=%d miss=%d hitRatio=%.4f%n",
                mh.l4Hit, mh.l4Miss, (double) mh.l4Hit / total);

        System.out.printf("Main memory access ratio (L4 miss / total)=%.4f%n",
                (double) mh.l4Miss / total);

        // L4 내부 카운터랑 일치하는지 확인(정상 검증용)
        System.out.println("L4 internal hits=" + mh.getL4().getHits()
                + ", misses=" + mh.getL4().getMisses());
    }
}
