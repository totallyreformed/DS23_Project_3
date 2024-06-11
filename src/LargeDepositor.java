public class LargeDepositor implements Comparable<LargeDepositor> {
    public int AFM;
    public String firstName;
    public String lastName;
    public double savings;
    public double taxedIncome;

    // Constructor method
    public LargeDepositor(int AFM, String firstName, String lastName, double savings, double taxedIncome) {
        this.AFM = AFM;
        this.firstName = firstName;
        this.lastName = lastName;
        this.savings = savings;
        this.taxedIncome = taxedIncome;
    }

    // Getters and setters
    public int key() {
        return AFM;
    }

    public void setAFM(int AFM) {
        this.AFM = AFM;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getSavings() {
        return savings;
    }

    public void setSavings(double savings) {
        this.savings = savings;
    }

    public double getTaxedIncome() {
        return taxedIncome;
    }

    public void setTaxedIncome(double taxedIncome) {
        this.taxedIncome = taxedIncome;
    }

    @Override
    public int compareTo(LargeDepositor other) {
        /*
         * Returns NUM < 0 if current depositor less suspicious
         * Returns NUM > 0 if current depositor more suspicious
         * Returns NUM = 0 if depositors are identical
         */
        if (this.taxedIncome < 8000) {
            return Double.compare(Double.MAX_VALUE, other.taxedIncome < 8000 ? Double.MAX_VALUE : other.savings - other.taxedIncome);
        } else {
            return Double.compare(this.savings - this.taxedIncome, other.taxedIncome < 8000 ? Double.MAX_VALUE : other.savings - other.taxedIncome);
        }
    }

    @Override
    public String toString() {
        return "AFM: " + AFM + ", Name: " + firstName + " " + lastName + ", Savings: " + savings + ", Taxed Income: " + taxedIncome;
    }
}
