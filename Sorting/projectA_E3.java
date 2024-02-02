import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;
import java.io.PrintWriter;
import java.util.Stack;
import java.util.LinkedList;
import java.util.PriorityQueue;

//----------------------------------------------------------------------------------
//---------------- RedBlackTree Class and necessary basics -------------------------
//----------------------------------------------------------------------------------
class RedBlackTree {
    public Node root;// root node

    public RedBlackTree() {
        super();
        root = null;
    }

    // creating subclass
    class Node {
        int data;
        Node left;
        Node right;
        char colour;
        Node parent;

        Node(int data) {
            super();
            this.data = data; // only including data. not key
            this.left = null; // left subtree
            this.right = null; // right subtree
            this.colour = 'R'; // colour . either 'R' or 'B'
            this.parent = null; // required at time of rechecking.
        }
    }

    // this function performs left rotation
    Node rotateLeft(Node node) {
        Node x = node.right;
        Node y = x.left;
        x.left = node;
        node.right = y;
        node.parent = x; // parent resetting is also important.
        if (y != null)
            y.parent = node;
        return (x);
    }

    // this function performs right rotation
    Node rotateRight(Node node) {
        Node x = node.left;
        Node y = x.right;
        x.right = node;
        node.left = y;
        node.parent = x;
        if (y != null)
            y.parent = node;
        return (x);
    }

    boolean ll = false;
    boolean rr = false;
    boolean lr = false;
    boolean rl = false;

    Node insertHelp(Node root, int data) {
        // f is true when RED RED conflict is there.
        boolean f = false;

        // recursive calls to insert at proper position according to BST properties.
        if (root == null)
            return (new Node(data));
        else if (data < root.data) {
            root.left = insertHelp(root.left, data);
            root.left.parent = root;
            if (root != this.root) {
                if (root.colour == 'R' && root.left.colour == 'R')
                    f = true;
            }
        } else {
            root.right = insertHelp(root.right, data);
            root.right.parent = root;
            if (root != this.root) {
                if (root.colour == 'R' && root.right.colour == 'R')
                    f = true;
            }
            // at the same time of insertion, we are also assigning parent nodes
            // also we are checking for RED RED conflicts
        }

        // now lets rotate.
        if (this.ll) // for left rotate.
        {
            root = rotateLeft(root);
            root.colour = 'B';
            root.left.colour = 'R';
            this.ll = false;
        } else if (this.rr) // for right rotate
        {
            root = rotateRight(root);
            root.colour = 'B';
            root.right.colour = 'R';
            this.rr = false;
        } else if (this.rl) // for right and then left
        {
            root.right = rotateRight(root.right);
            root.right.parent = root;
            root = rotateLeft(root);
            root.colour = 'B';
            root.left.colour = 'R';

            this.rl = false;
        } else if (this.lr) // for left and then right.
        {
            root.left = rotateLeft(root.left);
            root.left.parent = root;
            root = rotateRight(root);
            root.colour = 'B';
            root.right.colour = 'R';
            this.lr = false;
        }
        // when rotation and recolouring is done flags are reset.
        // Now lets take care of RED RED conflict
        if (f) {
            if (root.parent.right == root) // to check which child is the current node of its parent
            {
                if (root.parent.left == null || root.parent.left.colour == 'B') // case when parent's sibling is black
                {// perform certaing rotation and recolouring. This will be done while
                 // backtracking. Hence setting up respective flags.
                    if (root.left != null && root.left.colour == 'R')
                        this.rl = true;
                    else if (root.right != null && root.right.colour == 'R')
                        this.ll = true;
                } else // case when parent's sibling is red
                {
                    root.parent.left.colour = 'B';
                    root.colour = 'B';
                    if (root.parent != this.root)
                        root.parent.colour = 'R';
                }
            } else {
                if (root.parent.right == null || root.parent.right.colour == 'B') {
                    if (root.left != null && root.left.colour == 'R')
                        this.rr = true;
                    else if (root.right != null && root.right.colour == 'R')
                        this.lr = true;
                } else {
                    root.parent.right.colour = 'B';
                    root.colour = 'B';
                    if (root.parent != this.root)
                        root.parent.colour = 'R';
                }
            }
            f = false;
        }
        return (root);
    }

