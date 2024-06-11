import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

class RandomizedBST implements TaxEvasionInterface {
    private class TreeNode {
        private LargeDepositor item;
        private TreeNode left; // Pointer to left subtree
        private TreeNode right; // Pointer to right subtree

        int N; // Number of nodes in the subtree rooted at this TreeNode
    
        // TreeNode constructor accepting data
        public TreeNode(LargeDepositor item) {
            this.item = item;
            this.N = 1;
        }
    }
    
    private TreeNode root; // Root of the BST
    private StringDoubleEndedQueue<LargeDepositor> topDepositorsQueue;

    // Initialize the tree
    public RandomizedBST() {
        topDepositorsQueue = new StringDoubleEndedQueueImpl<>();
    }

    // Insert new node to the tree
    @Override
    public void insert(LargeDepositor item) {
        root = insertAsRoot(item, root);
    }


    /**
     * Inserts a new node into the BST with the specified item as the root.
     *
     * @param item The LargeDepositor item to be inserted.
     * @param node The current TreeNode being considered for insertion.
     * @return The updated TreeNode after insertion.
     */
    private TreeNode insertAsRoot(LargeDepositor item, TreeNode node) {
        // If the current TreeNode is null, create a new TreeNode with the item as the root
        if (node == null) {
            return new TreeNode(item);
        }

        node.N++;

        // Generate a random number between 0 and the size of the current subtree plus 1
        // This determines whether the new item will become the root or be inserted further down
        if (Math.random() * (node.N + 1) < 1.0) {
            // If the random number falls within the size of the subtree, insert the new item as the root
            return insertAtRoot(item, node);
        } else if (item.key() < node.item.key()) {
            // If the item's key is less than the current node's key, recursively insert it into the left subtree
            node.left = insertAsRoot(item, node.left);
        } else {
            // Otherwise, recursively insert it into the right subtree
            node.right = insertAsRoot(item, node.right);
        }

        // Return the updated TreeNode after insertion
        return node;
    }


    /**
     * Helper method to insert a new node as root after a rotation.
     *
     * @param item The LargeDepositor item to be inserted.
     * @param node The current TreeNode being considered for insertion.
     * @return The updated TreeNode after insertion.
     */
    private TreeNode insertAtRoot(LargeDepositor item, TreeNode node) {

        if (node == null) {
            return new TreeNode(item);
        }

        // Calculate the comparison result between the item's key and the current node's key
        int cmp = item.key() - node.item.key();

        if (cmp < 0) {
            // If the item's key is less than the current node's key, insert it into the left subtree
            // then perform a right rotation to balance the tree
            node.left = insertAtRoot(item, node.left);
            node = rotateRight(node);
        } else if (cmp > 0) {
            // If the item's key is greater than the current node's key, insert it into the right subtree
            // then perform a left rotation to balance the tree
            node.right = insertAtRoot(item, node.right);
            node = rotateLeft(node);
        }

        // Return the updated TreeNode after insertion
        return node;
    }


    // Left rotation of a node
    private TreeNode rotateLeft(TreeNode node) {
        TreeNode x = node.right;
        node.right = x.left;
        x.left = node;
        x.N = node.N;
        node.N = 1 + size(node.left) + size(node.right);
        return x;
    }

    // Right rotation of a node
    private TreeNode rotateRight(TreeNode node) {
        TreeNode x = node.left; 
        node.left = x.right;
        x.right = node;
        x.N = node.N;
        node.N = 1 + size(node.left) + size(node.right);
        return x;
    }

    private int size(TreeNode x) {
        if (x == null) {
            return 0;
        }

        return x.N;
    }

