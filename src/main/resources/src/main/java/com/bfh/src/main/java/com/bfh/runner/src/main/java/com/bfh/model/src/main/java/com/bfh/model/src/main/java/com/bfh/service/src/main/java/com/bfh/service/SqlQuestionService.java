package com.bfh.service;

import org.springframework.stereotype.Service;

@Service
public class SqlQuestionService {

    /*
     * Set your regNo here (should match what you send in GenerateWebhookRequest).
     * The last two digits determine odd/even.
     */
    private final String regNo = "REG12347"; // <-- CHANGE THIS to your regNo

    /*
     * Put your solved SQL queries below:
     * - finalQueryIfOdd  : SQL that solves Question 1 (for odd regNo)
     * - finalQueryIfEven : SQL that solves Question 2 (for even regNo)
     *
     * After you solve the SQL problem from the PDF (links provided in the original task),
     * paste the final single SQL query string here.
     */
    private final String finalQueryIfOdd = "/* put final SQL for Question 1 here */";
    private final String finalQueryIfEven = "/* put final SQL for Question 2 here */";

    public String getFinalSqlQuery() {
        try {
            String digits = regNo.replaceAll("\\D+", "");
            if (digits.length() < 2) {
                // fallback: take regNo last two chars
                digits = regNo.substring(Math.max(0, regNo.length() - 2));
            } else {
                digits = digits.substring(digits.length() - 2);
            }

            int lastTwo = Integer.parseInt(digits);
            System.out.println("RegNo last two digits: " + lastTwo);
            if (lastTwo % 2 == 0) {
                System.out.println("Using EVEN final query.");
                return finalQueryIfEven;
            } else {
                System.out.println("Using ODD final query.");
                return finalQueryIfOdd;
            }
        } catch (Exception e) {
            System.err.println("Error while determining regNo parity: " + e.getMessage());
            e.printStackTrace();
            // Default to odd
            return finalQueryIfOdd;
        }
    }
}
