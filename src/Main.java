import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        String folderPath = "C:\\Users\\Vlad\\Desktop\\Java";
        File file = new File(folderPath);

        FolderSizeCalculator calculator = new FolderSizeCalculator(file);
        ForkJoinPool pool = new ForkJoinPool();
        long size = pool.invoke(calculator);
        System.out.println(size);
    }

    public static long getFolderSize(File folder) {
        if (folder.isFile()) {
            return folder.length();
        }

        long sum = 0;
        File[] files = folder.listFiles();
        for (File file : files) {
            sum += getFolderSize(file);
        }
        return sum;
    }

    public static String getHumanReadableSize(long size) {
        return "";
    }

    public static long getSizeFromHumanReadable(String size)
    {
        HashMap<Character, Integer> char2multiplier = getMultipliers();
        char sizeFactor = size
                .replaceAll("[0-9\\s+]+", "")
                .charAt(0);
        int multiplier = char2multiplier.get(sizeFactor);
        long length = multiplier * Long.valueOf(
                size.replaceAll("[^0-9]", "")
        );
        return length;
    }

    private static HashMap<Character, Integer> getMultipliers()
    {
        char[] multipliers = {'B', 'K', 'M', 'G', 'T'};
        HashMap<Character, Integer> char2multiplier = new HashMap<>();
        for(int i = 0; i < multipliers.length; i++)
        {
            char2multiplier.put(
                    multipliers[i],
                    (int) Math.pow(1024, i)
            );
        }
        return char2multiplier;
    }
}