    // Load data from file to the tree
    @Override
    public void load(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+");
                int AFM = Integer.parseInt(parts[0]);
                String firstName = parts[1];
                String lastName = parts[2];
                double savings = Double.parseDouble(parts[3]);
                double taxedIncome = Double.parseDouble(parts[4]);
                LargeDepositor depositor = new LargeDepositor(AFM, firstName, lastName, savings, taxedIncome);
                insert(depositor);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Update savings of an existing depositor
    @Override
    public void updateSavings(int AFM, double savings) {
        root = updateSavings(AFM, savings, root);
    }

    /**
     * Updates the savings of a depositor with the specified AFM, if found in the binary search tree.
     * If the depositor is not found, prints a message indicating the absence of the depositor with the given AFM.
     * 
     * @param AFM The AFM of the depositor whose savings are to be updated.
     * @param savings The new savings amount to be updated for the depositor.
     * @param node The current TreeNode being considered for updating savings.
     * @return The updated TreeNode after updating the savings, or null if the depositor with the specified AFM is not found.
     */
    private TreeNode updateSavings(int AFM, double savings, TreeNode node) {
        if (node == null) {
            System.out.println("Depositor with AFM " + AFM + " not found");
            return null;
        }

        // Calculate the comparison result between the specified AFM and the AFM of the current node's depositor
        int cmp = AFM - node.item.key();

        // If the specified AFM is less than the current node's depositor's AFM, recursively update savings in the left subtree
        if (cmp < 0) {
            node.left = updateSavings(AFM, savings, node.left);
        } 
        // If the specified AFM is greater than the current node's depositor's AFM, recursively update savings in the right subtree
        else if (cmp > 0) {
            node.right = updateSavings(AFM, savings, node.right);
        } 
        // If the specified AFM matches the current node's depositor's AFM, update the savings amount for the depositor
        else {
            node.item = new LargeDepositor(AFM, node.item.firstName, node.item.lastName, savings, node.item.taxedIncome);
        }

        // Return the updated TreeNode after updating the savings, or null if the depositor is not found
        return node;
    }


    // Search for a depositor by AFM
    @Override
    public LargeDepositor searchByAFM(int AFM) {
        return searchByAFM(AFM, root);
    }

    /**
     * Helper method to search for a depositor by their AFM in the binary search tree.
     * 
     * @param AFM The AFM of the depositor to search for.
     * @param node The current TreeNode being considered for searching.
     * @return The LargeDepositor object corresponding to the specified AFM, or null if not found.
     */
    private LargeDepositor searchByAFM(int AFM, TreeNode node) {
        if (node == null) {
            System.out.println("Depositor with AFM " + AFM + " not found.");
            return null;
        }

        // Calculate the comparison result between the specified AFM and the AFM of the current node's depositor
        int cmp = AFM - node.item.key();

        if (cmp < 0) {
            return searchByAFM(AFM, node.left);
        } 
        else if (cmp > 0) {
            return searchByAFM(AFM, node.right);
        } 
        else {
            return node.item;
        }
    }


    // Search for a depositor by last name
    @Override
    public StringDoubleEndedQueueImpl<LargeDepositor> searchByLastName(String last_name) {
        StringDoubleEndedQueueImpl<LargeDepositor> result = new StringDoubleEndedQueueImpl<>();
        searchByLastName(last_name, root, result);
        return result.size() > 0 ? result : null;
    }

    /**
     * Searches for depositors with a given last name in the binary search tree.
     * If depositors with the specified last name are found, they are added to the result queue.
     * 
     * @param last_name The last name of the depositors to search for.
     * @param node The current TreeNode being considered for searching.
     * @param result The queue to store the depositors with the specified last name.
     */
    private void searchByLastName(String last_name, TreeNode node, StringDoubleEndedQueueImpl<LargeDepositor> result) {
        if (node != null) {
            // Compare the specified last name with the last name of the current node's depositor
            int cmp = last_name.compareTo(node.item.lastName);

            if (cmp < 0) {
                searchByLastName(last_name, node.left, result);
            } 
            else if (cmp > 0) {
                searchByLastName(last_name, node.right, result);
            } 
            // If the specified last name matches the current node's depositor's last name,
            // add the current depositor to the result queue and recursively search for more depositors
            // with the same last name in the right subtree (as depositors are sorted by last name)
            else {
                result.addLast(node.item);
                searchByLastName(last_name, node.right, result);
            }
        }
    }


    // Remove depositor by AFM
    @Override
    public void remove(int AFM) {
        root = remove(AFM, root);
    }

    /**
     * Removes the depositor with the specified AFM (ΑΦΜ) from the binary search tree if found.
     * If the depositor is not found, prints a message indicating the absence of the depositor with the given AFM.
     * 
     * @param AFM The AFM of the depositor to be removed.
     * @param node The current TreeNode being considered for removal.
     * @return The updated TreeNode after removing the depositor, or null if the depositor with the specified AFM is not found.
     */
    private TreeNode remove(int AFM, TreeNode node) {
        if (node == null) {
            System.out.println("Depositor with AFM " + AFM + " not found.");
            return null;
        }
    
        // Calculate the comparison result between the specified AFM and the AFM of the current node's depositor
        int cmp = AFM - node.item.key();
    
        if (cmp < 0) {
            node.left = remove(AFM, node.left);
        } 
        else if (cmp > 0) {
            node.right = remove(AFM, node.right);
        } 
        else {
            // Join the left and right subtrees to create a new subtree without the current node
            node = join(node.left, node.right);
        }
    
        // If the node is not null, update the size of the node
        if (node != null) {
            // Update the size of the node based on the sizes of its left and right subtrees
            node.N = 1 + size(node.left) + size(node.right);
        }
    
        // Return the updated TreeNode after removing the depositor, or null if the depositor is not found
        return node;
    }


    /**
     * Joins two subtrees into a single tree.
     *
     * @param a The root of the first subtree.
     * @param b The root of the second subtree.
     * @return The root of the combined tree.
     */
    private TreeNode join(TreeNode a, TreeNode b) {
        // If one of the subtrees is empty, return the other subtree
        if (a == null) return b;
        if (b == null) return a;

        // Generate a random number between 0 and the total number of nodes in both subtrees
        // The probability of choosing a node from subtree 'a' is proportional to the size of subtree 'a'
        if (Math.random() * (a.N + b.N) < a.N) {
            // If the random number falls within the size of subtree 'a', attach 'b' to the right of 'a'
            // Recursively join the right subtree of 'a' with 'b'
            a.right = join(a.right, b);
            // Update the size of subtree 'a'
            a.N = 1 + size(a.left) + size(a.right);
            // Return 'a' as the new root of the combined tree
            return a;
        } else {
            // If the random number falls outside the size of subtree 'a', attach 'a' to the left of 'b'
            // Recursively join 'a' with the left subtree of 'b'
            b.left = join(a, b.left);
            // Update the size of subtree 'b'
            b.N = 1 + size(b.left) + size(b.right);
            // Return 'b' as the new root of the combined tree
            return b;
        }
}

    // Calculate mean savings of all depositors
    @Override
    public double getMeanSavings() {
        if (root == null) {
            return 0.0;
        }
        return getSumSavings(root) / root.N;
    }

    private double getSumSavings(TreeNode node) {
        if (node == null) {
            return 0.0;
        }
        return node.item.getSavings() + getSumSavings(node.left) + getSumSavings(node.right);
    }

    // Print top k large depositors
    @Override
    public void printTopLargeDepositors(int k) {
        printTopLargeDepositors(root, k);
        while (!topDepositorsQueue.isEmpty()) {
            LargeDepositor depositor = topDepositorsQueue.removeFirst();
            System.out.println(depositor);
        }
    }

    // Helper method
    private void printTopLargeDepositors(TreeNode node, int k) {
        if (node != null) {
            printTopLargeDepositors(node.right, k);

            if (topDepositorsQueue.size() < k) {
                topDepositorsQueue.addLast(node.item);
            } else {
                if (node.item.compareTo(topDepositorsQueue.getFirst()) > 0) {
                    topDepositorsQueue.removeFirst();
                    topDepositorsQueue.addLast(node.item);
                }
            }

            printTopLargeDepositors(node.left, k);
        }
    }

    // Print all depositors by AFM (sorted)
    @Override
    public void printByAFM() {
        printByAFM(root);
    }

    // Helper method
    private void printByAFM(TreeNode node) {
        if (node != null) {
            printByAFM(node.left);
            System.out.println(node.item);
            printByAFM(node.right);
        }
    }

    // Main Function
    public static void main(String[] args) {
        RandomizedBST bst = new RandomizedBST();
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("Menu:");
            System.out.println("1. Insert new large depositor");
            System.out.println("2. Load data from file");
            System.out.println("3. Update savings of an existing depositor");
            System.out.println("4. Search depositor by AFM");
            System.out.println("5. Search depositors by last name");
            System.out.println("6. Remove depositor by AFM");
            System.out.println("7. Calculate mean savings");
            System.out.println("8. Print top large depositors");
            System.out.println("9. Print all depositors sorted by AFM");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch(choice) {
                case 1:
                    // Insert new large depositor
                    LargeDepositor depositor = readFromUser(scanner);
                    bst.insert(depositor);
                    break;
                case 2:
                    // Load data from file
                    System.out.println("Enter filename: ");
                    String filename = scanner.nextLine();
                    bst.load(filename);
                    break;
                case 3:
                    // Update savings of an existing depositor
                    System.out.println("Enter AFM: ");
                    int afm = scanner.nextInt();
                    System.out.println("Enter new savings: ");
                    double newSavings = scanner.nextDouble();
                    bst.updateSavings(afm, newSavings);
                    break;
                case 4:
                    // Search depositor by AFM
                    System.out.println("Enter AFM: ");
                    int searchAFM = scanner.nextInt();
                    LargeDepositor resultByAFM = bst.searchByAFM(searchAFM);
                    if (resultByAFM != null) {
                        System.out.println(resultByAFM);
                    } else {
                        System.out.println("Depositor not found");
                    }
                    break;
                case 5:
                    // Search depositors by last name
                    System.out.println("Enter last name: ");
                    String lastName = scanner.nextLine();
                    StringDoubleEndedQueueImpl<LargeDepositor> resultList = bst.searchByLastName(lastName);
                    if (resultList != null) {
                        System.out.println("Depositors with last name " + lastName + ":");
                        resultList.printQueue(System.out);
                        break;
                    }
                case 6:
                    // Remove depositor by AFM
                    System.out.println("Enter AFM: ");
                    int removeAFM = scanner.nextInt();
                    bst.remove(removeAFM);
                    break;
                case 7:
                    // Calculate mean savings
                    double meanSavings = bst.getMeanSavings();
                    System.out.println("Mean Savings: " + meanSavings);
                    break;
                case 8:
                    // Print top k large depositors
                    System.out.println("Enter k for top depositors: ");
                    int k = scanner.nextInt();
                    bst.printTopLargeDepositors(k);
                    break;
                case 9:
                    // Print all depositors sorted by AFM
                    bst.printByAFM();
                    break;
                case 0:
                    // Exit the program
                    System.out.println("Exiting program");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

    }

    // Useful function in case the user inputs LargeDepositor data
    private static LargeDepositor readFromUser(Scanner scanner) {
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter AFM: ");
        int afm = scanner.nextInt();
        System.out.print("Enter savings: ");
        double savings = scanner.nextDouble();
        System.out.print("Enter taxed income: ");
        double taxedIncome = scanner.nextDouble();

        return new LargeDepositor(afm, firstName, lastName, savings, taxedIncome);
    }
}
