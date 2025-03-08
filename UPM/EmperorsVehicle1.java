import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class EmperorsVehicle1 {

    static int h, w;
    static char[][] map;
    static boolean[][] marked;
    static int[][] heapIndex;


    public static void main(String[] args) throws IOException {


        // read input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] tokens = br.readLine().split(" ");
        h = Integer.parseInt(tokens[0]);
        w = Integer.parseInt(tokens[1]);
        map = new char[h][];
        for (int i = 0; i < h; i++) {
            map[i] = br.readLine().toCharArray();
        }
        // end read input



        // squares on map using dp
        // using dp find of biggest square formed with [i][j] as bottom right corner
        int[][] dp = new int[h+2][w+2];
        int temp;

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                temp = Math.min(dp[i][j], Math.min(dp[i+1][j], dp[i][j+1]));
                dp[i+1][j+1] = (map[i][j] == '0' ? temp+1 : 0);
            }
        }

        // DEBUG
//        printMatrix(dp);
        // END DEBUG

        // end squares on map using dp

        // initialize marked[][]
        marked = new boolean[h+2][w+2];

        // start a DFS search along the map with maximum size
        // if you find paths with smaller size push them to stacks for smaller sizes
        // once you finish a search with a size not reaching bot-right corner
        // use a non-empty stack for a smaller size to continue DFS

        // start with the size of square at top-left or bot-right corner
        // whichever is smaller so you have as few size-downs as possible
        int size=1;
        while (dp[size][size] != 0 && dp[size+1][size+1] == dp[size][size] + 1) size++;
        if (dp[h][w] < size) size = dp[h][w];
        if (size == 0) {
            System.out.println(0);
            return;
        }
        // end start square size

        Stack<int[]>[] stacks = new Stack[size+1];
        for (int j = 0; j < stacks.length; j++) {
            stacks[j] = new Stack<int[]>();
            if(dp[j][j] == j) {
                stacks[j].push(new int[]{j, j});
                marked[j][j] = true;
            }
        }

        Stack<int[]> s = stacks[size];
        int x, y;
        int[] cord;
        while (size > 0){

            if(s.empty()){
                // explored everything on this size
                // try to explore with a smaller size

                while (size > 0 && stacks[size].empty()) size--;
                s = stacks[size];
//                System.out.println("size == " + size);
//                System.out.println("s.empty() == " + s.empty());
            }

            cord = s.pop();
            y = cord[0];
            x = cord[1];

            // DEBUG
//            System.out.println("Currently at [" + y + ", " + x + "]" + " with size == " + size);
            // END DEBUG

            // reached dest
            if (x == w && y == h){
                System.out.println(size);
                return;
            }// end reached dest


            // check left, up, right, down
            if (x - 1 > 0 && !marked[y][x-1] && dp[y][x-1] != 0){
                if (dp[y][x-1] >= size){
                    s.push(new int[]{y, x-1});
                }else {
                    stacks[dp[y][x-1]].push(new int[]{y, x-1});
                }
                marked[y][x-1] = true;
            }
            if (y - 1 > 0 && !marked[y-1][x] && dp[y-1][x] != 0){
                if (dp[y-1][x] >= size){
                    s.push(new int[]{y-1, x});
                }else {
                    stacks[dp[y-1][x]].push(new int[]{y-1, x});
                }
                marked[y-1][x] = true;
            }
            if (x + 1 <= w && !marked[y][x+1] && dp[y][x+1] != 0){
                if (dp[y][x+1] >= size){
                    s.push(new int[]{y, x+1});
                }else {
                    stacks[dp[y][x+1]].push(new int[]{y, x+1});
                }
                marked[y][x+1] = true;
            }
            if (y + 1 <= h && !marked[y+1][x] && dp[y+1][x] != 0){
                if (dp[y+1][x] >= size){
                    s.push(new int[]{y+1, x});
                }else {
                    stacks[dp[y+1][x]].push(new int[]{y+1, x});
                }
                marked[y+1][x] = true;
            }// end check left, up, right, down



        }
        System.out.println(0);
    }

    // print matrix
    public static void printMatrix(int[][] m){
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                System.out.printf("%d\t", m[i][j]);
            }
            System.out.println();
        }
    }


    // manhattan distance function
    public static int dist(int x1, int y1, int x2, int y2){
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }




    public static boolean checkRow(int x, int y, int size){
        if(x+size > w || x < 0) return false;
        for (int i = 0; i < size; i++) {
            if (map[y][x+i] == '1') return false;
        }
        return true;
    }
    public static boolean checkCol(int x, int y, int size){
        if(y+size > h || y < 0) return false;
        for (int i = 0; i < size; i++) {
            if (map[y+i][x] == '1') return false;
        }
        return true;
    }
}