    // function to insert data into tree.
    public void insert(int data) {
        if (this.root == null) {
            this.root = new Node(data);
            this.root.colour = 'B';
        } else
            this.root = insertHelp(this.root, data);
    }

    public int[] inorder(Node node) {
        int[] nums = {};
        if (node == null)
            return nums;

        // first array
        int[] a = inorder(node.left);

        // second array
        int[] b = inorder(node.right);

        // determines length of firstArray
        int a1 = a.length;

        // determines length of secondArray
        int b1 = b.length;

        // resultant array size
        int c1 = a1 + b1 + 1;

        // create the resultant array
        int[] c = new int[c1];

        // copy array
        int index = 0;
        for (int num : a)
            c[index++] = num;
        c[index++] = node.data;
        for (int num : b)
            c[index++] = num;

        return c;
    }

    public int[] inorder() {
        int[] arr = inorder(root);

        return arr;
    }

}

// --------------------------------------------------------------------------------------
// ---------------- BinarySearchTree Class and necessary basics
// -------------------------
// --------------------------------------------------------------------------------------
class BinarySearchTree {

    class Node {
        int key;
        Node left, right;

        public Node(int item) {
            key = item;
            left = right = null;
        }
    }

    Node root;

    // Constructor
    BinarySearchTree() {
        root = null;
    }

    Node insert(Node node, int key) {
        // If the tree is empty, return a new node
        if (node == null) {
            node = new Node(key);
            return node;
        }

        // Otherwise, recur down the tree
        if (key < node.key)
            node.left = insert(node.left, key);
        else if (key > node.key)
            node.right = insert(node.right, key);

        // Return the (unchanged) node pointer
        return node;
    }

    void insert(int key) {
        if (root == null)
            root = insert(this.root, key);
        else
            insert(this.root, key);
    }

    public int[] inorder(Node node) {
        int[] nums = {};
        if (node == null)
            return nums;

        // first array
        int[] a = inorder(node.left);

        // second array
        int[] b = inorder(node.right);

        // determines length of firstArray
        int a1 = a.length;

        // determines length of secondArray
        int b1 = b.length;

        // resultant array size
        int c1 = a1 + b1 + 1;

        // create the resultant array
        int[] c = new int[c1];

        // copy array
        int index = 0;
        for (int num : a)
            c[index++] = num;
        c[index++] = node.key;
        for (int num : b)
            c[index++] = num;

        return c;
    }

    public int[] inorder() {
        int[] arr = inorder(root);

        return arr;
    }
}

public class projectA_E3 {

