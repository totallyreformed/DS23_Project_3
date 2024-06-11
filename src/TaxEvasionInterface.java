public interface TaxEvasionInterface {

    /*
     * Insert new LargeDepositor node
     * Prints error message when AFM already in the tree
     * Randomized tree insert
     * 
     */
    void insert(LargeDepositor item);

    /*
     * Takes input file and updates tree usin insert method
     * 1 insert per input file line
     */
    void load(String filename);

    /*
     * Updates LargeDepositor savings (already exists in the tree)
     * If given AFM doesn't exist, print message
     */
    void updateSavings(int AFM, double savings);

    /*
     * Search LargeDepositor by AFM
     * Prints message accordingly if not found
     */
    LargeDepositor searchByAFM(int AFM);

    /*
     * Search LargeDepositor by last name
     * Prints list of max 5 subjects with respective details
     * Returns null if not found
     */
    StringDoubleEndedQueueImpl<LargeDepositor> searchByLastName(String last_name);

    /*
     * Removes item from tree based on AFM
     */
    void remove(int AFM);

    /*
     * Calculate Mean Savings for each item in the tree
     */
    double getMeanSavings();

    /*
     * Print details about the top k largest depositors
     * 
     * Uses the following criteria:
     * if (taxedIncome < 8000) sus++
     * savings - taxedIncome -> Means of comparing each depositor
     */
    void printTopLargeDepositors(int k);

    /*
     * Prints all elements of the tree based on AFM
     * Ascending order
     */
    void printByAFM();
}
