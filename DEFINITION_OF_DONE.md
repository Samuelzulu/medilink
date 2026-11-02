# Medilink Definition of Done (DoD)
A work item is considered Done and eligible for release only when all of the following criteria are satisfied:

1. [ ] Acceptance Criteria Verified: All acceptance criteria specified in the GitHub issue are met.
2. [ ] Peer Code Review: The pull request is reviewed and formally approved by at least one peer developer (self-merging is strictly prohibited).
3. [ ] Automated Testing: All unit and integration tests pass cleanly with zero errors or failures.
4. [ ] Verification Activity Executed: A non-author executes manual verification steps in a staging environment to confirm the bug/feature behaves correctly.
5. [ ] Regression & Reliability Check: core booking, cancellation, and notification workflow remain functional without side effects.
6. [ ] Documentation & Templates: Configuration, API schemas, and deployment instructions are updated where applicable.
7. [ ] Traceability: The pull request references the issue number (e.g., Closes #1), and the card is updated on the GitHub Project board.