    public static void main(String[] args) {
        String input = "fileBegin.txt";
        String output = "fileResult.txt";
        try {
            // reading file
            File mydoc = new File(input);
            Scanner reader = new Scanner(mydoc);

            // reading type of data structure

            String dStructureType = reader.nextLine();

            // reading numbers
            int i = 0;
            int[] nums = new int[1000];

            // checking data structure type exist

            if (dStructureType.isEmpty()) {
                System.out.println("The type of data structure is not specified");
                reader.close();
                System.exit(0);
            } else {
                while (reader.hasNextInt()) {

                    nums[i++] = reader.nextInt();

                }
                nums = Arrays.copyOf(nums, i);
                // System.out.println(Arrays.toString(nums));
                reader.close();
            }
            // sorting with data structures
            if (dStructureType != null) {
                if (dStructureType.equals("stack")) {
                    sortWithStack(nums);
                } else if (dStructureType.equals("queue")) {
                    sortWithQueue(nums);
                } else if (dStructureType.equals("linkedlist")) {
                    LinkedList<Integer> sortedList = sortWithLinkedlist(nums);
                    // Update the original nums array with the sorted list
                    for (int x = 0; x < nums.length; x++) {
                        nums[x] = sortedList.get(x);
                    }

                } else if (dStructureType.equals("bst")) {
                    nums = sortWithBST(nums);
                } else if (dStructureType.equals("rbt")) {
                    nums = sortWithRBT(nums);
                } else if (dStructureType.equals("array")) {
                    sortWithArray(nums);
                } else if (dStructureType.equals("heap")) {
                    sortWithHaep(nums);

                } else {
                    System.out.println("Hmmmm..... \n something is went wrong!");
                    System.exit(0);
                }

                // create file and save datas in it
                PrintWriter printWriter = new PrintWriter(new File(output));
                printWriter.print("Sorted With: " + dStructureType);
                printWriter.print("\n");
                printWriter.print(Arrays.toString(nums));
                printWriter.close();
                System.out.println(Arrays.toString(nums));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // sorting methods functions
    // =====================================================
    // =====================================================
    public static void sortWithStack(int[] nums) {
        Stack<Integer> stack = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();

        // Push all elements of the array into the stack
        for (int num : nums) {
            stack.push(num);
        }

        // Continue until the original stack is empty
        while (!stack.isEmpty()) {
            int current = stack.pop();

            // Move elements from stack2 to stack
            while (!stack2.isEmpty() && stack2.peek() > current) {
                stack.push(stack2.pop());
            }

            // Push the current element onto stack2
            stack2.push(current);
        }

        // Copy the sorted elements back to the original array
        for (int i = nums.length - 1; i >= 0; i--) {
            nums[i] = stack2.pop();
        }
    }

    public static void sortWithQueue(int[] nums) {
        // Create a priority queue
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();

        // Enqueue each elements
        for (int num : nums) {
            priorityQueue.offer(num);
        }

        // Dequeue and update array
        for (int i = 0; i < nums.length; i++) {
            nums[i] = priorityQueue.poll();
        }
    }

    public static LinkedList<Integer> sortWithLinkedlist(int[] nums) {
        LinkedList<Integer> sortedList = new LinkedList<>();

        for (int num : nums) {
            insertInOrder(sortedList, num);
        }

        return sortedList;
    }

    public static void insertInOrder(LinkedList<Integer> list, int num) {
        // Find the position to insert the new element in sorted order
        int index = 0;
        while (index < list.size() && list.get(index) < num) {
            index++;
        }

        // Insert the new element at the found position
        list.add(index, num);
    }

    public static void sortWithArray(int[] nums) {
        // using bubble sort
        int n = nums.length;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (nums[j] > nums[j + 1]) {
                    // changing elements if firts one is bigger than second one
                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                }
            }
        }
    }

    public static void sortWithHaep(int[] nums) {
        int n = nums.length;

        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(nums, n, i);
        }

        // extract an element from the heap
        for (int i = n - 1; i >= 0; i--) {
            // Move current root to the end
            int temp = nums[0];
            nums[0] = nums[i];
            nums[i] = temp;

            // call max heapify on the reduced heap
            heapify(nums, i, 0);
        }
    }

    public static void heapify(int[] nums, int n, int i) {
        int largest = i; // Initialize largest as root
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        // If left child is larger than root
        if (left < n && nums[left] > nums[largest]) {
            largest = left;
        }

        // If right child is larger than largest so far
        if (right < n && nums[right] > nums[largest]) {
            largest = right;
        }

        // If largest is not root
        if (largest != i) {
            int swap = nums[i];
            nums[i] = nums[largest];
            nums[largest] = swap;

            heapify(nums, n, largest);
        }
    }

    // ---------------- for BST ---------
    public static int[] sortWithBST(int[] nums) {
        BinarySearchTree tree = new BinarySearchTree();
        for (int num : nums)
            tree.insert(num);
        return tree.inorder();
    }

    // ---------------- for RBT ---------
    public static int[] sortWithRBT(int[] nums) {
        RedBlackTree tree = new RedBlackTree();
        for (int num : nums)
            tree.insert(num);
        return tree.inorder();
    }

}
